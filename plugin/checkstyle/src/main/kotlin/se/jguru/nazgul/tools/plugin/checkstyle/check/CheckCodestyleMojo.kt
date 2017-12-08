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
package se.jguru.nazgul.tools.plugin.checkstyle.check

import com.puppycrawl.tools.checkstyle.DefaultLogger
import com.puppycrawl.tools.checkstyle.XMLLogger
import com.puppycrawl.tools.checkstyle.api.AuditListener
import com.puppycrawl.tools.checkstyle.api.AutomaticBean
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import org.apache.maven.model.Resource
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.Component
import org.apache.maven.plugins.annotations.Execute
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.plugins.annotations.ResolutionScope
import org.apache.maven.project.MavenProject
import org.codehaus.plexus.configuration.PlexusConfiguration
import org.codehaus.plexus.util.FileUtils
import org.codehaus.plexus.util.IOUtil
import org.codehaus.plexus.util.PathTool
import org.codehaus.plexus.util.ReaderFactory
import org.codehaus.plexus.util.xml.pull.MXParser
import org.codehaus.plexus.util.xml.pull.XmlPullParser
import org.codehaus.plexus.util.xml.pull.XmlPullParserException
import se.jguru.nazgul.tools.plugin.checkstyle.CheckstyleRule
import se.jguru.nazgul.tools.plugin.checkstyle.Matcher
import se.jguru.nazgul.tools.plugin.checkstyle.Scope
import se.jguru.nazgul.tools.plugin.checkstyle.Severity
import se.jguru.nazgul.tools.plugin.checkstyle.integration.CheckstyleParametersBuilder
import se.jguru.nazgul.tools.plugin.checkstyle.integration.CheckstyleRunner
import se.jguru.nazgul.tools.plugin.checkstyle.integration.CheckstyleRunnerException
import se.jguru.nazgul.tools.plugin.checkstyle.report.ReportFormat
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.Reader

/**
 * Maven Mojo implementation to integrate Checkstyle into its build.
 * This Mojo only requires XML reports by checkstyle.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@Mojo(name = "check",
        defaultPhase = LifecyclePhase.VERIFY,
        requiresDependencyResolution = ResolutionScope.TEST,
        threadSafe = true)
@Execute(goal = "checkstyle")
class CheckCodestyleMojo : AbstractMojo() {

    /**
     * The Maven Project Object.
     */
    @Parameter(defaultValue = "\${project}", readonly = true, required = true)
    protected var project: MavenProject? = null

    /**
     * Specifies the names filter of the source files to be excluded for Checkstyle.
     */
    @Parameter(property = "checkstyle.excludes")
    private val excludes: List<String>? = null

    /**
     * Specifies the names filter of the source files to be included for Checkstyle.
     */
    @Parameter(property = "checkstyle.includes")
    private val includes: List<String>? = null

    /**
     * Specifies the path and filename to save the Checkstyle output.
     * The format of the output file is determined by the `outputFileFormat` parameter.
     */
    @Parameter(
            property = "checkstyle.output.file",
            defaultValue = "\${project.build.directory}/checkstyle-result.xml")
    private var outputFile: File? = null

    /**
     * Specifies the format of the output to be used when writing to the output
     * file. Valid values are "PLAIN" and "XML".
     */
    @Parameter(property = "checkstyle.output.format", defaultValue = "PLAIN")
    private val outputFormat: ReportFormat? = null

    /**
     * Fail the build on a violation. The goal checks for the violations
     * after logging them (if [.logViolationsToConsole] is `true`).
     * Compare this to [.failsOnError] which fails the build immediately
     * before examining the output log.
     */
    @Parameter(property = "checkstyle.failOnViolation", defaultValue = "true")
    private val failOnViolation: Boolean = false

    /**
     * The maximum number of allowed violations.
     * The execution fails only if the number of violations is above this limit.
     */
    @Parameter(property = "checkstyle.maxAllowedViolations", defaultValue = "0")
    private val maxAllowedViolations: Int = 0

    /**
     * The lowest severity level that is considered a violation.
     * Valid values are "`error`", "`warning`" and "`info`".
     */
    @Parameter(property = "checkstyle.violationSeverity", defaultValue = "ERROR")
    private val ignoreViolationsWithSeverityLessThan: Severity? = null

    /**
     * <p>Comma-separated list of Checkstyle rules to ignore.
     * Each value within the supplied List should be one of the following...</p>
     * <ol>
     *     <li>A Checkstyle rule name, such as "HeaderCheck"</li>
     *     <li>A Checkstyle rule category, such as "... ?"</li>
     *     <li>A Package name of rule class(es), such as "com.puppycrawl.tools.checkstyle.checks.header"</li>
     * </ol>
     *
     * @see RuleUtil#parseMatchers
     */
    @Parameter(property = "checkstyle.violation.ignoreFrom")
    private val ignoreViolationsFrom: String? = null

    /**
     * Skip entire check.
     */
    @Parameter(property = "checkstyle.skip", defaultValue = "false")
    private val skip: Boolean = false

    /**
     * Skip Checkstyle execution will only scan the outputFile.
     */
    @Parameter(property = "checkstyle.skipExec", defaultValue = "false")
    private val skipExec: Boolean = false

    /**
     * List containing Maven Resources to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.resources}", readonly = true)
    protected var resources: List<Resource>? = null

    /**
     * Specifies the location of the test resources to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.testResources}", readonly = true)
    protected var testResources: List<Resource>? = null

    /**
     * Specifies the location of the source directories to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.compileSourceRoots}")
    protected var sourceDirectories: List<String>? = null

    /**
     * Specifies the location of the source directories to be used for Checkstyle.
     */
    @Parameter(defaultValue = "\${project.testSourceRoots}")
    protected var testSourceDirectories: List<String>? = null

    /**
     * Specifies the location of the XML configuration to use.
     *
     * Potential values are a filesystem path, a URL, or a classpath resource.
     * This parameter expects that the contents of the location conform to the
     * xml format (Checkstyle [Checker module](http://checkstyle.sourceforge.net/config.html#Modules)) configuration
     * of rulesets.
     *
     * This parameter is resolved as resource, URL, then file. If successfully
     * resolved, the contents of the configuration is copied into the
     * `${project.build.directory}/checkstyle-configuration.xml`
     * file before being passed to Checkstyle as a configuration.
     *
     * There are 2 predefined rulesets.
     *  * `sun_checks.xml`: Sun Checks.
     *  * `google_checks.xml`: Google Checks.
     */
    @Parameter(property = "checkstyle.config.location", defaultValue = "sun_checks.xml")
    private var configLocation: String? = null

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
    private val propertiesLocation: String? = null

    /**
     * Allows for specifying raw property expansion information.
     */
    @Parameter
    private val propertyExpansion: String? = null

    /**
     * Specifies the location of the License file (a.k.a. the header file) that
     * can be used by Checkstyle to verify that source code has the correct
     * license header.
     *
     * You need to use ${checkstyle.header.file} in your Checkstyle xml
     * configuration to reference the name of this header file.
     * For instance:
     *
     * <module name="RegexpHeader">
     * <property name="headerFile" value="${checkstyle.header.file}"/>
     * </module>
     */
    @Parameter(property = "checkstyle.header.file", defaultValue = "LICENSE.txt")
    private val headerLocation: String? = null

    /**
     * Specifies the cache file used to speed up Checkstyle on successive runs.
     */
    @Parameter(defaultValue = "\${project.build.directory}/checkstyle-cachefile")
    private val cacheFile: String? = null

    /**
     * The key to be used in the properties for the suppressions file.
     */
    @Parameter(property = "checkstyle.suppression.expression", defaultValue = "checkstyle.suppressions.file")
    private val suppressionsFileExpression: String? = null

    /**
     * Specifies the location of the suppressions XML file to use.
     *
     * This parameter is resolved as resource, URL, then file. If successfully
     * resolved, the contents of the suppressions XML is copied into the
     * `${project.build.directory}/checkstyle-suppressions.xml` file
     * before being passed to Checkstyle for loading.
     *
     * See `suppressionsFileExpression` for the property that will
     * be made available to your Checkstyle configuration.
     *
     * @since 2.0-beta-2
     */
    @Parameter(property = "checkstyle.suppressions.location")
    private val suppressionsLocation: String? = null

    /**
     * The file encoding to use when reading the source files. If the property `project.build.sourceEncoding`
     * is not set, the platform default encoding is used. **Note:** This parameter always overrides the
     * property `charset` from Checkstyle's `TreeWalker` module.
     *
     * @since 2.2
     */
    @Parameter(property = "encoding", defaultValue = "\${project.build.sourceEncoding}")
    private val encoding: String? = null

    /**
     * By using this property, you can specify the whole Checkstyle rules
     * inline directly inside this pom. This feature requires Maven 3+.
     *
     *
     * <pre>
     * &lt;plugin&gt;
     * ...
     * &lt;configuration&gt;
     * &lt;checkstyleRules&gt;
     * &lt;module name="Checker"&gt;
     * &lt;module name="FileTabCharacter"&gt;
     * &lt;property name="eachLine" value="true" /&gt;
     * &lt;/module&gt;
     * &lt;module name="TreeWalker"&gt;
     * &lt;module name="EmptyBlock"/&gt;
     * &lt;/module&gt;
     * &lt;/module&gt;
     * &lt;/checkstyleRules&gt;
     * &lt;/configuration&gt;
     * ...
     * </pre>
     */
    @Parameter
    private val checkstyleRules: PlexusConfiguration? = null

    /**
     * @since 2.5
     */
    @Component(role = CheckstyleRunner::class, hint = "default")
    private var checkstyleRunner: CheckstyleRunner? = null

    /**
     * Tmp output file for inlined Checkstyle rules
     */
    @Parameter(property = "checkstyle.output.rules.file",
            defaultValue = "\${project.build.directory}/checkstyle-rules.xml")
    private val rulesFiles: File? = null

    /**
     * If `null`, the Checkstyle plugin will display violations on stdout.
     * Otherwise, a text file will be created with the violations.
     */
    @Parameter
    private val useFile: File? = null

    /**
     * If this is true, and Checkstyle reported any violations or errors,
     * the build fails immediately after running Checkstyle, before checking the log
     * for [.logViolationsToConsole]. If you want to use [.logViolationsToConsole],
     * use [.failOnViolation] instead of this.
     */
    @Parameter(defaultValue = "false")
    private val failsOnError: Boolean = false

    /**
     * Output errors to console.
     */
    @Parameter(property = "checkstyle.consoleOutput", defaultValue = "false")
    private val consoleOutput: Boolean = false


    companion object {

        /**
         * Message prefix for detected configuration errors.
         */
        val CONFIG_ERROR_PREFIX = "Configuration error detected: "

        /**
         * Filter defining Java files
         */
        val JAVA_FILES = "**\\/*.java"

        /**
         * The header lines of a Checkstyle file.
         */
        val CHECKSTYLE_FILE_HEADER = ("<?xml version=\"1.0\"?>\n"
                + "<!DOCTYPE module PUBLIC \"-//Puppy Crawl//DTD Check Configuration 1.3//EN\"\n"
                + "        \"http://www.puppycrawl.com/dtds/configuration_1_3.dtd\">\n")

    }

    // Internal state
    private var checkstyleOutputStream: OutputStream? = null

    /**
     * Mojo main execution method, which invokes the Checkstyle engine.
     */
    @Throws(MojoExecutionException::class, MojoFailureException::class)
    override fun execute() {

        // #0) Skip processing?
        //
        if (skip) {
            return
        }

        // #1) Read mandatory configuration.
        //
        val violationSeverity = ignoreViolationsWithSeverityLessThan ?: Severity.ERROR;

        // #2) Skip re-executing (i.e. re-use existing outputFile)?
        //
        if (!skipExec) {

            if (log.isDebugEnabled) {
                log.debug("Using violationSeverity '$violationSeverity'")
            }

            if (checkstyleRules != null) {

                val inlineCSRules: PlexusConfiguration = checkstyleRules

                if ("sun_checks.xml" != configLocation) {
                    throw MojoExecutionException(CONFIG_ERROR_PREFIX
                            + "Both inline rule configuration and configLocation specified.")
                }
                if (inlineCSRules.childCount > 1) {
                    throw MojoExecutionException(CONFIG_ERROR_PREFIX
                            + "Currently only one root module is supported")
                }

                val checkerModule = inlineCSRules.getChild(0)

                when {
                    rulesFiles != null -> {
                        try {
                            FileUtils.forceMkdir(rulesFiles.parentFile)
                            FileUtils.fileWrite(rulesFiles, CHECKSTYLE_FILE_HEADER + checkerModule.toString())
                        } catch (e: IOException) {
                            throw MojoExecutionException(e.message, e)
                        }

                        configLocation = rulesFiles.absolutePath
                    }
                }
            }

            val currentClassLoader = Thread.currentThread().contextClassLoader

            try {

                val builder = CheckstyleParametersBuilder()
                builder
                        .withConsoleLogger(DefaultLogger(checkstyleOutputStream, AutomaticBean.OutputStreamOptions.CLOSE))
                        .withConsoleOutput(consoleOutput)
                        .withIncludes(includes)
                        .withExcludes(excludes)
                        .withDirectories(Scope.COMPILE, resolveDirectories(Scope.COMPILE))
                        .withDirectories(Scope.TEST, resolveDirectories(Scope.TEST))
                        .withResources(Scope.COMPILE, resources)
                        .withResources(Scope.TEST, testResources)
                        .withProject(project)
                        .withFailsOnError(failsOnError)
                        .withAuditListener(getListener(outputFormat))
                        .withEncoding(encoding)
                        .withSuppressionsLocation(suppressionsLocation)


                /*
                val request = CheckstyleExecutorRequest()
                request.setResourceIncludes(resourceIncludes)
                        .setResourceExcludes(resourceExcludes)
                        .setIncludeResources(includeResources)
                        .setIncludeTestResources(includeTestResources)
                        .setIncludeTestSourceDirectory(includeTestSourceDirectory)
                        .setStringOutputStream(stringOutputStream)
                        .setConfigLocation(configLocation)
                        .setConfigurationArtifacts(collectArtifacts("config"))
                        .setPropertyExpansion(propertyExpansion)
                        .setHeaderLocation(headerLocation)
                        .setLicenseArtifacts(collectArtifacts("license"))
                        .setCacheFile(cacheFile)
                        .setSuppressionsFileExpression(suppressionsFileExpression)
                        .propertiesLocation = propertiesLocation
                        */

                // Delegate the execution to Checkstyle.
                checkstyleRunner?.executeCheckstyle(builder.build())

            } catch (e: CheckstyleException) {
                throw MojoExecutionException("Failed during checkstyle configuration", e)
            } catch (e: CheckstyleRunnerException) {
                throw MojoExecutionException("Failed during checkstyle execution", e)
            } finally {
                //be sure to restore original context classloader
                Thread.currentThread().contextClassLoader = currentClassLoader
            }
        }

        if (ReportFormat.XML != outputFormat) {
            throw MojoExecutionException("Output format is '" + outputFormat
                    + "', checkstyle:check requires format to be 'xml'.")
        }

        // Handle the output file.
        val outputFileExists = outputFile?.exists() ?: false
        val outputFilePath = outputFile?.absolutePath ?: "<none>"
        if (!outputFileExists) {
            log.info("Unable to perform checkstyle:check, unable to find checkstyle:checkstyle outputFile.")
            return
        }

        var reader: Reader? = null
        try {

            reader = BufferedReader(ReaderFactory.newXmlReader(outputFile))

            val xpp = MXParser()
            xpp.setInput(reader)

            // Handle violation logging
            val violations = countViolations(xpp, violationSeverity)

            val violationWord = when (violations > 1) {
                true -> "violations"
                false -> "violation"
            }

            val abortWithError = violations > maxAllowedViolations && failsOnError
            val warnButDontAbort = violations > maxAllowedViolations && !failOnViolation

            if (abortWithError) {

                var msg = "You have $violations Checkstyle $violationWord."
                if (maxAllowedViolations > 0) {
                    msg += " The maximum number of allowed violations is $maxAllowedViolations."
                }
                throw MojoFailureException(msg)

            } else if (warnButDontAbort) {

                log.warn("$violations checkstyle:check $violationWord detected but failOnViolation set to false")
            }

            reader.close()
            reader = null

        } catch (e: IOException) {
            throw MojoExecutionException("Unable to read Checkstyle results xml: " + outputFilePath, e)
        } catch (e: XmlPullParserException) {
            throw MojoExecutionException("Unable to read Checkstyle results xml: " + outputFilePath, e)
        } finally {
            IOUtil.close(reader)
        }
    }

    //
    // Private helpers
    //

    private fun toSortedDirList(dirs: List<File>): String {

        return dirs.map { it -> it.absolutePath }
                .sortedBy { it -> it }
                .reduce { l, r -> l + ", " + r }
    }

    private fun resolveDirectories(scope: Scope): List<File> {

        // Ensure we do not operate on insane arguments.
        val toResolve: List<String> = when (scope) {
            Scope.COMPILE -> this.sourceDirectories
            Scope.TEST -> this.testSourceDirectories
        } ?: listOf()

        // Resolve all directories, and copy them into the respective Collection.
        val toReturn = mutableListOf<File>()
        val invalidDirs = mutableListOf<File>()

        toResolve.map { it -> FileUtils.resolveFile(project!!.basedir, it) }
                .filter { it -> it.isDirectory }
                .forEach { it -> toReturn.add(it) }

        toResolve.map { it -> FileUtils.resolveFile(project!!.basedir, it) }
                .filter { it -> !it.isDirectory }
                .forEach { it -> invalidDirs.add(it) }

        // Log some.
        val scopeDescription = scope.name.toLowerCase()

        if (invalidDirs.isNotEmpty()) {

            log.warn("Found " + invalidDirs.size + " invalid " + scopeDescription
                    + " scope directories. Ignoring: " + toSortedDirList(invalidDirs))
        }

        if (log.isDebugEnabled) {

            log.debug("Found " + toReturn.size + " valid " + scopeDescription
                    + " scope directories. Using: " + toSortedDirList(toReturn))
        }

        // All Done.
        return toReturn
    }

    fun getLogger(): DefaultLogger {

        checkstyleOutputStream = if (useFile == null) {
            ByteArrayOutputStream()
        } else {
            getOutputStream(useFile)
        }

        return DefaultLogger(checkstyleOutputStream, AutomaticBean.OutputStreamOptions.CLOSE)
    }

    @Throws(MojoExecutionException::class)
    private fun getOutputStream(file: File): OutputStream {

        val parentFile = file.absoluteFile.parentFile

        if (!parentFile.exists()) {
            parentFile.mkdirs()
        }

        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            throw MojoExecutionException("Unable to create output stream: " + file, e)
        }

        return fileOutputStream
    }

    private fun getListener(outputFormat: ReportFormat?): AuditListener = when (outputFormat) {
        ReportFormat.XML -> XMLLogger(checkstyleOutputStream, AutomaticBean.OutputStreamOptions.CLOSE)
        else -> DefaultLogger(checkstyleOutputStream, AutomaticBean.OutputStreamOptions.CLOSE)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun countViolations(xpp: XmlPullParser, configuredViolationSeverity: Severity): Int {

        val basedir = project?.basedir?.absolutePath
        var file = ""

        var count = 0
        var ignoreCount = 0
        val ignores = when (ignoreViolationsFrom) {
            null -> null
            else -> null // TODO: This.
        /*RuleUtil.parseMatchers(ignoreViolationsFrom
            .split(",".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()) */
        }

        var eventType = xpp.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {

            when {

            // Start a tag
            //
                eventType != XmlPullParser.START_TAG -> {
                    eventType = xpp.next()
                    // continue // TODO: Provide the equivalence of this
                }

            // The container element is named "file", such as:
            // <file name="/full/path/to/SomeFile.java">
            //      ....
            // </file>
            //
                xpp.name == "file" -> {

                    file = PathTool.getRelativeFilePath(
                            basedir,
                            xpp.getAttributeValue(XmlPullParser.NO_NAMESPACE, "name"))

                    if (log.isDebugEnabled) {
                        log.debug("Found file ('name' attribute): " + file)
                    }
                }

            // The child "error" element contains checkstyle violation. It has the form:
            //
            // <error
            //      line="82"
            //      severity="warning"
            //      message="Line is longer than 120 characters (found 133)."
            //      source="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck"/>
            //
            //
                xpp.name == "error" -> {

                    // Does this error line represent a violation?
                    val currentSeverityValue = xpp.getAttributeValue("", "severity")
                    val currentSeverity = Severity.parse(currentSeverityValue)

                    if (configuredViolationSeverity.isViolation(currentSeverity)) {

                        // Should we ignore or count the violation within the current source file?
                        val source = xpp.getAttributeValue("", "source")

                        if (Matcher.ignore(ignores, source)) {
                            ignoreCount++
                        } else {
                            count++

                            if (consoleOutput) {

                                val line = xpp.getAttributeValue("", "line")
                                val column = xpp.getAttributeValue("", "column")
                                val message = xpp.getAttributeValue("", "message")
                                val rule = CheckstyleRule.getCategory(source)// RuleUtil.getName(source)
                                val category = CheckstyleRule.getCategory(source)

                                log.info(file + ":[" + line + (if (column == null) "" else ',' + column)
                                        + "] (" + category + ") " + rule + ": " + message)
                            }
                        }
                    }

                    /*val severity: Severity
                    try {
                        severity = Severity.parse()
                    } catch (e: Exception) {
                        throw MojoFailureException(CONFIG_ERROR_PREFIX
                                + "'severity' attribute read from plugin configuration. " + e.message)
                    }

                    val source = xpp.getAttributeValue("", "source")
                   */
                }
            }

            /*
            // Start original converted code.
            if (eventType != XmlPullParser.START_TAG) {

                eventType = xpp.next()  // TODO: Not injected presently
                continue                // TODO: Not injected presently

            } else if ("file" == xpp.name) {
                file = PathTool.getRelativeFilePath(basedir, xpp.getAttributeValue("", "name"))
                //file = file.substring( file.lastIndexOf( File.separatorChar ) + 1 );

            } else if ("error" == xpp.name) {

                val severity = Severity.parse(currentSeverityValue, Severity.ERROR)

                if (!isViolation(severity)) {
                    continue
                }

                val source = xpp.getAttributeValue("", "source")

                if (ignore(ignores, source)) {
                    ignoreCount++
                } else {
                    count++

                    if (logViolationsToConsole) {
                        val line = xpp.getAttributeValue("", "line")
                        val column = xpp.getAttributeValue("", "column")
                        val message = xpp.getAttributeValue("", "message")
                        val rule = RuleUtil.getName(source)
                        val category = RuleUtil.getCategory(source)

                        log(severity, file + ":[" + line + (if (column == null) "" else ',' + column) + "] ("
                                + category + ") " + rule + ": " + message)
                    }
                }

            }
            */

            eventType = xpp.next()
        }

        if (ignoreCount > 0) {
            log.info("Ignored " + ignoreCount + " error" + (if (ignoreCount > 1) "s" else "") + ", " + count
                    + " violation" + (if (count > 1) "s" else "") + " remaining.")
        }

        return count
    }
}
