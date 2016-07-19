/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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
package se.jguru.nazgul.tools.visualization.api.diagram.attribute;

import se.jguru.nazgul.tools.visualization.api.StringRenderable;

/**
 * <p>Specification for an Attribute list which sorts its key/value pairs alphabetically, corresponding to
 * the DOT grammar specification below. This implementation only uses comma for
 * delimiters ({@link #DELIMITER}.</p>
 * <pre>
 *     attr_list : '[' [ a_list ] ']' [ attr_list ]
 *     a_list    : ID '=' ID [ (';' | ',') ] [ a_list ]
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     [ sides="7", distortion="-0.687574", orientation="58", skew="-0.180116", color="lightsteelblue1" ] ;
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface AttributeList extends StringRenderable {

    /**
     * The token starting an AttributeList.
     */
    String START_TOKEN = "[ ";

    /**
     * The token ending a SortedAttributeList.
     */
    String END_TOKEN = " ]";
    /**
     * The token separating attribute (key/value) pairs from one another.
     */
    String DELIMITER = ", ";
    /**
     * The token separating key and value attributes from one another.
     */
    String SEPARATOR = "=";

    /**
     * <p>Adds the supplied attribute key/value pair to this {@link AttributeList}
     * Any key already present will be overwritten - the latest added attribute is the one being rendered.</p>
     * <p><strong>Note:</strong> It is not recommended to use this method to add key/value pairs - but instead use
     * the typed attribute-adding methods in sub-interfaces. Those methods expose all attributes known to (and hence
     * processable by) the respective {@link se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement}
     * subtypes.</p>
     *
     * @param key   The non-null and non-empty attribute key.
     * @param value The non-null attribute value.
     * @return This {@link AttributeList}, for chaining.
     */
    AttributeList addAttribute(String key, String value);

    /**
     * Indicates if this {@link AttributeList} is empty or contains data.
     *
     * @return {@code true} if this {@link AttributeList} does not contain data.
     */
    boolean isEmpty();
}
