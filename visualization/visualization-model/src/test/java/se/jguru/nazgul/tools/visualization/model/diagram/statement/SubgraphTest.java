/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class SubgraphTest extends AbstractEntityTest {

    // Shared state
    private Node node1, node2;
    private Subgraph unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        jaxb.add(GenericRoot.class, Subgraph.class, Node.class);

        unitUnderTest = new Subgraph("subgraph");

        node1 = new Node(new NodeID("node1", new Port(Port.CompassPoint.NORTH)));
        node2 = new Node("node2");
        unitUnderTest.add(node1, node2);

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
        validateIdenticalXml("testdata/statement/subgraph.xml", marshalledXml);
        validateIdenticalJson("testdata/statement/subgraph.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class, "testdata/statement/subgraph.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class, "testdata/statement/subgraph.json");

        final Subgraph xmlSubgraph = getSubgraphFrom(fromXml);
        final Subgraph jsonSubgraph = getSubgraphFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest, xmlSubgraph);
        validateIdenticalPublicFields(unitUnderTest, jsonSubgraph);

        Assert.assertEquals(unitUnderTest, xmlSubgraph);
        Assert.assertEquals(unitUnderTest, jsonSubgraph);

        Assert.assertEquals(unitUnderTest.hashCode(), xmlSubgraph.hashCode());
        Assert.assertEquals(unitUnderTest.hashCode(), jsonSubgraph.hashCode());
    }

    //
    // Private helpers
    //

    private Subgraph getSubgraphFrom(final GenericRoot genericRoot) {
        return (Subgraph) genericRoot.getItems().get(0);
    }
}
