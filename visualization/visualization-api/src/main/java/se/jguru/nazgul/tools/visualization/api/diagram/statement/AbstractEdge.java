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

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderable;
import se.jguru.nazgul.tools.visualization.api.Renderable;
import se.jguru.nazgul.tools.visualization.api.diagram.NodeID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstract implementation of an Edge, storing commonly shared state for all Edge components.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Renderable.NAMESPACE, propOrder = {"nodeID", "subgraph"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractEdge extends AbstractStringRenderable {

    // Internal state
    @XmlElement
    private NodeID nodeID;

    @XmlElement
    private Subgraph subgraph;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public AbstractEdge() {
        // Do nothing
    }

    /**
     * Creates an AbstractEdge using a NodeID.
     *
     * @param indentationLevel The non-negative indentation level.
     * @param nodeID A non-null {@link NodeID} instance.
     */
    public AbstractEdge(final NodeID nodeID, final int indentationLevel) {

        // Delegate
        super(indentationLevel);

        // Check sanity
        if (nodeID == null) {
            throw new IllegalArgumentException("Cannot handle null 'nodeID' argument.");
        }

        // Assign internal state
        this.nodeID = nodeID;
    }

    /**
     * Creates an AbstractEdge using a {@link Subgraph} instance.
     *
     * @param indentationLevel The non-negative indentation level.
     * @param subgraph A non-null {@link Subgraph} instance.
     */
    public AbstractEdge(final Subgraph subgraph, final int indentationLevel) {

        // Delegate
        super(indentationLevel);

        // Check sanity
        if (subgraph == null) {
            throw new IllegalArgumentException("Cannot handle null 'subgraph' argument.");
        }

        // Assign internal state
        this.subgraph = subgraph;
    }

    /**
     * Retrieves the ID of the non-null NodeID/Subgraph instance within this {@link AbstractEdge}.
     *
     * @return The non-null identifier of this {@link AbstractEdge}.
     */
    public String getId() {
        return nodeID == null ? subgraph.getId() : nodeID.getId();
    }

    /**
     * Retrieves the rendering of this {@link AbstractEdge}, implying the rendering of its non-null part
     * (i.e. NodeID or Subgraph respectively).
     *
     * @return the non-null rendering of this {@link AbstractEdge}.
     */
    protected String getAbstractEdgeRendering() {
        return nodeID == null ? "\"" + subgraph.getId() + "\"" : nodeID.render();
    }
}
