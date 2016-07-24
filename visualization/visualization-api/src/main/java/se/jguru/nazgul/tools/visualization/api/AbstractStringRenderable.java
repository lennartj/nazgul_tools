package se.jguru.nazgul.tools.visualization.api;

import se.jguru.nazgul.tools.visualization.api.diagram.AbstractStringIdentifiable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Abstract implementation of a StringRenderable, which contains the level of indentation for this
 * {@link StringRenderable}, and also provides methods to cope with pretty-printing (i.e. {@link #doRender()}, which
 * is called from within the {@link #render()} method of this {@link AbstractStringRenderable}).
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Renderable.NAMESPACE, propOrder = {"indentationLevel"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractStringRenderable implements Serializable, StringRenderable {

    /**
     * The indentation level of this AbstractRenderable, corresponding to
     */
    @XmlAttribute
    private int indentationLevel;

    /**
     * Default constructor createing an {@link AbstractStringRenderable} with the indentation of 1.
     */
    public AbstractStringRenderable() {
        this(1);
    }

    /**
     * Compound constructor creating an {@link AbstractStringRenderable} with the supplied indentationLevel.
     *
     * @param indentationLevel a non-negative indentation level.
     */
    public AbstractStringRenderable(final int indentationLevel) {
        setIndentationLevel(indentationLevel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * Standard implementation of pretty-printing rendering, emitting a string on the form
     * <pre><code>[getIndentation()][doRender()][NEWLINE]</code></pre>
     *
     * @return Renders this {@link AbstractStringRenderable} fully.
     */
    @Override
    public final String render() {

        // All Done.
        return getIndentation() + doRender() + NEWLINE;
    }

    /**
     * Retrieves the indentation String for the indentation used at the current indentationLevel.
     *
     * @return the full indentation String corresponding to the current indentationLevel.
     */
    protected String getIndentation() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getIndentationLevel(); i++) {
            builder.append(getSingleIndent());
        }

        // All Done.
        return builder.toString();
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
    protected abstract String doRender();
}
