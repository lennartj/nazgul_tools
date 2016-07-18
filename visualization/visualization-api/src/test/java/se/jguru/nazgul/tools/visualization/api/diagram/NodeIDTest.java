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

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeIDTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullId() {
        new NodeID(null, null);
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
        new NodeID.Port(null);
    }

    @Test
    public void validateRenderingWithNullPort() {

        // Assemble
        final NodeID unitUnderTest = new NodeID("foobar", null);

        // Act
        final String result = unitUnderTest.render();

        // Assert
        Assert.assertEquals("\"foobar\"", result.trim());
    }

    @Test
    public void validateRenderingWithPort() {

        // Assemble
        final NodeID.Port port = new NodeID.Port("aPort", NodeID.CompassPoint.NORTH);
        final NodeID unitUnderTest = new NodeID("foobar", port);

        // Act
        final String result = unitUnderTest.render();

        // Assert
        Assert.assertEquals("\"foobar\" : aPort : n", result.trim());
        Assert.assertEquals("aPort", port.getId());
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
        Assert.assertEquals("\"foobar\" : ne", result.trim());
        Assert.assertEquals("[NodeID]:  \"foobar\" : ne", unitUnderTest.toString());
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
}
