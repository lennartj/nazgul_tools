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
package se.jguru.nazgul.tools.visualization.api.diagram;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderable;
import se.jguru.nazgul.tools.visualization.api.Renderable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstract identifiable Renderable implementation, sporting a string ID and standard implementations of the
 * {@link Object#hashCode()} and {@link Object#equals(Object)} methods.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Renderable.NAMESPACE, propOrder = {"id"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractStringIdentifiable extends AbstractStringRenderable {

    // Internal state
    @XmlElement(required = true)
    private String id;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public AbstractStringIdentifiable() {
        // Do nothing.
    }

    /**
     * Creates an {@link AbstractStringIdentifiable} wrapping the supplied data.
     *
     * @param id               a non-null and non-empty identifier, assumed to be unique within a Graph.
     * @param indentationLevel the non-negative indentation level of this {@link AbstractStringIdentifiable}.
     */
    protected AbstractStringIdentifiable(final String id, final int indentationLevel) {

        // Delegate
        super(indentationLevel);

        // Check sanity
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'id' argument.");
        }

        // Assign internal state
        this.id = id;
    }

    /**
     * Retrieves the identifier of this {@link AbstractStringIdentifiable}. Should be unique within a Graph.
     *
     * @return the unique identifier of this {@link AbstractStringIdentifiable}.
     */
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {

        // Fail fast
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractStringIdentifiable)) {
            return false;
        }

        // Delegate to internal state
        final AbstractStringIdentifiable that = (AbstractStringIdentifiable) o;
        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    /**
     * @return A standard string representation of this {@link AbstractStringIdentifiable}.
     */
    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "]: " + render();
    }

    /**
     * Convenience method used to quote the id for rendering, as well as surround it with whitespace.
     *
     * @return The id, surrounded by whitespace and quoted.
     */
    protected String getQuotedId() {
        return " \"" + getId() + "\" ";
    }
}