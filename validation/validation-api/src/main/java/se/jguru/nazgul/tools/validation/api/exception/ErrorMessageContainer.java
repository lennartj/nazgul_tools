/*
 * Copyright (c) jGuru Europe AB
 * All rights reserved.
 */
package se.jguru.nazgul.tools.validation.api.exception;

import se.jguru.nazgul.tools.validation.api.expression.ValidationExecutor;

/**
 * Specification for any exception types able to contain messages.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface ErrorMessageContainer extends ValidationExecutor {

    /**
     * Adds the provided error message to this ErrorMessageContainer.
     *
     * @param message The message to add, provided that the message is neither null nor empty.
     */
    void addErrorMessage(String message);

    /**
     * Checks if this ErrorMessageContainer contains any errors, and - if so -
     * throws an exception to indicate that fact.
     *
     * @throws IllegalStateException if the internal state of this ErrorMessageContainer implies an
     *                               invalid state.
     */
    void endExpressionAndValidate() throws IllegalStateException;

    /**
     * @return An description of this ErrorMessageContainer.
     */
    String toString();
}
