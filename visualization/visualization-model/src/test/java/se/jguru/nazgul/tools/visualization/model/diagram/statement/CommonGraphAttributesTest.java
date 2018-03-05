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
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.GraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommonGraphAttributesTest extends AbstractEntityTest {

    // Shared state
    private CommonGraphAttributes unitUnderTest;
    private GenericRoot container;

    @Before
    public void setupSharedState() {

        jaxb.add(GenericRoot.class, CommonGraphAttributes.class);

        unitUnderTest = new CommonGraphAttributes();
        container = new GenericRoot(unitUnderTest);
    }

    @Test
    public void validateMarshalling() {

        // Assemble
        final GraphAttributes attributes = unitUnderTest.getAttributes();

        attributes.padding = new PointOrRectangle(2.0, 4.0);
        attributes.textColor = StandardCssColor.Beige;
        attributes.backgroundColor = StandardCssColor.Silver;
        attributes.searchSize = 5;
        attributes.minimumDistanceBetweenNodes = 4.2;

        // Act
        final String marshalledXml = marshalAsXml(container);
        final String marshalledJson = marshalAsJson(container);

        // System.out.println("Got: " + marshalledXml);
        // System.out.println("Got: " + marshalledJson);

        // Assert
        validateIdenticalXml("testdata/statement/commonGraphAttributes.xml", marshalledXml);
        validateIdenticalJson("testdata/statement/commonGraphAttributes.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final GenericRoot fromXml = unmarshalFromXml(GenericRoot.class,
                "testdata/statement/commonGraphAttributes.xml");
        final GenericRoot fromJSon = unmarshalFromJson(GenericRoot.class,
                "testdata/statement/commonGraphAttributes.json");

        final CommonGraphAttributes xmlGraphAttributes = getCommonGraphAttributesFrom(fromXml);
        final CommonGraphAttributes jsonGraphAttributes = getCommonGraphAttributesFrom(fromJSon);

        // Assert
        validateIdenticalPublicFields(unitUnderTest, xmlGraphAttributes);
        validateIdenticalPublicFields(unitUnderTest, jsonGraphAttributes);

        Assert.assertEquals(unitUnderTest, xmlGraphAttributes);
        Assert.assertEquals(unitUnderTest, jsonGraphAttributes);

        Assert.assertEquals(unitUnderTest.hashCode(), xmlGraphAttributes.hashCode());
        Assert.assertEquals(unitUnderTest.hashCode(), jsonGraphAttributes.hashCode());
    }

    //
    // Private helpers
    //

    private CommonGraphAttributes getCommonGraphAttributesFrom(final GenericRoot genericRoot) {
        return (CommonGraphAttributes) genericRoot.getItems().get(0);
    }
}
