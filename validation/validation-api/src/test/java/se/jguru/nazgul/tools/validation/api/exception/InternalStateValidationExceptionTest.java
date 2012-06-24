package se.jguru.nazgul.tools.validation.api.exception;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.validation.api.exception.AbstractErrorMessageContainer;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class InternalStateValidationExceptionTest {

    @Test
    public void validateNoExceptionThrownOnWhenNoMessagesPresent() {

        // Assemble
        final InternalStateValidationException unitUnderTest = new InternalStateValidationException();

        // Act & Assert
        unitUnderTest.endExpressionAndValidate();
    }

    @Test
    public void validateCausePresentIfGivenWhenCreated() {

        // Assemble
        final String message = "We need a newer version";
        final UnsupportedClassVersionError error = new UnsupportedClassVersionError(message);

        // Act & Assert
        try {

            InternalStateValidationException
                    .create(error)
                    .notNull(null, "irrelevant")
                    .endExpressionAndValidate();

            Assert.fail("We should always throw an exception when running notNull with a null argument.");

        } catch (AbstractErrorMessageContainer e) {

            Assert.assertSame(error, e.getCause());
        }
    }
}
