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

package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.AbstractEntityTest;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.VerticalAlignment;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeAttributesTest extends AbstractEntityTest {

    // Shared state
    private NodeAttributes unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        // Configure the PlainJaxbContextRule
        jaxb.add(GenericRoot.class, NodeAttributes.class);

        // Create the unit under test
        unitUnderTest = new NodeAttributes();

        unitUnderTest.degreesRotated = 45;
        unitUnderTest.backgroundColor = StandardCssColor.Silver;
        unitUnderTest.labelAlignment = VerticalAlignment.BOTTOM;
        unitUnderTest.distortion = 5.5;
        unitUnderTest.label = "This is a Node";
        unitUnderTest.margin = new PointOrRectangle(4.0, 5.0);
        unitUnderTest.penWidthInPoints = 2;
        unitUnderTest.textColor = StandardCssColor.Brown;
        unitUnderTest.numberOfSides = 8;

        this.container = new GenericRoot(unitUnderTest);
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
        validateIdenticalXml("testdata/attribute/nodeAttributes.xml", marshalledXml);
        validateIdenticalJson("testdata/attribute/nodeAttributes.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class, "testdata/attribute/nodeAttributes.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class, "testdata/attribute/nodeAttributes.json");

        final NodeAttributes xmlAttributes = getNodeAttributesFrom(fromXml);
        final NodeAttributes jsonAttributes = getNodeAttributesFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest, xmlAttributes);
        validateIdenticalPublicFields(unitUnderTest, jsonAttributes);

        Assert.assertEquals(unitUnderTest, xmlAttributes);
        Assert.assertEquals(unitUnderTest, jsonAttributes);

        Assert.assertEquals(unitUnderTest.hashCode(), xmlAttributes.hashCode());
        Assert.assertEquals(unitUnderTest.hashCode(), jsonAttributes.hashCode());
    }

    //
    // Private helpers
    //

    private NodeAttributes getNodeAttributesFrom(final GenericRoot genericRoot) {
        return (NodeAttributes) genericRoot.getItems().get(0);
    }
}
