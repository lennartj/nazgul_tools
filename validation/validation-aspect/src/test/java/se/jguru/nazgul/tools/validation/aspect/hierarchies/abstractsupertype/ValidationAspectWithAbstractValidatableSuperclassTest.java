/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
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
