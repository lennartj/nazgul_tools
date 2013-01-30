/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect;

import se.jguru.nazgul.tools.validation.api.Validatable;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DummyEntity implements Validatable {

    // Internal state
    public List<String> callTrace = new ArrayList<String>();
    public int value = 0;

    public DummyEntity() {
        callTrace.add("Default constructor called");
    }

    public DummyEntity(int value) {
        callTrace.add("Non-default constructor called [" + value + "]");
        this.value = value;
    }

    /**
     * Performs validation of the internal state of this Validatable.
     *
     * @throws InternalStateValidationException
     *          if the state of this Validatable was in an incorrect
     *          state (i.e. invalid).
     */
    @Override
    public void validateInternalState() throws InternalStateValidationException {

        InternalStateValidationException.create()
                .notTrue(value < 5, "Value cannot be < 5!")
                .endExpressionAndValidate();
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "DummyEntity: " + callTrace;
    }
}
