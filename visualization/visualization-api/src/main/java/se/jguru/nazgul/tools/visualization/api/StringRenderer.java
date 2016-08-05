package se.jguru.nazgul.tools.visualization.api;

/**
 * Specification for a Renderer which yields Strings, and also supports pretty-printing (i.e. indentation).
 * Typically a StringRenderer should be invoked in a simple, two-step manner:
 * <pre>
 *     <code>
 *         final Object someEntity = ...
 *         final StringRenderer renderer = new MyStringRendererImplementation();
 *
 *         // Assign the indentation, which should be done from the structure
 *         // level of the someEntity within its hierarchy.
 *         renderer.setIndentationLevel(2);
 *
 *         if(renderer.accept(someEntity)) {
 *
 *              // Render, and retrieve the result
 *              final String result = someRenderer.render(someEntity);
 *
 *              // Now, do something with the result.
 *              // ....
 *         }
 *
 *     </code>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface StringRenderer extends Renderer<String> {

    /**
     * OS-neutral newline String/char.
     */
    String NEWLINE = System.getProperty("line.separator");

    /**
     * A default indentation string, holding 2 whitespaces.
     */
    String TWO_SPACES = "  ";

    /**
     * Assigns the indentation level of this {@link Renderable}.
     *
     * @param indentationLevel The non-negative level of indentation.
     * @throws IllegalArgumentException if the indentationLevel argument was negative.
     */
    void setIndentationLevel(final int indentationLevel) throws IllegalArgumentException;

    /**
     * Retrieves the indentation level of this {@link Renderable}.
     *
     * @return the indentation level of this {@link Renderable}.
     */
    int getIndentationLevel();

    /**
     * Retrieves an indentation string, which is echoed once for each indentation level to create
     * a pretty-printed rendering.
     *
     * @return the default single indentation string.
     */
    String getSingleIndent();
}
