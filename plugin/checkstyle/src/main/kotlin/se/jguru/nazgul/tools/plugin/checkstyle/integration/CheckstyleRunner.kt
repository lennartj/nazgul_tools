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

package se.jguru.nazgul.tools.plugin.checkstyle.integration

import com.puppycrawl.tools.checkstyle.Checker
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import org.apache.maven.model.Resource
import org.apache.maven.plugin.logging.Log
import org.apache.maven.project.MavenProject
import org.codehaus.plexus.component.annotations.Component
import org.codehaus.plexus.component.annotations.Requirement
import org.codehaus.plexus.resource.ResourceManager
import org.codehaus.plexus.util.FileUtils
import org.codehaus.plexus.util.StringUtils
import se.jguru.nazgul.tools.plugin.checkstyle.CheckstyleResults
import java.io.File
import java.io.IOException
import java.io.Serializable
import java.lang.Exception

/**
 * Exception indicating a problem with the CheckstyleRunner execution.
 */
class CheckstyleRunnerException(message: String, cause: Throwable?) : Exception(message, cause) {

    /**
     * Convenience constructor for CheckstyleRunnerExceptions without a cause.
     */
    constructor(message: String) : this(message, null)
}

/**
 * Executor wrapper ("runner") issuing calls to Checkstyle.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@Component(role = CheckstyleRunner::class,
        hint = "default",
        instantiationStrategy = "per-lookup")
class CheckstyleRunner(val mavenProject: MavenProject, val log: Log) : Serializable {

    @Requirement(hint = "default")
    private var locator: ResourceManager? = null

    @Requirement(hint = "license")
    private var licenseLocator: ResourceManager? = null

    /**
     * Sets up the execution environment, and then Executes a Checkstyle checker command.
     *
     * @param parameters The active Checkstyle configuration (i.e. parameters) used to call the Checker method.
     * @throws CheckstyleRunnerException if this CheckstyleRunner failed calling the Checker method.
     * @throws CheckstyleException if the Checker method call failed.
     */
    @Throws(CheckstyleRunnerException::class, CheckstyleException::class)
    fun executeCheckstyle(parameters: CheckstyleParameters) : CheckstyleResults {

        // Augment the execution environment?
        val compoundEnvironmentFacet = CompoundEnvironmentFacet(parameters, mavenProject, log)

        // TODO: Add Facets to the compound Facet
        val prefix = "Incorrect configuration detected: "
        if (locator == null) {
            throw CheckstyleRunnerException(prefix + "null 'locator' ResourceManager. Incomplete injection.");
        }

        compoundEnvironmentFacet.addFacet(ResourceEnvironmentFacet(
                parameters,
                mavenProject,
                log,
                locator!!,
                parameters.artifactsHoldingCheckstyleConfiguration));

        compoundEnvironmentFacet.addFacet(ResourceEnvironmentFacet(
                parameters,
                mavenProject,
                log,
                licenseLocator!!,
                parameters.artifactsHoldingLicenseDefinitions));

        // Find the files to process.
        val files = findSourceFiles(parameters)

        // Create the parameters as required for the Checker.
        //
        // 1) Single-module parameters
        // 2) Multi-module / Aggregate parameters
        //
        var sourceDirectories: Collection<File> = ArrayList<File>()
        var testSourceDirectories: Collection<File> = ArrayList<File>()
        val sourceDirectoriesByProject = HashMap<MavenProject, Collection<File>>()
        val testSourceDirectoriesByProject = HashMap<MavenProject, Collection<File>>()

        try {

            // Start/Open the environment facet
            compoundEnvironmentFacet.open()

            // Create and launch the Checker
            val checkstyleChecker = Checker()
            val checkstyleErrors = checkstyleChecker.process(files)
        } catch (ex : Exception) {

            compoundEnvironmentFacet.close()
        }
        /*
        compoundEnvironmentFacet { it ->
            run {



                val nbErrors = checkstyleChecker.process(files)

                checkstyleChecker.destroy()

            }
        }
        */

        // All Done.
    }

    //
    // Private helpers
    //

    @Throws(IOException::class)
    private fun findSourceFiles(parameters: CheckstyleParameters): List<File> {

        val toReturn = mutableSetOf<File>()

        // #1) Collect the excludes filter input
        //
        val excludesStr = StringBuilder()
        excludesStr.append(CheckstyleParameters.getFilePatternStringFor(parameters.excludes))

        parameters.excludes
                .filter { it -> it.isNotEmpty() }
                .forEach { it ->
                    run {
                        if (excludesStr.isNotEmpty()) {
                            excludesStr.append(",")
                        }

                        excludesStr.append(it)
                    }
                }

        FileUtils.getDefaultExcludes().forEach { it ->
            run {
                if (excludesStr.isNotEmpty()) {
                    excludesStr.append(",")
                }

                excludesStr.append(it)
            }
        }

        // #2) If this is an aggregate run, we need to assemble the files
        //     within the entire reactor
        //

        if (parameters.isAggregate) {

            for (project in parameters.reactorProjects) {
                addFilesFromProject(project, parameters, excludesStr, toReturn)
            }

        } else {

            addFilesFromProject(this.mavenProject, parameters, excludesStr, toReturn)
        }

        if (log.isDebugEnabled) {
            log.debug("Added " + toReturn.size + " files to process.")
        }

        // All Done.
        return ArrayList(toReturn)
    }

    private fun addFilesFromProject(project: MavenProject,
                                    parameters: CheckstyleParameters,
                                    excludesStr: StringBuilder,
                                    toReturn: MutableSet<File>) {

        val sourceDirs = mutableListOf<File>()
        val testSourceDirs = mutableListOf<File>()

        // CompileSourceRoots and TestCompileSourceRoots are absolute paths
        //
        project.compileSourceRoots
                .filter { it -> it != null }
                .filter { it -> it.isNotEmpty() }
                .forEach { it -> sourceDirs.add(File(it)) }

        project.testCompileSourceRoots
                .filter { it -> it != null }
                .filter { it -> it.isNotEmpty() }
                .forEach { it -> testSourceDirs.add(File(it)) }

        // Add the non-excluded source directory files
        //
        sourceDirs.filter { aDir -> aDir.isDirectory }
                .forEach { current ->
                    run {

                        val sourceFiles = FileUtils.getFiles(
                                current,
                                CheckstyleParameters.getFilePatternStringFor(parameters.includes),
                                excludesStr.toString())

                        toReturn.addAll(sourceFiles)

                        if (log.isDebugEnabled) {
                            log.debug("Added " + sourceFiles.size + " source files from directory '"
                                    + current.absolutePath + "'.")
                        }
                    }
                }

        if (parameters.includeTestSourceDirectory && testSourceDirs.isNotEmpty()) {

            // Add the non-excluded test-scope files
            //
            testSourceDirs.filter { aDir -> aDir.isDirectory }
                    .forEach { current ->
                        run {

                            val testSourceFiles = FileUtils.getFiles(
                                    current,
                                    CheckstyleParameters.getFilePatternStringFor(parameters.includes),
                                    excludesStr.toString())

                            toReturn.addAll(testSourceFiles)

                            if (log.isDebugEnabled) {
                                log.debug("Added " + testSourceFiles.size
                                        + " test source files from directory '" + current.absolutePath + "'.")
                            }

                        }
                    }
        }

        // Add compile-scope resources to the Checker, if so indicated.
        //
        if (parameters.includeResources && parameters.resources.isNotEmpty()) {
            addResourceFiles(parameters, parameters.resources, toReturn)
        } else {
            log.debug("No resources found in this project.")
        }

        // Add test-scope resources to the Checker, if so indicated.
        //
        if (parameters.includeTestResources && parameters.testResources.isNotEmpty()) {
            addResourceFiles(parameters, parameters.testResources, toReturn)
        } else {
            log.debug("No test resources found in this project.")
        }
    }

    @Throws(IOException::class)
    private fun addResourceFiles(parameters: CheckstyleParameters,
                                 resources: List<Resource?>,
                                 files: MutableCollection<File>) {


        for (resource in resources) {

            // Only handle non-null resource directories
            //
            val processedDirectory = resource?.directory?.let {

                // Check sanity
                val resourcesDirectory = File(it)
                if (resourcesDirectory.isDirectory) {

                    var includes = parameters.resourceIncludes
                    var excludes = parameters.resourceExcludes

                    // Add all resources from under the ${basedir}, but
                    // otherwise only the directory itself.
                    if (resourcesDirectory == this.mavenProject.basedir) {

                        val resourceIncludes = resource.includes?.let {
                            StringUtils.join(it.iterator(), ",")
                        }
                        if (StringUtils.isEmpty(includes)) {
                            includes = resourceIncludes
                        } else {
                            includes += "," + resourceIncludes
                        }

                        val resourceExcludes = resource.excludes?.let {
                            StringUtils.join(it.iterator(), ",")
                        }
                        if (StringUtils.isEmpty(excludes)) {
                            excludes = resourceExcludes
                        } else {
                            excludes += "," + resourceExcludes
                        }
                    }

                    val resourceFiles = FileUtils.getFiles(resourcesDirectory, includes, excludes)
                    files.addAll(resourceFiles)

                    if (log.isDebugEnabled) {
                        log.debug("Added " + resourceFiles.size + " resource files found in '"
                                + resourcesDirectory.absolutePath + "'.")
                    }

                } else {

                    if (log.isDebugEnabled) {
                        log.debug("The resources directory '" + resourcesDirectory.absolutePath
                                + "' does not exist or is not a directory.")
                    }
                }
            }
        }
    }
}
