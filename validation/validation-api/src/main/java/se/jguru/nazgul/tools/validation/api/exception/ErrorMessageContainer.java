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
