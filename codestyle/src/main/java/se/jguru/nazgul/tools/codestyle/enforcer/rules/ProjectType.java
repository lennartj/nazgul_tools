/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2015 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *        http://www.jguru.se/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Definition of project types, including pattern matching for recognition.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public enum ProjectType {

    /**
     * <p>Reactor project, of type pom. May not contain anything except module definitions.</p>
     */
    REACTOR(".*-reactor$", null, "pom"),

    /**
     * <p>Parent pom project, of type pom, defining dependencies and/or build
     * life cycles. May not contain module definitions.</p>
     */
    PARENT(".*-parent$", null, "pom"),

    /**
     * <p>Pom project, defining assemblies and/or aggregation projects. May not contain module definitions.</p>
     */
    ASSEMBLY(".*-assembly$", null, "pom"),

    /**
     * Aspect definition project, holding publicly available aspects.
     */
    ASPECT(".*-aspect$", ".*\\.aspect$", "bundle|jar"),

    /**
     * <p>Model project defining entities. May have test-scope dependencies on test and proof-of-concept projects.</p>
     */
    MODEL(".*-model$", ".*\\.model$", "bundle|jar"),

    /**
     * Application project defining JEE-deployable artifacts.
     * Injections of implementation projects are permitted here.
     */
    JEE_APPLICATION(null, null, "war|ear|ejb"),

    /**
     * Standalone application project defining runnable Java applications.
     * Injections of implementation projects are permitted here.
     */
    STANDALONE_APPLICATION(".*-application$", ".*\\.application$", "bundle|jar"),

    /**
     * <p>Example project providing runnable example code for showing the
     * typical scenarios of the component. Should contain relevant documentation
     * as well as cut-and-paste code. No dependency rules.</p>
     */
    EXAMPLE(".*-example$", ".*\\.example$", null),

    /**
     * <p>{@code javaagent} definition project, holding implementation of a
     * JVM agent to be launched in-process on the form
     * <code>-javaagent:[yourpath/][agentjar].jar=[option1]=[value1],[option2]=[value2]</code></p>
     * <p>This project type can import/inject implementation dependencies, as
     * it is considered an application entrypoint.</p>
     */
    JAVA_AGENT(".*-agent$", ".*\\.agent$", "bundle|jar"),

    /**
     * <p>API project, defining service interaction, abstract implementations and exceptions. May have compile-scope
     * dependencies on model projects within the same component, and test-scope dependencies on test and
     * proof-of-concept projects.</p>
     */
    API(".*-api$", ".*\\.api$", "bundle|jar"),

    /**
     * <p>SPI project, defining service interaction, abstract implementations and exceptions. Must have compile-scope
     * dependencies to API projects within the same component. May have test-scope dependencies on test and
     * proof-of-concept projects.</p>
     */
    SPI(".*-spi-\\w*$", ".*\\.spi\\.\\w*$", "bundle|jar"),

    /**
     * <p>Implementation project, implementing service interactions from an API or SPI project,
     * including dependencies on 3rd party libraries. Must have compile-scope dependencies to API or SPI projects
     * within the same component. May have test-scope dependencies on test and proof-of-concept projects.</p>
     */
    IMPLEMENTATION(".*-impl-\\w*$", ".*\\.impl\\.\\w*$", "bundle|jar"),

    /**
     * <p>Test artifact helper project, implementing libraries facilitating testing within
     * other projects. No dependency rules.</p>
     */
    TEST(".*-test$", ".*\\.test\\.\\w*$", null),

    /**
     * <p>Integration test artifact helper project, used to perform automated
     * tests for several projects. No dependency rules.</p>
     */
    INTEGRATION_TEST(".*-it$", ".*\\.it\\.\\w*$", null),

    /**
     * <p>Codestyle helper project, providing implementations for use within the build definition cycle.
     * Typically used within local reactors to supply changes or augmentations to build configurations
     * such as {@code checkstyle.xml}, or custom enforcer rule implementations. No dependency rules.</p>
     */
    CODESTYLE(".*-codestyle$", ".*\\.codestyle$", "jar|bundle"),

    /**
     * Project, defining a Maven plugin.
     */
    PLUGIN(".*-maven-plugin$", null, "maven-plugin"),

    /**
     * <p>Proof-of-concept helper project, holding proof of concept implementations. No dependency rules.</p>
     */
    PROOF_OF_CONCEPT(".*-poc$", ".*\\.poc\\.\\w*$", null);

    // Internal state
    private Pattern artifactIdPattern;
    private Pattern groupIdPattern;
    private Pattern packagingPattern;

    /**
     * ProjectType constructor.
     *
     * @param artifactIdPattern Maven ArtifactID regexp pattern for recognizing a ProjectType.
     */
    ProjectType(final String artifactIdPattern,
                final String groupIdPattern,
                final String packagingPattern) {

        if (artifactIdPattern != null) {
            this.artifactIdPattern = Pattern.compile(artifactIdPattern);
        }

        if (packagingPattern != null) {
            this.packagingPattern = Pattern.compile(packagingPattern);
        }

        if (groupIdPattern != null) {
            this.groupIdPattern = Pattern.compile(groupIdPattern);
        }
    }

    /**
     * Checks if the provided artifactID complies with the naming standard
     * for this ProjectType.
     *
     * @param artifactID The artifactID which should be checked for compliance.
     * @return {@code true} if the provided artifactID was compliant with the
     * naming rules for this ProjectType.
     */
    public boolean isCompliantArtifactID(final String artifactID) {
        return artifactID != null && (artifactIdPattern == null || artifactIdPattern.matcher(artifactID).matches());
    }

    /**
     * Checks if the provided groupID complies with the naming standard
     * for this ProjectType.
     *
     * @param groupID The groupID which should be checked for compliance.
     * @return {@code true} if the provided groupID was compliant with the
     * naming rules for this ProjectType. If no groupID pattern is
     * given, {@code true} is returned.
     */
    public boolean isCompliantGroupID(final String groupID) {
        return groupID != null && (groupIdPattern == null || groupIdPattern.matcher(groupID).matches());
    }

    /**
     * Checks if the provided packaging complies with the standard/requirements
     * of this ProjectType.
     *
     * @param packaging THe packaging which should be checked for compliance.
     * @return {@code true} if the provided packaging was compliant with the
     * rules for this ProjectType. If no packaging pattern was given,
     * {@code true} is returned.
     */
    public boolean isCompliantPackaging(final String packaging) {
        return packaging != null && (packagingPattern == null || packagingPattern.matcher(packaging).matches());
    }

    /**
     * Acquires the ProjectType instance for the provided internal Artifact,
     * or throws an IllegalArgumentException holding an exception message
     * if a ProjectType could not be found for the provided Artifact.
     *
     * @param internalArtifact The internally developed artifact
     * @return The corresponding ProjectType.
     */
    public static ProjectType getProjectType(final Artifact internalArtifact) {

        // Check sanity
        if(internalArtifact == null) {
            throw new NullPointerException("Cannot handle null internalArtifact argument.");
        }

        final List<ProjectType> matches = findCandidates(internalArtifact.getGroupId(),
                internalArtifact.getArtifactId(),
                internalArtifact.getType(),
                "Incorrect internalArtifact type definition for ["
                        + internalArtifact.getGroupId()
                        + " :: " + internalArtifact.getArtifactId()
                        + " :: " + internalArtifact.getVersion() + "]: ");

        // All done.
        return matches.get(0);
    }

    /**
     * Acquires the ProjectType instance for the provided MavenProject,
     * or throws an IllegalArgumentException holding an exception message
     * if a ProjectType could not be found for the provided MavenProject.
     *
     * @param project The MavenProject to classify.
     * @return The corresponding ProjectType.
     * @throws IllegalArgumentException if the given project could not be mapped to a [single] ProjectType.
     *                                  The exception message holds
     */
    public static ProjectType getProjectType(final MavenProject project) throws IllegalArgumentException {

        // Check sanity
        if(project == null) {
            throw new NullPointerException("Cannot handle null project argument.");
        }

        final List<ProjectType> matches = findCandidates(project.getGroupId(),
                project.getArtifactId(),
                project.getPackaging(),
                "Incorrect project type definition for ["
                        + project.getGroupId()
                        + " :: " + project.getArtifactId()
                        + " :: " + project.getVersion() + "]: ");

        // Validate the internal requirements for the two different pom projects.
        final ProjectType toReturn = matches.get(0);
        switch (toReturn) {
            case PARENT:
            case ASSEMBLY:
                // This project should not contain modules.
                if (project.getModules() != null && !project.getModules().isEmpty()) {
                    throw new IllegalArgumentException(ProjectType.PARENT
                            + " projects may not contain module definitions. "
                            + "(Modules are reserved for reactor projects).");
                }
                break;

            case REACTOR:
                // This project not contain dependency definitions.
                final List<?> dependencies = project.getDependencies();
                if (dependencies != null && !dependencies.isEmpty()) {
                    throw new IllegalArgumentException(ProjectType.REACTOR
                            + " projects may not contain dependency definitions."
                            + " (Dependencies should be defined within parent projects).");
                }

                final DependencyManagement dependencyManagement = project.getDependencyManagement();
                if (dependencyManagement != null) {

                    // Dig out all dependency management-defined dependencies.
                    final List<Dependency> templateDependencies = dependencyManagement.getDependencies();
                    if (templateDependencies != null && !templateDependencies.isEmpty()) {
                        throw new IllegalArgumentException(ProjectType.REACTOR
                                + " projects may not contain dependency [management] definitions."
                                + " (Dependencies should be defined within parent projects).");
                    }
                }
                break;

            // No action should be taken for other project types.
            default:
                break;
        }

        // All done.
        return toReturn;
    }

    //
    // Private helpers
    //

    private static List<ProjectType> findCandidates(final String groupId,
                                                    final String artifactId,
                                                    final String packaging,
                                                    final String prefix) {

        final List<ProjectType> matches = new ArrayList<ProjectType>();

        for (final ProjectType current : ProjectType.values()) {
            if (current.isCompliantArtifactID(artifactId)
                    && current.isCompliantGroupID(groupId)
                    && current.isCompliantPackaging(packaging)) {
                matches.add(current);
            }
        }

        // Check sanity
        if (matches.isEmpty()) {
            throw new IllegalArgumentException(prefix + " Not matching any defined project types.");
        }
        if (matches.size() > 1) {
            throw new IllegalArgumentException(prefix + " Matching several project types (" + matches + ").");
        }
        return matches;
    }
}
