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

import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;

/**
 * Enforcer rule to validate ProjectType compliance, to harmonize the pom structure in terms
 * of groupId, artifactId, packaging and (rudimentary) content checks.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidateProjectType extends AbstractNonCacheableEnforcerRule {

    /**
     * Delegate method, implemented by concrete subclasses.
     *
     * @param project The active MavenProject.
     * @param helper  The EnforcerRuleHelper instance, from which the MavenProject has been retrieved.
     * @throws se.jguru.nazgul.tools.codestyle.enforcer.rules.RuleFailureException If the enforcer rule
     *                                                                             was not satisfied.
     */
    @Override
    @SuppressWarnings("PMD.PreserveStackTrace")
    protected void performValidation(final MavenProject project, final EnforcerRuleHelper helper)
            throws RuleFailureException {

        try {
            ProjectType.getProjectType(project);
        } catch (IllegalArgumentException e) {
            throw new RuleFailureException(e.getMessage());
        }
    }

    /**
     * @return A human-readable short description for this AbstractEnforcerRule.
     * (Example: "No -impl dependencies permitted in this project")
     */
    @Override
    protected String getShortRuleDescription() {
        return "POM groupId, artifactId and packaging must comply with defined standard";
    }
}
