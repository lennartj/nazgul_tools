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

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.AttributeList;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class EdgeTest extends AbstractGraphTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullRightSideEdgeWithNodeID() {

        // Act & Assert
        new Edge(new NodeID("foo"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullRightSideEdgeWithSubgraph() {

        // Act & Assert
        new Edge(new Subgraph("foo"), null);
    }

    @Test
    public void validateRenderingWithAttributes() {

        // Assemble
        final Graph graph = createStandardGraph(true);
        final List<String> expectedTexts = new ArrayList<>(Arrays.asList(
                "\"node1\"",
                "\"node2\"",
                "\"node1\"=\"foobar\"",
                "\"node1\"->\"node2\"[key_1=\"value_1\",key_2=\"value_2\",key_3=\"value_3\"]"));

        final Edge unitUnderTest = graph.getStatements().findEdge("node1", "node2");
        Assert.assertNotNull(unitUnderTest);

        final AttributeList attributes = unitUnderTest.getAttributes();
        for (int i = 3; i > 0; i--) {
            attributes.addAttribute("key_" + i, "value_" + i);
        }

        // Act
        final String result = unitUnderTest.render();
        final DotParser.GraphContext graphContext = validateGraph(graph, expectedTexts);

        // Assert
        Assert.assertEquals("\"node1\"  ->  \"node2\"  [ key_1=\"value_1\", key_2=\"value_2\", key_3=\"value_3\" ]",
                result.trim());
        Assert.assertNotNull(graphContext);
        Assert.assertNotNull(result);
    }
}
