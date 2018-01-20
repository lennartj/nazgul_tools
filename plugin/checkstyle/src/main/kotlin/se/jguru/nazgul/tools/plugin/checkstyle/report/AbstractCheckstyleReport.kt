/*-
 * #%L
 * Nazgul Project: nazgul-tools-checkstyle-maven-plugin
 * %%
 * Copyright (C) 2010 - 2017 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *       http://www.jguru.se/licenses/jguruCorporateSourceLicense-2.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package se.jguru.nazgul.tools.plugin.checkstyle.report

import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.DefaultLogger
import com.puppycrawl.tools.checkstyle.XMLLogger
import com.puppycrawl.tools.checkstyle.api.AuditListener
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import com.puppycrawl.tools.checkstyle.api.SeverityLevel
import org.apache.maven.artifact.Artifact
import org.apache.maven.doxia.tools.SiteTool
import org.apache.maven.model.Plugin
import org.apache.maven.model.Resource
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugin.descriptor.PluginDescriptor
import org.apache.maven.plugins.annotations.Component
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.reporting.AbstractMavenReport
import org.apache.maven.reporting.MavenReportException
import org.codehaus.plexus.resource.ResourceManager
import org.codehaus.plexus.resource.loader.FileResourceLoader
import org.codehaus.plexus.util.PathTool
import org.codehaus.plexus.util.StringUtils
import se.jguru.nazgul.tools.plugin.checkstyle.CheckstyleResults
import se.jguru.nazgul.tools.plugin.checkstyle.integration.CheckstyleRunner
import se.jguru.nazgul.tools.plugin.checkstyle.integration.CheckstyleRunnerException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern

/**
 * [ResourceBundle.Control] implementation which reads [PropertyResourceBundle] files with UTF-8 encoding.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class Utf8ResourceBundleControl : ResourceBundle.Control() {

    // Internal state
    private var lastLocale : Locale? = null

    /**
     * Do not cache the values.
     */
    override fun getTimeToLive(baseName: String?, locale: Locale?): Long {
        return TTL_DONT_CACHE;
    }

    /**
     * Creates a new [ResourceBundle] which reads the data file assuming UTF-8 encoding.
     */
    @Throws(IllegalAccessException::class, InstantiationException::class, IOException::class)
    override fun newBundle(baseName: String,
                           locale: Locale,
                           format: String,
                           loader: ClassLoader,
                           reload: Boolean): ResourceBundle? {

        // Delegate to default implementation to find base data.
        val bundleName = toBundleName(baseName, locale)
        val resourceName = toResourceName(bundleName, "properties")

        // Open an URLConnection to the resource, or die trying.
        val conn = loader.getResource(resourceName).openConnection()
        if (reload) {
            conn.useCaches = false
        }

        // Ensure to AutoClose the InputStream
        conn.getInputStream().use {

            // ... and finally, use UTF-8 to read the stream.
            return PropertyResourceBundle(InputStreamReader(it, StandardCharsets.UTF_8))
        }
    }
}

/**
 * Abstract report implementation for Checkstyle reports.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
abstract class AbstractCheckstyleReport : AbstractMavenReport() {

    companion object {

        /**
         * Resource path to the Checkstyle plugin resources
         */
        const val PLUGIN_RESOURCES = "org/apache/maven/plugin/checkstyle"

        /**
         * [java.util.regex.Pattern] finding Java files.
         */
        const val JAVA_FILES = "**\\/*.java"

        /**
         * Retrieves the ResourceBundle for the Checkstyle Report.
         */
        private fun getBundle(locale: Locale): ResourceBundle {
            return ResourceBundle.getBundle("checkstyle-report",
                    locale,
                    AbstractCheckstyleReport::class.java.classLoader,
                    Utf8ResourceBundleControl())
        }
    }

    /**
     * The plexus-injected Doxia SiteTool implementation.
     */
    @Component(role = SiteTool::class)
    var siteTool: SiteTool? = null

    /**
     * Specifies the cache file used to speed up Checkstyle on successive runs.
     */
    @Parameter(defaultValue = "\${project.build.directory}/checkstyle-cachefile")
    var cacheFile: String? = null

    /**
     * Specifies the cache file used to speed up Checkstyle on successive runs.
     */
    @Parameter(defaultValue = "ERROR")
    var severityLevel: String? = null

    /**
     * Specifies the location of the XML configuration to use.
     *
     * Potential values are a filesystem path, a URL, or a classpath resource.
     * This parameter expects that the contents of the location conform to the
     * xml format (Checkstyle [Checker module](http://checkstyle.sourceforge.net/config.html#Modules))
     * configuration of rulesets.
     *
     * This parameter is resolved as resource, URL, then file. If successfully
     * resolved, the contents of the configuration is copied into the
     * [${project.build.directory}/checkstyle-configuration.xml]
     * file before being passed to Checkstyle as a configuration.
     *
     * There are 2 predefined rulesets included in Maven Checkstyle Plugin:
     * <ul>
     *     <li><strong>sun_checks.xml</strong>: Checks from SUN / Oracle.</li>
     *     <li><strong>google_checks.xml</strong>: Checkstyle from Google</li>
     * </ul>
     */
    @Parameter(property = "checkstyle.config.location", defaultValue = "sun_checks.xml")
    protected var configLocation: String? = null

    /**
     * Output errors to console.
     */
    @Parameter(property = "checkstyle.consoleOutput", defaultValue = "false")
    protected var consoleOutput: Boolean = false

    /**
     * The file encoding to use when reading the source files. If the property `project.build.sourceEncoding`
     * is not set, the platform default encoding is used.
     *
     * **Note:** This parameter always overrides the property `charset` from Checkstyle's `TreeWalker` module.
     */
    @Parameter(property = "encoding", defaultValue = "\${project.build.sourceEncoding}")
    protected var encoding: String? = null

    /**
     * Specifies if the build should fail upon a violation.
     */
    @Parameter(defaultValue = "false")
    protected var failsOnError: Boolean = false

    /**
     * Specifies the location of the License file (a.k.a. the header file) that
     * can be used by Checkstyle to verify that source code has the correct
     * license header.
     *
     * You need to use ${checkstyle.header.file} in your Checkstyle xml
     * configuration to reference the name of this header file.
     *
     * For instance:
     * ```
     * <module name="RegexpHeader">
     * <property name="headerFile" value="${checkstyle.header.file}"/>
     * </module>
     * ```
     */
    @Parameter(property = "checkstyle.header.file", defaultValue = "LICENSE.txt")
    protected var headerLocation: String? = null

    /**
     * Skip entire check.
     */
    @Parameter(property = "checkstyle.skip", defaultValue = "false")
    protected var skip: Boolean = false

    /**
     * Specifies the path and filename to save the Checkstyle output.
     * The format of the output file is determined by the `outputFileFormat` parameter.
     */
    @Parameter(property = "checkstyle.output.file", defaultValue = "\${project.build.directory}/checkstyle-result.xml")
    private val outputFile: File? = null

    /**
     * Specifies the location of the properties file.
     *
     * This parameter is resolved as URL, File then resource. If successfully
     * resolved, the contents of the properties location is copied into the
     * `${project.build.directory}/checkstyle-checker.properties`
     * file before being passed to Checkstyle for loading.
     *
     * The contents of the `propertiesLocation` will be made
     * available to Checkstyle for specifying values for parameters within the
     * xml configuration (specified in the `configLocation`
     * parameter).
     */
    @Parameter(property = "checkstyle.properties.location")
    protected var propertiesLocation: String? = null

    /**
     * Allows for specifying raw property expansion information.
     */
    @Parameter
    protected var propertyExpansion: String? = null

    /**
     * Specifies the location of the resources to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.resources}", readonly = true)
    protected var resources: List<Resource>? = null

    /**
     * Specifies the location of the test resources to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.testResources}", readonly = true)
    protected var testResources: List<Resource>? = null

    /**
     * Specifies the names filter of the source files to be used for Checkstyle.
     */
    @Parameter(property = "checkstyle.includes", defaultValue = JAVA_FILES, required = true)
    protected var includes: String? = null

    /**
     * Specifies the names filter of the source files to be excluded for Checkstyle.
     */
    @Parameter(property = "checkstyle.excludes")
    protected var excludes: String? = null

    /**
     * Specifies the names filter of the resource files to be used for Checkstyle.
     */
    @Parameter(property = "checkstyle.resourceIncludes", defaultValue = "**/*.properties", required = true)
    protected var resourceIncludes: String? = null

    /**
     * Specifies the names filter of the resource files to be excluded for Checkstyle.
     */
    @Parameter(property = "checkstyle.resourceExcludes")
    protected var resourceExcludes: String? = null

    /**
     * Specifies whether to include the resource directories in the check.
     */
    @Parameter(property = "checkstyle.includeResources", defaultValue = "true", required = true)
    protected var includeResources: Boolean = false

    /**
     * Specifies whether to include the test resource directories in the check.
     */
    @Parameter(property = "checkstyle.includeTestResources", defaultValue = "true", required = true)
    protected var includeTestResources: Boolean = false

    /**
     * Specifies the location of the source directories to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.compileSourceRoots}")
    private val sourceDirectories: List<String>? = null

    /**
     * Specifies the location of the test source directories to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.testCompileSourceRoots}")
    private val testSourceDirectories: List<String>? = null

    /**
     * Include or not the test source directory/directories to be used for Checkstyle.
     */
    @Parameter(defaultValue = "false")
    protected var includeTestSourceDirectory: Boolean = false

    /**
     * The key to be used in the properties for the suppressions file.
     */
    @Parameter(property = "checkstyle.suppression.expression", defaultValue = "checkstyle.suppressions.file")
    protected var suppressionsFileExpression: String? = null

    /**
     * Specifies the location of the suppressions XML file to use.
     *
     * This parameter is resolved as resource, URL, then file. If successfully
     * resolved, the contents of the suppressions XML is copied into the
     * `${project.build.directory}/checkstyle-supressions.xml` file
     * before being passed to Checkstyle for loading.
     *
     * See `suppressionsFileExpression` for the property that will
     * be made available to your Checkstyle configuration.
     */
    @Parameter(property = "checkstyle.suppressions.location")
    protected var suppressionsLocation: String? = null

    /**
     * If `null`, the Checkstyle plugin will display violations on stdout.
     * Otherwise, a text file will be created with the violations.
     */
    @Parameter
    private val useFile: File? = null

    /**
     * Specifies the format of the output to be used when writing to the output
     * file. Valid values are "`plain`" and "`xml`".
     */
    @Parameter(property = "checkstyle.output.format", defaultValue = "xml")
    private val outputFileFormat: String? = null

    /**
     * Specifies if the Rules summary should be enabled or not.
     */
    @Parameter(property = "checkstyle.enable.rules.summary", defaultValue = "true")
    private val enableRulesSummary: Boolean = false

    /**
     * Specifies if the Severity summary should be enabled or not.
     */
    @Parameter(property = "checkstyle.enable.severity.summary", defaultValue = "true")
    private val enableSeveritySummary: Boolean = false

    /**
     * Specifies if the Files summary should be enabled or not.
     */
    @Parameter(property = "checkstyle.enable.files.summary", defaultValue = "true")
    private val enableFilesSummary: Boolean = false

    /**
     * Specifies if the RSS should be enabled or not.
     */
    @Parameter(property = "checkstyle.enable.rss", defaultValue = "true")
    private val enableRSS: Boolean = false

    /**
     * The Plugin Descriptor
     */
    @Parameter(defaultValue = "\${plugin}", readonly = true, required = true)
    private var plugin: PluginDescriptor? = null

    /**
     * Link the violation line numbers to the source xref. Will link
     * automatically if Maven JXR plugin is being used.
     *
     * @since 2.1
     */
    @Parameter(property = "linkXRef", defaultValue = "true")
    private val linkXRef: Boolean = false

    /**
     * Location of the Xrefs to link to.
     */
    @Parameter(defaultValue = "\${project.reporting.outputDirectory}/xref")
    private val xrefLocation: File? = null

    /**
     * When using custom treeWalkers, specify their names here so the checks
     * inside the treeWalker end up the the rule-summary.
     *
     * @since 2.11
     */
    @Parameter
    private val treeWalkerNames: List<String>? = null

    /**
     */
    @Component
    protected var locator: ResourceManager? = null

    /**
     * CheckstyleRssGenerator.
     *
     * @since 2.4
     */
    // @Component(role = CheckstyleRssGenerator::class, hint = "default")
    // protected var checkstyleRssGenerator: CheckstyleRssGenerator? = null

    /**
     * @since 2.5
     */
    @Component(role = CheckstyleRunner::class, hint = "default")
    protected var checkstyleExecutor: CheckstyleRunner? = null

    protected lateinit var stringOutputStream: ByteArrayOutputStream

    /**
     * Creates and returns the report generation listener.
     *
     * @return The audit listener.
     * @throws MavenReportException If something goes wrong.
     */
    protected// TODO: failure if not a report
    val listener: AuditListener?
        @Throws(MavenReportException::class)
        get() {
            var listener: AuditListener? = null

            if (StringUtils.isNotEmpty(outputFileFormat)) {
                val resultFile = outputFile

                val out = getOutputStream(resultFile!!)

                if ("xml" == outputFileFormat) {
                    listener = XMLLogger(out, true)
                } else if ("plain" == outputFileFormat) {
                    listener = DefaultLogger(out, true)
                } else {
                    throw MavenReportException("Invalid output file format: (" + outputFileFormat
                            + "). Must be 'plain' or 'xml'.")
                }
            }

            return listener
        }

    /**
     * Creates and returns the console listener.
     *
     * @return The console listener.
     * @throws MavenReportException If something goes wrong.
     */
    protected val consoleListener: DefaultLogger
        @Throws(MavenReportException::class)
        get() {
            val consoleListener: DefaultLogger

            if (useFile == null) {
                stringOutputStream = ByteArrayOutputStream()
                consoleListener = DefaultLogger(stringOutputStream, false)
            } else {
                val out = getOutputStream(useFile)

                consoleListener = DefaultLogger(out, true)
            }

            return consoleListener
        }


    private val copyright: String
        get() {
            var copyright: String
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            if (StringUtils.isNotEmpty(project.inceptionYear) && currentYear.toString() != project.inceptionYear) {
                copyright = project.inceptionYear + " - " + currentYear
            } else {
                copyright = currentYear.toString()
            }

            if (project.organization != null && StringUtils.isNotEmpty(project.organization.name)) {
                copyright = copyright + " " + project.organization.name
            }
            return copyright
        }

    /**
     * {@inheritDoc}
     */
    override fun getName(locale: Locale): String {
        return getBundle(locale).getString("report.checkstyle.name")
    }

    /**
     * {@inheritDoc}
     */
    override fun getDescription(locale: Locale): String {
        return getBundle(locale).getString("report.checkstyle.description")
    }

    /**
     * {@inheritDoc}
     */
    @Throws(MavenReportException::class)
    public override fun executeReport(locale: Locale) {
        locator!!.addSearchPath(FileResourceLoader.ID, project.file.parentFile.absolutePath)
        locator!!.addSearchPath("url", "")

        locator!!.setOutputDirectory(File(project.build.directory))

        // for when we start using maven-shared-io and maven-shared-monitor...
        // locator = new Locator( new MojoLogMonitorAdaptor( getLog() ) );

        // locator = new Locator( getLog(), new File( project.getBuild().getDirectory() ) );

        val currentClassLoader = Thread.currentThread().contextClassLoader

        try {
            val request = createRequest()
                    .setLicenseArtifacts(collectArtifacts("license"))
                    .setConfigurationArtifacts(collectArtifacts("configuration"))

            val results = checkstyleExecutor!!.executeCheckstyle(request)

            val bundle = getBundle(locale)
            generateReportStatics()
            generateMainReport(results, bundle)

            /*
            if (enableRSS) {
                val checkstyleRssGeneratorRequest = CheckstyleRssGeneratorRequest(this.project, this.copyright, outputDirectory, log)
                checkstyleRssGenerator!!.generateRSS(results, checkstyleRssGeneratorRequest)
            }
            */

        } catch (e: CheckstyleException) {
            throw MavenReportException("Failed during checkstyle configuration", e)
        } catch (e: CheckstyleRunnerException) {
            throw MavenReportException("Failed during checkstyle execution", e)
        } finally {
            //be sure to restore original context classloader
            Thread.currentThread().contextClassLoader = currentClassLoader
        }
    }

    /**
     * Create the Checkstyle executor request.
     *
     * @return The executor request.
     * @throws MavenReportException If something goes wrong during creation.
     */
    // @Throws(MavenReportException::class)
    // protected abstract fun createRequest(): CheckstyleExecutorRequest

    private fun collectArtifacts(hint: String): List<Artifact> {
        if (plugin == null || plugin!!.groupId == null) {
            // Maven 2.x workaround
            plugin = mojoExecution!!.mojoDescriptor.pluginDescriptor
        }

        val artifacts = ArrayList<Artifact>()

        val pluginManagement = project.build.pluginManagement
        if (pluginManagement != null) {
            artifacts.addAll(getCheckstylePluginDependenciesAsArtifacts(pluginManagement.pluginsAsMap, hint))
        }

        artifacts.addAll(getCheckstylePluginDependenciesAsArtifacts(project.build.pluginsAsMap, hint))

        return artifacts
    }

    private fun getCheckstylePluginDependenciesAsArtifacts(plugins: Map<String, Plugin>,
                                                           hint: String): List<Artifact> {
        val artifacts = ArrayList<Artifact>()

        val checkstylePlugin = plugins[plugin!!.groupId + ":" + plugin!!.artifactId]
        if (checkstylePlugin != null) {
            for (dep in checkstylePlugin.dependencies) {
                // @todo if we can filter on hints, it should be done here...
                val depKey = dep.groupId + ":" + dep.artifactId
                val artifact = plugin!!.artifactMap[depKey] as Artifact

                if (artifact != null) {
                    artifacts.add(artifact)
                } else if (log.isDebugEnabled) {
                    log.debug("Got null CheckstylePlugin Dependency Artifact from key ["
                            + depKey + "]. Excluding null Artifact from return List [" + hint + "]. ")
                }
            }
        }
        return artifacts
    }

    @Throws(MavenReportException::class)
    private fun getOutputStream(file: File): OutputStream {
        val parentFile = file.absoluteFile.parentFile

        if (!parentFile.exists()) {
            parentFile.mkdirs()
        }

        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            throw MavenReportException("Unable to create output stream: " + file, e)
        }

        return fileOutputStream
    }

    @Throws(MavenReportException::class)
    private fun generateReportStatics() {
        val rresource = ReportResource(PLUGIN_RESOURCES, outputDirectory)
        try {
            rresource.copy("images/rss.png")
        } catch (e: IOException) {
            throw MavenReportException("Unable to copy static resources.", e)
        }
    }

    /**
     * Create the Checkstyle executor request.
     *
     * @return The executor request.
     * @throws MavenReportException If something goes wrong during creation.
     */
    @Throws(MavenReportException::class)
    protected abstract fun createRequest()

    private fun generateMainReport(results: CheckstyleResults,
                                   bundle: ResourceBundle) {

        // Find the severityLevel as configured
        val severityLevel: SeverityLevel? = if (this.severityLevel == null) {
            null
        } else {
            try {
                SeverityLevel.valueOf(this.severityLevel!!)
            } catch (e: Exception) {

                throw MojoFailureException("Could not parse '$severityLevel' into a SeverityLevel. "
                        + "Valid values: " + SeverityLevel.values().map { it.name }.reduce { l, r -> l + ", " + r })
            }
        }

        val doxiaSinkHelper = DoxiaSinkHelper(sink,
                bundle,
                DefaultConfiguration("checkstyleConfiguration"),
                project.basedir,
                siteTool!!,
                treeWalkerNames)

        val generator = CheckstyleReportCreator(
                log,
                doxiaSinkHelper,
                enableSeveritySummary,
                enableRulesSummary,
                enableFilesSummary,
                severityLevel,
                xrefLocation,
                enableRSS,
                DefaultConfiguration("checkstyleConfiguration"))

        if (linkXRef) {

            var relativePath = PathTool.getRelativePath(getOutputDirectory(), xrefLocation!!.absolutePath)
            if (StringUtils.isEmpty(relativePath)) {
                relativePath = "."
            }
            /*
            relativePath = relativePath + "/" + xrefLocation.name
            if (xrefLocation.exists()) {
                // XRef was already generated by manual execution of a lifecycle
                // binding
                generator.xrefLocation = relativePath

            } else {
                // Not yet generated - check if the report is on its way
                for (report in getProject().reportPlugins as Iterable<ReportPlugin>) {
                    val artifactId = report.artifactId
                    if ("maven-jxr-plugin" == artifactId || "jxr-maven-plugin" == artifactId) {
                        generator.xrefLocation = relativePath
                    }
                }
            }
            */

            if (generator.xrefLocation == null && results.fileCount > 0) {
                log.warn("Unable to locate Source XRef to link to - DISABLED")
            }
        }

        generator.generateReport(results, "standard")
    }
}
