/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2017 jGuru Europe AB
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
import se.jguru.nazgul.tools.visualization.api.dot.AttributeRenderer;
import se.jguru.nazgul.tools.visualization.api.dot.GraphRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.EdgeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.GraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.NodeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowDirection;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowType;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ClusterMode;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.EdgeStyle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.HorizontalAlignment;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.VerticalAlignment;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonEdgeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonGraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonNodeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.util.ArrayList;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class AttributeRendererTest extends AbstractRendererTest {

    // Shared state
    private AttributeRenderer unitUnderTest = new AttributeRenderer();
    private GraphRenderer graphRenderer = new GraphRenderer();


    @Test
    public void validateRenderingAllAttributes() {

        // Assemble
        final String expected = "digraph \"foobar\" {" + newline + 
                "  " + newline + 
                "  // Common Attribute Statements" + newline + 
                "  graph [ charset=\"UTF-8\", clusterrank=\"global\", fontcolor=\"#FF0000\", label=\"Example Graph\", labeljust=\"c\", margin=\"(2.0,2.0)\" ] ;" + newline +
                "  node [ fontcolor=\"#A52A2A\", labelloc=\"c\", orientation=\"45\", sides=\"8\" ] ;" + newline + 
                "  edge [ arrowhead=\"box\", dir=\"both\", penwidth=\"2\", style=\"bold\" ] ;" + newline + 
                "" + newline + 
                "  // Node Statements" + newline + 
                "  \"node1\" [ bgcolor=\"#FAFAD2\", fontcolor=\"#006400\", label=\"FooBar!\" ] ;" + newline + 
                "  \"node2\" ;" + newline + 
                "" + newline + 
                "  // Identifier Statements" + newline + 
                "  \"node1\" = \"foobar\" ;" + newline + 
                "" + newline + 
                "  // Edge Statements" + newline + 
                "  \"node1\" -> \"node2\"  ;" + newline + 
                "}";

        final Graph graph = createStandardGraph(true);
        graph.add(new CommonGraphAttributes(), new CommonEdgeAttributes(), new CommonNodeAttributes());

        final GraphAttributes graphAttrs = graph.getStatements().getCommonGraphAttributes().getAttributes();
        final EdgeAttributes edgeAttrs = graph.getStatements().getCommonEdgeAttributes().getAttributes();
        final NodeAttributes nodeAttrs = graph.getStatements().getCommonNodeAttributes().getAttributes();

        graphAttrs.label = "Example Graph";
        graphAttrs.textColor = StandardCssColor.Red;
        graphAttrs.clusterRank = ClusterMode.GLOBAL;
        graphAttrs.labelHorizontalAlignment = HorizontalAlignment.CENTER;
        graphAttrs.margin = new PointOrRectangle(2, 2);

        edgeAttrs.style = EdgeStyle.BOLD;
        edgeAttrs.arrowHead = new ArrowType(true, ArrowType.Shape.BOX);
        edgeAttrs.direction = ArrowDirection.BOTH;
        edgeAttrs.penWidthInPoints = 2;

        nodeAttrs.numberOfSides = 8;
        nodeAttrs.degreesRotated = 45;
        nodeAttrs.labelAlignment = VerticalAlignment.CENTER;
        nodeAttrs.textColor = StandardCssColor.Brown;

        final Node node1 = graph.getStatements().findNode("node1", true);
        final NodeAttributes node1Attributes = node1.getAttributes();

        node1Attributes.label = "FooBar!";
        node1Attributes.textColor = StandardCssColor.DarkGreen;
        node1Attributes.backgroundColor = StandardCssColor.LightGoldenRodYellow;

        // Act
        final String graphResult = graphRenderer.render(renderConfiguration, graph);
        final DotParser.GraphContext graphContext = validateGraph(graph, null);

        // Assert
        Assert.assertEquals(expected, graphResult);

        Assert.assertNotNull(graphContext.DIGRAPH());
        Assert.assertNull(graphContext.STRICT());
        Assert.assertNull(graphContext.GRAPH());
    }
}
