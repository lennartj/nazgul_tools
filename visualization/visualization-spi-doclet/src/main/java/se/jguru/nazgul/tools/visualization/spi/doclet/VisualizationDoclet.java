/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-spi-doclet
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

package se.jguru.nazgul.tools.visualization.spi.doclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.formats.html.HtmlDoclet;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.jguru.nazgul.tools.visualization.spi.doclet.graphviz.DotFacade;
import se.jguru.nazgul.tools.visualization.spi.doclet.javadoc.JavaDocOption;
import se.jguru.nazgul.tools.visualization.spi.doclet.javadoc.VisualizationWrappedRootDoc;

/**
 * Doclet implementation which synthesizes class diagrams from the given RootDoc object graph.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class VisualizationDoclet implements SimpleDoclet {

    // Our log
    private static final Logger log = LoggerFactory.getLogger(VisualizationDoclet.class);

    // Internal state
    private HtmlDoclet htmlDoclet;
    private DotFacade dotFacade;

    /**
     * Default constructor.
     */
    public VisualizationDoclet() {
        this(new DotFacade());
    }

    /**
     * Test-friendly state injection constructor.
     *
     * @param dotFacade The DotFacade instance to use
     */
    public VisualizationDoclet(final DotFacade dotFacade) {

        // Check sanity
        if (dotFacade == null) {
            throw new NullPointerException("Cannot handle null 'dotFacade' argument.");
        }

        // Assign internal state
        this.dotFacade = dotFacade;
        this.htmlDoclet = new HtmlDoclet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean start(final RootDoc root) {

        // Check sanity
        if (this.dotFacade == null) {
            throw new IllegalStateException("The DotFacade cannot be null.");
        }

        // Wrap the received RootDoc instance.
        final VisualizationWrappedRootDoc rootDoc = new VisualizationWrappedRootDoc(root);
        htmlDoclet.configuration.root = root;

        try {
            htmlDoclet.configuration.setOptions();
        } catch (Configuration.Fault fault) {
            fault.printStackTrace();
        }

        for (ClassDoc cDoc : rootDoc.classes()) {

            // Log somewhat
            if (log.isDebugEnabled()) {
                log.debug(getJavaDocAndTagsLog(cDoc));
            }
        }

        for (PackageDoc pDoc : rootDoc.specifiedPackages()) {

            // Log somewhat
            if (log.isDebugEnabled()) {
                log.debug(getJavaDocAndTagsLog(pDoc));
            }
        }

        // Convert the DOT-generated Map and write the image.
        // dotFacade.writePngImageAndImageMap(rootDoc, theDotDiagram, outputDirectory, fileName);

        // Delegate further execution to the standard Doclet.
        return HtmlDoclet.start(root);
    }

    private <T extends Doc> String getJavaDocAndTagsLog(final T theDoc) {

        // Draft up the initial string.
        final String initialString = "Processing " + (theDoc instanceof PackageDoc ? "PackageDoc" : "ClassDoc")
                + " [" + theDoc.name() + "]\n";
        final StringBuilder builder = new StringBuilder(initialString);

        // Add comments and tags
        builder.append("Comment: ").append(theDoc.commentText());
        builder.append("[" + (theDoc.tags() == null ? "0" : theDoc.tags().length) + "] Tags.");

        if (theDoc.tags() != null && theDoc.tags().length > 0) {
            for (Tag current : theDoc.tags()) {
                builder.append("Tag [" + current.name() + "]: " + current.text() + "\n");
            }
        }

        // All Done.
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateJavaDocOptions(final String[][] options, final DocErrorReporter errorReporter) {

        // This Visualization Doclet recognizes no special options.
        // Simply delegate to the standard doclet.
        try {
            htmlDoclet.sharedInstanceForOptions.setOptions(options);
        } catch (Configuration.Fault fault) {
            fault.printStackTrace();
        }

        // All done?
        return HtmlDoclet.validOptions(options, errorReporter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int optionLength(final String option) {

        // #1) Handle the -help option.
        if (JavaDocOption.HELP.getOption().equals(option)) {

            // First, provide the help text from the Standard Doclet.
            final int toReturn = HtmlDoclet.optionLength(option);

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
        return HtmlDoclet.optionLength(option);
    }
}
