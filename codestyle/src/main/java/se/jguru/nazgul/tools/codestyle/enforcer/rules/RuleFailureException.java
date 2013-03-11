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

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.artifact.Artifact;

/**
 * Exception indicating that an enforcer rule implementation has encountered a failure state.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RuleFailureException extends RuntimeException {

    // Internal state
    private static final long serialVersionUID = -9014353905297786126L;
    private Artifact offendingArtifact;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RuleFailureException(final String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public RuleFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message           the detail message. The detail message is saved for
     *                          later retrieval by the {@link #getMessage()} method.
     * @param offendingArtifact The artifact which triggered this RuleFailureException.
     */
    public RuleFailureException(final String message, final Artifact offendingArtifact) {
        super(message);
        this.offendingArtifact = offendingArtifact;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message           the detail message (which is saved for later retrieval
     *                          by the {@link #getMessage()} method).
     * @param cause             the cause (which is saved for later retrieval by the
     *                          {@link #getCause()} method).  (A <tt>null</tt> value is
     *                          permitted, and indicates that the cause is nonexistent or
     *                          unknown.)
     * @param offendingArtifact The artifact which triggered this RuleFailureException.
     * @since 1.4
     */
    public RuleFailureException(final String message, final Throwable cause, final Artifact offendingArtifact) {
        super(message, cause);
        this.offendingArtifact = offendingArtifact;
    }

    /**
     * @return The offending Artifact, or <code>null</code> if no such artifact was defined.
     */
    public Artifact getOffendingArtifact() {
        return offendingArtifact;
    }
}
