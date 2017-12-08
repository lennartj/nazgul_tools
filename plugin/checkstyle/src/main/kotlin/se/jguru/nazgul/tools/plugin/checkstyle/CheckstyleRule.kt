/*-
 * #%L
 * Nazgul Project: nazgul-tools-checkstyle-maven-plugin
 * %%
 * Copyright (C) 2010 - 2017 jGuru Europe AB
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
package se.jguru.nazgul.tools.plugin.checkstyle

import com.puppycrawl.tools.checkstyle.api.AuditEvent
import se.jguru.nazgul.tools.plugin.checkstyle.CheckstyleRule.Companion.CHECKSTYLE_PACKAGE
import java.io.Serializable

/**
 * Algorithm stash to manipulate Checkstyle rules.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class CheckstyleRule : Serializable {

    companion object {

        /**
         * The Checkstyle 'checks' package name.
         */
        val CHECKSTYLE_PACKAGE = "com.puppycrawl.tools.checkstyle.checks"

        /**
         * Get the rule name from an audit event source name.
         *
         * @param eventSrcName the audit event source name
         * @return the rule name, which is the class name without package and removed eventual "Check" suffix
         */
        fun getName(eventSrcName: String?): String? {

            // Fail fast
            if(eventSrcName == null) {
                return null
            }

            // Remove the "Check" suffix, if present.
            val launderedSourceName = if(eventSrcName.endsWith("Check")) {
                eventSrcName.substring(0, eventSrcName.length - 5)
            } else {
                eventSrcName
            }

            // All Done.
            return launderedSourceName.substring(launderedSourceName.lastIndexOf('.') + 1)
        }

        /**
         * Get the rule category from an audit event source name.
         *
         * @param event the Checkstyle AuditEvent
         * @return the "rule category", which is the last package name or "misc" or "extension"
         */
        fun getCategory(event: AuditEvent): String? {
            return getCategory(event.sourceName)
        }

        /**
         * Get the rule category from an audit event source name.
         *
         * @param auditEventSourceName the audit event source name
         * @return the "rule category", which is the last package name or "misc" or "extension"
         */
        fun getCategory(auditEventSourceName: String?): String? {

            // Fail fast
            if (auditEventSourceName == null) {
                return null
            }

            // Find the package name/category of the auditEvent
            val indexOfLastDot = auditEventSourceName.lastIndexOf('.')
            val eventSrcName = when (indexOfLastDot) {
                -1 -> ""
                else -> auditEventSourceName.substring(0, indexOfLastDot)
            }

            // Handle special cases
            if (CHECKSTYLE_PACKAGE == eventSrcName) {
                return "misc"
            } else if (!eventSrcName.startsWith(CHECKSTYLE_PACKAGE)) {
                return "extension"
            }

            // All Done.
            return eventSrcName.substring(eventSrcName.lastIndexOf('.') + 1)
        }
    }
}

/**
 * Audit event source name matcher.
 */
interface Matcher {

    companion object {

        fun parseMatchers(specs: Array<String>): List<Matcher> {

            val toReturn = mutableListOf<Matcher>()

            specs
                    .map { spec -> spec.trim { it <= ' ' } }
                    .map {
                        when {

                        // trimmedSpec starting with uppercase is a rule name
                            Character.isUpperCase(it[0]) -> RuleMatcher(it)

                        // "misc" is a special case
                            "misc" == it -> PackageMatcher(CheckstyleRule.CHECKSTYLE_PACKAGE)

                        // "extension" is a special case
                            "extension" == it -> ExtensionMatcher()

                            !it.contains(".") -> PackageMatcher(CheckstyleRule.CHECKSTYLE_PACKAGE + '.' + it)
                            else -> // by default, trimmedSpec is a package name
                                PackageMatcher(it)
                        }
                    }
                    .forEach { toReturn.add(it) }

            return toReturn
        }

        fun ignore(ignoreExpressions: List<Matcher>?, source: String): Boolean = ignoreExpressions
                ?.filter { it.match(source) }
                ?.any { true }
                ?: false
    }

    /**
     * Does the event source name match?
     *
     * @param eventSrcName the event source name
     * @return boolean
     */
    fun match(eventSrcName: String): Boolean
}

private class RuleMatcher(private val rule: String) : Matcher {

    override fun match(eventSrcName: String): Boolean {
        return rule == CheckstyleRule.getName(eventSrcName)
    }
}

private class PackageMatcher(private val packageName: String) : Matcher {

    override fun match(eventSrcName: String): Boolean {
        return eventSrcName.startsWith(packageName) && !eventSrcName.substring(packageName.length + 1).contains(".")
    }
}

/**
 * An extension does not start with Checkstyle package.
 */
private class ExtensionMatcher : Matcher {
    override fun match(eventSrcName: String): Boolean {
        return !eventSrcName.startsWith(CHECKSTYLE_PACKAGE)
    }
}
