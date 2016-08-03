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

    /*
    @Test
    public void validateComparisons() {

        // Assemble
        final Graph gnatGraph = createStandardGraph("gnat", true);
        final Graph anotherGnatGraph = createStandardGraph("gnat", true);

        // Act & Assert
        Assert.assertTrue(gnatGraph.equals(gnatGraph));
        Assert.assertTrue(gnatGraph.equals(anotherGnatGraph));
        Assert.assertFalse(gnatGraph.equals("gnat"));
        Assert.assertFalse(gnatGraph.equals(null));
        Assert.assertFalse(gnatGraph.equals(unitUnderTest));
        Assert.assertEquals(gnatGraph.hashCode(), anotherGnatGraph.hashCode());
    }
    */

    //
    // Private helpers
    //

    private Identifier getIdentifierFrom(final GenericRoot genericRoot) {
        return (Identifier) genericRoot.getItems().get(0);
    }
}
