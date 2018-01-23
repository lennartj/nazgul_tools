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
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.EdgeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowType;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommonEdgeAttributesTest extends AbstractEntityTest {

    // Shared state
    private CommonEdgeAttributes unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        jaxb.add(GenericRoot.class, CommonEdgeAttributes.class);

        unitUnderTest = new CommonEdgeAttributes();
        container = new GenericRoot(unitUnderTest);
    }

    @Test
    public void validateMarshalling() {

        // Assemble
        final EdgeAttributes attributes = unitUnderTest.getAttributes();

        attributes.centerOfHeadLabel = new PointOrRectangle(8.0, 16.0);
        attributes.textColor = StandardCssColor.RosyBrown;
        attributes.backgroundColor = StandardCssColor.AntiqueWhite;
        attributes.edgeTooltip = "Foobar!";
        attributes.label = "This is a FooBar";
        attributes.arrowHead = new ArrowType(true, ArrowType.Clip.LEFT, ArrowType.Shape.DIAMOND);

        // Act
        final String marshalledXml = marshalAsXml(container);
        final String marshalledJson = marshalAsJson(container);

        // System.out.println("Got: " + marshalledXml);
        // System.out.println("Got: " + marshalledJson);

        // Assert
        validateIdenticalXml("testdata/statement/commonEdgeAttributes.xml", marshalledXml);
        validateIdenticalJson("testdata/statement/commonEdgeAttributes.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class,
                "testdata/statement/commonEdgeAttributes.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class,
                "testdata/statement/commonEdgeAttributes.json");

        final CommonEdgeAttributes xmlEdgeAttributes = getCommonEdgeAttributesFrom(fromXml);
        final CommonEdgeAttributes jsonEdgeAttributes = getCommonEdgeAttributesFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest, xmlEdgeAttributes);
        validateIdenticalPublicFields(unitUnderTest, jsonEdgeAttributes);

        Assert.assertEquals(unitUnderTest, xmlEdgeAttributes);
        Assert.assertEquals(unitUnderTest, jsonEdgeAttributes);

        Assert.assertEquals(unitUnderTest.hashCode(), xmlEdgeAttributes.hashCode());
        Assert.assertEquals(unitUnderTest.hashCode(), jsonEdgeAttributes.hashCode());
    }

    //
    // Private helpers
    //

    private CommonEdgeAttributes getCommonEdgeAttributesFrom(final GenericRoot genericRoot) {
        return (CommonEdgeAttributes) genericRoot.getItems().get(0);
    }
}
