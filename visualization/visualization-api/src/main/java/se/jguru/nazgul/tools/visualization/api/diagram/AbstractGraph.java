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

import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstract specification for a container of {@link Statement} subclasses.
 * This implies either a Graph or a Subgraph.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"statements"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractGraph extends AbstractStringIdentifiable {

    // Internal state
    @XmlElement
    private Statements statements;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public AbstractGraph() {
        // Do nothing
    }

    /**
     * Compound constructor creating an {@link AbstractGraph} instance wrapping the supplied data.
     *
     * @param id a non-null and non-empty identifier, assumed to be unique (within a Graph).
     */
    protected AbstractGraph(final String id) {

        // Delegate
        super(id);

        // Create internal state
        this.statements = new Statements();
    }

    /**
     * @return The currently known Statements.
     */
    public Statements getStatements() {
        return statements;
    }

    /**
     * Convenience method to add one or more {@link Statement} objects to the contained
     * {@link Statements} holder within this {@link AbstractGraph}.
     *
     * @param toAdd One or more {@link Statement} instances.
     */
    public void add(final Statement... toAdd) {
        if (toAdd != null) {
            getStatements().add(toAdd);
        }
    }
}
