/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
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
public class RestrictImplDependencies extends AbstractNonCacheableEnforcerRule {

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
            ProjectType.ASSEMBLY,
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
        for (final Artifact current : ((Set<Artifact>) project.getDependencyArtifacts())) {

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
    protected final String getShortRuleDescription() {
        return "Impl projects should only be injected in applications";
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
    public final void setIncludedGroupIdPatterns(final String includedGroupIdPatterns) {
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
    public final void setExcludedGroupIdPatterns(final String excludedGroupIdPatterns) {
        this.dontEvaluateGroupIds = splice2Pattern(excludedGroupIdPatterns);
    }
}
