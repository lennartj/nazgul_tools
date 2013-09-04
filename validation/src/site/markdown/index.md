# Validation Component

The Validation component provides a simple means to validate the internal state of newly created
objects, to ensure that the object is in a valid [internal] state. The validation component can be
used without requiring the presence of any particular execution environment, such as a JEE or Spring container.
(Of course, the validation component works just as well inside a container as outside of it.)

## Validating object state

The process to receive automatic validation is trivial if your artifact project uses the `nazgul-parent` pom
as a parent within its inheritance chain:

1. Ensure that your pom.xml has the `nazgul-parent` pom as direct or indirect parent.
2. Let your relevant class implement the Validatable interface. (This is illustrated in row 1 in the listing below).
3. Define the expression for validating the internal state of the instance within the `validateInternalState`
   method. (This is illustrated in rows 32 - 34 in the listing below.)

If your artifact project does not use the nazgul-parent pom as a parent within its inheritance chain, please
refer to the nazgul-parent pom to see how to configure the AspectJ plugin to automatically weave all
your classes with the validationAspect. The validation aspect is what ultimately drives the invocation of
the `validateInternalState` method.

For API details regarding which methods/validations are available for writing the `validateInternalState` method,
please refer to the ExpressionBuilder class. Several concrete examples can be found within the test cases within the
validation-aspect project and within the `nazgul-core` project/reactor.

<pre class="brush: java" title="Example Validatable class."><![CDATA[
public class DummyEntity implements Validatable {

    // Internal state
    public int value = 0;

    /**
     * JAXB/JPA-friendly constructor.
     */
    public DummyEntity() {
        // Validation not performed after calling the default constructor.
    }

    /**
     * Compound constructor, so the validateInternalState method
     * will be invoked after the constructor is run.
     */
    public DummyEntity(int value) {
        this.value = value;

        // Validation performed after calling this [non-default] constructor.
    }

    /**
     * Performs validation of the internal state of this Validatable.
     *
     * @throws InternalStateValidationException
     *          if the state of this Validatable was in an incorrect
     *          state (i.e. invalid).
     */
    @Override
    public void validateInternalState() throws InternalStateValidationException {

        InternalStateValidationException.create()
                .notTrue(value < 5, "Value cannot be < 5!")
                .endExpressionAndValidate();
    }
}
]]></pre>
