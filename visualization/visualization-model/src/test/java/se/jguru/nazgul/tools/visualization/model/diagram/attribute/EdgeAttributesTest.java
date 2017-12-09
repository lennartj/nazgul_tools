/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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

package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.AbstractEntityTest;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowType;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class EdgeAttributesTest extends AbstractEntityTest {

    // Shared state
    private EdgeAttributes unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        // Configure the PlainJaxbContextRule
        jaxb.add(GenericRoot.class, EdgeAttributes.class);

        // Create the unit under test
        unitUnderTest = new EdgeAttributes();

        unitUnderTest.arrowHead = new ArrowType(false, ArrowType.Shape.DIAMOND);
        unitUnderTest.backgroundColor = StandardCssColor.Beige;
        unitUnderTest.arrowSize = 14.0;
        unitUnderTest.constraint = true;
        unitUnderTest.label = "Some edge";
        unitUnderTest.labelFontColor = StandardCssColor.Brown;

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
        validateIdenticalXml("testdata/attribute/edgeAttributes.xml", marshalledXml);
        validateIdenticalJson("testdata/attribute/edgeAttributes.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class, "testdata/attribute/edgeAttributes.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class, "testdata/attribute/edgeAttributes.json");

        final EdgeAttributes xmlAttributes = getEdgeAttributesFrom(fromXml);
        final EdgeAttributes jsonAttributes = getEdgeAttributesFrom(fromJSon);

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

    private EdgeAttributes getEdgeAttributesFrom(final GenericRoot genericRoot) {
        return (EdgeAttributes) genericRoot.getItems().get(0);
    }
}
