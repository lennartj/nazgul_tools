package se.jguru.nazgul.tools.visualization.spi.doclet.its;

/**
 * Placeholder class used for javadoc investigations.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ToJavaDoc {

    // Internal state
    private String someValue;

    /**
     * Compound constructor creating a ToJavaDoc object containing a someValue.
     *
     * @param someValue The someValue String.
     */
    public ToJavaDoc(final String someValue) {
        this.someValue = someValue;
    }

    /**
     * Retrieves the someValue.
     *
     * @return the someValue.
     */
    public String getSomeValue() {
        return someValue;
    }
}
