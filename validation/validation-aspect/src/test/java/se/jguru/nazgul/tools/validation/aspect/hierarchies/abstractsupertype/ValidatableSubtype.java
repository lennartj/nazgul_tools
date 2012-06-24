/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect.hierarchies.abstractsupertype;


import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidatableSubtype extends ValidatableSupertype {

    // Internal state
    private int age;

    public ValidatableSubtype() {
        super("FooBar");
    }

    public ValidatableSubtype(String name, int age) {
        super(name);
        this.age = age;
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

        final int minAge = 12;

        InternalStateValidationException.create()
                .notTrue(age < minAge, "age < " + minAge)
                .notTrue(getName() == null, "getName() == null")
                .endExpressionAndValidate();
    }
}
