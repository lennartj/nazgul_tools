/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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
package se.jguru.nazgul.tools.visualization.model.diagram.statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.AbstractEntityTest;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class EdgeTest extends AbstractEntityTest {

    // Shared state
    private Edge unitUnderTest1, unitUnderTest2;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        jaxb.add(GenericRoot.class, Node.class, Edge.class);

        unitUnderTest1 = new Edge(new NodeID("sourceNode"), new RightSideEdge(new NodeID(("targetNode"))));
        unitUnderTest2 = new Edge(new Subgraph("sourceSubgraph"), new RightSideEdge(new Subgraph(("targetSubgraph"))));
        container = new GenericRoot(unitUnderTest1, unitUnderTest2);
    }

    @Test
    public void validateMarshalling() {

        // Assemble

        // Act
        final String marshalledXml = marshalAsXml(container);
        final String marshalledJson = marshalAsJson(container);

        // System.out.println("Got: " + marshalledXml);
        // System.out.println("Got: " + marshalledJson);

        // Assert
        validateIdenticalXml("testdata/statement/edges.xml", marshalledXml);
        validateIdenticalJson("testdata/statement/edges.json", marshalledJson);
    }

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
}