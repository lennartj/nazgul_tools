/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.commons.lang.Validate;
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
     * Model project defining entities.
     * <p/>
     * May have test-scope dependencies on test and proof-of-concept projects.
     */
    MODEL(".*-model$", ".*\\.model$", "bundle"),

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
     * Test artifact helper project, implementing libraries facilitating testing within
     * other projects.
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

        this.artifactIdPattern = Pattern.compile(artifactIdPattern);

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
        return artifactID != null && artifactIdPattern.matcher(artifactID).matches();
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
        return groupID != null && groupIdPattern == null || groupIdPattern.matcher(groupID).matches();
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
        return packaging != null && packagingPattern == null || packagingPattern.matcher(packaging).matches();
    }

    /**
     * Acquires the ProjectType instance for the provided MavenProject,
     * or throws an IllegalArgumentException holding an exception message
     * if a ProjectType could not be found for the provided MavenProject.
     *
     * @param project The MavenProject to classify.
     * @return The corresponding ProjectType.
     * @throws IllegalArgumentException if a single ProjectType could not be
     *                                  matched to the provided MavenProject.
     */
    public static ProjectType getProjectType(final MavenProject project) throws IllegalArgumentException {

        Validate.notNull(project, "Cannot handle null project argument.");

        final String prefix = "Incorrect project type definition for [" + project.getGroupId()
                + " :: " + project.getArtifactId() + " :: " + project.getVersion() + "]: ";

        final List<ProjectType> matches = new ArrayList<ProjectType>();

        for (ProjectType current : ProjectType.values()) {
            if (current.isCompliantArtifactID(project.getArtifactId())
                    && current.isCompliantGroupID(project.getGroupId())
                    && current.isCompliantPackaging(project.getPackaging())) {
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
}
