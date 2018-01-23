/*-
 * #%L
 * Nazgul Project: nazgul-tools-checkstyle-maven-plugin
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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

import org.apache.maven.artifact.Artifact
import org.apache.maven.plugin.logging.Log
import org.apache.maven.project.MavenProject
import org.codehaus.plexus.resource.ResourceManager
import java.io.File
import java.io.Serializable
import java.lang.Exception
import java.util.concurrent.atomic.AtomicInteger

/**
 * Specification for an AutoCloseable facet of the execution environment.
 * This facet is configured for - and exists - only while the CheckstyleRunner executes.
 *
 * @param parameters The CheckstyleParameters to use for this EnvironmentFacet.
 * @param project The active MavenProject.
 * @param log The active Maven Log.
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
abstract class EnvironmentFacet(val parameters: CheckstyleParameters,
                                val project: MavenProject,
                                val log: Log) : Serializable, AutoCloseable {

    val openResult = mutableMapOf<String, Any>()
    val closeResult = mutableMapOf<String, Any>()

    /**
     * Standardized open method
     */
    fun open(): Map<String, Any> {

        // Log some
        if (log.isDebugEnabled) {
            log.debug("Opening EnvironmentFacet [" + javaClass.simpleName + "]")
        }

        // Execute, and stash the results within "openResult"
        openResult.putAll(onOpen())

        if (log.isDebugEnabled) {
            log.debug("Retrieved result Map: " + openResult)
        }

        // All Done.
        return openResult
    }

    /**
     * Standardized close method
     */
    final override fun close() {

        // Log some
        if (log.isDebugEnabled) {
            log.debug("Closing EnvironmentFacet [" + javaClass.simpleName + "]")
        }

        closeResult.putAll(onClose())

        if (log.isDebugEnabled) {
            log.debug("Retrieved result Map: " + closeResult)
        }
    }

    /**
     * Override this method to setup/open this EnvironmentFacet.
     * This method is automagically called upon opening.
     *
     * @return a Map relating opening parameters/result keys to their corresponding values.
     */
    abstract protected fun onOpen(): Map<String, Any>

    /**
     * Override this method to cleanup/close this EnvironmentFacet. This method is automagically called upon closing.
     *
     * @return a Map relating closing parameters/result keys to their corresponding values.
     */
    abstract protected fun onClose(): Map<String, Any>
}

/**
 * Compound container of EnvironmentFacets.
 * This is a stateful implementation, in regards to the
 *
 * @param parameters The CheckstyleParameters to use for this EnvironmentFacet.
 * @param project The active MavenProject.
 * @param log The active Maven Log.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class CompoundEnvironmentFacet(parameters: CheckstyleParameters,
                               project: MavenProject,
                               log: Log) : EnvironmentFacet(parameters, project, log) {

    // Internal state
    private val facets = ArrayList<EnvironmentFacet>()

    /**
     * Adds the supplied EnvironmentFacet to this CompoundEnvironmentFacet.
     */
    fun addFacet(aFacet: EnvironmentFacet) {
        facets.add(aFacet)
    }

    override fun onOpen(): Map<String, Any> {

        val toReturn = mutableMapOf<String, Any>()

        //
        // Open all known EnvironmentFacets, and
        // add their respective results to the returned Map.
        //
        facets.map { aFacet -> aFacet.open() }.forEach { theResult -> toReturn.putAll(theResult) }

        // All Done.
        return toReturn.toSortedMap()
    }

    override fun onClose(): Map<String, Any> {

        val toReturn = mutableMapOf<String, Any>()

        //
        // Close all known EnvironmentFacets, and
        // add their respective results to the returned Map.
        //
        facets.forEach { aFacet ->
            run {

                // First, close the facet
                aFacet.close()

                // Then, add its result to the return Map
                toReturn.putAll(aFacet.closeResult)
            }
        }

        // All Done.
        return toReturn.toSortedMap()
    }
}

/**
 * Environment facet which adds a (Set of) Artifacts to the ResourceManager during the execution.
 *
 * @param artifacts The Set of Maven artifacts to add to the given ResourceManager
 * @param parameters The CheckstyleParameters received.
 * @param resourceManager The ResourceManager to which the supplied artifacts should be added.
 */
class ResourceEnvironmentFacet(parameters: CheckstyleParameters,
                               project: MavenProject,
                               log: Log,
                               val resourceManager: ResourceManager,
                               val artifacts: Set<Artifact?>) : EnvironmentFacet(parameters, project, log) {

    companion object {

        /**
         * The URL ResourceManager ID
         */
        val URL_RESOURCE_MANAGER_ID = "url"

        /**
         * The JAR ResourceManager ID
         */
        val JAR_RESOURCE_MANAGER_ID = "jar"
    }

    /**
     * Adds the "" and each supplied Artifact as SearchPaths to the given ResourceManager.
     */
    override fun onOpen(): Map<String, Any> {

        val index = AtomicInteger()
        val toReturn = mutableMapOf<String, Any>()

        // #1) Set the output directory
        resourceManager.setOutputDirectory(File(project.build.directory))

        if (artifacts.isNotEmpty()) {

            // #2.1) Add the current URL to the ResourceManager
            resourceManager.addSearchPath(URL_RESOURCE_MANAGER_ID, "")

            // #2.2) Add all provided artifacts to the ResourceManager.
            artifacts
                    .filter { anArtifact -> anArtifact != null }
                    .forEach { anArtifact ->
                        run {

                            // Handle the current Artifact
                            val art = anArtifact!!
                            val toAdd = "jar:" + art.file.toURI().toURL()

                            // Log some.
                            if (log.isDebugEnabled) {
                                log.debug("Artifact [" + art.groupId + ":" + art.artifactId + ":" + art.version
                                        + "] added to ResourceManager using URL [" + toAdd + "]")
                            }

                            // Synthesize the path to add.
                            resourceManager.addSearchPath(JAR_RESOURCE_MANAGER_ID, toAdd)
                        }
                    }
        }

        // All Done.
        return toReturn
    }

    override fun onClose(): Map<String, Any> {

        // Do nothing
        return mapOf()
    }
}
