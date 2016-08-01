/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
 * %%
 * Copyright (C) 2010 - 2016 jGuru Europe AB
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
package se.jguru.nazgul.tools.visualization.model.diagram.attribute.types;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration for specifying the vertical alignment (i.e. top, middle/center, bottom).
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE)
@XmlEnum(String.class)
public enum VerticalAlignment {

    /**
     * Vertical alignment to the top.
     */
    TOP("t"),

    /**
     * Centered vertical alignment.
     */
    CENTER("c"),

    /**
     * Bottom vertical alignment.
     */
    BOTTOM("b");

    // Internal state
    private String dotTokenValue;

    VerticalAlignment(final String dotTokenValue) {
        this.dotTokenValue = dotTokenValue;
    }

    /**
     * Retrieves the Token value for this {@link VerticalAlignment}.
     *
     * @return the Token value for this {@link VerticalAlignment}.
     */
    public String getDotTokenValue() {
        return dotTokenValue;
    }
}
