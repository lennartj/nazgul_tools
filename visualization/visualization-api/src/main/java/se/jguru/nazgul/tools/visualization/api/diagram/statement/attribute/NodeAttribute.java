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
package se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute;

import se.jguru.nazgul.tools.visualization.api.diagram.AbstractStringIdentifiable;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.NodeAttributeList;

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
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"attributes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeAttribute extends AbstractStringIdentifiable implements AttributeStatement {

    // Internal state
    @XmlElement
    private NodeAttributeList attributes;

    /**
     * Default constructor
     */
    public NodeAttribute() {

        // Delegate
        super("node");

        // Assign internal state
        this.attributes = new NodeAttributeList();
    }

    /**
     * Retrieves the {@link NodeAttributeList} which can be used to add attributes to this {@link NodeAttribute}
     * Statement.
     *
     * @return the {@link NodeAttributeList} which can be used to add attributes to this {@link NodeAttribute}
     * Statement.
     */
    public NodeAttributeList getAttributes() {
        return attributes;
    }

    /**
     * <p>Renders this AttributeStatement on the following form:</p>
     * <pre>node attr_list</pre>
     */
    @Override
    public String render() {
        return getId() + " " + attributes.render();
    }
}
