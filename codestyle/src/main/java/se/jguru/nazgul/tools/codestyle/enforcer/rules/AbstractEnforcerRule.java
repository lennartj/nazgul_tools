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
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Abstract Enforcer rule specification, handling some pretty printing of exceptions.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractEnforcerRule implements EnforcerRule {

    /**
     * Defines if the results of this AbstractEnforcerRule is cacheable.
     *
     * @see #isCacheable()
     */
    protected boolean cacheable = false;

    /**
     * This is the interface into the rule. This method should throw an exception
     * containing a reason message if the rule fails the check. The plugin will
     * then decide based on the fail flag if it should stop or just log the
     * message as a warning.
     *
     * @param helper The helper provides access to the log, MavenSession and has
     *               helpers to get common components. It is also able to lookup components
     *               by class name.
     * @throws org.apache.maven.enforcer.rule.api.EnforcerRuleException
     *          the enforcer rule exception
     */
    @Override
    @SuppressWarnings("PMD.PreserveStackTrace")
    public final void execute(final EnforcerRuleHelper helper) throws EnforcerRuleException {

        final MavenProject project;
        try {
            project = (MavenProject) helper.evaluate("${project}");
        } catch (final ExpressionEvaluationException e) {

            // Whoops.
            final String msg = "Could not acquire MavenProject. (Expression lookup failure for: "
                    + e.getLocalizedMessage() + ")";
            throw new EnforcerRuleException(msg, e);
        }

        // Delegate.
        try {
            performValidation(project, helper);
        } catch (RuleFailureException e) {

            // Create a somewhat verbose failure message.
            String message =
                    "\n"
                            + "\n#"
                            + "\n# Structure rule failure:"
                            + "\n# " + getShortRuleDescription()
                            + "\n# "
                            + "\n# Message: " + e.getLocalizedMessage()
                            + "\n# " + "\n# Offending project [" + project.getGroupId() + ":"
                            + project.getArtifactId() + ":" + project.getVersion() + "]" + "\n#";

            final Artifact art = e.getOffendingArtifact();
            if (art != null) {

                message += "\n# Offending artifact [" + art.getGroupId() + ":" + art.getArtifactId()
                        + ":" + art.getVersion() + "]"
                        + "\n#";
            }
            message += "\n";

            // Re-throw for pretty print
            throw new EnforcerRuleException(message);
        }
    }

    /**
     * Delegate method, implemented by concrete subclasses.
     *
     * @param project The active MavenProject.
     * @param helper  The EnforcerRuleHelper instance, from which the MavenProject has been retrieved.
     * @throws RuleFailureException If the enforcer rule was not satisfied.
     */
    protected abstract void performValidation(final MavenProject project, final EnforcerRuleHelper helper)
            throws RuleFailureException;

    /**
     * @return A human-readable short description for this AbstractEnforcerRule.
     *         (Example: "No -impl dependencies permitted in this project")
     */
    protected abstract String getShortRuleDescription();

    /**
     * This method tells the enforcer if the rule results may be cached.
     * If the result is true, the results will be remembered for future executions
     * in the same build (ie children).
     * Subsequent iterations of the rule will be queried to see if they are also cacheable.
     * This will allow the rule to be uncached further down the tree if needed.
     *
     * @return <code>true</code> if rule is cacheable
     */
    @Override
    public boolean isCacheable() {
        return cacheable;
    }

    /**
     * Helper method which splices the provided string into a List, separating on commas.
     *
     * @param toSplice The string to splice
     * @return A list holding the elements of the spliced string.
     */
    protected static List<String> splice(final String toSplice) {

        final List<String> toReturn = new ArrayList<String>();

        final StringTokenizer tok = new StringTokenizer(toSplice, ",", false);
        while (tok.hasMoreTokens()) {
            toReturn.add(tok.nextToken());
        }

        return toReturn;
    }

    /**
     * Helper method which splices the provided String into a List of Pattern instances.
     *
     * @param toSplice The string to splice
     * @return A List holding the elements of the spliced string, converted to Patterns
     * @throws PatternSyntaxException if the {@code Pattern.compile} method could not compile the provided string.
     */
    protected static List<Pattern> splice2Pattern(final String toSplice) throws PatternSyntaxException {

        final List<Pattern> toReturn = new ArrayList<Pattern>();

        for (final String current : splice(toSplice)) {
            toReturn.add(Pattern.compile(current));
        }

        return toReturn;
    }

    /**
     * Matches the provided {@code toMatch} string with all Patterns in the patternList.
     * If one pattern matches, this method returns {@code true}.
     *
     * @param toMatch     The string to match to every Pattern in the supplied patternList.
     * @param patternList The List of Patterns to use in matching.
     * @return {@code true} if one pattern in the patternList matches, this method returns {@code true}.
     */
    protected static boolean matches(final String toMatch, final List<Pattern> patternList) {

        for (final Pattern current : patternList) {
            if (current.matcher(toMatch).matches()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if any element within source startsWith the provided toCheck string.
     *
     * @param source  The list of strings which could possibly contain toCheck.
     * @param toCheck The string to validate.
     * @return <code>true</code> if any element within source returns true to
     *         <code>toCheck.startsWith(element)</code>.
     */
    protected static boolean containsPrefix(final List<String> source, final String toCheck) {

        if (source != null) {

            for (final String current : source) {
                if (toCheck.startsWith(current)) {
                    return true;
                }
            }
        }

        // The prefix was not found within the provided string toCheck.
        return false;
    }
}
