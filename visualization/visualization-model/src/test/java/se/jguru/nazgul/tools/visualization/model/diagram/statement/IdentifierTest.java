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
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class IdentifierTest extends AbstractEntityTest {

    // Shared state
    private Identifier unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        jaxb.add(GenericRoot.class, Identifier.class);

        unitUnderTest = new Identifier("foo", "bar");
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
        validateIdenticalXml("testdata/statement/identifier.xml", marshalledXml);
        validateIdenticalJson("testdata/statement/identifier.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class, "testdata/statement/identifier.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class, "testdata/statement/identifier.json");

        final Identifier xmlNodeID = getIdentifierFrom(fromXml);
        final Identifier jsonNodeID = getIdentifierFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest, xmlNodeID);
        validateIdenticalPublicFields(unitUnderTest, jsonNodeID);

        Assert.assertEquals(unitUnderTest, xmlNodeID);
        Assert.assertEquals(unitUnderTest, jsonNodeID);

        Assert.assertEquals(unitUnderTest.hashCode(), xmlNodeID.hashCode());
        Assert.assertEquals(unitUnderTest.hashCode(), jsonNodeID.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullIdentifier() {
        new Identifier(null, "bar");
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

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnEqualIdentifiers() {
        new Identifier("foo", "foo");
    }


    @Test
    public void validateComparisons() {

        // Assemble
        final Identifier identifier1 = new Identifier("foo", "bar");
        final Identifier identifier3 = new Identifier("gnat", "gnu");

        // Act & Assert
        Assert.assertTrue(identifier1.equals(unitUnderTest));
        Assert.assertTrue(identifier1.equals(identifier1));
        Assert.assertFalse(unitUnderTest.equals(identifier3));
        Assert.assertFalse(identifier1.equals(null));
        Assert.assertFalse(identifier3.equals(unitUnderTest));
        Assert.assertEquals(identifier1.hashCode(), unitUnderTest.hashCode());
    }

    //
    // Private helpers
    //

    private Identifier getIdentifierFrom(final GenericRoot genericRoot) {
        return (Identifier) genericRoot.getItems().get(0);
    }
}
