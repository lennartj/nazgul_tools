/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-impl-doclet
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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
package se.jguru.nazgul.tools.visualization.impl.doclet.javadoc;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.standard.Standard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a doclet drafted simply to investigate the Doclet Lifecycle.
 * The Doclet documentation is itself quite lacking in clarity and detail.
 * It is simply too difficult to set up proper unit tests for Doclets.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class LifecycleInvestigationDoclet {

    // Internal state
    private static List<String> eventSequence = new ArrayList<>();

    /**
     * Generate documentation here.
     * This method is required for all doclets.
     *
     * @return true on success.
     */
    public static boolean start(final RootDoc root) {

        // Perform some reflective investigation of the RootDoc

        final boolean toReturn = Standard.start(root);
        eventSequence.add("start (root): " + toReturn);

        // We should emit the eventSequence here.
        for(int i = 0; i < eventSequence.size(); i++) {
            System.out.println(" event [" + i + " / " + eventSequence.size() + "]: " + eventSequence.get(i));
        }

        // All Done.
        return toReturn;
    }

    /**
     * <p>Check for doclet-added options.  Returns the number of
     * arguments you must specify on the command line for the
     * given option.  For example, "-d docs" would return 2.</p>
     * <p>This method is required if the doclet contains any options.
     * If this method is missing, Javadoc will print an invalid flag
     * error for every option.</p>
     *
     * @return number of arguments on the command line for an option
     * including the option name itself.  Zero return means
     * option not known.  Negative value means error occurred.
     */
    public static int optionLength(final String option) {

        final int standardOptionLength = Standard.optionLength(option);

        eventSequence.add("optionLength (" + option + "): " + standardOptionLength);

        return standardOptionLength;
    }

    /**
     * Check that options have the correct arguments.
     * <p>
     * This method is not required, but is recommended,
     * as every option will be considered valid if this method
     * is not present.  It will default gracefully (to true)
     * if absent.
     * <p>
     * Printing option related error messages (using the provided
     * DocErrorReporter) is the responsibility of this method.
     *
     * @return true if the options are valid.
     */
    public static boolean validOptions(final String options[][], DocErrorReporter reporter) {

        final boolean toReturn = Standard.validOptions(options, reporter);

        if(options == null || options.length == 0) {
            eventSequence.add("validOptions (none supplied): " + toReturn);
        } else{
            for (int i = 0; i < options.length; i++) {

                final String thisOption = Arrays.stream(options[i]).reduce((l, r) -> l + " " + r).orElse("<none>");
                eventSequence.add("validOptions [" + i + " / " + options.length + "] (" + thisOption + "): " + toReturn);
            }
        }

        // All Done.
        return toReturn;
    }

    /**
     * Return the version of the Java Programming Language supported
     * by this doclet.
     * <p>
     * This method is required by any doclet supporting a language version
     * newer than 1.1.
     *
     * @return the language version supported by this doclet.
     * @since 1.5
     */
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
