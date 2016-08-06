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
package se.jguru.nazgul.tools.visualization.api.diagram.dot;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.dot.EdgeRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.EdgeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowType;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.EdgeStyle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Edge;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.RightSideEdge;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Subgraph;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class EdgeRendererTest extends AbstractRendererTest {

    @Test
    public void validateRendering() {

        // Assemble
        final EdgeRenderer unitUnderTest = new EdgeRenderer();

        final Node node1 = new Node("node1");
        final Node node2 = new Node("node2");
        final Subgraph subgraph = new Subgraph("exampleSubgraph");
        final Graph trivialGraph = new Graph("exampleGraph", true, true);

        trivialGraph.add(node1, subgraph);
        subgraph.add(node2);

        final Edge nodeEdge = trivialGraph.addEdge("node1", "node2");
        final Edge subgraphEdge = new Edge(subgraph, RightSideEdge.to("node2", trivialGraph));
        trivialGraph.add(subgraphEdge);

        final EdgeAttributes attributes = subgraphEdge.getAttributes();
        attributes.arrowHead = new ArrowType(true, ArrowType.Shape.DIAMOND);
        attributes.textColor = StandardCssColor.RosyBrown;
        attributes.label = "FooBar!";
        attributes.style = EdgeStyle.BOLD;

        // Act
        final String renderedNodeEdge = unitUnderTest.render(renderConfiguration, nodeEdge);
        final String renderedSubgraphEdge = unitUnderTest.render(renderConfiguration, subgraphEdge);

        // Assert
        Assert.assertEquals("\"node1\" -> \"node2\"  ;" + renderConfiguration.getNewline(), renderedNodeEdge);
        Assert.assertEquals("\"exampleSubgraph\" -> \"node2\" [ \"arrowhead\"=\"diamond\", \"fontcolor\"=\"#BC8F8F\"," +
                " \"label\"=\"FooBar!\", \"style\"=\"bold\" ] ;" + renderConfiguration.getNewline(),
                renderedSubgraphEdge);
    }
}
