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
package se.jguru.nazgul.tools.visualization.model.diagram;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.AbstractEntityTest;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeIDTest extends AbstractEntityTest {

    // Shared state
    private NodeID unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        jaxb.add(GenericRoot.class, NodeID.class);

        unitUnderTest = new NodeID("someNode", new Port(Port.CompassPoint.EAST));
        container = new GenericRoot(unitUnderTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullId() {
        new NodeID(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnSettingNullPort() {

        // Act & Assert
        unitUnderTest.setPort(null);
    }

    @Test
    public void validatePortHandling() {

        // Assemble
        unitUnderTest.setPort(new Port(Port.CompassPoint.NORTH));

        // Act
        final Port thePort = unitUnderTest.getPort();
        final String compassPointDotToken = thePort.getCompassPoint().getDotToken();

        // Assert
        Assert.assertEquals("n", compassPointDotToken);
        Assert.assertEquals("[NodeID]: someNode, ([Port]: <anonymous>, CompassPoint: NORTH)", unitUnderTest.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullPort() {

        // Assemble
        final NodeID unitUnderTest = new NodeID("node21", null);

        // Act & Assert
        unitUnderTest.setPort(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullCompassPoint() {
        new NodeID(null);
    }

    @Test
    public void validateComparisonAndEquality() {

        // Assemble
        final NodeID nodeID1 = new NodeID("node1");
        final NodeID nodeID2 = new NodeID("node2");

        // Act & Assert
        Assert.assertEquals(0, nodeID1.compareTo(nodeID1));
        Assert.assertEquals(nodeID1.hashCode(), nodeID1.hashCode());
        Assert.assertEquals(-1, nodeID1.compareTo(null));
        Assert.assertEquals("node1".compareTo("node2"), nodeID1.compareTo(nodeID2));

        Assert.assertTrue(nodeID1.equals(nodeID1));
        Assert.assertFalse(nodeID1.equals(null));
        Assert.assertFalse(nodeID1.equals("node1"));
        Assert.assertFalse(nodeID1.equals(nodeID2));
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
        validateIdenticalXml("testdata/nodeId.xml", marshalledXml);
        validateIdenticalJson("testdata/nodeId.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class, "testdata/nodeId.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class, "testdata/nodeId.json");

        final NodeID xmlNodeID = getNodeIDFrom(fromXml);
        final NodeID jsonNodeID = getNodeIDFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest, xmlNodeID);
        validateIdenticalPublicFields(unitUnderTest, jsonNodeID);

        Assert.assertEquals(unitUnderTest, xmlNodeID);
        Assert.assertEquals(unitUnderTest, jsonNodeID);

        Assert.assertEquals(unitUnderTest.hashCode(), xmlNodeID.hashCode());
        Assert.assertEquals(unitUnderTest.hashCode(), jsonNodeID.hashCode());
    }

    //
    // Private helpers
    //

    private NodeID getNodeIDFrom(final GenericRoot genericRoot) {
        return (NodeID) genericRoot.getItems().get(0);
    }
}
