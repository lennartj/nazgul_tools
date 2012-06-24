/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect.hierarchies.validatablesupertype;

import se.jguru.nazgul.tools.validation.api.Validatable;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidatableSuperType implements Validatable {

    private int id;

    public ValidatableSuperType(final int id) {
        this.id = id;
    }

    @Override
    public void validateInternalState() throws InternalStateValidationException {
        InternalStateValidationException.create()
                .notTrue(id < 2, "Id was < 2")
                .endExpressionAndValidate();
    }
}
