package se.jguru.nazgul.tools.visualization.api.diagram.dot;

import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphRendererTest extends AbstractRendererTest {

    @Test
    public void validateRendering() {

        // Assemble
        final Graph graph = createStandardGraph(true);

        // Act & Assert
        validateGraph(graph, getBaseExpectedTests("->"));
    }
}
