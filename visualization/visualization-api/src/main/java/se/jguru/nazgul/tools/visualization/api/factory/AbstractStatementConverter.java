/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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

package se.jguru.nazgul.tools.visualization.api.factory;

import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statement;

/**
 * Abstract TypeConverter implementation handling conversion to {@link Statement}s.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractStatementConverter<From, S extends Statement> implements TypeConverter<From, S> {

    /**
     * Sports a null parameter sanity check, and delegates further execution to the {@link #doConvert(Object)} method.
     *
     * @param toConvert The object to convert.
     * @return The converted result
     * @throws IllegalArgumentException if the toConvert attribute was null and this
     *                                  AbstractTypeConverter does not accept null values.
     */
    @Override
    public final S convert(final From toConvert) {

        // Handle nulls
        if (toConvert == null && !acceptsNullValues()) {
            throw new IllegalArgumentException("Converter ["
                    + getClass().getName() + "] cannot accept null 'toConvert' arguments.");
        }

        // All Done
        return doConvert(toConvert);
    }

    /**
     * Implement this method to perform conversion of a non-null value.
     *
     * @param toConvert The received, non-null value.
     * @return The converted result.
     */
    protected abstract S doConvert(final From toConvert);
}
