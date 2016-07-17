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
package se.jguru.nazgul.tools.visualization.api.diagram.statement;

import se.jguru.nazgul.tools.visualization.api.StringRenderable;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.SortedAttributeList;

/**
 * <p>Attribute statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     attr_stmt : (graph | node | edge) attr_list
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     graph [	fontname = "Helvetica-Oblique",
 *              fontsize = "36",
 *              label = "\n\n\n\nObject Oriented Graphs\nStephen North, 3/19/93",
 *              size = "6,6" ];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class Attribute implements Statement {

    /**
     * An enumeration corresponding to the selection within the grammar statement:
     * <strong><code>attr_stmt : (graph | node | edge) attr_list</code></strong> in the
     * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
     */
    public enum AttributeType implements StringRenderable {

        GRAPH("graph"),
        NODE("node"),
        EDGE("edge");

        // Internal state
        private String dotToken;

        AttributeType(final String dotToken) {
            this.dotToken = dotToken;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String render() {
            return dotToken;
        }
    }

    // Internal state
    private AttributeType attributeType;
    private SortedAttributeList attributeList;

    /**
     * Creates an {@link Attribute} for the supplied {@link AttributeType}.
     *
     * @param attributeType A non-null {@link AttributeType} indicating which
     */
    public Attribute(final AttributeType attributeType) {

        // Check sanity
        if (attributeType == null) {
            throw new IllegalArgumentException("Cannot handle null 'attributeType' argument.");
        }

        // Assign internal state
        this.attributeType = attributeType;
        this.attributeList = new SortedAttributeList();
    }

    /**
     * Retrieves the {@link AttributeType} of this {@link Attribute}.
     *
     * @return the non-null type of this {@link Attribute}.
     */
    public AttributeType getAttributeType() {
        return attributeType;
    }

    /**
     * Retrieves the current {@link SortedAttributeList} of this {@link Attribute}.
     *
     * @return the non-null {@link SortedAttributeList} of this {@link Attribute}.
     */
    public SortedAttributeList getAttributeList() {
        return attributeList;
    }

    /**
     * <p>Renders this AttributeStatement on the following form:</p>
     * <pre>(graph | node | edge) attr_list</pre>
     */
    @Override
    public String render() {
        return getAttributeType().render() + " " + getAttributeList().render();
    }
}
