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

import se.jguru.nazgul.tools.visualization.api.Renderable;
import se.jguru.nazgul.tools.visualization.api.diagram.AbstractGraph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

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
@XmlType(namespace = Renderable.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Subgraph extends AbstractGraph implements Statement {

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Subgraph() {
        // Do nothing
    }

    /**
     * Compound constructor creating a sub-graph statement wrapping the supplied data.
     *
     * @param id a non-null and non-empty identifier, assumed to be unique within a Graph.
     */
    public Subgraph(final String id) {
        super(id, 0);
    }

    /**
     * <p>Renders this SubgraphStatement on the following form:</p>
     * <pre>subgraph ID '{' stmt_list '}'</pre>
     *
     * @return the output of this {@link Subgraph} in its current state.
     */
    @Override
    public String doRender() {

        // First, update the indentation level of the statements within this Subgraph
        final int childIndentationLevel = getIndentationLevel() + 1;
        getStatements().setIndentationLevel(childIndentationLevel);

        // Now render this Subgraph fully
        return "subgraph " + getQuotedId() + " { "
                + NEWLINE
                + getStatements().render()
                + getIndentation() + "}";
    }
}
