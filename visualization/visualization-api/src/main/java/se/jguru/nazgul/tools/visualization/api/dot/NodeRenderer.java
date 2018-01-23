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
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.NodeAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Node;

/**
 * Node statement Renderer, complying to the specification in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeRenderer extends AbstractStringRenderer<Node> {

    // Internal state
    private AttributeRenderer attributeRenderer;
    private NodeIdRenderer nodeIdRenderer;

    /**
     * Default constructor.
     */
    public NodeRenderer() {

        // Delegate
        super(Node.class);

        // Assign internal state
        attributeRenderer = new AttributeRenderer();
        nodeIdRenderer = new NodeIdRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final RenderConfiguration config, final Node node) {

        // Do we have a non-empty NodeAttributes within the supplied Node?
        // Don't add the extra newline after the attributes - so call doRender directly.
        final NodeAttributes nodeAttributes = node.getAttributes();
        final String renderedNodeAttributes = attributeRenderer.doRender(config, nodeAttributes);

        // All Done.
        return config.getIndent() + nodeIdRenderer.doRender(config, node.getNodeID())
                + (renderedNodeAttributes.isEmpty() ? "" : " " + renderedNodeAttributes);
    }
}
