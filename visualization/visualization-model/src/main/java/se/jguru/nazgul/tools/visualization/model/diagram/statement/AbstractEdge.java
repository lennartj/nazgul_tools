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


package se.jguru.nazgul.tools.visualization.model.diagram.statement;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstract implementation of an Edge, storing commonly shared state for all Edge components.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"nodeID", "subgraph"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractEdge extends AbstractIdentifiable {

    /**
     * The optional NodeID corresponding to the left side of this {@link AbstractEdge}.
     * This AbstractEdge must either originate from a NodeID or a Subgraph.
     */
    @XmlElement
    private NodeID nodeID;

    /**
     * The optional {@link Subgraph} corresponding to the left side of this {@link AbstractEdge}.
     * This AbstractEdge must either originate from a NodeID or a Subgraph.
     */
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
     * @param nodeID A non-null {@link NodeID} instance.
     */
    public AbstractEdge(final NodeID nodeID) {

        // Delegate
        super(nodeID.getId());

        // Assign internal state
        this.nodeID = nodeID;
    }

    /**
     * Creates an AbstractEdge using a {@link Subgraph} instance.
     *
     * @param subgraph A non-null {@link Subgraph} instance.
     */
    public AbstractEdge(final Subgraph subgraph) {

        // Delegate
        super(subgraph.getId());

        // Assign internal state
        this.subgraph = subgraph;
    }

    /**
     * Retrieves the optional NodeID of this {@link AbstractEdge}
     *
     * @return the optional NodeID of this {@link AbstractEdge}
     */
    public NodeID getNodeID() {
        return nodeID;
    }

    /**
     * Retrieves the optional {@link Subgraph} of this {@link AbstractEdge}.
     *
     * @return the optional Subgraph of this {@link AbstractEdge}.
     */
    public Subgraph getSubgraph() {
        return subgraph;
    }
}
