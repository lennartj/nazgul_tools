/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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

package se.jguru.nazgul.tools.visualization.api.diagram.dot;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.dot.CommonEdgeAttributesRenderer;
import se.jguru.nazgul.tools.visualization.api.dot.CommonGraphAttributesRenderer;
import se.jguru.nazgul.tools.visualization.api.dot.CommonNodeAttributesRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.EdgeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.GraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.NodeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowDirection;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowType;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ClusterMode;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.EdgeStyle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.HorizontalAlignment;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.VerticalAlignment;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonEdgeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonGraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonNodeAttributes;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommonAttributesRendererTest extends AbstractRendererTest {

    @Test
    public void validateRenderingCommonGraphAttributes() {

        // Assemble
        final String expected = "graph [ \"lheight\"=\"15.0\", \"lwidth\"=\"10.5\", charset=\"UTF-8\", "
                + "clusterrank=\"global\", fontcolor=\"#F0F8FF\", label=\"Example Graph\", labeljust=\"c\", margin=\""
                + "(2.0,2.0)\" ] ;" + renderConfiguration.getNewline();

        final CommonGraphAttributesRenderer unitUnderTest = new CommonGraphAttributesRenderer();
        final CommonGraphAttributes commonGraphAttributes = new CommonGraphAttributes();
        final GraphAttributes graphAttrs = commonGraphAttributes.getAttributes();

        graphAttrs.labelSize = new PointOrRectangle(10.5, 15);
        graphAttrs.label = "Example Graph";
        graphAttrs.textColor = StandardCssColor.AliceBlue;
        graphAttrs.clusterRank = ClusterMode.GLOBAL;
        graphAttrs.labelHorizontalAlignment = HorizontalAlignment.CENTER;
        graphAttrs.margin = new PointOrRectangle(2, 2);

        // Act
        final String result = unitUnderTest.render(renderConfiguration, commonGraphAttributes);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void validateRenderingCommonNodeAttributes() {

        // Assemble
        final String expected = "node [ fontcolor=\"#A52A2A\", labelloc=\"c\", orientation=\"45\", sides=\"8\" ] ;"
                + renderConfiguration.getNewline();

        final CommonNodeAttributesRenderer unitUnderTest = new CommonNodeAttributesRenderer();
        final CommonNodeAttributes commonNodeAttributes = new CommonNodeAttributes();
        final NodeAttributes nodeAttrs = commonNodeAttributes.getAttributes();

        nodeAttrs.numberOfSides = 8;
        nodeAttrs.degreesRotated = 45;
        nodeAttrs.labelAlignment = VerticalAlignment.CENTER;
        nodeAttrs.textColor = StandardCssColor.Brown;

        // Act
        final String result = unitUnderTest.render(renderConfiguration, commonNodeAttributes);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void validateRenderingCommonEdgeAttributes() {

        // Assemble
        final String expected =  "edge [ arrowhead=\"obox\", dir=\"both\", penwidth=\"2\", style=\"bold\" ] ;"
                + renderConfiguration.getNewline();

        final CommonEdgeAttributesRenderer unitUnderTest = new CommonEdgeAttributesRenderer();
        final CommonEdgeAttributes commonEdgeAttributes = new CommonEdgeAttributes();
        final EdgeAttributes edgeAttrs = commonEdgeAttributes.getAttributes();

        edgeAttrs.style = EdgeStyle.BOLD;
        edgeAttrs.arrowHead = new ArrowType(false, ArrowType.Shape.BOX);
        edgeAttrs.direction = ArrowDirection.BOTH;
        edgeAttrs.penWidthInPoints = 2;

        // Act
        final String result = unitUnderTest.render(renderConfiguration, commonEdgeAttributes);

        // Assert
        Assert.assertEquals(expected, result);
    }
}
