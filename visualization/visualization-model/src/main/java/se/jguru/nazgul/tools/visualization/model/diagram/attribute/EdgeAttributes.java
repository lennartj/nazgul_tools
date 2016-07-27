/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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
package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowDirection;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * POJO implementation of an entity containing attributes relevant for
 * {@link se.jguru.nazgul.tools.visualization.model.diagram.statement.Edge} objects.
 * Property descriptions copied from <a href="http://www.graphviz.org/content/attrs">The Graphviz Website's
 * description of attributes.</a>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"distortion"})
@XmlAccessorType(XmlAccessType.FIELD)
public class EdgeAttributes extends AbstractAttributes {

    /**
     * <p>Style of arrowhead on the head node of an edge. This will only appear if the dir attribute is "forward" or
     * "both". Note: Some attributes, such as dir or arrowtail, are ambiguous when used in DOT with an undirected
     * graph since the head and tail of an edge are meaningless.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#darrowsize">arrowsize</i></a>.</p>
     */
    @XmlAttribute
    public double arrowSize;

    /**
     * <p>Style of arrowhead on the head node of an edge. This will only appear if the dir attribute is "forward" or
     * "both".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#darrowhead">arrowhead</i></a>.</p>
     */
    @XmlElement
    public ArrowType arrowHead;

    /**
     * <p>Style of arrowhead on the tail node of an edge. This will only appear if the dir attribute is "back" or
     * "both".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#darrowtail">arrowtail</i></a>.</p>
     */
    @XmlElement
    public ArrowType arrowTail;

    /**
     * <p>If false, the edge is not used in ranking the nodes.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dconstraint">constraint</i></a>.</p>
     */
    @XmlAttribute
    public Boolean constraint;

    /**
     * <p>If true, attach edge label to edge by a 2-segment polyline, underlining the label, then going to the
     * closest point of spline.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#ddecorate">decorate</i></a>.</p>
     */
    @XmlAttribute
    public Boolean decorate;

    /**
     * <p>Set edge type for drawing arrowheads. This indicates which ends of the edge should be decorated with an
     * arrowhead. The actual style of the arrowhead can be specified using the {@link #arrowHead} and {@link #arrowTail}
     * attributes.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#ddecorate">decorate</i></a>.</p>
     */
    @XmlAttribute
    public ArrowDirection direction;
}
