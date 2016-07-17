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
import se.jguru.nazgul.tools.visualization.api.diagram.Comment;

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
public class Statements implements StringRenderable {

    /**
     * <p>The order in which statements should normally be rendered, i.e:</p>
     *
     * <ol>
     * <li>{@link Attribute} statements</li>
     * <li>{@link Node} statements</li>
     * <li>{@link Identifier} statements</li>
     * <li>{@link Edge} statements</li>
     * <li>{@link Subgraph} statements</li>
     * </ol>
     */
    public static final List<Class<? extends Statement>> DEFAULT_STATEMENT_ORDER = Collections.unmodifiableList(
            Arrays.asList(Attribute.class, Node.class, Identifier.class, Edge.class, Subgraph.class));

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
    private Map<Class<? extends Statement>, List<Statement>> type2StatementsMap;
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
            setCommentFor(current, Comment.createSingleLineComment(current.getSimpleName() + " statements."));
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
    public void add(final Statement... toAdd) {

        if (toAdd != null) {
            for (Statement current : toAdd) {
                if (current != null) {

                    // Get the actual implementation of the current Statement
                    final Class<? extends Statement> currentType = current.getClass();

                    // Get or create the List of Statements for the supplied type.
                    List<Statement> statementList = type2StatementsMap.get(currentType);
                    if (statementList == null) {
                        statementList = new ArrayList<>();
                        type2StatementsMap.put(currentType, statementList);
                    }

                    // Add the current Statement to the List.
                    statementList.add(current);
                }
            }
        }
    }

    /**
     * <p>Returns a commented, suite of all provided Statements, in the following order:</p>
     * <ol>
     * <li>{@link Attribute} statements</li>
     * <li>{@link Node} statements</li>
     * <li>{@link Identifier} statements</li>
     * <li>{@link Edge} statements</li>
     * <li>{@link Subgraph} statements</li>
     * </ol>
     *
     * @return
     */
    @Override
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
