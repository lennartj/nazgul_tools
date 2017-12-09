/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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

package se.jguru.nazgul.tools.visualization.model.diagram.statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.AbstractEntityTest;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RightSideEdgeTest extends AbstractEntityTest {

    // Shared state
    private Edge edge;
    private Graph graph;

    @Before
    public void setupSharedState() {

        jaxb.add(Graph.class, Node.class, Edge.class);

        graph = new Graph("rightSideEdgeTestGraph", true, true);
        graph.add(new Node("node1"), new Node("node2"));

        final Subgraph subgraph = new Subgraph("subgraph");
        subgraph.add(new Node("node3"), new Node("node4"));
        graph.add(subgraph);

        edge = graph.addEdge("node1", "node3");
    }

    @Test
    public void validateMarshalling() {

        // Assemble
        final RightSideEdge rightSideEdge = edge.getRightSideEdge().to("node4", graph);

        // Act
        final String marshalledXml = marshalAsXml(graph);
        final String marshalledJson = marshalAsJson(graph);

        // System.out.println("Got: " + marshalledXml);
        // System.out.println("Got: " + marshalledJson);

        // Assert
        validateIdenticalXml("testdata/statement/rightsideedge.xml", marshalledXml);
        validateIdenticalJson("testdata/statement/rightsideedge.json", marshalledJson);

        Assert.assertEquals("->", RightSideEdge.getEdgeSeparator(graph.isDigraph()));
        Assert.assertNull(rightSideEdge.getRightSideEdge());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullRightSideEdgeToId() {
        RightSideEdge.to(null, graph);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullRightSideEdgeToGraph() {
        RightSideEdge.to("node1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnSettingNullRightSideEdge() {

        // Assemble
        final RightSideEdge rightSideEdge = edge.getRightSideEdge().to("node4", graph);

        // Act & Assert
        rightSideEdge.setRightSideEdge(null);
    }

    @Test
    public void validateSettingRightSideEdgeOfRightSideEdge() {

        // Assemble
        final RightSideEdge rightSideEdge = edge.getRightSideEdge().to("node4", graph);
        rightSideEdge.setRightSideEdge(RightSideEdge.to("subgraph", graph));

        // Act & Assert
        final RightSideEdge rightmostSideEdge = rightSideEdge.getRightSideEdge();
        Assert.assertNotNull(rightmostSideEdge);
        Assert.assertNull(RightSideEdge.to("notDefined", graph));
    }

    /*
    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class, "testdata/statement/edges.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class, "testdata/statement/edges.json");

        final Edge[] xmlEdges = getEdgesFrom(fromXml);
        final Edge[] jsonEdges = getEdgesFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest1, xmlEdges[0]);
        validateIdenticalPublicFields(unitUnderTest2, xmlEdges[1]);
        validateIdenticalPublicFields(unitUnderTest1, jsonEdges[0]);
        validateIdenticalPublicFields(unitUnderTest1, jsonEdges[1]);

        Assert.assertEquals(unitUnderTest1, xmlEdges[0]);
        Assert.assertEquals(unitUnderTest1, jsonEdges[0]);
        Assert.assertEquals(unitUnderTest2, xmlEdges[1]);
        Assert.assertEquals(unitUnderTest2, jsonEdges[1]);


        Assert.assertEquals(unitUnderTest1.hashCode(), xmlEdges[0].hashCode());
        Assert.assertEquals(unitUnderTest2.hashCode(), xmlEdges[1].hashCode());
        Assert.assertEquals(unitUnderTest1.hashCode(), jsonEdges[0].hashCode());
        Assert.assertEquals(unitUnderTest2.hashCode(), jsonEdges[1].hashCode());

        Assert.assertNotNull(unitUnderTest1.getAttributes());
        Assert.assertNotNull(xmlEdges[0].getAttributes());
        Assert.assertNotNull(jsonEdges[0].getAttributes());

        Assert.assertNotNull(unitUnderTest1.getRightSideEdge());
        Assert.assertNotNull(xmlEdges[0].getRightSideEdge());
        Assert.assertNotNull(jsonEdges[0].getRightSideEdge());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullRightSideEdgeWithNodeID() {
        new Edge(new NodeID("foo"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullRightSideEdgeWithSubgraph() {
        new Edge(new Subgraph("foo"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnEmptyIdentifier() {
        new Identifier("", "bar");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullTargetIdentifier() {
        new Identifier("foo", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnEmptyTargetIdentifier() {
        new Identifier("foo", "");
    }

    //
    // Private helpers
    //

    private Edge[] getEdgesFrom(final GenericRoot genericRoot) {
        Edge[] toReturn = new Edge[2];

        return genericRoot.getItems().toArray(toReturn);
    }
    */
}
