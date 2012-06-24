/*
 * Copyright (c) jGuru Europe AB
 * All rights reserved.
 */
package se.jguru.nazgul.tools.validation.api.expression;

import se.jguru.nazgul.tools.validation.api.exception.AbstractErrorMessageContainer;

/**
 * Specification for an end link in a Validation chain.
 * The ValidationExecutor is used to validate all known
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface ValidationExecutor {

    /**
     * Ends this (chain of) ValidationExpressions and performs the actual validation.
     *
     * @throws AbstractErrorMessageContainer if the aggregate ValidationExpression failed to validate correctly.
     */
    void endExpressionAndValidate() throws AbstractErrorMessageContainer;
}
