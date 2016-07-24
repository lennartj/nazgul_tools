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

import org.junit.Rule;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.EdgeAttributeList;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.GraphAttributeList;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.model.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.model.StandardCssColor;
import se.jguru.nazgul.tools.visualization.api.diagram.jaxb.PlainJaxbContextRule;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute.AttributeStatement;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute.NodeAttribute;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class AttributeTest extends AbstractGraphTest {

    @Rule public PlainJaxbContextRule jaxb = new PlainJaxbContextRule();

    @Test
    public void validateGraphWithAttributes() {

        // Assemble
        final Graph graph = createAttributedGraph();
        final List<String> expectedTexts = (Arrays.asList(
                "edge[color=\"#FAFAD2\"]",
                "graph[bgcolor=\"#7FFFD4\",margin=\"10.0,10.0\",pad=\"5.0,5.0\"]",
                "node[gnu=\"gnorp\"]",
                "\"node1\"[some=\"attribute\"]",
                "\"node2\"",
                "\"node1\"=\"foobar\"",
                "\"node1\"->\"node2\""));

        // Act & Assert
        validateGraph(graph, expectedTexts);
    }

    @Test
    public void validateMarshalling() {

        // Assemble
        final Graph graph = createAttributedGraph();
        jaxb.add(Graph.class, AttributeStatement.class);

        // Act
        final String result = jaxb.marshal(Thread.currentThread().getContextClassLoader(), false, null, graph);
        System.out.println("Got: " + result);

        // Assert
    }

    //
    // Private helpers
    //

    private Graph createAttributedGraph() {

        final Graph toReturn = createStandardGraph(true);

        // First, use the builder pattern to add a new GraphAttribute statement.
        toReturn.add(new GraphAttributeList()
                .withBgColor(StandardCssColor.Aquamarine)
                .withPad(new PointOrRectangle(5, 5))
                .withMargin(new PointOrRectangle(10, 10))
                .toGraphAttributeStatement());

        // Second, use the generic addAttribute method to enable
        // adding an arbitrary property.
        final NodeAttribute nodeAttributeStatement = new NodeAttribute();
        nodeAttributeStatement.getAttributes()
                .addAttribute("gnu", "gnorp");
        toReturn.add(nodeAttributeStatement);

        // Third, use the builder pattern to add a new EdgeAttributeStatement.
        toReturn.add(new EdgeAttributeList()
                .withColor(StandardCssColor.LightGoldenRodYellow)
                .toEdgeAttributeStatement());

        final Node node1 = toReturn.getStatements().findNode("node1", false);
        node1.getAttributes().addAttribute("some", "attribute");

        return toReturn;
    }
}
