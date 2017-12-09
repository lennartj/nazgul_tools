/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
 * %%
 * Copyright (C) 2010 - 2017 jGuru Europe AB
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
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.NodeAttributes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Node statement Renderer, complying to the specification in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"nodeID", "nodeAttributes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Node extends AbstractIdentifiable implements Statement {

    /**
     * The Node identifier of this Node.
     */
    @XmlElement(required = true)
    private NodeID nodeID;

    /**
     * The optional node attribute list within this Node.
     */
    @XmlElement
    private NodeAttributes nodeAttributes;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Node() {
        // Do nothing
    }

    /**
     * Convenience constructor creating a {@link Node} with the supplied ID.
     *
     * @param id a non-null and non-empty identifier, assumed to be unique within a Graph.
     */
    public Node(final String id) {
        this(new NodeID(id, null));
    }

    /**
     * Compound constructor creating a {@link Node} wrapping the supplied {@link NodeID}.
     *
     * @param nodeID A non-null {@link NodeID} instance.
     */
    public Node(final NodeID nodeID) {

        super(nodeID.getId());

        this.nodeID = nodeID;
        this.nodeAttributes = new NodeAttributes();
    }

    /**
     * Retrieves the {@link NodeAttributes} of this {@link Node}.
     *
     * @return the non-null {@link NodeAttributes} of this {@link Node}.
     */
    public NodeAttributes getAttributes() {
        return nodeAttributes;
    }

    /**
     * Retrieves the non-null {@link NodeID} for this {@link Node}.
     *
     * @return the non-null {@link NodeID} for this {@link Node}.
     */
    public NodeID getNodeID() {
        return nodeID;
    }
}
