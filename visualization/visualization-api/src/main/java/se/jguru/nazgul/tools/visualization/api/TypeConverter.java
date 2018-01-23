/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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
package se.jguru.nazgul.tools.visualization.api;

import java.io.Serializable;

/**
 * Specification for how to convert one type to another.
 * This is TypeConverter represents a conversion in one direction only.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@FunctionalInterface
public interface TypeConverter<From, To> extends Serializable {

    /**
     * Indicates if this TypeConverter can convert null values.
     *
     * @return {@code true} if this {@link TypeConverter} can convert null values.
     */
    default boolean acceptsNullValues() {
        return false;
    }

    /**
     * Converts the supplied toConvert From object into an object of class To.
     *
     * @param toConvert The object to convert.
     * @return The converted result.
     */
    To convert(From toConvert);
}
