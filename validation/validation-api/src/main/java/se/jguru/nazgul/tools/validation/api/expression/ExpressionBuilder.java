/*
 * #%L
 * Nazgul Project: nazgul-tools-validation-api
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *       http://www.jguru.se/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package se.jguru.nazgul.tools.validation.api.expression;

import org.apache.commons.lang3.Validate;
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
    private final ErrorMessageContainer messageContainer;

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

        if (property instanceof String && ((String) property).isEmpty()) {
            messageContainer.addErrorMessage("Property '" + name + "' cannot be empty.");
        }
        if (property instanceof Collection && ((Collection<?>) property).isEmpty()) {
            messageContainer.addErrorMessage("Collection '" + name + "' must contain elements.");
        }
        if (property instanceof Map && ((Map<?, ?>) property).isEmpty()) {
            messageContainer.addErrorMessage("Map '" + name + "' must contain elements.");
        }

        return this;
    }
}
