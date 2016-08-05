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

import se.jguru.nazgul.tools.visualization.api.Renderer;
import se.jguru.nazgul.tools.visualization.model.diagram.AbstractGraph;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statement;

import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DotRenderingEngine {

    // Internal state
    private List<Renderer<String>> renderers;

    public String render(final Graph aGraph) {

        // Create the StringBuffer to hold the result.
        final StringBuffer buffer = new StringBuffer();

        append(buffer, 0, );
    }

    private void append(final StringBuffer buffer, final int indentationLevel, final Statement statement) {

        // Render the supplied statement
        renderers.stream()
                .filter(current -> current.accept(statement))
                .filter(current -> current != null)
                .forEach(current -> {

                    // First, set the indentation level for this Renderer
                    current.setIndentationLevel(indentationLevel);

                    // Then, render the statement
                    buffer.append(current.render(statement));
                });
    }

    private StringRenderer findRendererFor(final Statement statement) {

    }

    private StringRenderer findRendererFor(final AbstractGraph graph) {

    }
}
