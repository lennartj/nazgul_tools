/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;

/**
 * Enforcer rule to validate project name compliance.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidateProjectNames extends AbstractEnforcerRule {

    /**
     * Delegate method, implemented by concrete subclasses.
     *
     * @param project The active MavenProject.
     * @param helper  The EnforcerRuleHelper instance, from which the MavenProject has been retrieved.
     * @throws se.jguru.nazgul.tools.codestyle.enforcer.rules.RuleFailureException
     *          If the enforcer rule was not satisfied.
     */
    @Override
    protected void performValidation(final MavenProject project, final EnforcerRuleHelper helper)
            throws RuleFailureException {

        final ProjectType projectType = getProjectType(project.getArtifact());

        if (projectType == null) {

            final Artifact artifact = project.getArtifact();
            throw new RuleFailureException("Project [" + artifact.getGroupId() + "::" + artifact.getArtifactId()
                    + "] did not follow standard naming rules.");
        }
    }

    /**
     * @return A human-readable short description for this AbstractEnforcerRule.
     *         (Example: "No -impl dependencies permitted in this project")
     */
    @Override
    protected String getShortRuleDescription() {
        return "Project names must comply with defined standard";
    }

    /**
     * Checks if cached result is valid.
     *
     * @param cachedRule the last cached instance of the rule. This is to be used by the rule to
     *                   potentially determine if the results are still valid (ie if the configuration has been overridden)
     * @return <code>true</code> if the stored results are valid for the same id.
     */
    @Override
    public boolean isResultValid(final EnforcerRule cachedRule) {
        return false;
    }

    /**
     * If the rule is to be cached, this id is used as part of the key. This can allow rules to take parameters
     * that allow multiple results of the same rule to be cached.
     *
     * @return id to be used by the enforcer to determine uniqueness of cache results. The ids only need to be unique
     *         within a given rule implementation as the full key will be [classname]-[id]
     */
    @Override
    public String getCacheId() {
        return null;
    }

    //
    // Private helpers
    //

    private static ProjectType getProjectType(final Artifact artifact) {

        for(ProjectType current : ProjectType.values()) {
            if(current.isCompliantArtifactID(artifact.getArtifactId())
                    && current.isCompliantGroupID(artifact.getGroupId())) {
                return current;
            }
        }

        // Could not find a defined project type.
        return null;
    }
}
