/*
 * #%L
 * Nazgul Project: nazgul-tools-validation-api
 * %%
 * Copyright (C) 2010 - 2015 jGuru Europe AB
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

package se.jguru.nazgul.tools.validation.api.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class InternalStateValidationExceptionTest {

    @Test
    public void validateNoExceptionThrownOnWhenNoMessagesPresent() {

        // Assemble
        final InternalStateValidationException unitUnderTest = new InternalStateValidationException();

        // Act & Assert
        unitUnderTest.endExpressionAndValidate();
    }

    @Test
    public void validateCausePresentIfGivenWhenCreated() {

        // Assemble
        final String message = "We need a newer version";
        final UnsupportedClassVersionError error = new UnsupportedClassVersionError(message);

        // Act & Assert
        try {

            InternalStateValidationException
                    .create(error)
                    .notNull(null, "irrelevant")
                    .endExpressionAndValidate();

            Assert.fail("We should always throw an exception when running notNull with a null argument.");

        } catch (AbstractErrorMessageContainer e) {

            Assert.assertSame(error, e.getCause());
        }
    }
}
