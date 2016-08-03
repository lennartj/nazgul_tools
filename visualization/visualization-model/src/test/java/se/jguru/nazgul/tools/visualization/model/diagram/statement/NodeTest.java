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
import se.jguru.nazgul.tools.visualization.model.diagram.Port;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.NodeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeTest extends AbstractEntityTest {

    // Shared state
    private Node unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        jaxb.add(GenericRoot.class, Node.class);

        unitUnderTest = new Node(new NodeID("node1", new Port(Port.CompassPoint.NORTH)));

        final NodeAttributes attributes = unitUnderTest.getAttributes();
        attributes.numberOfSides = 8;
        attributes.textColor = StandardCssColor.Brown;

        container = new GenericRoot(unitUnderTest);
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
        validateIdenticalXml("testdata/statement/node.xml", marshalledXml);
        validateIdenticalJson("testdata/statement/node.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class, "testdata/statement/node.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class, "testdata/statement/node.json");

        final Node xmlNodeID = getNodeFrom(fromXml);
        final Node jsonNodeID = getNodeFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest, xmlNodeID);
        validateIdenticalPublicFields(unitUnderTest, jsonNodeID);

        Assert.assertEquals(unitUnderTest, xmlNodeID);
        Assert.assertEquals(unitUnderTest, jsonNodeID);

        Assert.assertEquals(unitUnderTest.hashCode(), xmlNodeID.hashCode());
        Assert.assertEquals(unitUnderTest.hashCode(), jsonNodeID.hashCode());

        Assert.assertEquals(unitUnderTest.getNodeID(), xmlNodeID.getNodeID());
        Assert.assertEquals(unitUnderTest.getNodeID(), jsonNodeID.getNodeID());
    }

    @Test
    public void validateNodeId() {

        // Assemble
        final Node aNode = new Node("foo");

        // Act & Assert
        Assert.assertEquals(aNode.getId(), aNode.getNodeID().getId());
    }

    //
    // Private helpers
    //

    private Node getNodeFrom(final GenericRoot genericRoot) {
        return (Node) genericRoot.getItems().get(0);
    }
}
