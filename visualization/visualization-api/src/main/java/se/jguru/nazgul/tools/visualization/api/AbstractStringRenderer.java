package se.jguru.nazgul.tools.visualization.api;

import se.jguru.nazgul.tools.visualization.api.diagram.AbstractStringIdentifiable;

/**
 * Abstract implementation of a Renderer yielding String results, and sporting facilities for pretty
 * printing/indenting the String result.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractStringRenderer implements StringRenderer {

    // Internal state
    private int indentationLevel;

    /**
     * Default constructor createing an {@link AbstractStringRenderer} with the indentation level of 0.
     */
    public AbstractStringRenderer() {
        this(0);
    }

    /**
     * Compound constructor creating an {@link AbstractStringRenderer} with the supplied indentationLevel.
     *
     * @param indentationLevel a non-negative indentation level.
     */
    public AbstractStringRenderer(final int indentationLevel) {
        setIndentationLevel(indentationLevel);
    }

    /**
     * {@inheritDoc}
     */
    public final void setIndentationLevel(final int indentationLevel) throws IllegalArgumentException {

        // Check sanity
        if (indentationLevel < 0) {
            throw new IllegalArgumentException("Cannot handle negative 'indentationLevel' argument. (Got: "
                    + indentationLevel + ")");
        }

        // All Done.
        this.indentationLevel = indentationLevel;
    }

    /**
     * Retrieves the current indentation level.
     *
     * @return the current indentation level.
     */
    public final int getIndentationLevel() {
        return this.indentationLevel;
    }

    /**
     * Retrieves an indentation string, which is echoed once for each indentation level to create
     * a pretty-printed rendering. The standard implementation simply returns {@link #TWO_SPACES};
     * override to use another indent string. Override to use another String to render a single indentation level.
     *
     * @return the default single indentation string (i.e. {@link #TWO_SPACES}).
     */
    public String getSingleIndent() {
        return TWO_SPACES;
    }

    /**
     * Renders the supplied entity into a format intended for consumption by humans or applications.
     * Typically: <pre><code>[getIndentation()][doRender()][NEWLINE]</code></pre>
     *
     * @param entity An accepted entity Object which should be rendered.
     * @return Renders this {@link AbstractStringRenderer} fully.
     */
    @Override
    public String render(final Object entity) {
        return getIndentation() + doRender(entity) + NEWLINE;
    }

    /**
     * Retrieves the indentation String for the indentation used at the current indentationLevel.
     *
     * @return the full indentation String corresponding to the current indentationLevel.
     */
    protected final String getIndentation() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getIndentationLevel(); i++) {
            builder.append(getSingleIndent());
        }

        // All Done.
        return builder.toString();
    }

    /**
     * Quotes the supplied string (i.e. surrounds it with "'s).
     *
     * @param toQuote The string to quote.
     * @return the supplied string surrounded with double quotes.
     */
    protected final String quote(final String toQuote) {
        return "\"" + toQuote + "\"";
    }

    /**
     * @return A standard string representation of this {@link AbstractStringIdentifiable}.
     */
    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "]: " + doRender();
    }

    /**
     * Override this method to perform rendering without any trailing newlines or starting indentations.
     *
     * @return The result of the plain rendering
     */
    protected abstract String doRender(final Object acceptedEntity);
}
