/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2016 jGuru Europe AB
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
package se.jguru.nazgul.tools.visualization.api.diagram.jaxb;

import org.junit.rules.TestWatcher;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;

/**
 * <p>jUnit rule simplifying working with comparing XML structures, typically used in a way similar to the
 * following:</p>
 * <pre>
 *     <code>
 *         // Create the Rule
 *         \@Rule public XmlValidationRule xmlValidation = new XmlValidationRule();
 *
 *         // Within some test ...
 *         Assert.assertTrue("Non-identical: " + xmlValidation.getDiffForIdenticalStructures(expected, actual),
 *              xmlValidation.identical(expected, actual));
 *     </code>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class XmlValidationRule extends TestWatcher {

    /**
     * Compares the expected and actual XML structures for identity.
     *
     * @param expected The expected XML structure.
     * @param actual   The actual XML structure.
     * @return A {@link Diff} containing any information about XML differences between the two structures.
     */
    public Diff getDiffForIdenticalStructures(final String expected, final String actual) {

        // Pad the arguments if required.
        final String effectiveExpected = expected == null ? "" : expected;
        final String effectiveActual = actual == null ? "" : actual;

        // All Done.
        return enrichAndGet(DiffBuilder.compare(effectiveExpected).withTest(effectiveActual), true);
    }

    /**
     * Compares the expected and actual XML structures, for identity.
     *
     * @param expected The expected XML structure.
     * @param actual   The actual XML structure.
     * @return true if the two XML structures were identical (i.e. their {@link Diff#hasDifferences()} returned false.
     */
    public boolean identical(final String expected, final String actual) {
        return !getDiffForIdenticalStructures(expected, actual).hasDifferences();
    }

    /**
     * Compares the expected and actual XML structures for similarity.
     *
     * @param expected The expected XML structure.
     * @param actual   The actual XML structure.
     * @return A {@link Diff} containing any information about XML differences between the two structures.
     */
    public Diff getDiffForSimilarStructures(final String expected, final String actual) {

        // Pad the arguments if required.
        final String effectiveExpected = expected == null ? "" : expected;
        final String effectiveActual = actual == null ? "" : actual;

        // All Done.
        return enrichAndGet(DiffBuilder.compare(effectiveExpected).withTest(effectiveActual), false);
    }

    /**
     * Compares the expected and actual XML structures, for similarity.
     *
     * @param expected The expected XML structure.
     * @param actual   The actual XML structure.
     * @return true if the two XML structures were identical (i.e. their {@link Diff#hasDifferences()} returned false.
     */
    public boolean similar(final String expected, final String actual) {
        return !getDiffForSimilarStructures(expected, actual).hasDifferences();
    }

    //
    // Private helpers
    //

    /**
     * Enriches the supplied 'raw' DiffBuilder by normalizing and ignoring WhiteSpace, and ignoring Comments.
     * Generates a Diff which checks either identical structures (if the 'identical' flag is set to true), or similar
     * structures.
     *
     * @param raw       The raw DiffBuilder, as
     * @param identical If true, then uses {@link DiffBuilder#checkForIdentical()} and otherwise
     *                  {@link DiffBuilder#checkForSimilar()}
     * @return A calculated Diff.
     */
    private Diff enrichAndGet(final DiffBuilder raw, final boolean identical) {

        // Check sanity
        if (raw == null) {
            throw new IllegalArgumentException("Cannot handle null 'raw' DiffBuilder argument.");
        }

        final DiffBuilder intermediate = raw.withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .ignoreWhitespace()
                .ignoreComments()
                .normalizeWhitespace();

        // All Done.
        return (identical ? intermediate.checkForIdentical() : intermediate.checkForSimilar()).build();
    }
}
