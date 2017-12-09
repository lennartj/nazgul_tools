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

import com.puppycrawl.tools.checkstyle.DefaultLogger
import com.puppycrawl.tools.checkstyle.api.AuditListener
import org.apache.maven.artifact.Artifact
import org.apache.maven.model.Resource
import org.apache.maven.project.MavenProject
import se.jguru.nazgul.tools.plugin.checkstyle.Scope
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.Serializable

class CheckstyleParametersBuilder : Serializable {

    // Internal state
    private var params = CheckstyleParameters()

    /**
     * Adds any non-null/non-empty Strings to the params.includes List,
     * provided that it is not already included.
     *
     * @see CheckstyleParameters#includes
     */
    fun withIncludes(includes: List<String>?): CheckstyleParametersBuilder {

        includes
                ?.filter { it -> it.isNotEmpty() }
                ?.filter { it -> !params.includes.contains(it) }
                ?.forEach { it -> params.includes.add(it) }

        return this
    }

    /**
     * Adds any non-null/non-empty Strings to the params.excludes List,
     * provided that it is not already excludes.
     *
     * @see CheckstyleParameters#excludes
     */
    fun withExcludes(excludes: List<String>?): CheckstyleParametersBuilder {

        excludes
                ?.filter { it -> it.isNotEmpty() }
                ?.filter { it -> !params.excludes.contains(it) }
                ?.forEach { it -> params.excludes.add(it) }

        return this
    }

    /**
     * Adds any directory Files to the params.sourceDirectories or params.testSourceDirectories, provided
     * they are directories and not already added.
     */
    fun withDirectories(scope: Scope, sourceDirs: List<File>): CheckstyleParametersBuilder {

        val targetCollection = when (scope) {
            Scope.COMPILE -> params.sourceDirectories
            Scope.TEST -> params.testSourceDirectories
        }

        sourceDirs.filter { it -> it.isDirectory }
                .filter { it -> !targetCollection.contains(it) } // TODO: Does this really work?
                .forEach { it -> targetCollection.add(it) }

        return this
    }

    /**
     * Adds any Resources to the resource collection for the given Scope.
     * @param scope The scope for which to add the resources provided.
     * @param resources The List of Resources to add.
     */
    fun withResources(scope: Scope, resources: List<Resource>?): CheckstyleParametersBuilder {

        val targetCollection = when (scope) {
            Scope.COMPILE -> params.resources
            Scope.TEST -> params.testResources
        }

        // Use ?. to handle null values.
        resources
                ?.filter { it -> !targetCollection.contains(it) }
                ?.forEach { it -> targetCollection.add(it) }

        return this
    }

    /**
     * Assigns the AuditListener
     */
    fun withAuditListener(listener : AuditListener) : CheckstyleParametersBuilder {

        params.listener = listener

        return this
    }

    fun withProject(project: MavenProject?) : CheckstyleParametersBuilder {

        when {
            project != null -> params.project = project
        }

        return this
    }

    fun withFailsOnError(failsOnError: Boolean) : CheckstyleParametersBuilder {

        params.failsOnError = failsOnError

        return this
    }

    fun withConsoleLogger(logger : DefaultLogger) : CheckstyleParametersBuilder {

        params.logger = logger

        return this
    }

    fun withConsoleOutput(consoleOutput : Boolean) : CheckstyleParametersBuilder {

        params.consoleOutput = consoleOutput

        return this
    }

    fun withEncoding(encoding : String?) : CheckstyleParametersBuilder {

        params.encoding = encoding ?: "UTF-8"

        return this
    }

    fun withSuppressionsLocation(suppressionsLocation : String?) : CheckstyleParametersBuilder {

        params.suppressionsLocation = suppressionsLocation

        return this
    }

    /**
     * Simply retrieves the fully configured CheckstyleParameters object from this Builder.
     */
    fun build(): CheckstyleParameters {
        return params
    }
}

/**
 * Parameter holder class for a Checkstyle call.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class CheckstyleParameters : Serializable {

    /**
     * Filters to identify Checkstyle source files.
     */
    var includes = mutableListOf<String>()

    /**
     * Filters to identify excluded Checkstyle source files.
     */
    var excludes = mutableListOf<String>()

    /**
     * The Compile Sources directories.
     */
    var sourceDirectories = mutableListOf<File>()

    /**
     * The Test Sources directories.
     */
    var testSourceDirectories = mutableListOf<File>()

    /**
     * Artifacts holding configuration for the checkstyle Checker
     */
    var artifactsHoldingCheckstyleConfiguration = mutableSetOf<Artifact>()

    /**
     * Artifacts holding license files (definitions) for the checkstyle Checker
     */
    var artifactsHoldingLicenseDefinitions = mutableSetOf<Artifact>()

    /**
     * Indicates if the Checkstyle run is an Aggregated run or not.
     */
    var isAggregate = false

    /**
     * The Projects within the Build Reactor. This is used when running
     * Checkstyle in aggregate (i.e. full-reactor) mode.
     */
    var reactorProjects = mutableListOf<MavenProject>()

    /**
     * Indicates if files within test-scope source directories should
     * be included into the Checkstyle check.
     */
    var includeTestSourceDirectory = false

    /**
     * Indicates if compile-scope resources should be added as arguments to the Checker.
     */
    var includeResources = false

    /**
     * The (compile-scope) resources to add as arguments to the Checker.
     * @see #includeResources
     */
    var resources = mutableListOf<Resource?>()

    /**
     * Indicates if test-scope resources should be added as arguments to the Checker.
     */
    var includeTestResources = false

    /**
     * The (test-scope) resources to add as arguments to the Checker.
     */
    var testResources = mutableListOf<Resource?>()

    /**
     * Specifies names filter for resources.
     */
    var resourceIncludes: String? = null

    /**
     * Specifies names filter for resources.
     */
    var resourceExcludes: String? = null

    /**
     * The active MavenProject
     */
    var project: MavenProject? = null

    /**
     * The Checkstyle audit listener.
     */
    var listener: AuditListener? = null

    /**
     * The DefaultLogger used by the Checker.
     */
    var logger: DefaultLogger? = null

    /**
     * Indicates if the plugin's execution should fail if an error was thrown.
     */
    var failsOnError: Boolean = false

    /**
     * Indicates if we should log output to the console.
     */
    var consoleOutput: Boolean = false

    /**
     * The encoding used by the plugin. 
     */
    var encoding: String? = null

    /**
     * The location of the checkstyle suppressions file.
     */
    var suppressionsLocation: String? = null

    companion object {

        /**
         * Condenses the string patterns as given into a file pattern string.
         *
         * @param patterns The list of patterns to condense.
         */
        fun getFilePatternStringFor(patterns: List<String>): String {

            val builder = StringBuilder()

            patterns
                    .filter { it -> it.isNotEmpty() }
                    .forEach { it ->
                        run {
                            if (builder.isNotEmpty()) {
                                builder.append(",")
                            }

                            builder.append(it)
                        }
                    }

            // All Done.
            return builder.toString()
        }
    }

    //
    // Under this: Potential parameters.
    //

    var stringOutputStream: ByteArrayOutputStream? = null

    var propertiesLocation: String? = null

    var configLocation: String? = null

    var propertyExpansion: String? = null

    var headerLocation: String? = null

    var cacheFile: String? = null

    var suppressionsFileExpression: String? = null

    var licenseArtifacts: List<Artifact>? = null

}
