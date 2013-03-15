/*
 * #%L
 * Nazgul Project: nazgul-tools-validation-aspect
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

package se.jguru.nazgul.tools.validation.aspect.hierarchies.abstractsupertype;

import org.junit.Test;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidationAspectWithAbstractValidatableSuperclassTest {

    @Test
    public void validateNoAspectInteractionForDefaultConstructor() {

        // Act & Assert
        new ValidatableSubtype();
    }

    @Test(expected = InternalStateValidationException.class)
    public void validateAspectGeneratedExceptionOnIncorrectValue() {

        // Assemble
        final int tooLow = 2;

        // Act & Assert
        new ValidatableSubtype("Lennart", tooLow);
    }

    @Test
    public void validateNoExceptionForCorrectValue() {

        // Assemble
        final int ok = 15;

        // Act & Assert
        new ValidatableSubtype("Lennart", ok);
    }
}
