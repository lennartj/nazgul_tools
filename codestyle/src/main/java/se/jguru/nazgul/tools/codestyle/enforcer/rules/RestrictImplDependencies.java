/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Enforcer rule to restrict importing Impl projects with the exception of actual
 * JEE deployment projects.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RestrictImplDependencies extends AbstractEnforcerRule {

    /**
     * Suffix to identify an implementation project.
     */
    public static final String IMPLEMENTATION_PROJECT_SUFFIX = "-impl";

    /**
     * Prefix for our own plugins.
     */
    public static final String PLUGINS_GROUPID = "se.jguru.nazgul.tools.codestyle";

    /**
     * Packagings for projects implying this rule should be ignored.
     */
    public static final String[] IGNORED_PACKAGING_TYPES = {"ear", "war", "pom"};

    // Internal state
    private List<String> evaluateGroupIds = new ArrayList<String>();
    private List<String> dontEvaluateGroupIds = new ArrayList<String>();
    private List<String> ignoreArtifactsWithGroupIdPrefixes = new ArrayList<String>();

    /**
     * Default constructor.
     */
    public RestrictImplDependencies() {
        ignoreArtifactsWithGroupIdPrefixes.add(PLUGINS_GROUPID);
    }

    /**
     * Delegate method, implemented by concrete subclasses.
     *
     * @param project The active MavenProject.
     * @param helper  The EnforcerRuleHelper instance, from which the MavenProject has been retrieved.
     * @throws RuleFailureException If the enforcer rule was not satisfied.
     */
    @Override
    protected void performValidation(final MavenProject project, final EnforcerRuleHelper helper)
            throws RuleFailureException {

        // Don't evaluate for ignored project types.
        for (String current : IGNORED_PACKAGING_TYPES) {
            if (current.equalsIgnoreCase(project.getPackaging())) {
                return;
            }
        }

        // Don't evaluate if told not to.
        if (dontEvaluateGroupIds.size() > 0 && containsPrefix(dontEvaluateGroupIds, project.getGroupId())) {

            // Log somewhat
            helper.getLog().debug("Ignored [" + project.getGroupId() + ":" + project.getArtifactId()
                    + "] since its groupId was excluded from enforcement.");
            return;

        }

        // Don't evaluate if not told to.
        if (evaluateGroupIds.size() > 0 && !containsPrefix(evaluateGroupIds, project.getGroupId())) {

            // Log somewhat
            helper.getLog().debug("Ignored [" + project.getGroupId() + ":" + project.getArtifactId()
                    + "] since its groupId was not included in enforcement.");
            return;
        }

        // Acquire all project dependencies.
        for (Artifact current : ((Set<Artifact>) project.getDependencyArtifacts())) {

            // Don't evaluate for test-scope dependencies.
            if (Artifact.SCOPE_TEST.equalsIgnoreCase(current.getScope())) {
                continue;
            }

            // Don't evaluate artifacts which we are told to ignore...
            if (ignoreArtifactsWithGroupIdPrefixes.size() > 0
                    && containsPrefix(ignoreArtifactsWithGroupIdPrefixes, current.getGroupId())) {
                continue;
            }

            // Evaluate this Artifact.
            if (current.getArtifactId().toLowerCase().endsWith(IMPLEMENTATION_PROJECT_SUFFIX)) {
                throw new RuleFailureException(
                        "Don't use -Impl dependencies outside of application projects.",
                        current);
            }
        }
    }

    /**
     * @return A human-readable short description for this AbstractEnforcerRule.
     *         (Example: "No -impl dependencies permitted in this project")
     */
    @Override
    protected String getShortRuleDescription() {
        return "Impl projects should only be injected in applications";
    }

    /**
     * Checks if cached result is valid.
     *
     * @param cachedRule the last cached instance of the rule. This is to be used by the rule to
     *                   potentially determine if the results are still valid (i.e. if the configuration
     *                   has been overridden)
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

    /**
     * Assigns included GroupIdPrefixes, which includes any projects whose GroupID
     * starts with the provided includedGroupIdPrefices into enforcement by this rule.
     *
     * @param includedGroupIdPrefices Comma-separated list of groupID prefixes that
     *                                should be included in enforcement.
     */
    public void setIncludedGroupIdPrefixes(final String includedGroupIdPrefices) {
        this.evaluateGroupIds = splice(includedGroupIdPrefices);
    }

    /**
     * Assigns excluded GroupIdPrefixes, which removes any projects whose GroupID
     * starts with the provided excludedGroupIdPrefixes from enforcement by this rule.
     *
     * @param excludedGroupIdPrefixes Comma-separated list of groupID prefixes that
     *                                should be excluded from enforcement.
     */
    public void setExcludedGroupIdPrefixes(final String excludedGroupIdPrefixes) {
        this.dontEvaluateGroupIds = splice(excludedGroupIdPrefixes);
    }

    /**
     * Assigns a list of GroupIdPrefixes, to indicate that any artifacts slated for evaluation
     * should be ignored if their groupId starts with one of the provided {@code ignoreArtifactsWithGroupIdPrefixes}.
     *
     * @param ignoredArtifactGroupIdPrefixes Comma-separated list of groupID prefixes for artifacts that
     *                                       should be excluded from enforcement.
     */
    public void setIgnoreEvaluatingArtifactsWithGroupIdPrefixes(final String ignoredArtifactGroupIdPrefixes) {
        this.ignoreArtifactsWithGroupIdPrefixes.addAll(splice(ignoredArtifactGroupIdPrefixes));
    }
}
