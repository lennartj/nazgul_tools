/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2016 jGuru Europe AB
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
package se.jguru.nazgul.tools.visualization.api.diagram.statement;

import org.antlr.v4.runtime.RuleContext;
import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.spi.dot.DotDiagramValidator;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RightSideEdgeTest extends AbstractGraphTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullGraphForNodeID() {
        new RightSideEdge(new NodeID("foo"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullGraphForSubGraph() {
        new RightSideEdge(new Subgraph("foo"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullSubGraph() {

        // Assemble
        final Subgraph noSubgraph = null;

        // Act & Assert
        new RightSideEdge(noSubgraph, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnAssigningNullRightSideEdge() {

        // Assemble
        final Graph graph = createStandardGraph(true);

        // Act
        final Edge edge = graph.getStatements().findEdge("node1", "node2", true);
        final RightSideEdge unitUnderTest = edge.getRightSideEdge();

        // Assert
        unitUnderTest.setRightSideEdge(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullIdInFactoryMethod() {

        // Assemble
        final Graph parent = createStandardGraph(true);

        // Act & Assert
        RightSideEdge.to(null, parent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullParentInFactoryMethod() {

        // Assemble
        final Graph parent = createStandardGraph(false);

        // Act & Assert
        RightSideEdge.to("foo", null);
    }

    @Test
    public void validateRenderingWithinDirectedGraph() {

        // Assemble
        final List<String> expectedTexts = getBaseExpectedTests("->");
        final Graph graph = createStandardGraph(true);

        // Act
        final Edge edge = graph.getStatements().findEdge("node1", "node2", true);
        final RightSideEdge unitUnderTest = edge.getRightSideEdge();

        // Assert
        validateGraph(graph, expectedTexts);
    }

    @Test
    public void validateRenderingWithinDirectedSubgraph() {

        // Assemble
        final Graph graph = createStandardGraph(true);
        final List<String> expectedTexts = new ArrayList<>(getBaseExpectedTests("->"));
        expectedTexts.add("subgraph\"subgraph_1\"{\"node3\";\"subgraph_1\"->\"node3\";}");

        /*
        // Subgraph statements.
        subgraph  "subgraph_1" {

        // Node statements.
        "node3"  ;

        // Edge statements.
        "subgraph_1" ->  "node3"  ;

        }  ;
         */

        final Subgraph subgraph = new Subgraph("subgraph_1");
        final Node node3 = new Node("node3");

        subgraph.add(node3);
        graph.add(subgraph);

        final Edge edgeIntoSubgraph = new Edge(subgraph, RightSideEdge.to("node3", graph));
        subgraph.add(edgeIntoSubgraph);

        // Act
        final Edge edge = graph.getStatements().findEdge("node1", "node2", true);
        final RightSideEdge unitUnderTest = edge.getRightSideEdge();

        // Assert
        validateGraph(graph, expectedTexts);
    }

    @Test
    public void validateRenderingWithinGraph() {

        // Assemble
        final Graph graph = createStandardGraph(false);
        final List<String> expectedTexts = getBaseExpectedTests("--");

        // Act
        final Edge edge = graph.getStatements().findEdge("node1", "node2", true);
        final RightSideEdge unitUnderTest = edge.getRightSideEdge();

        // Assert
        validateGraph(graph, expectedTexts);
    }

    @Test
    public void validateRenderingWithinGraphUsingChild() {

        // Assemble
        final Graph graph = createStandardGraph(false);
        graph.add(new Node("node3"));

        // Act
        final Edge edge = graph.getStatements().findEdge("node1", "node2", true);
        final RightSideEdge unitUnderTest = edge.getRightSideEdge();
        unitUnderTest.setRightSideEdge(RightSideEdge.to("node3", graph));

        /*
        graph "foobar" {

        // Node statements.
        "node1"  ;
        "node2"  ;
        "node3"  ;

        // Identifier statements.
        "node1"  = "foobar"  ;

        // Edge statements.
        "node1"  --  "node2"  --  "node3"  ;
        }
         */

        // Assert
        final String rendered = graph.render();
        final InputStream in = new ByteArrayInputStream(rendered.getBytes());
        final DotParser.GraphContext graphContext = DotDiagramValidator.validate(in);
        Assert.assertNotNull(graphContext);

        final List<DotParser.StmtContext> parsedStatements = graphContext.stmt_list().stmt();
        Assert.assertEquals(5, parsedStatements.size());

        final List<String> parsedStatementTexts = parsedStatements.stream()
                .map(RuleContext::getText)
                .collect(Collectors.toList());
        final List<String> expectedTexts = Arrays.asList("\"node1\"",
                "\"node2\"",
                "\"node3\"",
                "\"node1\"=\"foobar\"",
                "\"node1\"--\"node2\"--\"node3\"");

        expectedTexts.forEach(c -> Assert.assertTrue(parsedStatementTexts.contains(c)));
    }
}
