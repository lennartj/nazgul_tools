/*
 * #%L
 * Nazgul Project: nazgul-tools-validation-api
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
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
package se.jguru.nazgul.tools.validation.api.expression;

import se.jguru.nazgul.tools.validation.api.exception.AbstractErrorMessageContainer;

/**
 * Specification for an end link in a Validation chain.
 * The ValidationExecutor is used to validate all known
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface ValidationExecutor {

    /**
     * Ends this (chain of) ValidationExpressions and performs the actual validation.
     *
     * @throws AbstractErrorMessageContainer if the aggregate ValidationExpression failed to validate correctly.
     */
    void endExpressionAndValidate() throws AbstractErrorMessageContainer;
}
