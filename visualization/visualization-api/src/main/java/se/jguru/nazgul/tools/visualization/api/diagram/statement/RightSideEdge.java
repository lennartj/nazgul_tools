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

import se.jguru.nazgul.tools.visualization.api.StringRenderable;
import se.jguru.nazgul.tools.visualization.api.diagram.AbstractGraph;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.NodeID;

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
public class RightSideEdge extends AbstractEdge implements StringRenderable {

    // Internal state
    private boolean isDirectedGraph;
    private RightSideEdge rightSideEdge;

    /**
     * Creates a {@link RightSideEdge} wrapping the supplied data.
     *
     * @param nodeID      A non-null {@link NodeID} instance.
     * @param owningGraph The non-null {@link Graph} instance wherein this {@link RightSideEdge} is part.
     */
    public RightSideEdge(final NodeID nodeID, final Graph owningGraph) {
        super(nodeID);

        // Check sanity
        if (owningGraph == null) {
            throw new IllegalArgumentException("Cannot handle null 'owningGraph' argument.");
        }

        // Assign internal state
        this.isDirectedGraph = owningGraph.isDigraph();
    }

    /**
     * Creates a {@link RightSideEdge} wrapping the supplied data.
     *
     * @param subgraph    A non-null {@link Subgraph} instance.
     * @param owningGraph The non-null {@link Graph} instance wherein this {@link RightSideEdge} is part.
     */
    public RightSideEdge(final Subgraph subgraph, final Graph owningGraph) {
        super(subgraph);

        // Check sanity
        if (owningGraph == null) {
            throw new IllegalArgumentException("Cannot handle null 'owningGraph' argument.");
        }

        // Assign internal state
        this.isDirectedGraph = owningGraph.isDigraph();
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
     * Renders this {@link RightSideEdge} according to the structure:
     * <pre>edgeop (node_id | subgraph) [ edgeRHS ]</pre>
     *
     * @return The fully rendered version of this {@link RightSideEdge}, including all child {@link RightSideEdge}
     * instances linked to it.
     */
    @Override
    public String render() {

        final String prefix = (isDirectedGraph ? "->" : "--") + " " + getAbstractEdgeRendering();

        // Done?
        if (rightSideEdge == null) {
            return prefix;
        }

        // All Done.
        return prefix + " " + rightSideEdge.render();
    }

    /**
     * Factory method creating a {@link RightSideEdge} which terminates in the Node or Subgraph with the supplied ID
     * and within the supplied Graph.
     *
     * @param id              A non-null identifier of a NodeID or Subgraph to which this {@link RightSideEdge}
     *                        should go.
     * @param immediateParent The non-null {@link AbstractGraph} within which the returned {@link RightSideEdge}
     *                        should reside.
     * @param withinGraph     The non-null Graph which is the ultimate parent of the {@link RightSideEdge} to create.
     * @return A newly constructed {@link RightSideEdge} which <strong>is not yet added to any {@link Edge} or
     * {@link RightSideEdge}</strong> - or null if no Node or Subgraph with the supplied id was
     * found in the withinGraph.
     */
    public static RightSideEdge to(final String id, final AbstractGraph immediateParent, final Graph withinGraph) {

        // Check sanity
        if (id == null) {
            throw new IllegalArgumentException("Cannot handle null 'id' argument.");
        }
        if (immediateParent == null) {
            throw new IllegalArgumentException("Cannot handle null 'immediateParent' argument.");
        }
        if (withinGraph == null) {
            throw new IllegalArgumentException("Cannot handle null 'withinGraph' argument.");
        }

        // First, assume that the ID is a NodeID.
        final Statements statements = immediateParent.getStatements();
        final Node node = statements.find(Node.class, id, true);
        if (node != null) {
            return new RightSideEdge(node.getNodeID(), withinGraph);
        }

        // Next, assume that the ID is a Subgraph.
        final Subgraph subgraph = statements.find(Subgraph.class, id, true);
        if (subgraph != null) {
            return new RightSideEdge(subgraph, withinGraph);
        }

        // All Done.
        return null;
    }
}
