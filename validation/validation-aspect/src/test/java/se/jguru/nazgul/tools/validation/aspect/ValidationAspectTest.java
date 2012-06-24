/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect;

import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;
import org.junit.Test;
import se.jguru.nazgul.tools.validation.aspect.DummyEntity;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidationAspectTest {

    @Test
    public void validateNoAspectInteractionForDefaultConstructor() {

        // Act & Assert
        new DummyEntity();
    }

    @Test(expected = InternalStateValidationException.class)
    public void validateAspectGeneratedExceptionOnIncorrectValue() {

        // Assemble
        int tooLow = 2;

        // Act & Assert
        new DummyEntity(tooLow);
    }

    @Test
    public void validateNoExceptionForCorrectValue() {

        // Assemble
        int ok = 7;

        // Act & Assert
        new DummyEntity(ok);
    }
}
