/*-
 * #%L
 * Nazgul Project: nazgul-tools-validation-api
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


package se.jguru.nazgul.tools.validation.api;

import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * <p>Specification for providing an object with Validation mechanics.
 * Each Validatable object can perform <strong>rudimentary</strong>
 * validation to assert its state before being serialized and after
 * being deserialized.</p>
 * <p>Making an object implement Validatable does not imply that all
 * uses of the object is guaranteed. Validatable objects should
 * primarily make use of their own data to ascertain its valid state.</p>
 * <p>It is the responsibilities of services using the Validatable object
 * (as opposed to the validation mechanics provided within this Validatable)
 * to provide extra/semantic validation for object <strong>graphs</strong>
 * in which this Validatable instance is part.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface Validatable {

    /**
     * Performs validation of the internal state of this Validatable.
     *
     * @throws InternalStateValidationException if the state of this Validatable was
     *                                          in an incorrect state (i.e. invalid).
     */
    void validateInternalState() throws InternalStateValidationException;
}
