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

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Abstract AttributeList implementation delegating all calls to an internal AttributeList delegate.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"delegate"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({SortedAttributeList.class, EdgeAttributeList.class, NodeAttributeList.class, GraphAttributeList.class})
public abstract class AbstractDelegatingAttributeList implements AttributeList {

    // Internal state
    @XmlElements({
        @XmlElement(name = "SortedAttributeList", type = SortedAttributeList.class)
    })
    protected AttributeList delegate;

    /**
     * Default constructor creating an {@link AbstractDelegatingAttributeList} delegating all calls to
     * a {@link SortedAttributeList} instance. This constructor should be preferred.
     */
    public AbstractDelegatingAttributeList() {
        this(new SortedAttributeList());
    }

    /**
     * Compound constructor creating an {@link AbstractDelegatingAttributeList} wrapping the supplied
     * {@link AttributeList} to which all calls will be forwarded.
     *
     * @param delegate The delegate {@link AttributeList}.
     */
    protected AbstractDelegatingAttributeList(final AttributeList delegate) {

        // Check sanity
        if(delegate == null) {
            throw new IllegalArgumentException("Cannot handle null 'delegate' argument.");
        }

        // Assign internal state
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String render() {
        return delegate.render();
    }

    /**
     * <p>The {@link AbstractDelegatingAttributeList} returns a reference to itself, rather than the
     * delegate {@link AttributeList}, to facilitate chaining.</p>
     *
     * {@inheritDoc}
     */
    @Override
    public final AttributeList addAttribute(final String key, final String value) {

        // Check sanity, and add the Attribute in the delegate.
        if(key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
            delegate.addAttribute(key, value);
        }

        // Return this object, for chaining.
        return this;
    }

    /**
     * Convenience method to handle converting doubles to Strings in a gentle way.
     *
     * @param key   The non-null and non-empty attribute key.
     * @param value The double attribute value.
     * @return This {@link AttributeList}, for chaining.
     */
    protected final AttributeList addAttribute(final String key, final double value) {

        // Convert the double to a String gently.
        final String doubleValue = BigDecimal.valueOf(value).toPlainString();

        // Delegate.
        return addAttribute(key, doubleValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> toMap() {
        return delegate.toMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isEmpty() {
        return delegate.isEmpty();
    }
}
