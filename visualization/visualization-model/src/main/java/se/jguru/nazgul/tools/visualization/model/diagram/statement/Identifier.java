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
package se.jguru.nazgul.tools.visualization.model.diagram.statement;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Identifier statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>ID '=' ID</pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"targetIdentifier"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Identifier extends AbstractIdentifiable implements Statement {

    /**
     * The ID of the right side ("target side") for this Identifier.
     */
    @XmlElement(required = true)
    private String targetIdentifier;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Identifier() {
        // Do nothing
    }

    /**
     * Compound constructor creating an {@link Identifier} wrapping the supplied data.
     *
     * @param id               A non-empty identifier which must be different from the targetIdentifier.
     * @param targetIdentifier A non-empty identifier which must be different from the id.
     */
    public Identifier(final String id, final String targetIdentifier) {

        // Delegate
        super(id);

        // Check sanity
        if (targetIdentifier == null || targetIdentifier.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'targetIdentifier' argument.");
        }
        if (targetIdentifier.equals(id)) {
            throw new IllegalArgumentException("Cannot assign id '" + id
                    + "' to itself; 'id' and 'targetIdentifier' must differ.");
        }

        // Assign internal state
        this.targetIdentifier = targetIdentifier;
    }

    /**
     * The target identifier of this {@link Identifier}.
     *
     * @return the non-empty target identifier of this {@link Identifier}.
     */
    public String getTargetIdentifier() {
        return targetIdentifier;
    }
}
