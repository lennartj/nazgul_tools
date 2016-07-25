package se.jguru.nazgul.tools.visualization.model.diagram;

import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statement;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstract specification for a container of {@link Statement} subclasses.
 * This implies either a Graph or a Subgraph.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"statements"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractGraph extends AbstractIdentifiable {

    /**
     * The statements contained within this AbstractGraph. May be empty, but never null.
     */
    @XmlElement(required = true)
    private Statements statements;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public AbstractGraph() {
        // Do nothing
    }

    /**
     * Compound constructor creating an {@link AbstractGraph} instance wrapping the supplied data.
     *
     * @param id a non-null and non-empty identifier, assumed to be unique (within a Graph).
     */
    protected AbstractGraph(final String id) {

        // Delegate
        super(id);

        // Create internal state
        this.statements = new Statements();
    }

    /**
     * @return The currently known Statements.
     */
    public Statements getStatements() {
        return statements;
    }

    /**
     * Convenience method to add one or more {@link Statement} objects to the contained
     * {@link Statements} holder within this {@link AbstractGraph}.
     *
     * @param toAdd One or more {@link Statement} instances.
     */
    public void add(final Statement... toAdd) {
        if (toAdd != null) {
            getStatements().add(toAdd);
        }
    }
}
