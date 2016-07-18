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

import se.jguru.nazgul.tools.visualization.api.diagram.AbstractGraph;

/**
 * <p>Subgraph statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     subgraph	: [ subgraph [ ID ] ] '{' stmt_list '}'
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *  subgraph cluster_1 {
 *      label="Subgraph B";
 *      a -> f;
 *      f -> c;
 *  }
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class Subgraph extends AbstractGraph implements Statement {

    /**
     * Compound constructor creating a sub-graph statement wrapping the supplied data.
     *
     * @param id a non-null and non-empty identifier, assumed to be unique within a Graph.
     */
    public Subgraph(final String id) {

        // Delegate
        super(id);
    }

    /**
     * <p>Renders this SubgraphStatement on the following form:</p>
     * <pre>subgraph ID '{' stmt_list '}'</pre>
     *
     * @return the output of this {@link Subgraph} in its current state.
     */
    @Override
    public String render() {

        return "subgraph " + getQuotedId()
                + "{ "
                + NEWLINE
                + getStatements().render()
                + NEWLINE
                + "} ";
    }
}
