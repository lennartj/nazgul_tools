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

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>Attribute list which sorts its key/value pairs alphabetically, corresponding to
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
public class SortedAttributeList implements StringRenderable {

    /**
     * The token starting a SortedAttributeList.
     */
    public static final String START_TOKEN = "[ ";

    /**
     * The token ending a SortedAttributeList.
     */
    public static final String END_TOKEN = " ]";

    /**
     * The token separating attribute (key/value) pairs from one another.
     */
    public static final String DELIMITER = ", ";

    /**
     * The token separating key and value attributes from one another.
     */
    public static final String SEPARATOR = "=";

    // Internal state
    private SortedMap<String, String> attributes;

    /**
     * Creates a new {@link SortedAttributeList} with an empty attributes Map using natural sort order.
     */
    public SortedAttributeList() {
        this(null);
    }

    /**
     * Creates a new {@link SortedAttributeList} with an empty attributes Map using the
     * supplied {@link Comparator} for sort ordering.
     *
     * @param comparator An optional (i.e. nullable) Comparator indicating the sort order within the underlying
     *                   attributes {@link SortedMap}. If null, the {@link TreeMap} natural sort order is used.
     * @see TreeMap#TreeMap(Comparator)
     */
    public SortedAttributeList(final Comparator<String> comparator) {

        // Assign internal state
        this.attributes = new TreeMap<>(comparator);
    }

    /**
     * Adds the supplied attribute key/value pair to this {@link SortedAttributeList}
     * Any key already present will be overwritten - the latest added attribute is the one being rendered.
     *
     * @param key   The non-null and non-empty attribute key.
     * @param value The non-null attribute value.
     */
    public void addAttribute(final String key, final String value) {

        // Check sanity
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'key' argument.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Cannot handle null 'value' argument.");
        }

        // Assign internal state
        attributes.put(key, value);
    }

    /**
     * Indicates if this {@link SortedAttributeList} is empty or contains data.
     *
     * @return {@code true} if this {@link SortedAttributeList} does not contain data.
     */
    public boolean isEmpty() {
        return attributes == null || attributes.isEmpty();
    }

    /**
     * Renders all attributes separated by commas, and surrounded by "[ " and " ]".
     *
     * @return The {@link SortedAttributeList} conforming to the DOT language specification for an attribute list,
     * namely {@code attr_list : '[' [ a_list ] ']' [ attr_list ]}
     */
    @Override
    public String render() {

        // Fail fast
        if (attributes.isEmpty()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder(START_TOKEN);
        for (Map.Entry<String, String> current : attributes.entrySet()) {
            builder.append(current.getKey() + SEPARATOR + "\"" + current.getValue() + "\"" + DELIMITER);
        }

        // Remove the last delimiter
        builder.delete(builder.length() - DELIMITER.length(), builder.length());

        // All Done.
        builder.append(END_TOKEN);
        return builder.toString();
    }
}
