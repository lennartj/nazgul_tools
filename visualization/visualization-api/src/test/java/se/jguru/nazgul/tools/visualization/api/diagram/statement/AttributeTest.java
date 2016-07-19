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

import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class AttributeTest extends AbstractGraphTest {

    @Test
    public void validateGraphWithAttributes() {

        // Assemble
        final Graph graph = createStandardGraph(true);
        final List<String> expectedTexts = (Arrays.asList(
                "edge[foo=\"bar\"]",
                "graph[baz=\"gnat\"]",
                "node[gnu=\"gnorp\"]",
                "\"node1\"[some=\"attribute\"]",
                "\"node2\"",
                "\"node1\"=\"foobar\"",
                "\"node1\"->\"node2\""));

        graph.add(Attribute.AttributeType.EDGE.get().addAttribute("foo", "bar"));
        graph.add(Attribute.AttributeType.GRAPH.get().addAttribute("baz", "gnat"));
        graph.add(Attribute.AttributeType.NODE.get().addAttribute("gnu", "gnorp"));

        final Node node1 = graph.getStatements().find(Node.class, "node1");
        node1.getAttributes().addAttribute("some", "attribute");

        // Act
        validateGraph(graph, expectedTexts);
    }
}
