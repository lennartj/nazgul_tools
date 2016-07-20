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
package se.jguru.nazgul.tools.visualization.api.diagram.attribute;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.model.ArrowType;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.model.StandardCssColor;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute.EdgeAttribute;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute.GraphAttribute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Attributes relevant for {@link se.jguru.nazgul.tools.visualization.api.diagram.statement.Edge} objects.
 * Property descriptions copied from <a href="http://www.graphviz.org/content/attrs">The Graphviz Website's
 * description of attributes.</a>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class EdgeAttributeList extends AbstractDelegatingAttributeList {

    /**
     * Creates an EdgeAttribute statement from this {@link EdgeAttributeList}.
     *
     * @return An {@link EdgeAttribute} populated with the state within this {@link EdgeAttributeList}.
     */
    public EdgeAttribute toEdgeAttributeStatement() {

        final EdgeAttribute toReturn = new EdgeAttribute();

        // Copy all properties from this GraphAttributeList.
        delegate.toMap().entrySet().forEach(c -> toReturn.getAttributes().addAttribute(c.getKey(), c.getValue()));

        // All Done.
        return toReturn;
    }

    /**
     * <p>Style of arrowhead on the head node of an edge. This will only appear if the dir attribute is "forward" or
     * "both".</p>
     * <p><strong>Note:</strong> Some attributes, such as dir or arrowtail, are ambiguous when used in DOT with an undirected
     * graph since the head and tail of an edge are meaningless. As a convention, the first time an undirected edge
     * appears, the DOT parser will assign the left node as the tail node and the right node as the head. For
     * example, the edge A -- B will have tail A and head B. It is the user's responsibility to handle such edges
     * consistently. If the edge appears later, in the format
     * <code>B -- A [taillabel = "tail"]</code>
     * the drawing will attach the tail label to node A. To avoid possible confusion when such attributes are
     * required, the user is encouraged to use a directed graph. If it is important to make the graph appear
     * undirected, this can be done using the dir, arrowtail or arrowhead attributes.</p>
     * <p>The tools accept standard C representations for int and double types. For the bool type, TRUE values are
     * represented by "true" or "yes" (case-insensitive) and any non-zero integer, and FALSE values by "false" or
     * "no" (case-insensitive) and zero. In addition, there are a variety of specialized types such as arrowType,
     * color, point and rankdir.</p>
     *
     * @param arrowHead The non-null {@link ArrowType} value defining the arrowHead.
     * @return This {@link EdgeAttributeList}.
     */
    public EdgeAttributeList withArrowHead(final ArrowType arrowHead) {

        if (arrowHead != null) {
            addAttribute("arrowhead", arrowHead.toString());
        }

        // All Done.
        return this;
    }

    /**
     * <p>Style of arrowhead on the tail node of an edge. This will only appear if the dir attribute is "back" or
     * "both"</p>
     * <p><strong>Note:</strong> Some attributes, such as dir or arrowtail, are ambiguous when used in DOT with an undirected
     * graph since the head and tail of an edge are meaningless. As a convention, the first time an undirected edge
     * appears, the DOT parser will assign the left node as the tail node and the right node as the head. For
     * example, the edge A -- B will have tail A and head B. It is the user's responsibility to handle such edges
     * consistently. If the edge appears later, in the format
     * <code>B -- A [taillabel = "tail"]</code>
     * the drawing will attach the tail label to node A. To avoid possible confusion when such attributes are
     * required, the user is encouraged to use a directed graph. If it is important to make the graph appear
     * undirected, this can be done using the dir, arrowtail or arrowhead attributes.</p>
     * <p>The tools accept standard C representations for int and double types. For the bool type, TRUE values are
     * represented by "true" or "yes" (case-insensitive) and any non-zero integer, and FALSE values by "false" or
     * "no" (case-insensitive) and zero. In addition, there are a variety of specialized types such as arrowType,
     * color, point and rankdir.</p>
     *
     * @param arrowTail The non-null {@link ArrowType} value defining the arrowHead.
     * @return This {@link EdgeAttributeList}.
     */
    public EdgeAttributeList withArrowTail(final ArrowType arrowTail) {

        if (arrowTail != null) {
            addAttribute("arrowtail", arrowTail.toString());
        }

        // All Done.
        return this;
    }

    /**
     * Multiplicative scale factor for arrowheads. Defaults to 1.0.
     *
     * @param arrowSize The non-negative arrow size scale factor.
     * @return This {@link EdgeAttributeList}.
     */
    public EdgeAttributeList withArrowSize(final double arrowSize) {
        return (EdgeAttributeList) addAttribute("arrowsize", arrowSize);
    }

    /**
     * Basic drawing color for graphics, not text. For the latter, use the fontcolor attribute.
     * For edges, the value can either be a single color or a colorList.
     * In the latter case, if colorList has no fractions, the edge is drawn using parallel splines or lines, one for
     * each color in the list, in the order given.
     * The head arrow, if any, is drawn using the first color in the list, and the tail arrow, if any, the second
     * color. This supports the common case of drawing opposing edges, but using parallel splines instead of
     * separately routed multiedges. If any fraction is used, the colors are drawn in series, with each color being
     * given roughly its specified fraction of the edge.
     *
     * @param color A non-null {@link StandardCssColor}.
     * @return This {@link GraphAttributeList}.
     */
    public EdgeAttributeList withColor(final StandardCssColor color) {

        // Add the attribute
        if (color != null) {
            addAttribute("color", color.getRgbValue());
        }

        // All Done.
        return this;
    }

    /**
     * This attribute specifies a color scheme namespace. If defined, it specifies the context for interpreting
     * color names. In particular, if a color value has form "xxx" or "//xxx", then the color xxx will be evaluated
     * according to the current color scheme. If no color scheme is set, the standard X11 naming is used. For
     * example, if colorscheme=bugn9, then color=7 is interpreted as "/bugn9/7".
     *
     * @param colorScheme A non-empty colorScheme specification.
     * @return This {@link EdgeAttributeList}.
     */
    public EdgeAttributeList withColorScheme(final String colorScheme) {
        return (EdgeAttributeList) addAttribute("colorscheme", colorScheme);
    }


}
