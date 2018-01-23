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
package se.jguru.nazgul.tools.visualization.model.diagram;

import se.jguru.nazgul.tools.visualization.model.diagram.attribute.AbstractAttributes;

/**
 * Specification for an object which can hold a set of typed attributes.
 * These attributes are wrapped within a Statement, which is created upon first access unless already present.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface TypedAttributeHolder<T extends AbstractAttributes> {

    /**
     * Retrieves the typed attributes (packaged within an {@link AbstractAttributes} container).
     *
     * @return the typed attributes (packaged within an {@link AbstractAttributes} container).
     */
    T getAttributes();
}
