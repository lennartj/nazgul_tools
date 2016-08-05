package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.StringRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.AbstractGraph;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statement;


import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DotRenderingEngine {

    // Internal state
    private List<StringRenderer> renderers;

    public String render(final Graph aGraph) {

        // Create the StringBuffer to hold the result.
        final StringBuffer buffer = new StringBuffer();

        append(buffer, 0, );
    }

    private void append(final StringBuffer buffer, final int indentationLevel, final Statement statement) {

        // Render the supplied statement
        renderers.stream()
                .filter(current -> current.accept(statement))
                .filter(current -> current != null)
                .forEach(current -> {

                    // First, set the indentation level for this Renderer
                    current.setIndentationLevel(indentationLevel);

                    // Then, render the statement
                    buffer.append(current.render(statement));
                });
    }

    private StringRenderer findRendererFor(final Statement statement) {

    }

    private StringRenderer findRendererFor(final AbstractGraph graph) {

    }
}
