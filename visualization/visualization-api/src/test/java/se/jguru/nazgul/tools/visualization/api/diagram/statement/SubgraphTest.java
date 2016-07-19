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
import se.jguru.nazgul.tools.visualization.spi.dot.DotDiagramValidator;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class SubgraphTest extends AbstractGraphTest {

    @Test
    public void validateAddingSubgraphsAndNodes() {

        // Assemble
        final Graph standardGraph = createStandardGraph(true);

        // Act & Assert
        validateGraph(standardGraph, "->");
    }

    @Test
    public void validateFindingNodesRecursively() {

        // Assemble
        final Graph standardGraph = createStandardGraph(true);

        // Act
        final List<Node> nodeList = new ArrayList<>();
        for(int i = 1; i < 4; i++) {
            nodeList.add(standardGraph.getStatements().find(Node.class, "node_" + i, true));
        }

        // Assert
        Assert.assertEquals(3, nodeList.size());
        for(int i = 0; i < nodeList.size(); i++) {

            final Node current = nodeList.get(i);
            Assert.assertNotNull(current);
            Assert.assertEquals("node_" + (i + 1), current.getId());
        }
    }

    //
    // Private helpers
    //

    protected Graph createStandardGraph(final boolean directed) {

        final Graph graph = new Graph("subgraphTest", directed, false);

        for(int i = 1; i < 4; i++) {
            final Subgraph subgraph = new Subgraph("subgraph_" + i);
            subgraph.add(new Node("node_" + i));
            graph.add(subgraph);
        }

        // All Done.
        return graph;
    }

    private void validateGraph(final Graph graph, final String edgeOp) {

        final String rendered = graph.render();
        final InputStream in = new ByteArrayInputStream(rendered.getBytes());
        final DotParser.GraphContext graphContext = DotDiagramValidator.validate(in);
        Assert.assertNotNull(graphContext);

        final List<DotParser.StmtContext> parsedStatements = graphContext.stmt_list().stmt();
        Assert.assertEquals(3, parsedStatements.size());

        final List<String> statementTexts = parsedStatements.stream()
                .map(RuleContext::getText)
                .collect(Collectors.toList());

        for(int i = 1; i < 4; i++) {

            final String expectedString = "subgraph\"subgraph_" + i + "\"{\"node_" + i + "\";}";
            Assert.assertTrue("Expected [" + expectedString + "], in: " + statementTexts,
                    statementTexts.contains(expectedString));
        }

        // parsedStatements.stream().forEach(c -> Assert.assertEquals("goo", c.getText()));

        /*
        digraph "subgraphTest" {

        // Subgraph statements.
        subgraph  "subgraph_1" {

            // Node statements.
            "node_1"  ;
        }  ;

        subgraph  "subgraph_2" {

            // Node statements.
            "node_2"  ;

        }  ;

        subgraph  "subgraph_3" {

            // Node statements.
            "node_3"  ;
        }  ;
    }
         */
    }
}
