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

package se.jguru.nazgul.tools.visualization.model;

import org.eclipse.persistence.oxm.record.JSONFormattedWriterRecord;
import org.junit.Assert;
import org.junit.Rule;
import se.jguru.nazgul.tools.visualization.model.jaxb.PlainJaxbContextRule;
import se.jguru.nazgul.tools.visualization.model.jaxb.XmlValidationRule;

import javax.json.Json;
import javax.json.JsonStructure;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractEntityTest {

    @Rule public PlainJaxbContextRule jaxb = new PlainJaxbContextRule();
    @Rule public XmlValidationRule xmlValidation = new XmlValidationRule();

    protected String marshalAsXml(final Object... toMarshal) {
        return jaxb.marshal(Thread.currentThread().getContextClassLoader(), false, null, toMarshal);
    }

    protected String marshalAsJson(final Object... toMarshal) {
        return jaxb.marshal(Thread.currentThread().getContextClassLoader(), true, null, toMarshal);
    }

    protected void validateIdenticalXml(final String pathToResource, final String marshalledXML) {
        Assert.assertTrue(xmlValidation.identical(PlainJaxbContextRule.readFully(pathToResource), marshalledXML));
    }

    protected void validateSimilarXml(final String pathToResource, final String marshalledXML) {
        Assert.assertTrue(xmlValidation.similar(PlainJaxbContextRule.readFully(pathToResource), marshalledXML));
    }

    protected void validateIdenticalJson(final String pathToResource, final String marshalledJSON) {
        final String expected = formatJson(PlainJaxbContextRule.readFully(pathToResource));
        final String actual = formatJson(marshalledJSON);
        Assert.assertEquals(expected, actual);
    }

    protected <T> T unmarshalFromXml(final Class<T> expectedType, final String pathToResource) {
        final String data = PlainJaxbContextRule.readFully(pathToResource);
        return jaxb.unmarshal(Thread.currentThread().getContextClassLoader(), false, expectedType, data);
    }

    protected <T> T unmarshalFromJson(final Class<T> expectedType, final String pathToResource) {
        final String data = PlainJaxbContextRule.readFully(pathToResource);
        return jaxb.unmarshal(Thread.currentThread().getContextClassLoader(), true, expectedType, data);
    }

    protected <T> void validateIdenticalPublicFields(final T expected, final T cmp) {

        for(Class<?> currentClass = expected.getClass();
            currentClass != null && currentClass != Object.class;
            currentClass = currentClass.getSuperclass()) {

            for(Field currentField : currentClass.getFields()) {

                final Object expectedValue = getValue(currentField, expected);
                final Object cmpValue = getValue(currentField, cmp);

                // Ignore null value pairs.
                if(expectedValue == null && cmpValue == null) {
                    continue;
                }

                // Assert the values.
                Assert.assertEquals(expectedValue, cmpValue);
            }
        }
    }

    private static Object getValue(final Field aField, final Object theObject) {
        try {
            return aField.get(theObject);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not acquire value from field [" + aField.toString() + "] in " +
                    "object of type [" + theObject.getClass().getName() + "]", e);
        }
    }

    private static String formatJson(final String json) {

        final JsonStructure intermediateStructure = Json.createReader(new StringReader(json)).read();
        final StringWriter out = new StringWriter();
        Json.createWriter(out).write(intermediateStructure);

        return out.toString();
    }
}
