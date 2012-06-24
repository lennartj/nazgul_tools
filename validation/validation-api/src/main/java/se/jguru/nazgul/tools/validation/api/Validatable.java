/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.api;

import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * Specification for providing an object with Validation mechanics.
 * Each Validatable object can perform <strong>rudimentary</strong>
 * validation to assert its state before being serialized and after
 * being deserialized.
 * <p/>
 * Making an object implement Validatable does not imply that all
 * uses of the object is guaranteed. Validatable objects should
 * primarily make use of their own data to ascertain its valid state.
 * <p/>
 * It is the responsibilities of services using the Validatable object
 * (as opposed to the validation mechanics provided within this Validatable)
 * to provide extra/semantic validation for object <strong>graphs</strong>
 * in which this Validatable instance is part.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface Validatable {

    /**
     * Performs validation of the internal state of this Validatable.
     *
     * @throws InternalStateValidationException
     *          if the state of this Validatable was
     *          in an incorrect state (i.e. invalid).
     */
    public void validateInternalState() throws InternalStateValidationException;
}
