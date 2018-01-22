/*
 * #%L
 * Nazgul Project: nazgul-tools-validation-aspect
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *       http://www.jguru.se/licenses/jguruCorporateSourceLicense-2.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package se.jguru.nazgul.tools.validation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.jguru.nazgul.tools.validation.api.Validatable;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

import java.lang.reflect.Constructor;

/**
 * <p>The aspect enforcing validity on a class implementing {@link Validatable}s (typically Entities or other classes
 * containing state). This aspect should be fired immediately after a non-default constructor is invoked, to ensure
 * that frameworks such as JAXB and JPA can perform full reflective population of an object <strong>before</strong>
 * state validation using this Aspect is performed.</p>
 * <p>Validation should be run only once, and only after the constructor of the ultimate
 * created instance is run (default AspectJ behaviour is to run the Aspect after any
 * constructor within the inheritance hierarchy is executed [i.e. after constructors
 * in superclasses are run, within the constructor of subtypes]).</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@Aspect
public class ValidationAspect {

    // Our log
    private static final Logger log = LoggerFactory.getLogger(ValidationAspect.class);

    /**
     * Defines a Pointcut for any constructor to a class implementing Validatable.
     * The Pointcut will return {@code true} for all non-default Constructors, i.e. when the signature
     * of the JoinPoint has more than 0 arguments.
     *
     * @param joinPoint The currently executing joinPoint.
     */
    @Pointcut(value = "initialization(se.jguru.nazgul.tools.validation.api.Validatable+.new(..)) && if()",
            argNames = "joinPoint")
    public static boolean anyNonDefaultConstructor(final JoinPoint joinPoint) {

        // Extract the JoinPoint signature.
        final Signature signature = joinPoint.getSignature();
        final boolean isConstructorSignature = signature instanceof ConstructorSignature;

        if (isConstructorSignature) {

            final Constructor constructor = ((ConstructorSignature) signature).getConstructor();
            final boolean isNonDefaultConstructor = constructor != null && constructor.getParameters().length > 0;

            log.trace("Non-Default Constructor signature [" + signature.toString() + "] for ["
                    + (constructor == null ? "<null>" : constructor.toString()) + "]: " + isNonDefaultConstructor);

            // All Done.
            return isNonDefaultConstructor;

        } else {
            log.debug("Found non-constructor signature: " + signature);
        }

        // Nopes.
        return false;
    }

    /**
     * Aspect invoking the {@link Validatable#validateInternalState()} method before returning from any non-default
     * constructor within a class implementing {@link Validatable}.
     *
     * @param joinPoint The currently executing joinPoint.
     * @throws InternalStateValidationException if the validation of the {@link Validatable} failed.
     */
    @AfterReturning(value = "anyNonDefaultConstructor(joinPoint) && target(aValidatable)",
            argNames = "joinPoint, aValidatable")
    public void performValidationAfterCompoundConstructor(final JoinPoint joinPoint, final Validatable aValidatable)
            throws InternalStateValidationException {

        if (log.isDebugEnabled()) {
            log.debug("Validating instance of type [" + aValidatable.getClass().getName() + "]");
        }

        if (joinPoint.getStaticPart() == null) {
            log.warn("Static part of join point was null for validatable of type: "
                    + aValidatable.getClass().getName(), new IllegalStateException());
            return;
        }

        // Ignore calling validateInternalState when we execute constructors in
        // any class but the concrete Validatable class.
        final ConstructorSignature sig = (ConstructorSignature) joinPoint.getSignature();
        final Class<?> constructorDefinitionClass = sig.getConstructor().getDeclaringClass();
        if (aValidatable.getClass() == constructorDefinitionClass) {

            // Now fire the validateInternalState method.
            aValidatable.validateInternalState();

        } else {

            log.debug("Ignored firing validatable for constructor defined in ["
                    + constructorDefinitionClass.getName() + "] and Validatable of type ["
                    + aValidatable.getClass().getName() + "]");
        }
    }
}
