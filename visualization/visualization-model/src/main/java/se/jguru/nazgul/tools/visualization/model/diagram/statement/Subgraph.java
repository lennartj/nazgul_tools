/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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

package se.jguru.nazgul.tools.visualization.model.diagram.statement;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractGraph;
import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

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
@XmlType(namespace = AbstractIdentifiable.NAMESPACE)
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
        super(id);
    }
}
