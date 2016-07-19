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
package se.jguru.nazgul.tools.visualization.api.diagram;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Attribute;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Edge;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Identifier;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.spi.dot.DotDiagramValidator;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphTest {

    @Test
    public void validateCreatingDigraph() {

        // Assemble
        final Graph unitUnderTest = new Graph("foobar", true, true);

        final Node node1 = new Node("node1");
        final Node node2 = new Node("node2");

        unitUnderTest.add(node1, node2);
        unitUnderTest.add(new Identifier(node1.getId(), "foobar"), null);
        unitUnderTest.add(null);
        unitUnderTest.addEdge("node1", "node2");

        // Act
        final String result = unitUnderTest.render();
        final InputStream in = new ByteArrayInputStream(result.getBytes());

        /*
        strict digraph "foobar" {

        // Node statements.
        "node1"  ;
        "node2"  ;

        // Identifier statements.
        "node1"  = "foobar"  ;

        // Edge statements.
        "node1"  ->  "node2"  ;
        }
        */

        // Assert
        final DotParser.GraphContext graphContext = DotDiagramValidator.validate(in);
        Assert.assertNotNull(graphContext);

        final List<DotParser.StmtContext> parsedStatements = graphContext.stmt_list().stmt();
        Assert.assertEquals(4, parsedStatements.size());

        final SortedMap<Integer, String> expectedTexts = new TreeMap<>();
        expectedTexts.put(0, "\"node1\"");
        expectedTexts.put(1, "\"node2\"");
        expectedTexts.put(2, "\"node1\"=\"foobar\"");
        expectedTexts.put(3, "\"node1\"->\"node2\"");
    }

    @Test
    public void createSimpleExampleGraph() {

        // Create the Graph parent.
        final boolean directed = true;
        final boolean strict = true;
        final Graph graph = new Graph("Example", directed, strict);

        // Add two Nodes to the Graph
        final Node node1 = new Node("node_1");
        final Node node2 = new Node("node_2");
        graph.add(node1, node2);

        // Add an extra identifier for node 1.
        graph.add(new Identifier(node1.getId(), "foobar"));

        // Add an Edge between Node1 and Node2.
        // Note:
        graph.addEdge("node_1", "node_2");

        // Render the Graph to create a Graphviz/Dot diagram
        final String diagram = graph.render();
        System.out.println("Got:\n" + diagram);
    }

    /*
    strict digraph "Example" {

        // Attribute statements.
        graph [ bgcolor="#eeeeee" ] ;
        node [ color="#555599", fillcolor="#cccccc", penwidth="2.0", style="filled" ] ;

        // Node statements.
        "First_Node"  [ color="#aa0000", fillcolor="#ffffff" ] ;
        "Second_Node" ;
        "Third_Node"  [ color="#ffffff", fillcolor="#333333", fontcolor="#ffffff" ] ;

        // Edge statements.
        "First_Node"  ->  "Second_Node"  ;
        "Second_Node"  ->  "Third_Node"  [ color="#0000ff", penwidth="2.0" ] ;
        "Third_Node"  ->  "First_Node"  ;
    }
     */

    @Test
    public void createExampleGraphWithAttributes() {

        // Create a directed and strict Graph parent.
        // Add some default attributes for the Graph and for its Nodes.
        //
        final Graph graph = new Graph("Example", true, true);
        graph.add(Attribute.AttributeType.GRAPH.get().addAttribute("bgcolor", "#eeeeee"));
        graph.add(Attribute.AttributeType.NODE.get()
                .addAttribute("color", "#555599")
                .addAttribute("fillcolor", "#cccccc")
                .addAttribute("penwidth", "2.0")
                .addAttribute("style", "filled"));

        // Add some Nodes to the Graph
        // Add some Attributes to override the standard ones.
        //
        final List<Node> nodes = Stream.of("First", "Second", "Third")
                .map(c -> new Node(c + "_Node"))
                .collect(Collectors.toList());

        nodes.get(0).getAttributes()
                .addAttribute("color", "#aa0000")
                .addAttribute("fillcolor", "#ffffff");

        nodes.get(2).getAttributes()
                .addAttribute("color", "#ffffff")
                .addAttribute("fillcolor", "#333333")
                .addAttribute("fontcolor", "#ffffff");

        graph.add(nodes.toArray(new Node[nodes.size()]));

        // Add Edges between the Nodes
        // Override some Attributes on the second Edge.
        //
        graph.addEdge("First_Node", "Second_Node");
        final Edge secondEdge = graph.addEdge("Second_Node", "Third_Node");
        graph.addEdge("Third_Node", "First_Node");

        secondEdge.getAttributes()
                .addAttribute("color", "#0000ff")
                .addAttribute("penwidth", "2.0");

        // Render the Graph to create a Graphviz/Dot diagram
        //
        final String diagram = graph.render();
        System.out.println("Got:\n" + diagram);
    }
}
