/*
 * Copyright (c) jGuru Europe AB
 * All rights reserved.
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
    private List<String> errorMessages = new ArrayList<String>();

    /**
     * Adds the provided error message to this ErrorMessageContainer.
     *
     * @param message The message to add, provided that the message is neither null nor empty.
     */
    @Override
    public final void addErrorMessage(final String message) {
        if (message == null || message.equals("")) {
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
        if (errorMessages.size() > 0) {
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

        StringBuilder builder = new StringBuilder();
        for (String current : errorMessages) {
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
