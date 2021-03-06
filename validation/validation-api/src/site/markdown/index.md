# Nazgul Tools: Validation API

The Validation API project contains Aspect callback specifications,
validation Exception definitions and an Exception message builder.
The type definitions within this project are available to all projects
within the Tomcat Embedder reactor, in particular supplied to all
Entity types.

A complement to any container-injected lifecycle callback method (á la
EJB callback methods), Validation API types are woven using AspectJ
and can therefore be used out of container and without any complex
runtime library requirement other than AspectJ runtime.

## Validatable interface

Defines a single method to be implemented by all constructor-complete
style classes, such as Entities. However, any class requiring runtime
validation following a non-default constructor call can implement the
Validatable interface.

A typical implementation of a validateInternalState method follows:

<pre class="brush: java" title="Example validateInternalState() method."><![CDATA[
/**
 * Performs validation of the internal state of this Validatable.
 *
 * @throws InternalStateValidationException
 *          if the state of this Validatable was
 *          in an incorrect state (i.e. invalid).
 */
 @Override
 public void validateInternalState() throws InternalStateValidationException {

    InternalStateValidationException.create()
        .notTrue(majorVersion < 0, "majorVersion < 0")
        .notTrue(minorVersion < 0, "minorVersion < 0")
        .notTrue(patchVersion < 0, "patchVersion < 0")
        .notNull(qualifier, "qualifier")
        .endExpressionAndValidate();
}
]]></pre>

