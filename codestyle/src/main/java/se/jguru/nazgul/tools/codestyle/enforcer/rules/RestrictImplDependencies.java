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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Enforcer rule to restrict importing Impl projects with the exception of actual
 * JEE deployment projects.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RestrictImplDependencies extends AbstractEnforcerRule {

    /**
     * Pattern defining groupID for artifacts that should be evaluated by this EnforcerRule instance.
     * This default value will be used unless overridden by [explicit] configuration.
     */
    public static final String EVALUATE_GROUPID = "^se\\.jguru\\.nazgul\\..*";

    /**
     * Pattern defining patterns for groupIDs that should be ignored by this EnforcerRule instance.
     * This default value will be used unless overridden by [explicit] configuration.
     */
    public static final String IGNORE_GROUPID = "^se\\.jguru\\.nazgul\\..*\\.generated\\..*,"
            + "^se\\.jguru\\.nazgul\\.tools\\.codestyle\\..*";

    /**
     * ProjectTypes for which this rule should be ignored.
     */
    public static final List<ProjectType> IGNORED_PROJECT_TYPES = Arrays.asList(
            ProjectType.JEE_APPLICATION,
            ProjectType.PARENT,
            ProjectType.REACTOR,
            ProjectType.PROOF_OF_CONCEPT,
            ProjectType.EXAMPLE,
            ProjectType.TEST,
            ProjectType.JAVA_AGENT,
            ProjectType.STANDALONE_APPLICATION);

    // Internal state
    private List<Pattern> evaluateGroupIds = new ArrayList<Pattern>();
    private List<Pattern> dontEvaluateGroupIds = new ArrayList<Pattern>();

    /**
     * Default constructor.
     */
    public RestrictImplDependencies() {

        // Add default values.
        setIncludedGroupIdPatterns(EVALUATE_GROUPID);
        setExcludedGroupIdPatterns(IGNORE_GROUPID);
    }

    /**
     * Delegate method, implemented by concrete subclasses.
     *
     * @param project The active MavenProject.
     * @param helper  The EnforcerRuleHelper instance, from which the MavenProject has been retrieved.
     * @throws RuleFailureException If the enforcer rule was not satisfied.
     */
    @SuppressWarnings({"unchecked"})
    @Override
    protected void performValidation(final MavenProject project, final EnforcerRuleHelper helper)
            throws RuleFailureException {

        // Acquire the ProjectType
        final ProjectType projectType;
        try {
            projectType = ProjectType.getProjectType(project);
        } catch (IllegalArgumentException e) {
            throw new RuleFailureException("Incorrect project definition for " + project, e);
        }

        // Don't evaluate for ignored project types.
        if (IGNORED_PROJECT_TYPES.contains(projectType)) {
            return;
        }

        // Don't evaluate if told not to.
        if (matches(project.getGroupId(), dontEvaluateGroupIds)) {

            // Log somewhat
            helper.getLog().debug("Ignored [" + project.getGroupId() + ":" + project.getArtifactId()
                    + "] since its groupId was excluded from enforcement.");
            return;

        }

        // Don't evaluate if not told to.
        if (!matches(project.getGroupId(), evaluateGroupIds)) {

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

            // Should this Artifact be evaluated?
            final boolean isIncludedInEvaluation = matches(current.getGroupId(), evaluateGroupIds);
            final boolean isNotExplicitlyExcludedFromEvaluation = !matches(current.getGroupId(), dontEvaluateGroupIds);
            if (isIncludedInEvaluation && isNotExplicitlyExcludedFromEvaluation) {

                final ProjectType artifactProjectType = ProjectType.getProjectType(current);
                final String prefix = "Don't use " + artifactProjectType + " dependencies ";

                if (artifactProjectType == ProjectType.IMPLEMENTATION) {
                    throw new RuleFailureException(prefix + "outside of application projects.", current);
                }

                if (artifactProjectType == ProjectType.TEST) {
                    throw new RuleFailureException(prefix + "in compile scope for non-test artifacts.", current);
                }

                if (artifactProjectType == ProjectType.JEE_APPLICATION
                        || artifactProjectType == ProjectType.PROOF_OF_CONCEPT) {
                    throw new RuleFailureException(prefix + "in bundles.", current);
                }
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
     * Assigns a set of patterns defining included GroupIds. Any projects whose GroupID
     * match any of the provided includedGroupIdPatterns will be validated by this rule.
     * <p/>
     * A typical configuration of this property within a pom is similar to below:
     * <p/>
     * <code>
     * &lt;includedGroupIdPatterns>^se\\.jguru\\.nazgul\\..*&lt;/includedGroupIdPatterns>
     * </code>
     *
     * @param includedGroupIdPatterns Comma-separated list of regexp patterns that groupID
     *                                should match to be included in enforcement.
     */
    public void setIncludedGroupIdPatterns(final String includedGroupIdPatterns) {
        this.evaluateGroupIds = splice2Pattern(includedGroupIdPatterns);
    }

    /**
     * Assigns a (comma separated) list of regexp patterns defining excluded GroupIds.
     * Any projects whose GroupID match any of the provided excludedGroupIdPatterns
     * will not be validated by this rule.
     * <p/>
     * <p/>
     * A typical configuration of this property within a pom is similar to below:
     * <p/>
     * <code>
     * &lt;excludedGroupIdPatterns>^se\\.jguru\\.nazgul\\..*\\.generated\\..*,
     * ^se\\.jguru\\.nazgul\\.tools\\.codestyle\\..*&lt;/excludedGroupIdPatterns>
     * </code>
     *
     * @param excludedGroupIdPatterns Comma-separated list of groupID prefixes that
     *                                should be excluded from enforcement.
     */
    public void setExcludedGroupIdPatterns(final String excludedGroupIdPatterns) {
        this.dontEvaluateGroupIds = splice2Pattern(excludedGroupIdPatterns);
    }
}
