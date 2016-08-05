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

import se.jguru.nazgul.tools.visualization.api.Renderable;
import se.jguru.nazgul.tools.visualization.api.diagram.AbstractStringIdentifiable;
import se.jguru.nazgul.tools.visualization.api.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.NodeAttributeList;

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
@XmlType(namespace = Renderable.NAMESPACE, propOrder = {"nodeID", "attributeList"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Node extends AbstractStringIdentifiable implements Statement {

    // Internal state
    @XmlElement(required = true)
    private NodeID nodeID;

    @XmlElement
    private NodeAttributeList attributeList;

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

        super(nodeID.getId(), 0);

        this.nodeID = nodeID;
        this.attributeList = new NodeAttributeList();
    }

    /**
     * Retrieves the {@link NodeAttributeList} of this {@link Node}.
     *
     * @return the non-null {@link NodeAttributeList} of this {@link Node}.
     */
    public NodeAttributeList getAttributes() {
        return attributeList;
    }

    /**
     * Retrieves the non-null {@link NodeID} for this {@link Node}.
     *
     * @return the non-null {@link NodeID} for this {@link Node}.
     */
    public NodeID getNodeID() {
        return nodeID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doRender() {
        return nodeID.render() + (attributeList.isEmpty() ? "" : " " + attributeList.render());
    }
}
