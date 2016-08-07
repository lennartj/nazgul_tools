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
package se.jguru.nazgul.tools.visualization.api.diagram.dot;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.dot.NodeRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.model.diagram.Port;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.NodeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Node;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeRendererTest extends AbstractRendererTest {

    @Test
    public void validateRendering() {

        // Assemble
        final Node plainNode = new Node("node1");
        final Node nodeWithPort = new Node(
                new NodeID("node2", new Port("portIdentifier", Port.CompassPoint.NORTH_WEST)));

        final NodeRenderer unitUnderTest = new NodeRenderer();

        // Act
        final String renderedPlainNode = unitUnderTest.render(renderConfiguration, plainNode);
        final String renderedNodeWithPort = unitUnderTest.render(renderConfiguration, nodeWithPort);

        // Assert
        Assert.assertEquals("\"node1\" ;" + renderConfiguration.getNewline(), renderedPlainNode);
        Assert.assertEquals("\"node2\": \"portIdentifier\" : nw ;" + renderConfiguration.getNewline(),
                renderedNodeWithPort);
    }

    @Test
    public void validateRenderingWithAttributes() {

        // Assemble
        final Node plainNode = new Node("node1");
        final NodeAttributes attributes = plainNode.getAttributes();
        attributes.numberOfSides = 6;
        attributes.margin = new PointOrRectangle(2.0, 3.0);

        final NodeRenderer unitUnderTest = new NodeRenderer();

        // Act
        final String renderedPlainNode = unitUnderTest.render(renderConfiguration, plainNode);

        // Assert
        Assert.assertEquals("\"node1\" [ margin=\"(2.0,3.0)\", sides=\"6\" ] ;"
                + renderConfiguration.getNewline(),
                renderedPlainNode);

    }
}
