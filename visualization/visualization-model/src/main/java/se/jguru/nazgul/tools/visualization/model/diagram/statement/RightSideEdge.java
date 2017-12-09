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
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Right-side Edge part statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     edgeRHS : edgeop (node_id | subgraph) [ edgeRHS ]
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     e -> b[label="0.7",weight="0.7"];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"rightSideEdge"})
@XmlAccessorType(XmlAccessType.FIELD)
public class RightSideEdge extends AbstractEdge {

    /**
     * An optional RightSideEdge hooked up to this one.
     */
    @XmlElement
    private RightSideEdge rightSideEdge;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public RightSideEdge() {
        // Do nothing.
    }

    /**
     * Creates a {@link RightSideEdge} wrapping the supplied data.
     *
     * @param nodeID A non-null {@link NodeID} instance.
     */
    public RightSideEdge(final NodeID nodeID) {
        super(nodeID);
    }

    /**
     * Creates a {@link RightSideEdge} wrapping the supplied data.
     *
     * @param subgraph A non-null {@link Subgraph} instance.
     */
    public RightSideEdge(final Subgraph subgraph) {
        super(subgraph);
    }

    /**
     * <p>Assigns the supplied {@link RightSideEdge} to this one, implying that the supplied {@link RightSideEdge}
     * should be rendered after this one, on the form:</p>
     * <pre>
     *     edgeRHS : edgeop (node_id | subgraph) [ edgeRHS ]
     * </pre>
     *
     * @param rightSideEdge a non-null {@link RightSideEdge} instance.
     */
    public void setRightSideEdge(final RightSideEdge rightSideEdge) {

        // Check sanity
        if (rightSideEdge == null) {
            throw new IllegalArgumentException("Cannot handle null 'rightSideEdge' argument.");
        }

        // Assign internal state
        this.rightSideEdge = rightSideEdge;
    }

    /**
     * Retrieves the optional RightSideEdge to the right of this {@link RightSideEdge}.
     *
     * @return the optional (i.e. nullable) RightSideEdge to the right of this {@link RightSideEdge}.
     */
    public RightSideEdge getRightSideEdge() {
        return rightSideEdge;
    }

    /**
     * Retrieves the RightSideEdge separator as indicated within the DOT language specification.
     *
     * @param isDirectedGraph if {@code true}, the edge symbol of a directed graph (i.e. "-&gt;") and otherwise the
     *                        edge symbol of an undirected graph (i.e. "--") is returned.
     * @return The edge separator given the status of a Graph.
     */
    public static String getEdgeSeparator(final boolean isDirectedGraph) {
        return isDirectedGraph ? "->" : "--";
    }

    /**
     * Factory method creating a {@link RightSideEdge} which terminates in the Node or Subgraph with the supplied ID
     * and within the supplied Graph.
     *
     * @param id          A non-null identifier of a NodeID or Subgraph to which this {@link RightSideEdge}
     *                    should go.
     * @param withinGraph The non-null Graph which is the ultimate parent of the {@link RightSideEdge} to create.
     * @return A newly constructed {@link RightSideEdge} which <strong>is not yet added to any {@link Edge} or
     * {@link RightSideEdge}</strong> - or null if no Node or Subgraph with the supplied id was
     * found in the withinGraph.
     */
    public static RightSideEdge to(final String id, final Graph withinGraph) {

        // Check sanity
        if (id == null) {
            throw new IllegalArgumentException("Cannot handle null 'id' argument.");
        }
        if (withinGraph == null) {
            throw new IllegalArgumentException("Cannot handle null 'withinGraph' argument.");
        }

        // First, assume that the ID is a NodeID.
        // final Statements statements = immediateParent.getStatements();
        final Node node = withinGraph.getStatements().findNode(id, true);
        if (node != null) {
            return new RightSideEdge(node.getNodeID());
        }

        // Next, assume that the ID is a Subgraph.
        final Subgraph subgraph = withinGraph.getStatements().findSubgraph(id, true);
        if (subgraph != null) {
            return new RightSideEdge(subgraph);
        }

        // All Done.
        return null;
    }
}
