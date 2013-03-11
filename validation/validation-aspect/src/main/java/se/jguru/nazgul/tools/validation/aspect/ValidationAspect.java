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
package se.jguru.nazgul.tools.validation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.jguru.nazgul.tools.validation.api.Validatable;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * The aspect enforcing validity on a class implementing Validatable (i.e. Entities).
 * This aspect should be fired immediately after a non-default constructor is invoked,
 * and is intended to run as a singleton.
 * <p/>
 * Validation should be run only once, and only after the constructor of the ultimate
 * created instance is run (default AspectJ behaviour is to run the Aspect after any
 * constructor within the inheritance hierarchy is executed [i.e. after constructors
 * in superclasses are run, within the constructor of subtypes]).
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@Aspect
public class ValidationAspect {

    // Our log
    private static final Logger log = LoggerFactory.getLogger(ValidationAspect.class);

    /**
     * Pointcut defining a default constructor within any class.
     */
    @Pointcut("initialization(*.new())")
    void anyDefaultConstructor() {
    }

    /**
     * Defines a Pointcut for any constructor to a class implementing Validatable -
     * except default constructors (i.e. those having no arguments).
     *
     * @param joinPoint    The currently executing joinPoint.
     * @param aValidatable The Validatable instance just created.
     */
    @Pointcut(value = "initialization(se.jguru.nazgul.tools.validation.api.Validatable+.new(..)) "
            + "&& this(aValidatable) "
            + "&& !anyDefaultConstructor()", argNames = "joinPoint, aValidatable")
    void anyNonDefaultConstructor(final JoinPoint joinPoint, final Validatable aValidatable) {
    }

    /**
     * Validation aspect, performing its job after calling any constructor except
     * non-private default ones (having no arguments).
     *
     * @param joinPoint   The currently executing joinPoint.
     * @param validatable The validatable instance just created.
     * @throws InternalStateValidationException
     *          if the validation of the validatable failed.
     */
    @AfterReturning(value = "anyNonDefaultConstructor(joinPoint, validatable)", argNames = "joinPoint, validatable")
    public void performValidationAfterCompoundConstructor(final JoinPoint joinPoint, final Validatable validatable)
            throws InternalStateValidationException {

        if (log.isDebugEnabled()) {
            log.debug("Validating instance of type [" + validatable.getClass().getName() + "]");
        }

        if (joinPoint.getStaticPart() == null) {
            log.warn("Static part of join point was null for validatable of type: "
                    + validatable.getClass().getName(), new IllegalStateException());
            return;
        }

        // Ignore calling validateInternalState when we execute constructors in
        // any class but the concrete Validatable class.
        ConstructorSignature sig = (ConstructorSignature) joinPoint.getSignature();
        Class constructorDefinitionClass = sig.getConstructor().getDeclaringClass();
        if (validatable.getClass() == constructorDefinitionClass) {

            // Now fire the validateInternalState method.
            validatable.validateInternalState();

        } else {

            log.debug("Ignored firing validatable for constructor defined in ["
                    + constructorDefinitionClass.getName() + "] and Validatable of type ["
                    + validatable.getClass().getName() + "]");
        }
    }
}
