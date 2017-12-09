/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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

package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Edge;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.RightSideEdge;

/**
 * Edge statement Renderer, complying to the specification in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class EdgeRenderer extends AbstractStringRenderer<Edge> {

    // Internal state
    private AttributeRenderer attributeRenderer;
    private NodeIdRenderer nodeIdRenderer;
    private RightSideEdgeRenderer rightSideEdgeRenderer;

    /**
     * Default constructor.
     */
    public EdgeRenderer() {

        // Delegate
        super(Edge.class);

        // Assign internal state
        attributeRenderer = new AttributeRenderer();
        nodeIdRenderer = new NodeIdRenderer();
        rightSideEdgeRenderer = new RightSideEdgeRenderer();
    }

    /**
     * <p>Renders the supplied Edge according to the following structure:</p>
     * <pre>(node_id | subgraph) edgeRHS [ attr_list ]</pre>
     * <p>... where ... </p>
     * <pre>edgeRHS   : edgeop (node_id | subgraph) [ edgeRHS ]</pre>
     *
     * @param config The non-null RenderConfiguration.
     * @param edge   The non-null Edge to be rendered.
     */
    @Override
    protected String doRender(final RenderConfiguration config, final Edge edge) {

        // Edges can be from/to Nodes and Subgraphs. Pick the correct ID source.
        final String edgeID = edge.getNodeID() != null
                ? nodeIdRenderer.doRender(config, edge.getNodeID())
                : quote(edge.getSubgraph().getId());

        // Render the attributes after the RightSideEdge.
        final RightSideEdge rightSideEdge = edge.getRightSideEdge();
        return config.getIndent() + edgeID + " "
                + rightSideEdgeRenderer.doRender(config, rightSideEdge) + " "
                + attributeRenderer.doRender(config, edge.getAttributes());
    }
}
