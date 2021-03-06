/*-
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.jguru.nazgul.tools.validation.api.Validatable;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DummyEntity implements Validatable {

    private static final Logger log = LoggerFactory.getLogger(DummyEntity.class);

    // Internal state
    public List<String> callTrace = new ArrayList<String>();
    public int value = 0;

    public DummyEntity() {
        log.info("Default constructor executing.");
        callTrace.add("Default constructor called");
    }

    public DummyEntity(final int value) {
        log.info("Compound constructor executing.");
        callTrace.add("Non-default constructor called [" + value + "]");
        this.value = value;
    }

    /**
     * Performs validation of the internal state of this Validatable.
     *
     * @throws InternalStateValidationException if the state of this Validatable was in an incorrect
     *                                          state (i.e. invalid).
     */
    @Override
    public void validateInternalState() throws InternalStateValidationException {

        InternalStateValidationException.create()
                .notTrue(value < 5, "Value cannot be < 5!")
                .endExpressionAndValidate();
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "DummyEntity: " + callTrace;
    }
}
