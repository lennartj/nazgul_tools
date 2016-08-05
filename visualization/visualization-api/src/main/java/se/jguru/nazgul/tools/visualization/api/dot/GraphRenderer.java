package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphRenderer extends AbstractStringRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final Object entity) {
        return entity != null && entity instanceof Graph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final Object acceptedEntity) {

        final Graph graph = (Graph) acceptedEntity;

        return (graph.isStrict() ? "strict " : "")
                + (graph.isDigraph() ? "digraph" : "graph")
                + " " + quote(graph.getId())
                + " {"
                + NEWLINE
                + graph.getStatements().
                + " } "
                + NEWLINE;
    }
}
