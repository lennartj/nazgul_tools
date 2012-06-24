/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
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
