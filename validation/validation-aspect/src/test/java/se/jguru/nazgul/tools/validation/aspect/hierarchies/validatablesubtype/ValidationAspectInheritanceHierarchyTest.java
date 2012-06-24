/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect.hierarchies.validatablesubtype;

import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidationAspectInheritanceHierarchyTest {

    @Test
    public void validateNoAspectInteractionForDefaultConstructor() {

        // Act & Assert
        new ValidatableSubType();
    }

    @Test(expected = InternalStateValidationException.class)
    public void validateAspectGeneratedExceptionOnIncorrectValueForValidatableSubtype() {

        // Assemble
        int tooLow = 2;

        // Act & Assert
        new ValidatableSubType("Sven-Benny", tooLow);
    }

    @Test
    public void validateNoExceptionForCorrectValueInValidatableSubtype() {

        // Assemble
        int ok = 7;

        // Act & Assert
        new ValidatableSubType(null, ok);
    }
}
