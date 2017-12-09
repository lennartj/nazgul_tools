/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-impl-doclet
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

package se.jguru.nazgul.tools.visualization.impl.doclet;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.standard.Standard;
import se.jguru.nazgul.tools.visualization.impl.doclet.javadoc.JavaDocOption;

/**
 * Doclet-compliant class which provides methods required by the Doclet specification.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class VisualizationDoclet implements SimpleDoclet {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean start(final RootDoc root) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateJavaDocOptions(final String[][] options, final DocErrorReporter errorReporter) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int optionLength(final String option) {

        // #1) Handle the -help option.
        if (JavaDocOption.HELP.getOption().equals(option)) {

            // First, provide the help text from the Standard Doclet.
            final int toReturn = Standard.optionLength(option);

            // Print the options provided by VisualizationDoclet.
            System.out.println();
            System.out.println("Provided by " + VisualizationDoclet.class.getName() + ":");
            for (JavaDocOption current : JavaDocOption.values()) {
                if (current != JavaDocOption.HELP) {
                    System.out.println(current.getOption() + " " + current.getHelpText());
                }
            }

            // Delegate to the standard Doclet implementation.
            return toReturn;
        }

        // #2) Handle all JavaDoc options known to this Doclet
        for (JavaDocOption current : JavaDocOption.values()) {
            if (current.getOption().equals(option)) {
                return current.getOptionLength();
            }
        }

        // #3) Delegate to the standard Doclet.
        return Standard.optionLength(option);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
