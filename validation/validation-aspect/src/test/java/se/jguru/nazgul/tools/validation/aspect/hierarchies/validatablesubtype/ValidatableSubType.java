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

package se.jguru.nazgul.tools.validation.aspect.hierarchies.validatablesubtype;

import se.jguru.nazgul.tools.validation.api.Validatable;
import se.jguru.nazgul.tools.validation.api.exception.InternalStateValidationException;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidatableSubType extends SupertypePlainType implements Validatable {

    // Internal state
    private final int value;

    public ValidatableSubType() {
        super("Lennart");
        this.value = -6;
    }

    public ValidatableSubType(final String name, final int value) {
        super(name);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Performs validation of the internal state of this Validatable.
     *
     * @throws InternalStateValidationException if the state of this Validatable was
     *                                          in an incorrect state (i.e. invalid).
     */
    @Override
    public void validateInternalState() throws InternalStateValidationException {

        InternalStateValidationException.create()
                .notTrue(value < 4, "Value cannot be < 4!")
                .endExpressionAndValidate();
    }
}
