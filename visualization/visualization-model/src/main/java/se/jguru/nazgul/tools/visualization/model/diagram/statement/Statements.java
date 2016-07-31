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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.jguru.nazgul.tools.visualization.model.diagram.AbstractGraph;
import se.jguru.nazgul.tools.visualization.model.diagram.Comment;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.NodeID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>Utility class which orders statements and comments in a particular order. The default order (which should
 * yield no problematic references) is:</p>
 * <ol>
 * <li>AttributeStatements, which implies zero or all of the types {@link CommonGraphAttributes},
 * {@link CommonEdgeAttributes} and {@link CommonNodeAttributes}</li>
 * <li>{@link Node} statements</li>
 * <li>{@link Identifier} statements</li>
 * <li>{@link Edge} statements</li>
 * <li>{@link Subgraph} statements</li>
 * </ol>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractGraph.NAMESPACE, propOrder = {"commonAttributesComment", "commonGraphAttributes",
        "commonNodeAttributes", "commonEdgeAttributes", "nodesComment", "nodes", "identifiersComment", "identifiers",
        "edgesComment", "edges", "subgraphComment", "subgraphs"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Statements implements Serializable {

    // Our Log
    @XmlTransient
    public static final Logger log = LoggerFactory.getLogger(Statement.class);

    /**
     * A comment to be rendered before the common/shared attribute statements.
     */
    @XmlElement
    private Comment commonAttributesComment;

    /**
     * An optional statement containing common/shared Graph attributes. The attributes defined within the
     * GraphAttribute statement will be set as default on all Graph/Subgraphs.
     *
     * @see #subgraphs
     */
    @XmlElement
    private CommonGraphAttributes commonGraphAttributes;

    /**
     * An optional statement containing common/shared Node attributes. The attributes defined within the
     * NodeAttribute statement will be set as default on all Nodes.
     *
     * @see #nodes
     */
    @XmlElement
    private CommonNodeAttributes commonNodeAttributes;

    /**
     * An optional statement containing common/shared Edge attributes. The attributes defined within the
     * EdgeAttribute statement will be set as default on all Nodes.
     *
     * @see #nodes
     */
    @XmlElement
    private CommonEdgeAttributes commonEdgeAttributes;

    /**
     * A comment to be rendered before the Node statements.
     */
    @XmlElement
    private Comment nodesComment;

    /**
     * A sequence of all Nodes added/known to this Statements wrapper.
     */
    @XmlElementWrapper
    @XmlElement(name = "node")
    private List<Node> nodes;

    /**
     * A comment to be rendered before the Identifier statements.
     */
    @XmlElement
    private Comment identifiersComment;

    /**
     * A sequence of all Identifiers added/known to this statements wrapper.
     */
    @XmlElementWrapper
    @XmlElement(name = "identifier")
    private List<Identifier> identifiers;

    /**
     * A comment to be rendered before the Edge statements.
     */
    @XmlElement
    private Comment edgesComment;

    /**
     * A sequence of all Edges added/known to this Statements wrapper.
     */
    @XmlElementWrapper
    @XmlElement(name = "edge")
    private List<Edge> edges;

    /**
     * A comment to be rendered before the Subgraph statements.
     */
    @XmlElement
    private Comment subgraphComment;

    /**
     * A sequence of all Subgraphs added/known to this Statements wrapper.
     */
    @XmlElement
    private List<Subgraph> subgraphs;

    /**
     * Default constructor, assigning default lines to all Comments.
     * Reserved for framework use only.
     */
    public Statements() {

        super();

        // Assign internal state
        this.nodes = new ArrayList<>();
        this.identifiers = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.subgraphs = new ArrayList<>();

        setCommonAttributesComment(null);
        setNodesComment(null);
        setIdentifiersComment(null);
        setEdgesComment(null);
        setSubgraphComment(null);
    }

    /**
     * Assigns the comment to be rendered before the Common/Shared attribute statements.
     *
     * @param lines The line(s) within the Comment. A standard Comment will be used if lines is {@code null}.
     */
    public final void setCommonAttributesComment(final String... lines) {
        this.commonAttributesComment = lines == null
                ? new Comment("Common Attribute Statements")
                : new Comment(lines);
    }

    /**
     * Assigns the comment to be rendered before the Node statements.
     *
     * @param lines The line(s) within the Comment. A standard Comment will be used if lines is {@code null}.
     */
    public final void setNodesComment(final String... lines) {
        this.nodesComment = lines == null
                ? new Comment("Node Statements")
                : new Comment(lines);
    }

    /**
     * Assigns the comment to be rendered before the Identifier statements.
     *
     * @param lines The line(s) within the Comment. A standard Comment will be used if lines is {@code null}.
     */
    public final void setIdentifiersComment(final String... lines) {
        this.identifiersComment = lines == null
                ? new Comment("Identifier Statements")
                : new Comment(lines);
    }

    /**
     * Assigns the comment to be rendered before the Edge statements.
     *
     * @param lines The line(s) within the Comment. A standard Comment will be used if lines is {@code null}.
     */
    public final void setEdgesComment(final String... lines) {
        this.edgesComment = lines == null
                ? new Comment("Edge Statements")
                : new Comment(lines);
    }

    /**
     * Assigns the comment to be rendered before the Subgraph statements.
     *
     * @param lines The line(s) within the Comment. A standard Comment will be used if lines is {@code null}.
     */
    public final void setSubgraphComment(final String... lines) {
        this.subgraphComment = (lines == null
                ? new Comment("Subgraph Statements")
                : new Comment(lines));
    }

    /**
     * Adds one or more {@link Statement} objects to this {@link Statements} holder.
     *
     * @param toAdd One or more {@link Statement} instances.
     */
    @SuppressWarnings("PMD")
    public void add(final Statement... toAdd) {

        if (toAdd != null) {

            Arrays.stream(toAdd)
                    .filter(c -> c != null)
                    .forEach(c -> {

                        // Resolve the type of the current Statement, and assign it to
                        // the appropriate internal state.
                        if (c instanceof CommonEdgeAttributes) {
                            this.commonEdgeAttributes = (CommonEdgeAttributes) c;
                        } else if (c instanceof CommonGraphAttributes) {
                            this.commonGraphAttributes = (CommonGraphAttributes) c;
                        } else if (c instanceof CommonNodeAttributes) {
                            this.commonNodeAttributes = (CommonNodeAttributes) c;
                        } else if (c instanceof Node) {
                            this.nodes.add((Node) c);
                        } else if (c instanceof Identifier) {
                            this.identifiers.add((Identifier) c);
                        } else if (c instanceof Edge) {
                            this.edges.add((Edge) c);
                        } else if (c instanceof Subgraph) {
                            this.subgraphs.add((Subgraph) c);
                        }
                    });
        }
    }

    /**
     * Retrieves all Nodes within this Statement instance. No recursion into any Subgraph is done.
     *
     * @return all Nodes being immediate children to this {@link Statements} instance.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Retrieves all Identifiers within this Statement instance. No recursion into any Subgraph is done.
     *
     * @return all Identifiers being immediate children to this {@link Statements} instance.
     */
    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    /**
     * Retrieves all Edges within this Statement instance. No recursion into any Subgraph is done.
     *
     * @return all Edges being immediate children to this {@link Statements} instance.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Retrieves all Subgraphs within this Statement instance. No recursion into any child Subgraph is done.
     *
     * @return all Subgraphs being immediate children to this {@link Statements} instance.
     */
    public List<Subgraph> getSubgraphs() {
        return subgraphs;
    }

    /**
     * Finds the Node with the supplied identifier within this Statements (and within any contained Subgraphs, if the
     * {@code recursive} flag is true).
     *
     * @param identifier The identifier to match against the {@link Node#getId()} of a matching Node.
     * @param recursive  If {@code true}, the search should recurse into the Statements of Subgraphs.
     * @return The Node with the supplied identifier - or null if none was found.
     */
    public Node findNode(final String identifier, final boolean recursive) {

        Optional<Node> toReturn = getNodes().stream().filter(c -> c.getId().equals(identifier)).findFirst();
        if (!toReturn.isPresent() && recursive) {

            // Does the Node with the given identifier exist in any Subgraph?
            toReturn = subgraphs.stream().map(c -> c.getStatements().findNode(identifier, true)).findFirst();
        }

        // All Done.
        return toReturn.orElse(null);
    }

    /**
     * Finds the Edge with the supplied identifier within this Statements (and within any contained Subgraphs, if the
     * {@code recursive} flag is true).
     *
     * @param fromId    The Node or Subgraph identifier of the starting point of the Edge to find.
     * @param toId      The Node or Subgraph identifier of the finishing point of the Edge to find.
     * @param recursive If {@code true}, the search should recurse into the Statements of Subgraphs.
     * @return The Edge with the supplied identifier - or null if none was found.
     */
    public Edge findEdge(final String fromId, final String toId, final boolean recursive) {

        Optional<Edge> toReturn = getEdges().stream()
                .filter(c -> c.getId().equals(fromId) && c.getRightSideEdge().getId().equals(toId))
                .findFirst();

        if (!toReturn.isPresent() && recursive) {

            // Does the Node with the given identifier exist in any Subgraph?
            toReturn = subgraphs.stream().map(c -> c.getStatements().findEdge(fromId, toId, true)).findFirst();
        }

        // All Done.
        return toReturn.orElse(null);
    }

    /**
     * Finds the Subgraph with the supplied identifier within this Statements (and within any contained Subgraphs, if
     * the {@code recursive} flag is true).
     *
     * @param identifier The identifier to match against the {@link Subgraph#getId()} of a matching Node.
     * @param recursive  If {@code true}, the search should recurse into the Statements of Subgraphs.
     * @return The Subgraph with the supplied identifier - or null if none was found.
     */
    public Subgraph findSubgraph(final String identifier, final boolean recursive) {

        Optional<Subgraph> toReturn = getSubgraphs().stream().filter(c -> c.getId().equals(identifier)).findFirst();
        if (!toReturn.isPresent() && recursive) {

            // Does the Node with the given identifier exist in any Subgraph?
            toReturn = subgraphs.stream().map(c -> c.getStatements().findSubgraph(identifier, true)).findFirst();
        }

        // All Done.
        return toReturn.orElse(null);
    }

    /**
     * Convenience method to add and return an Edge between a Node/Subgraph and another Node/Subgraph.
     *
     * @param fromId      The non-empty ID of the Node/Subgraph from which the Edge should originate.
     * @param toId        The non-empty ID of the Node/Subgraph to which the Edge should be directed.
     * @param parentGraph The non-null parent {@link Graph} where the Edge is about to be created.
     * @return The newly created Edge - or {@code null} if the Edge could not be created.
     */
    public Edge addEdge(final String fromId, final String toId, final Graph parentGraph) {

        // Check sanity
        if (fromId == null || fromId.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'fromId' argument.");
        }
        if (toId == null || toId.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'toId' argument.");
        }
        if (parentGraph == null) {
            throw new IllegalArgumentException("Cannot handle null 'parentGraph' argument.");
        }

        Edge toReturn = null;

        // Find the nodes between which the Edge should be created.
        final Node fromNode = findNode(fromId, true);
        final Subgraph fromSubgraph = findSubgraph(fromId, true);

        if (fromNode != null || fromSubgraph != null) {

            // Find the RHS edge data.
            final Node toNode = findNode(toId, true);
            final Subgraph toSubgraph = findSubgraph(toId, true);
            final NodeID toNodeId = toNode == null ? null : toNode.getNodeID();

            // Can we actually create an Edge?
            if (toNodeId != null || toSubgraph != null) {

                // Start by creating the RightSideEdge.
                final RightSideEdge rightSideEdge = toNodeId == null
                        ? new RightSideEdge(toSubgraph)
                        : new RightSideEdge(toNodeId);

                // Finally, create the Edge to return.
                toReturn = fromNode == null
                        ? new Edge(fromSubgraph, rightSideEdge)
                        : new Edge(fromNode.getNodeID(), rightSideEdge);
            }
        }

        // Add the newly created Edge, if applicable.
        if (toReturn != null) {
            add(toReturn);
        }

        // All Done.
        return toReturn;
    }
}
