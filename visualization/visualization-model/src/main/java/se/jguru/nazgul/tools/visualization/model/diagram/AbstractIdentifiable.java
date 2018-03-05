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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Abstract implementation for types which should be uniquely identifiable, sporting a string ID and standard
 * implementations of the {@link Object#hashCode()} and {@link Object#equals(Object)} methods.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"id"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractIdentifiable implements Serializable {

    /**
     * The XML namespace used by the Nazgul Tool Visualization Model.
     */
    public static final String NAMESPACE = "http://www.jguru.se/nazgul/tools/visualization";

    // Internal state
    @XmlElement(required = true)
    private String id;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public AbstractIdentifiable() {
        // Do nothing
    }

    /**
     * Compound constructor creating an {@link AbstractIdentifiable} instance wrapping the supplied data.
     *
     * @param id a non-null and non-empty identifier, assumed to be unique (within a Graph).
     * @throws IllegalArgumentException if the 'id' argument is null or empty.
     */
    protected AbstractIdentifiable(final String id) throws IllegalArgumentException {

        // Check sanity
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'id' argument.");
        }

        this.id = id;
    }

    /**
     * Retrieves the identifier of this {@link AbstractIdentifiable}. Should be unique within a Graph.
     *
     * @return the unique identifier of this {@link AbstractIdentifiable}.
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
        if (!(o instanceof AbstractIdentifiable)) {
            return false;
        }

        // Delegate to internal state
        final AbstractIdentifiable that = (AbstractIdentifiable) o;
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
     * @return A standard string representation of this {@link AbstractIdentifiable}.
     */
    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "]: " + getId();
    }

    /**
     * Convenience method used to quote the id for rendering, as well as surround it with whitespace.
     *
     * @return The id, surrounded by whitespace and quoted.
     */
    public String getQuotedId() {
        return " \"" + getId() + "\" ";
    }
}
