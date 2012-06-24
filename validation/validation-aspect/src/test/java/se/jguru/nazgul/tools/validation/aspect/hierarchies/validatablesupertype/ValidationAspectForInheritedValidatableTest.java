/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */
package se.jguru.nazgul.tools.validation.aspect.hierarchies.validatablesupertype;

import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidationAspectForInheritedValidatableTest {

    @Test
    public void validateThatSubValidatableTypePassValidation() {

        // Assemble
        final int okValue = 2;

        // Act & Assert
        new SubtypePlainType(okValue);
    }
}
