/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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

package se.jguru.nazgul.tools.visualization.api.diagram.dot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.dot.StatementsRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Edge;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Identifier;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.RightSideEdge;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statements;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Subgraph;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class StatementsRendererTest extends AbstractRendererTest {

    // Shared state
    private Statements toRender;
    private StatementsRenderer unitUnderTest;

    @Before
    @Override
    public void setupSharedState() {
        super.setupSharedState();

        // Setup shared state
        unitUnderTest = new StatementsRenderer();
        this.toRender = new Statements();

        toRender.add(
                new Node("node1"),
                new Node("node2"),
                new Identifier("node1", "firstNode"),
                new Edge(new NodeID("node1"), new RightSideEdge(new NodeID("node2"))));
    }

    @Test
    public void validateRendering() {

        // Assemble
        final String expected = newline +
                "// Node Statements" + newline +
                "\"node1\" ;" + newline +
                "\"node2\" ;" + newline +
                "" + newline +
                "// Identifier Statements" + newline +
                "\"node1\" = \"firstNode\" ;" + newline +
                "" + newline +
                "// Edge Statements" + newline +
                "\"node1\" -> \"node2\"  ;" + newline;

        final StatementsRenderer unitUnderTest = new StatementsRenderer();

        // Act
        final boolean isAccepted = unitUnderTest.accept(toRender);
        final String result = unitUnderTest.render(renderConfiguration, toRender);

        // Assert
        Assert.assertTrue(isAccepted);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void validateRenderingWithSubgraph() {

        // Assemble
        final String expected = newline
                +  "// Node Statements"
                + newline +
                "\"node1\" ;" + newline + 
                "\"node2\" ;" + newline + 
                newline + 
                "// Identifier Statements" + newline + 
                "\"node1\" = \"firstNode\" ;" + newline + 
                newline + 
                "// Edge Statements" + newline + 
                "\"node1\" -> \"node2\"  ;" + newline + 
                newline + 
                "// Subgraph Statements" + newline + 
                "subgraph \"foobar\" { " + newline + 
                "  " + newline + 
                "  // Node Statements" + newline + 
                "  \"node3\" ;" + newline + 
                newline + 
                "  // Edge Statements" + newline + 
                "  \"node3\" -> foobar  ;" + newline + 
                newline + 
                "}" + newline;
        
        final Subgraph subgraph = new Subgraph("foobar");
        subgraph.add(new Node("node3"), new Edge(new NodeID("node3"), new RightSideEdge(subgraph)));
        toRender.add(subgraph);

        // Act
        final String result = unitUnderTest.render(renderConfiguration, toRender);

        // Assert
        Assert.assertEquals(expected, result);
    }
}
