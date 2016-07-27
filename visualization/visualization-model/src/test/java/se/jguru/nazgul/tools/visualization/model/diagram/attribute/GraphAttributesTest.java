/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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
package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.jaxb.PlainJaxbContextRule;
import se.jguru.nazgul.tools.visualization.model.jaxb.XmlValidationRule;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphAttributesTest {

    @Rule public PlainJaxbContextRule jaxb = new PlainJaxbContextRule();
    @Rule public XmlValidationRule xmlValidation = new XmlValidationRule();

    @Test
    public void validateMarshalling() {

        // Assemble
        final GraphAttributes unitUnderTest = new GraphAttributes();

        unitUnderTest.pack = true;
        unitUnderTest.backgroundColor = StandardCssColor.AliceBlue;
        unitUnderTest.textColor = StandardCssColor.Black;

        // Act
        final String result = jaxb.marshal(Thread.currentThread().getContextClassLoader(), false, null, unitUnderTest);

        // Assert
        Assert.assertNotNull(result);
    }

}
