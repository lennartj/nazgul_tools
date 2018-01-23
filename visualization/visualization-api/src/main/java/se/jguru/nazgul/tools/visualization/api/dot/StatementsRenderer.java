/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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

package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonEdgeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonGraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonNodeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Edge;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Identifier;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statements;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Subgraph;

import java.util.List;

/**
 * <p>Utility class which renders statements and comments in a particular order.
 * The default order (which should yield no problematic/circular references) is:</p>
 * <ol>
 * <li>AttributeStatements, which implies zero or all of the types {@link CommonGraphAttributes},
 * {@link CommonEdgeAttributes} and {@link CommonNodeAttributes}</li>
 * <li>{@link Node} statements</li>
 * <li>{@link Identifier} statements</li>
 * <li>{@link Edge} statements</li>
 * <li>{@link Subgraph} statements</li>
 * </ol>
 * <p>Each section is prefaced by a comment.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class StatementsRenderer extends AbstractStringRenderer<Statements> {

    // Internal state
    private CommentRenderer commentRenderer;
    private NodeRenderer nodeRenderer;
    private IdentifierRenderer identifierRenderer;
    private EdgeRenderer edgeRenderer;
    private SubgraphRenderer subgraphRenderer;
    private CommonGraphAttributesRenderer commonGraphAttributesRenderer;
    private CommonNodeAttributesRenderer commonNodeAttributesRenderer;
    private CommonEdgeAttributesRenderer commonEdgeAttributesRenderer;

    /**
     * Default constructor.
     */
    public StatementsRenderer() {

        // Delegate
        super(Statements.class);

        // Assign internal state
        this.commentRenderer = new CommentRenderer();
        this.nodeRenderer = new NodeRenderer();
        this.identifierRenderer = new IdentifierRenderer();
        this.edgeRenderer = new EdgeRenderer();
        this.subgraphRenderer = new SubgraphRenderer();
        this.commonEdgeAttributesRenderer = new CommonEdgeAttributesRenderer();
        this.commonGraphAttributesRenderer = new CommonGraphAttributesRenderer();
        this.commonNodeAttributesRenderer = new CommonNodeAttributesRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final RenderConfiguration config, final Statements statements) {

        final StringBuilder builder = new StringBuilder(config.getIndent());

        // #1) Render the AttributeStatements plus comment, if they exist.
        final boolean hasAttributeStatements = statements.getCommonGraphAttributes() != null
                || statements.getCommonNodeAttributes() != null
                || statements.getCommonEdgeAttributes() != null;

        if (hasAttributeStatements) {

            // Add the comment first.
            builder.append(commentRenderer.render(config, statements.getCommonAttributesComment()));

            // Add the attributes in order
            if (statements.getCommonGraphAttributes() != null) {
                builder.append(commonGraphAttributesRenderer.render(config, statements.getCommonGraphAttributes()));
            }

            if (statements.getCommonNodeAttributes() != null) {
                builder.append(commonNodeAttributesRenderer.render(config, statements.getCommonNodeAttributes()));
            }

            if (statements.getCommonEdgeAttributes() != null) {
                builder.append(commonEdgeAttributesRenderer.render(config, statements.getCommonEdgeAttributes()));
            }
        }

        // #2) Render the other Statements in order, namely
        //
        //     Node statements
        //     Identifier statements
        //     Edge statements
        //     Subgraph statements
        final List<Node> nodes = statements.getNodes();
        if (!nodes.isEmpty()) {

            builder.append(commentRenderer.render(config, statements.getNodesComment()));

            // Render all Nodes in order.
            nodes.forEach(n -> builder.append(nodeRenderer.render(config, n)));
        }

        final List<Identifier> identifiers = statements.getIdentifiers();
        if (!identifiers.isEmpty()) {

            builder.append(commentRenderer.render(config, statements.getIdentifiersComment()));

            // Render all Identifiers in order.
            identifiers.forEach(i -> builder.append(identifierRenderer.render(config, i)));
        }

        final List<Edge> edges = statements.getEdges();
        if (edges != null && !edges.isEmpty()) {

            builder.append(commentRenderer.render(config, statements.getEdgesComment()));

            // Render all Edges in order.
            edges.forEach(i -> builder.append(edgeRenderer.render(config, i)));
        }

        final List<Subgraph> subgraphs = statements.getSubgraphs();
        if (!subgraphs.isEmpty()) {

            builder.append(commentRenderer.render(config, statements.getSubgraphComment()));

            // Render all Subgraphs in order.
            subgraphs.forEach(s -> builder.append(subgraphRenderer.render(config, s)));
        }

        // All Done.
        return builder.toString();
    }
}
