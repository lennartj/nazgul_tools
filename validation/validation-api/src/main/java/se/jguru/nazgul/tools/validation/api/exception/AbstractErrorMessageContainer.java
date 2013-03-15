/*
 * #%L
 * nazgul-tools-validation-api
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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of the ErrorMessageContainer interface.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class AbstractErrorMessageContainer extends IllegalStateException implements ErrorMessageContainer {

    // Internal state
    private final List<String> errorMessages = new ArrayList<String>();

    /**
     * Adds the provided error message to this ErrorMessageContainer.
     *
     * @param message The message to add, provided that the message is neither null nor empty.
     */
    @Override
    public final void addErrorMessage(final String message) {
        if (message == null || "".equals(message)) {
            return;
        }

        // All done.
        errorMessages.add(message);
    }

    /**
     * Checks if this ErrorMessageContainer contains any errors, and - if so -
     * throws an exception to indicate that fact.
     *
     * @throws IllegalStateException if the internal state of this ErrorMessageContainer implies an
     *                               invalid state.
     */
    @Override
    public final void endExpressionAndValidate() throws IllegalStateException {
        if (!errorMessages.isEmpty()) {
            throw this;
        }
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this <tt>Throwable</tt> instance
     *         (which may be <tt>null</tt>).
     */
    @Override
    public String getMessage() {

        final StringBuilder builder = new StringBuilder();
        for (final String current : errorMessages) {
            builder.append(current).append("\n");
        }

        // All done.
        return builder.toString();
    }

    //
    // Private constructors.
    //


    /**
     * Constructs an IllegalStateException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    protected AbstractErrorMessageContainer() {
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.5
     */
    protected AbstractErrorMessageContainer(final Throwable cause) {
        super(cause);
    }
}
