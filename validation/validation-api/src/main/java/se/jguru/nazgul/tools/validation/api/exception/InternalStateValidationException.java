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
 *        http://www.jguru.se/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package se.jguru.nazgul.tools.validation.api.exception;

import se.jguru.nazgul.tools.validation.api.expression.ExpressionBuilder;

/**
 * Exception indicating problems occurred when validating a Validatable instance.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class InternalStateValidationException extends AbstractErrorMessageContainer {

    /**
     * Factory method to create and return an ExpressionBuilder holding an empty
     * InternalStateValidationException as MessageContainer.
     *
     * @return A newly created InternalStateValidationException instance with an empty message buffer.
     */
    public static ExpressionBuilder create() {
        return new ExpressionBuilder(new InternalStateValidationException());
    }

    /**
     * Factory method to create and return an ExpressionBuilder holding an
     * InternalStateValidationException (given the provided cause) as MessageContainer.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @return A newly created InternalStateValidationException instance with an empty message buffer.
     */
    public static ExpressionBuilder create(final Throwable cause) {
        return new ExpressionBuilder(new InternalStateValidationException(cause));
    }

    /**
     * Constructs an IllegalStateException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public InternalStateValidationException() {
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exception that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.5
     */
    private InternalStateValidationException(final Throwable cause) {
        super(cause);
    }
}
