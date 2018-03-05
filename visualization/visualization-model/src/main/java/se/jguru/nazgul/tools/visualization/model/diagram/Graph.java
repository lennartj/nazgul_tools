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

import se.jguru.nazgul.tools.visualization.model.diagram.statement.Edge;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Graph statement Renderer, complying to the specification in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlRootElement(namespace = AbstractIdentifiable.NAMESPACE)
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"isDigraph", "isStrict"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Graph extends AbstractGraph {

    /**
     * 'true' to make this Graph a directed graph, and 'false' to make it an undirected one.
     */
    @XmlAttribute
    private boolean isDigraph;

    /**
     * 'true' to make this Graph strict.
     */
    @XmlAttribute
    private boolean isStrict;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Graph() {
        // Do nothing
    }

    /**
     * Compound constructor creating a {@link Graph} wrapping the supplied data.
     *
     * @param id        The ID of this Graph. While Graphviz/Dot permits the absence of an ID, this
     *                  builder requires one - and hence the ID can neither be null nor empty.
     * @param isDigraph {@code true} to make this Graph a directed graph, and false to make it an undirected one.
     * @param isStrict  {@code true} to make this Graph strict.
     */
    public Graph(final String id, final boolean isDigraph, final boolean isStrict) {

        // Delegate
        super(id);

        // Assign internal state
        this.isDigraph = isDigraph;
        this.isStrict = isStrict;
    }

    /**
     * Indicates if this Graph is directed ("digraph") or not ("graph").
     *
     * @return {@code true} if this Graph is directed ("digraph").
     */
    public boolean isDigraph() {
        return isDigraph;
    }

    /**
     * Indicates if this Graph is defined as strict or not.
     *
     * @return {@code true} if this Graph is strict.
     */
    public boolean isStrict() {
        return isStrict;
    }

    /**
     * Convenience method to add and return an Edge between a Node/Subgraph and another Node/Subgraph within the
     * immediate Statements in this Graph.
     *
     * @param fromId The non-empty ID of the Node/Subgraph from which the Edge should originate.
     * @param toId   The non-empty ID of the Node/Subgraph to which the Edge should be directed.
     * @return The newly created Edge - or {@code null} if the Edge could not be created.
     */
    public Edge addEdge(final String fromId, final String toId) {
        return getStatements().addEdge(fromId, toId, this);
    }
}
