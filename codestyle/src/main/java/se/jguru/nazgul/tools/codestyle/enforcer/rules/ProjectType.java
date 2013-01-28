/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.commons.lang.Validate;
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
     * Reactor project, of type pom.
     * <p/>
     * May not contain anything except module definitions.
     */
    REACTOR(".*-reactor$", null, "pom"),

    /**
     * Parent pom project, of type pom, defining dependencies and/or build
     * life cycles.
     * <p/>
     * May not contain module definitions.
     */
    PARENT(".*-parent$", null, "pom"),

    /**
     * Aspect definition project, holding publicly available aspects.
     */
    ASPECT(".*-aspect$", ".*\\.aspect$", "bundle"),

    /**
     * Model project defining entities.
     * <p/>
     * May have test-scope dependencies on test and proof-of-concept projects.
     */
    MODEL(".*-model$", ".*\\.model$", "bundle"),

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
     * Example project providing runnable example code for showing the
     * typical scenarios of the component. Should contain relevant documentation
     * as well as cut-and-paste code.
     * <p/>
     * No dependency rules.
     */
    EXAMPLE(".*-example$", ".*\\.example$", null),

    /**
     * {@code javaagent} definition project, holding implementation of a
     * JVM agent to be launched in-process on the form
     * <p/>
     * <code>-javaagent:[yourpath/][agentjar].jar=[option1]=[value1],[option2]=[value2]</code>
     * <p/>
     * This project type can import/inject implementation dependencies, as
     * it is considered an application entrypoint.
     */
    JAVA_AGENT(".*-agent$", ".*\\.agent$", "bundle"),

    /**
     * API project, defining service interaction, abstract implementations and exceptions.
     * <p/>
     * May have compile-scope dependencies on model projects within the same component, and test-scope
     * dependencies on test and proof-of-concept projects.
     */
    API(".*-api$", ".*\\.api$", "bundle"),

    /**
     * SPI project, defining service interaction, abstract implementations and exceptions.
     * <p/>
     * Must have compile-scope dependencies to API projects within the same component.
     * May have test-scope dependencies on test and proof-of-concept projects.
     */
    SPI(".*-spi-\\w*$", ".*\\.spi\\.\\w*$", "bundle"),

    /**
     * Implementation project, implementing service interactions from an API or SPI project,
     * including dependencies on 3rd party libraries.
     * <p/>
     * Must have compile-scope dependencies to API or SPI projects within the same component.
     * May have test-scope dependencies on test and proof-of-concept projects.
     */
    IMPLEMENTATION(".*-impl-\\w*$", ".*\\.impl\\.\\w*$", "bundle"),

    /**
     * Test artifact helper project, implementing libraries facilitating testing within
     * other projects.
     * <p/>
     * No dependency rules.
     */
    TEST(".*-test$", ".*\\.test\\.\\w*$", null),

    /**
     * Codestyle helper project, providing implementations for use within the build definition cycle.
     * Typically used within local reactors to supply changes or augmentations to build configurations
     * such as {@code checkstyle.xml}, or custom enforcer rule implementations.
     * <p/>
     * No dependency rules.
     */
    CODESTYLE(".*-codestyle$", ".*\\.codestyle$", "jar|bundle"),

    /**
     * Proof-of-concept helper project, holding proof of concept implementations.
     * <p/>
     * No dependency rules.
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
    private ProjectType(final String artifactIdPattern,
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
     *         naming rules for this ProjectType.
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
     *         naming rules for this ProjectType. If no groupID pattern is
     *         given, {@code true} is returned.
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
     *         rules for this ProjectType. If no packaging pattern was given,
     *         {@code true} is returned.
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

        Validate.notNull(internalArtifact, "Cannot handle null internalArtifact argument.");

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

        Validate.notNull(project, "Cannot handle null project argument.");

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
                // This project should not contain modules.
                if (project.getModules() != null && project.getModules().size() > 0) {
                    throw new IllegalArgumentException(ProjectType.PARENT
                            + " projects may not contain module definitions. "
                            + "(Modules are reserved for reactor projects).");
                }
                break;

            case REACTOR:
                // This project not contain dependency definitions.
                final List dependencies = project.getDependencies();
                if (dependencies != null && dependencies.size() > 0) {
                    throw new IllegalArgumentException(ProjectType.REACTOR
                            + " projects may not contain dependency definitions."
                            + " (Dependencies should be defined within parent projects).");
                }

                final DependencyManagement dependencyManagement = project.getDependencyManagement();
                if (dependencyManagement != null) {

                    // Dig out all dependency management-defined dependencies.
                    final List<Dependency> templateDependencies = dependencyManagement.getDependencies();
                    if (templateDependencies != null && templateDependencies.size() > 0) {
                        throw new IllegalArgumentException(ProjectType.REACTOR
                                + " projects may not contain dependency [management] definitions."
                                + " (Dependencies should be defined within parent projects).");
                    }
                }
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

        for (ProjectType current : ProjectType.values()) {
            if (current.isCompliantArtifactID(artifactId)
                    && current.isCompliantGroupID(groupId)
                    && current.isCompliantPackaging(packaging)) {
                matches.add(current);
            }
        }

        // Check sanity
        if (matches.size() == 0) {
            throw new IllegalArgumentException(prefix + " Not matching any defined project types.");
        }
        if (matches.size() > 1) {
            throw new IllegalArgumentException(prefix + " Matching several project types (" + matches + ").");
        }
        return matches;
    }
}
