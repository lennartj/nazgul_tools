/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect.hierarchies.validatablesubtype;

import se.jguru.nazgul.tools.validation.api.Validatable;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidatableSubType extends SupertypePlainType implements Validatable {

    // Internal state
    private int value;

    public ValidatableSubType() {
        super("Lennart");
        this.value = -6;
    }

    public ValidatableSubType(String name, int value) {
        super(name);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Performs validation of the internal state of this Validatable.
     *
     * @throws InternalStateValidationException
     *          if the state of this Validatable was
     *          in an incorrect state (i.e. invalid).
     */
    @Override
    public void validateInternalState() throws InternalStateValidationException {

        InternalStateValidationException.create()
                .notTrue(value < 4, "Value cannot be < 4!")
                .endExpressionAndValidate();
    }
}
