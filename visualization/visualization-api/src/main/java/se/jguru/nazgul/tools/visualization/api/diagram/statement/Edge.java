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
import se.jguru.nazgul.tools.visualization.api.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.EdgeAttributeList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Edge statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     edge_stmt : (node_id | subgraph) edgeRHS [ attr_list ]
 *     edgeRHS   : edgeop (node_id | subgraph) [ edgeRHS ]
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     e -> b[label="0.7",weight="0.7"];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Renderable.NAMESPACE, propOrder = {"attributes", "rightSideEdge"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Edge extends AbstractEdge implements Statement {

    // Internal state
    @XmlElement
    private EdgeAttributeList attributes;

    @XmlElement(required = true)
    private RightSideEdge rightSideEdge;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Edge() {
        // Do nothing.
    }

    /**
     * Compound constructor creating an {@link Edge} wrapping the supplied data.
     *
     * @param nodeID        A non-null {@link NodeID} instance.
     * @param rightSideEdge A non-null {@link RightSideEdge} instance.
     */
    public Edge(final NodeID nodeID, final RightSideEdge rightSideEdge) {

        // Delegate
        super(nodeID, 0);

        // Check sanity
        if (rightSideEdge == null) {
            throw new IllegalArgumentException("Cannot handle null 'rightSideEdge' argument.");
        }

        // Assign internal state
        this.rightSideEdge = rightSideEdge;
        this.attributes = new EdgeAttributeList();
    }

    /**
     * Compound constructor creating an {@link Edge} wrapping the supplied data.
     *
     * @param subgraph      A non-null {@link Subgraph} instance.
     * @param rightSideEdge A non-null {@link RightSideEdge} instance.
     */
    public Edge(final Subgraph subgraph, final RightSideEdge rightSideEdge) {

        // Delegate
        super(subgraph, 0);

        // Check sanity
        if (rightSideEdge == null) {
            throw new IllegalArgumentException("Cannot handle null 'rightSideEdge' argument.");
        }

        // Assign internal state
        this.rightSideEdge = rightSideEdge;
        this.attributes = new EdgeAttributeList();
    }

    /**
     * Retrieves the {@link EdgeAttributeList} of this Edge.
     *
     * @return a non-null {@link EdgeAttributeList}.
     */
    public EdgeAttributeList getAttributes() {
        return attributes;
    }

    /**
     * Retrieves the right side edge to this {@link Edge}.
     *
     * @return a non-null {@link RightSideEdge}.
     */
    public RightSideEdge getRightSideEdge() {
        return rightSideEdge;
    }

    /**
     * Renders this Edge according to the following structure:
     * <pre>(node_id | subgraph) edgeRHS [ attr_list ]</pre>
     * <p>... where ... </p>
     * <pre>edgeRHS   : edgeop (node_id | subgraph) [ edgeRHS ]</pre>
     *
     * @return This rendered Edge.
     * @see RightSideEdge
     */
    @Override
    public String doRender() {

        final String prefix = getAbstractEdgeRendering() + " " + getRightSideEdge().doRender();

        if (getAttributes().isEmpty()) {
            return prefix;
        }

        // Append the SortedAttributesList as well.
        return prefix + " " + getAttributes().render();
    }
}
