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

import java.io.Serializable
import java.util.*

/**
 * Enumeration of well-known severity levels.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
enum class Severity : Serializable {

    /**
     * Checkstyle info severity
     */
    INFO,

    /**
     * Checkstyle warning severity
     */
    WARNING,

    /**
     * Checkstyle error severity
     */
    ERROR;

    companion object {

        /**
         * Converts the supplied toParse string into a Severity
         * (case insensitive matching), and returns the supplied
         * fallback Severity if no match was found.
         */
        @Throws(IllegalArgumentException::class)
        fun parse(toParse: String): Severity {

            val toReturn = Severity.values().find { it -> it.name.equals(toParse, true) }

            when (toReturn) {

                // Complain
                null -> throw IllegalArgumentException("Cannot parse '" + toParse
                        + "' to a Severity. Permitted values: "
                        + Arrays.stream(Severity.values()).map { it -> it.name }.reduce { l, r -> l + ", " + r })

                // All Done.
                else -> return toReturn
            }
        }
    }

    /**
     * Checks if the supplied Severity is considered a violation, should
     * this severity be set as the violation level.
     */
    fun isViolation(toCheck: Severity): Boolean = toCheck >= this;
}
