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

/**
 * Abstract AttributeList implementation delegating all calls to an internal AttributeList delegate.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractDelegatingAttributeList implements AttributeList {

    // Internal state
    private AttributeList delegate;

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
        if(value != null && !value.isEmpty()) {
            delegate.addAttribute(key, value);
        }

        // Return this object, for chaining.
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isEmpty() {
        return delegate.isEmpty();
    }
}
