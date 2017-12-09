/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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

package se.jguru.nazgul.tools.visualization.model.diagram.statement;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.NodeAttributes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Attribute statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     attr_stmt : node attr_list
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     node [	fontname = "Helvetica-Oblique",
 *              fontsize = "36",
 *              label = "\n\n\n\nObject Oriented Graphs\nStephen North, 3/19/93",
 *              size = "6,6" ];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 * @see NodeAttributes
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"attributes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonNodeAttributes extends AbstractIdentifiable implements Statement {

    /**
     * The configured attributes for this CommonNodeAttributes statement.
     */
    @XmlElement
    private NodeAttributes attributes;

    /**
     * Default constructor
     */
    public CommonNodeAttributes() {

        // Delegate
        super("node");

        // Assign internal state
        this.attributes = new NodeAttributes();
    }

    /**
     * Retrieves the {@link NodeAttributes} wrapped within this {@link CommonNodeAttributes} statement.
     *
     * @return the non-null {@link NodeAttributes} wrapped within this {@link CommonNodeAttributes} statement.
     */
    public NodeAttributes getAttributes() {
        return attributes;
    }
}
