package se.jguru.nazgul.tools.visualization.api.diagram;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeIDTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullId() {
        new NodeID(null, null);
    }

    @Test
    public void validateRenderingWithNullPort() {

        // Assemble
        final NodeID unitUnderTest = new NodeID("foobar", null);

        // Act
        final String result = unitUnderTest.render();

        // Assert
        Assert.assertEquals("foobar", result.trim());
    }

    @Test
    public void validateRenderingWithPort() {

        // Assemble
        final NodeID.Port port = new NodeID.Port("aPort", NodeID.CompassPoint.NORTH);
        final NodeID unitUnderTest = new NodeID("foobar", port);

        // Act
        final String result = unitUnderTest.render();

        // Assert
        Assert.assertEquals("foobar : aPort : n", result.trim());
    }

    @Test
    public void validateRenderingWithIDlessPort() {

        // Assemble
        final NodeID.Port port = new NodeID.Port(NodeID.CompassPoint.NORTH_EAST);
        final NodeID unitUnderTest = new NodeID("foobar", port);

        // Act
        final String result = unitUnderTest.render();

        // Assert
        Assert.assertSame(NodeID.CompassPoint.NORTH_EAST, unitUnderTest.getPort().getCompassPoint());
        Assert.assertEquals("foobar", unitUnderTest.getId());
        Assert.assertEquals("foobar : ne", result.trim());
        Assert.assertEquals("[NodeID]: foobar : ne ", unitUnderTest.toString());
    }
}
