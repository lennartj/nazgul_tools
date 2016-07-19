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
