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
package se.jguru.nazgul.tools.plugin.checkstyle.report

import java.io.Serializable

/**
 * A enum providing all permitted Checkstyle report formats.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
enum class ReportFormat : Serializable {

    /**
     * XML report format.
     */
    XML,

    /**
     * Plain(text) report format.
     */
    PLAIN;

    /**
     * Companion object containing utility methods for this ReportFormat enum.
     */
    companion object {

        /**
         * Lenient parser which will perform a case-insensitive comparison for a ReportFormat.
         */
        fun getReportFormat(toParse: String?): ReportFormat {

            // Check sanity
            if(toParse == null) {
                return PLAIN
            }

            // Parse, or return a default value ("PLAIN")
            return ReportFormat
                    .values()
                    .find { current -> toParse.toLowerCase() == current.name.toLowerCase() }
                    ?: PLAIN;
        }
    }
}
