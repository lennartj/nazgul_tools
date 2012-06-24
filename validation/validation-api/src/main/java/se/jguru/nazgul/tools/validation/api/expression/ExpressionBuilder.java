/*
 * Copyright (c) jGuru Europe AB
 * All rights reserved.
 */
package se.jguru.nazgul.tools.validation.api.expression;

import org.apache.commons.lang.Validate;
import se.jguru.nazgul.tools.validation.api.exception.AbstractErrorMessageContainer;
import se.jguru.nazgul.tools.validation.api.exception.ErrorMessageContainer;

import java.util.Collection;
import java.util.Map;

/**
 * Builder class producing ValidationExpressions.
 * It is recommended for all users of this class to perform a static import,
 * in order for the DSL to become more fluent.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ExpressionBuilder implements ValidationExecutor {

    // Internal state
    private ErrorMessageContainer messageContainer;

    /**
     * Creates a new ExpressionBuilder, using the provided Exception as
     * container for any built messages.
     *
     * @param messageContainer The InternalStateValidationException to use
     *                         as container for all messages created by any
     *                         chained ValidationExpression instances managed
     *                         by this ExpressionBuilder.
     * @throws IllegalArgumentException if the messageContainer instance was <code>null</code>.
     */
    public ExpressionBuilder(final ErrorMessageContainer messageContainer) throws IllegalArgumentException {
        Validate.notNull(messageContainer, "Cannot handle null messageContainer instance.");
        this.messageContainer = messageContainer;
    }

    /**
     * Ends this (chain of) ValidationExpressions and performs the actual validation.
     *
     * @throws AbstractErrorMessageContainer if the aggregate ValidationExpression failed to validate correctly.
     */
    @Override
    public void endExpressionAndValidate() throws AbstractErrorMessageContainer {
        messageContainer.endExpressionAndValidate();
    }

    /**
     * Adds an arbitrary description.
     *
     * @param desc The description to add.
     * @return This object.
     */
    public ExpressionBuilder addDescription(final String desc) {
        messageContainer.addErrorMessage(desc);
        return this;
    }

    /**
     * Adds a description for a null property.
     *
     * @param property The property to check.
     * @param name     The property name.
     * @return This object.
     */
    public ExpressionBuilder notNull(final Object property, final String name) {

        if (property == null) {
            messageContainer.addErrorMessage("Property '" + name + "' cannot be null");
        }

        return this;
    }

    /**
     * Adds a description for a property, if the provided expression is not <code>true</code>.
     *
     * @param expression The expression to check.
     * @param name       The expression name.
     * @return This object.
     */
    public ExpressionBuilder notTrue(final boolean expression, final String name) {

        if (expression) {
            messageContainer.addErrorMessage("Expression '" + name + "' cannot be true.");
        }

        return this;
    }

    /**
     * Adds a description for a null or empty property (Strings, Collections and Maps are
     * checked for emptyness as well as null-ness).
     *
     * @param property The property to check.
     * @param name     The property name.
     * @return This object.
     */
    public ExpressionBuilder notNullOrEmpty(final Object property, final String name) {

        notNull(property, name);

        if (property instanceof String && ((String) property).length() == 0) {
            messageContainer.addErrorMessage("Property '" + name + "' cannot be empty.");
        }
        if (property instanceof Collection && ((Collection) property).size() == 0) {
            messageContainer.addErrorMessage("Collection '" + name + "' must contain elements.");
        }
        if (property instanceof Map && ((Map) property).size() == 0) {
            messageContainer.addErrorMessage("Map '" + name + "' must contain elements.");
        }

        return this;
    }
}
