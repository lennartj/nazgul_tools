/*
 * #%L
 * Nazgul Project: nazgul-tools-validation-api
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *       http://www.jguru.se/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package se.jguru.nazgul.tools.validation.api.expression;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.validation.api.exception.AbstractErrorMessageContainer;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ExpressionBuilderTest {

    @Test
    public void validateExceptionMessageOnEmptyCollection() {

        // Assemble
        final String listName = "emptyList";
        final String expectedExceptionMessage = "Collection '" + listName + "' must contain elements.";

        // Act & Assert
        validateNullOrEmptyCollectionOrMap(listName, expectedExceptionMessage, Collections.emptyList());
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void validateExceptionMessageOnEmptyMap() {

        // Assemble
        final String listName = "emptyList";
        final String expectedExceptionMessage = "Map '" + listName + "' must contain elements.";

        // Act & Assert
        validateNullOrEmptyCollectionOrMap(listName, expectedExceptionMessage, new HashMap());
    }

    @Test
    public void validateExceptionMessageOnEmptyString() {

        // Assemble
        final String stringPropertyName = "emptyList";
        final String expectedExceptionMessage = "Property '" + stringPropertyName + "' cannot be empty.";

        // Act & Assert
        validateNullOrEmptyCollectionOrMap(stringPropertyName, expectedExceptionMessage, "");
    }

    @Test
    public void validateNoExceptionMessageOnNonEmptyCollections() {

        // Assemble
        final List<String> list = new ArrayList<String>();
        final String string = "notEmpty";
        final Map<String, String> map = new HashMap<String, String>();

        list.add(string);
        map.put(string, string);

        // Act & Assert
        ExpressionBuilder unitUnderTest = InternalStateValidationException.create();

        unitUnderTest.notNullOrEmpty(string, "string").endExpressionAndValidate();
        unitUnderTest.notNullOrEmpty(list, "list").endExpressionAndValidate();
        unitUnderTest.notNullOrEmpty(map, "map").endExpressionAndValidate();
    }

    @Test
    public void validateTruthExpressions() {

        // Assemble
        final boolean isTrue = true;
        final boolean isFalse = false;
        final ExpressionBuilder unitUnderTest = InternalStateValidationException.create();

        // Act
        unitUnderTest.notTrue(isFalse, "this should not be a problem").endExpressionAndValidate();

        try {
            unitUnderTest.notTrue(isTrue, "this should be thrown").endExpressionAndValidate();
            Assert.fail("true value passed to notTrue should yield exception.");
        } catch (AbstractErrorMessageContainer abstractErrorMessageContainer) {
            // Ignore this
        }
    }


    @Test(expected = InternalStateValidationException.class)
    public void validateExceptionThrownWhenMessagesArePresent() {

        // Assemble
        final String msg = "This is an arbitrary description... sufficient to make unitUnderTest an exception.";
        final ExpressionBuilder unitUnderTest = InternalStateValidationException.create();
        unitUnderTest.addDescription(msg);

        // Act & Assert
        unitUnderTest.endExpressionAndValidate();
    }

    //
    // Private helpers
    //

    private void validateNullOrEmptyCollectionOrMap(String listName,
                                                    String expectedExceptionMessage,
                                                    Object emptyCollectionOrMap) {
        // Assemble
        final ExpressionBuilder unitUnderTest = InternalStateValidationException.create();

        // Act
        unitUnderTest.notNullOrEmpty(emptyCollectionOrMap, listName);

        String message = null;
        try {
            unitUnderTest.endExpressionAndValidate();
            Assert.fail("Expected exception when validating an empty Collection or Map.");
        } catch (InternalStateValidationException e) {
            message = e.getMessage();
        }

        // Assert
        Assert.assertEquals(expectedExceptionMessage + "\n", message);
    }
}
