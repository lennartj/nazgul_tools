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
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphRenderer extends AbstractStringRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final Object entity) {
        return entity != null && entity instanceof Graph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String renderPrologue(final RenderConfiguration configuration, final Object entity) {

        final Graph graph = (Graph) entity;

        return (graph.isStrict() ? "strict " : "")
                + (graph.isDigraph() ? "digraph" : "graph")
                + " " + quote(graph.getId())
                + " {";
    }

    @Override
    public String render(final RenderConfiguration configuration, final Object entity) {
        return null;
    }

    @Override
    public String renderEpilogue(final RenderConfiguration configuration, final Object entity) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final Object acceptedEntity) {

        final Graph graph = (Graph) acceptedEntity;

        return (graph.isStrict() ? "strict " : "")
                + (graph.isDigraph() ? "digraph" : "graph")
                + " " + quote(graph.getId())
                + " {"
                + NEWLINE
                + graph.getStatements().
                + " } "
                + NEWLINE;
    }
}