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
package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;

/**
 * AbstractStringRenderer implementation which renders a full Graph.
 * Delegates most rendering to an internal StatementsRenderer instance.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 * @see StatementsRenderer
 */
public class GraphRenderer extends AbstractStringRenderer<Graph> {

    // Internal state
    private StatementsRenderer statementsRenderer;

    /**
     * Default constructor.
     */
    public GraphRenderer() {

        // Delegate
        super(Graph.class);

        // Assign internal state
        this.statementsRenderer = new StatementsRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final RenderConfiguration config, final Graph entity) {

        // First, configure the RenderConfiguration for directed-ness.
        config.setDirectedGraph(entity.isDigraph());

        // We should use a 3-step rendering strategy.
        final String prologue =
                config.getIndent() + (entity.isStrict() ? "strict " : "")
                        + (entity.isDigraph() ? "digraph" : "graph")
                        + " " + quote(entity.getId())
                        + " {";

        // Render all the statements within the supplied Graph.
        // Increase the indentation level to achieve pretty printing.
        //
        // Also, note that the render() method appends a NEWLINE to the result.
        final String renderedStatements = statementsRenderer.render(
                config.cloneAndChangeIndentation(1),
                entity.getStatements());

        // Render the epilogue.
        final String epilogue = config.getIndent() + "}";

        // All Done.
        return prologue + renderedStatements + epilogue;
    }
}
