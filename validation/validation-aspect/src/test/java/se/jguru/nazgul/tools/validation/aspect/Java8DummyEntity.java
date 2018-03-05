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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class Java8DummyEntity implements Validatable {

    private static final Logger log = LoggerFactory.getLogger(DummyEntity.class);

    public List<String> callTrace = new ArrayList<>();
    private List<String> modifiedData;

    public Java8DummyEntity() {
        log.info("Default constructor executing.");
        callTrace.add("Default constructor called");
        modifiedData = new ArrayList<>();
    }

    public Java8DummyEntity(final String ... data) {
        log.info("Compound constructor executing.");

        final int numElements = data == null ? 0 : data.length;
        callTrace.add("Non-default constructor called [" + numElements + "]");

        if(numElements > 0) {
            modifiedData = Arrays.asList(data).stream().map(c -> "_" + c).collect(Collectors.toList());
        } else {
            modifiedData = new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateInternalState() throws InternalStateValidationException {

        InternalStateValidationException.create()
                .notNull(modifiedData, "modifiedData")
                .notTrue(modifiedData.isEmpty(), "modifiedData.isEmpty()")
                .endExpressionAndValidate();
    }
}
