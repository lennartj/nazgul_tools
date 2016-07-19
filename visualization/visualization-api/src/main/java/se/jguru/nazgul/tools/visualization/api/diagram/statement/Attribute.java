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
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.AbstractDelegatingAttributeList;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.SortedAttributeList;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.builder.EdgeAttributeList;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.builder.GraphAttributeList;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.builder.NodeAttributeList;

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
@SuppressWarnings("PMD")
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

        /**
         * Retrieves an Attribute wrapping this AttributeType.
         *
         * @return an Attribute wrapping this AttributeType.
         */
        public Attribute get() {
            return new Attribute(this);
        }
    }

    // Internal state
    private AttributeType attributeType;
    private AbstractDelegatingAttributeList attributeList;

    /**
     * Creates an {@link Attribute} for the supplied {@link AttributeType}.
     *
     * @param attributeType A non-null {@link AttributeType} indicating which
     */
    private Attribute(final AttributeType attributeType) {

        // Assign internal state
        this.attributeType = attributeType;
        switch (attributeType) {
            case NODE:
                this.attributeList = new NodeAttributeList();
                break;
            case GRAPH:
                this.attributeList = new GraphAttributeList();
                break;
            default:
                this.attributeList = new EdgeAttributeList();
                break;
        }
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
     * Retrieves the {@link AbstractDelegatingAttributeList} subtype pertaining to
     * this {@link AttributeType}'s Attribute.
     *
     * @return the non-null {@link AbstractDelegatingAttributeList} subtype pertaining to  this {@link Attribute}.
     */
    public <A extends AbstractDelegatingAttributeList> A getAttributeList() {
        return (A) attributeList;
    }

    /**
     * Convenience method to add a new key/value pair to this Attribute.
     *
     * @param key   A non-null and non-empty string constituting the key in the key/value pair.
     * @param value A non-null string constituting the value in the key/value pair.
     * @return This {@link Attribute} instance, for chaining.
     * @see SortedAttributeList#addAttribute(String, String)
     */
    public Attribute addAttribute(final String key, final String value) {

        // Delegate
        getAttributeList().addAttribute(key, value);

        // All Done.
        return this;
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
