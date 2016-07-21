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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.jguru.nazgul.tools.visualization.api.StringRenderable;
import se.jguru.nazgul.tools.visualization.api.diagram.AbstractStringIdentifiable;
import se.jguru.nazgul.tools.visualization.api.diagram.Comment;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.NodeID;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute.AttributeStatement;
import se.jguru.nazgul.tools.visualization.api.jaxb.ClassToCommentListAdapter;
import se.jguru.nazgul.tools.visualization.api.jaxb.ClassToStatementListAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Utility class which orders statements and comments in a particular order. The default order is given by the
 * {@link #DEFAULT_STATEMENT_ORDER} type Listing.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"type2CommentMap", "type2StatementsMap"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Statements implements StringRenderable {

    // Our Log
    public static final Logger log = LoggerFactory.getLogger(Statement.class);

    /**
     * <p>The order in which statements should normally be rendered, i.e:</p>
     * <p>
     * <ol>
     * <li>{@link AttributeStatement} statements</li>
     * <li>{@link Node} statements</li>
     * <li>{@link Identifier} statements</li>
     * <li>{@link Edge} statements</li>
     * <li>{@link Subgraph} statements</li>
     * </ol>
     */
    public static final List<Class<? extends Statement>> DEFAULT_STATEMENT_ORDER = Collections.unmodifiableList(
            Arrays.asList(AttributeStatement.class, Node.class, Identifier.class, Edge.class, Subgraph.class));

    /**
     * A Comparator sorting as per the order defined within the {@link #DEFAULT_STATEMENT_ORDER} List.
     */
    public static Comparator<Class<? extends Statement>> DEFAULT_STATEMENT_COMPARATOR
            = new Comparator<Class<? extends Statement>>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final Class<? extends Statement> left, final Class<? extends Statement> right) {

            // Handle nulls
            final int leftIndex = left == null ? -1 : DEFAULT_STATEMENT_ORDER.indexOf(left);
            final int rightIndex = right == null ? -1 : DEFAULT_STATEMENT_ORDER.indexOf(right);

            // All Done.
            return leftIndex - rightIndex;
        }
    };

    // Internal state
    @XmlJavaTypeAdapter(ClassToStatementListAdapter.class)
    @XmlElement
    private Map<Class<? extends Statement>, List<Statement>> type2StatementsMap;

    @XmlJavaTypeAdapter(ClassToCommentListAdapter.class)
    @XmlElement
    private Map<Class<? extends Statement>, Comment> type2CommentMap;

    /**
     * Default constructor uses {@link #DEFAULT_STATEMENT_COMPARATOR} for comparator between {@link Statement} types.
     */
    public Statements() {
        this(null);
    }

    /**
     * Compound constructor creating a Statements instance using the supplied {@link Comparator} to order statements.
     * If a null Comparator is supplied, {@link #DEFAULT_STATEMENT_COMPARATOR} is used.
     *
     * @param orderComparator An optional (i.e. nullable) Comparator used to order all known {@link Statement}
     *                        instance types within this {@link Statements} holder.
     */
    public Statements(final Comparator<Class<? extends Statement>> orderComparator) {

        // Check sanity
        final Comparator<Class<? extends Statement>> comparator = orderComparator == null
                ? DEFAULT_STATEMENT_COMPARATOR
                : orderComparator;

        // Assign internal state
        type2StatementsMap = new TreeMap<>(comparator);
        type2CommentMap = new TreeMap<>(comparator);

        // Assign default Comments
        for (Class<? extends Statement> current : DEFAULT_STATEMENT_ORDER) {
            setCommentFor(current, new Comment(current.getSimpleName() + " statements."));
        }
    }

    /**
     * Assigns the Comment to be emitted before all Statements of the supplied type are rendered.
     *
     * @param statementType The non-null type of {@link Statement} for which a {@link Comment} should be set.
     * @param comment       The non-null {@link Comment} to set.
     */
    public void setCommentFor(final Class<? extends Statement> statementType, final Comment comment) {

        // Check sanity
        if (statementType == null) {
            throw new IllegalArgumentException("Cannot handle null 'statementType' argument.");
        }
        if (comment == null) {
            throw new IllegalArgumentException("Cannot handle null 'comment' argument.");
        }

        // Add the comment.
        type2CommentMap.put(statementType, comment);
    }

    /**
     * Adds one or more {@link Statement} objects to this {@link Statements} holder.
     *
     * @param toAdd One or more {@link Statement} instances.
     */
    @SuppressWarnings("PMD")
    public void add(final Statement... toAdd) {

        if (toAdd != null) {
            for (Statement current : toAdd) {
                if (current != null) {

                    // Get the actual implementation of the current Statement
                    final Class<? extends Statement> currentType = current.getClass();
                    final Class<? extends Statement> effType = AttributeStatement.class.isAssignableFrom(currentType)
                            ? AttributeStatement.class
                            : currentType;

                    // Get or create the List of Statements for the supplied type.
                    List<Statement> statementList = type2StatementsMap.get(effType);
                    if (statementList == null) {
                        statementList = new ArrayList<>();
                        type2StatementsMap.put(effType, statementList);
                    }

                    // Add the current Statement to the List.
                    statementList.add(current);
                }
            }
        }
    }

    /**
     * Finds known {@link Statements} which are also {@link AbstractStringIdentifiable} subclasses and have the
     * supplied identifier. The search does not descend into contained {@link Subgraph}s.
     *
     * @param type       The type of Statement to find.
     * @param identifier The identifier of the Statement to find.
     * @param <T>        The type of Statement to find.
     * @return The {@link Statement} having its identifier (i.e. {@link AbstractStringIdentifiable#getId()}) equal to
     * the supplied identifier, or null if none was found.
     */
    public <T extends AbstractStringIdentifiable & Statement> T find(
            final Class<T> type,
            final String identifier) {
        return find(type, identifier, false);
    }

    /**
     * Finds known {@link Statements} which are also {@link AbstractStringIdentifiable} subclasses and have the
     * supplied identifier.
     *
     * @param type              The type of Statement to find.
     * @param identifier        The identifier of the Statement to find.
     * @param searchRecursively If {@code true}, the search will descend into {@link Subgraph}s, and otherwise only
     *                          this {@link Statements}' content are searched.
     * @param <T>               The type of Statement to find.
     * @return The {@link Statement} having its identifier (i.e. {@link AbstractStringIdentifiable#getId()}) equal to
     * the supplied identifier, or null if none was found.
     */
    @SuppressWarnings("PMD")
    public <T extends AbstractStringIdentifiable & Statement> T find(
            final Class<T> type,
            final String identifier,
            final boolean searchRecursively) {

        // Check sanity
        if (type == null) {
            throw new IllegalArgumentException("Cannot handle null 'type' argument.");
        }
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'identifier' argument.");
        }

        if (log.isDebugEnabled()) {
            log.debug("Searching for [" + identifier + "] of type [" + type.getSimpleName()
                    + "] within Statements [" + hashCode() + "]");
        }

        // Find the Statement with the supplied identifier.
        final Class<? extends Statement> effType = AttributeStatement.class.isAssignableFrom(type)
                ? AttributeStatement.class
                : type;
        final List<Statement> existingStatements = type2StatementsMap.get(effType);

        if (existingStatements != null && !existingStatements.isEmpty()) {

            for (Statement current : existingStatements) {

                final String currentId = ((AbstractStringIdentifiable) current).getId();
                if (identifier.equals(currentId)) {
                    return (T) current;
                }
            }
        }

        // Recursive search?
        if (searchRecursively) {

            // Find all our Subgraphs, and extend the search into them.
            final List<Statement> subgraphs = type2StatementsMap.get(Subgraph.class);

            if (subgraphs != null) {

                for (Statement stmnt : subgraphs) {

                    final Subgraph current = (Subgraph) stmnt;
                    final T result = current.getStatements().find(type, identifier, true);

                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        // None found.
        return null;
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
        final Node fromNode = find(Node.class, fromId);
        final Subgraph fromSubgraph = find(Subgraph.class, fromId);
        if (fromNode != null || fromSubgraph != null) {

            // Find the RHS edge data.
            final Node toNode = find(Node.class, toId);
            final Subgraph toSubgraph = find(Subgraph.class, toId);
            final NodeID toNodeId = toNode == null ? null : toNode.getNodeID();

            // Can we actually create an Edge?
            if (toNodeId != null || toSubgraph != null) {

                // Start by creating the RightSideEdge.
                final RightSideEdge rightSideEdge = toNodeId == null
                        ? new RightSideEdge(toSubgraph, parentGraph)
                        : new RightSideEdge(toNodeId, parentGraph);

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

    /**
     * Finds the Edge with the given from ID, and whose {@link RightSideEdge}'s ID matches the to ID.
     *
     * @param fromId The non-null ID of the Edge to match.
     * @param toId   The non-null ID of the {@link RightSideEdge} of the Edge to match.
     * @return An Edge whose ID matches the fromId argument and whose immediate {@link RightSideEdge}'s ID matches
     * the toId argument.
     */
    @SuppressWarnings("PMD")
    public Edge findEdge(final String fromId, final String toId) {

        // Check sanity
        if (fromId == null) {
            throw new IllegalArgumentException("Cannot handle null 'fromId' argument.");
        }
        if (toId == null) {
            throw new IllegalArgumentException("Cannot handle null 'toId' argument.");
        }

        for (Statement current : type2StatementsMap.get(Edge.class)) {

            // Find the current Edge's from and to IDs.
            final Edge currentEdge = (Edge) current;
            final String currentFromID = currentEdge.getId();
            final String currentToID = currentEdge.getRightSideEdge().getId();

            // Match?
            if (fromId.equals(currentFromID) && toId.equals(currentToID)) {
                return currentEdge;
            }
        }

        // All Done.
        return null;
    }

    /**
     * <p>Returns a commented, suite of all provided Statements, in the following order:</p>
     * <ol>
     * <li>{@link AttributeStatement} statements</li>
     * <li>{@link Node} statements</li>
     * <li>{@link Identifier} statements</li>
     * <li>{@link Edge} statements</li>
     * <li>{@link Subgraph} statements</li>
     * </ol>
     *
     * @return
     */
    @Override
    @SuppressWarnings("PMD")
    public String render() {

        final StringBuilder builder = new StringBuilder("");

        // Render the statements in order.
        for (Map.Entry<Class<? extends Statement>, List<Statement>> current : type2StatementsMap.entrySet()) {

            // Render the comment, if we have one.
            final Comment comment = type2CommentMap.get(current.getKey());
            if (comment != null) {
                builder.append(NEWLINE).append(comment.render());
            }

            // Render all statements of the same type, each on a line of its own.
            for (Statement currentStatement : current.getValue()) {
                builder.append(currentStatement.render()).append(Statement.NEWLINE_SEPARATOR);
            }
        }

        // All Done.
        return builder.toString();
    }
}
