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

package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;
import se.jguru.nazgul.tools.visualization.model.diagram.Port;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowDirection;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ArrowType;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.EdgeStyle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

/**
 * POJO implementation of an entity containing attributes relevant for
 * {@link se.jguru.nazgul.tools.visualization.model.diagram.statement.Edge} objects.
 * Property descriptions copied from <a href="http://www.graphviz.org/content/attrs">The Graphviz Website's
 * description of attributes.</a>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"arrowHead", "arrowSize", "arrowTail",
        "centerOfHeadLabel", "constraint", "decorate", "direction", "edgeTarget", "edgeTooltip", "edgeURL",
        "headClip", "headLabel", "headPort", "headTarget", "headTooltip", "headURL", "labelAngle", "labelDistance",
        "labelFloat", "labelFontColor", "labelFontName", "labelFontSizeInPoints", "labelPosition", "labelTarget",
        "labelTooltip", "layer", "logicalHead", "logicalTail", "minimumLength", "penWidthInPoints", "position",
        "samehead", "sametail", "style", "tooltip", "tailClip", "tailLabel", "tailPort", "tailTarget", "tailTooltip",
        "tailURL", "weight", "centerOfTailLabel"})
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
    @DotProperty(name = "arrowsize")
    public Double arrowSize;

    /**
     * <p>Style of arrowhead on the head node of an edge. This will only appear if the dir attribute is "forward" or
     * "both".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#darrowhead">arrowhead</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "arrowhead")
    public ArrowType arrowHead;

    /**
     * <p>Style of arrowhead on the tail node of an edge. This will only appear if the dir attribute is "back" or
     * "both".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#darrowtail">arrowtail</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "arrowtail")
    public ArrowType arrowTail;

    /**
     * <p>If false, the edge is not used in ranking the nodes.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dconstraint">constraint</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "constraint")
    public Boolean constraint;

    /**
     * <p>If true, attach edge label to edge by a 2-segment polyline, underlining the label, then going to the
     * closest point of spline.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#ddecorate">decorate</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "decorate")
    public Boolean decorate;

    /**
     * <p>Set edge type for drawing arrowheads. This indicates which ends of the edge should be decorated with an
     * arrowhead. The actual style of the arrowhead can be specified using the {@link #arrowHead} and {@link #arrowTail}
     * attributes.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#ddir">dir</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "dir")
    public ArrowDirection direction;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>If edgeURL is defined, this is the link used for the non-label parts of an edge. This value overrides any
     * URL defined for the edge. Also, this value is used near the head or tail node unless overridden by a headURL
     * or tailURL value, respectively.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dedgeURL">edgeURL</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "edgeURL")
    public String edgeURL;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>If the edge has a URL or edgeURL attribute, this attribute determines which window of the browser is used
     * for the URL attached to the non-label part of the edge. Setting it to "_graphviz" will open a new window if
     * it doesn't already exist, or reuse it if it does. If undefined, the value of the target is used.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dedgetarget">edgetarget</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "edgetarget")
    public String edgeTarget;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>Tooltip annotation attached to the non-label part of an edge. This is used only if the edge has a URL or
     * edgeURL attribute.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dedgetooltip">edgetooltip</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "edgetooltip")
    public String edgeTooltip;

    /**
     * <p>Position of an edge's head label, in points. The position indicates the center of the label.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dhead_lp">head_lp</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "head_lp")
    public PointOrRectangle centerOfHeadLabel;

    /**
     * <p>If true, the head of an edge is clipped to the boundary of the head node; otherwise, the end of the edge
     * goes to the center of the node, or the center of a port, if applicable.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dheadclip">headclip</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "headclip")
    public Boolean headClip;

    /**
     * <p>If headURL is defined, it is output as part of the head label of the edge. Also, this value is used near the
     * head node, overriding any URL value. </p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dheadURL">headURL</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "headURL")
    public String headURL;

    /**
     * <p>Indicates where on the head node to attach the head of the edge. In the default case, the edge is aimed
     * towards the center of the node, and then clipped at the node boundary.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dheadport">headport</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "headport")
    public Port headPort;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>If the edge has a headURL, this attribute determines which window of the browser is used for the URL.
     * Setting it to "_graphviz" will open a new window if it doesn't already exist, or reuse it if it does. If
     * undefined, the value of the {@link AbstractAttributes#targetURL} is used.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dheadtarget">headtarget</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "headtarget")
    public String headTarget;

    /**
     * <p>Text label to be placed near head of edge.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dheadlabel">headlabel</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "headlabel")
    public String headLabel;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>Tooltip annotation attached to the head of an edge. This is used only if the edge has
     * a headURL attribute.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dheadtooltip">headtooltip</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "headtooltip")
    public String headTooltip;

    /**
     * <p>This, along with {@link #labelDistance}, determine where the headlabel (taillabel) are placed with respect
     * to the head (tail) in polar coordinates. The origin in the coordinate system is the point where the edge
     * touches the node. The ray of 0 degrees goes from the origin back along the edge, parallel to the edge at the
     * origin.</p>
     * <p>The angle, in degrees, specifies the rotation from the 0 degree ray, with positive angles moving
     * counterclockwise and negative angles moving clockwise.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabelangle">labelangle</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "labelangle")
    public Double labelAngle;

    /**
     * <p>Multiplicative scaling factor adjusting the distance that the headlabel(taillabel) is from the head(tail)
     * node. The default distance is 10 points. See {@link #labelAngle} for more details.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabeldistance">labeldistance</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "labeldistance")
    public Double labelDistance;

    /**
     * <p>If true, allows edge labels to be less constrained in position. In particular, it may appear on top of
     * other edges.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabelfloat">labelfloat</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "labelfloat")
    public Boolean labelFloat;

    /**
     * <p>Color used for headlabel and taillabel. If not set, defaults to edge's fontcolor.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabelfontcolor">labelfontcolor</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "labelfontcolor")
    public StandardCssColor labelFontColor;

    /**
     * <p>Font used for headlabel and taillabel. If not set, defaults to edge's fontname.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabelfontname">labelfontname</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "labelfontname")
    public String labelFontName;

    /**
     * <p>Font size, in points, used for headlabel and taillabel. If not set, defaults to edge's fontsize.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabelfontsize">labelfontsize</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "labelfontsize")
    public Double labelFontSizeInPoints;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>If the edge has a URL or labelURL attribute, this attribute determines which window of the browser is used
     * for the URL attached to the label. Setting it to "_graphviz" will open a new window if it doesn't already
     * exist, or reuse it if it does. If undefined, the value of the target is used.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabeltarget">labeltarget</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "labeltarget")
    public String labelTarget;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>Tooltip annotation attached to label of an edge. This is used only if the edge has a URL or labelURL
     * attribute.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabeltooltip">labeltooltip</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "labeltooltip")
    public String labelTooltip;

    /**
     * <p>Specifies layers in which the node, edge or cluster is present. The value of this property is a LayerRange,
     * which is defined as follows:</p>
     * <h2>LayerRange specification</h2>
     * <p>Specifies a list of layers defined by the layers attribute. It consists of a list of layer intervals
     * separated by any collection of characters from the layerlistsep attribute. Each layer interval is specified
     * as either a layerId or a layerIdslayerId, where layerId = "all", a decimal integer or a layer name. (An
     * integer i corresponds to layer i, layers being numbered from 1.) The string s consists of 1 or more separator
     * characters specified by the layersep attribute.</p>
     * <p>Thus, assuming the default values for layersep and layerlistsep, if layers="a:b:c:d:e:f:g:h", the layerRange
     * string layers="a:b,d,f:all" would denote the layers a b d f g h".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlayer">layer</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "layer")
    public String layer;

    /**
     * <p>Logical head of an edge. When {@link GraphAttributes#compound} is true, if logicalHead is defined
     * and is the name of a cluster containing the real head, the edge is clipped to the boundary of the cluster.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlhead">lhead</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "lhead")
    public String logicalHead;

    /**
     * <p>Logical tail of an edge. When {@link GraphAttributes#compound} is true, if logicalTail is defined
     * and is the name of a cluster containing the real tail, the edge is clipped to the boundary of the cluster.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dltail">ltail</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "ltail")
    public String logicalTail;

    /**
     * <p>Label position, in points. The position indicates the center of the label.
     * At present, most device-independent units are either inches or points, which we take as 72 points per inch.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlp">lp</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "lp")
    public PointOrRectangle labelPosition;

    /**
     * <p>Minimum edge length (rank difference between head and tail).</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dminlen">minlen</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "minlen")
    public Double minimumLength;

    /**
     * <p>Specifies the width of the pen, in points, used to draw lines and curves, including the boundaries of
     * edges and clusters. The value is inherited by subclusters. It has no effect on text.</p>
     * <p>Previous to 31 January 2008, the effect of penwidth=W was achieved by including setlinewidth(W) as part of a
     * style specification. If both are used, penwidth will be used.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dpenwidth">penwidth</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "penwidth")
    public Integer penWidthInPoints;

    /**
     * <p>Position of node, or spline control points. For nodes, the position indicates the center of the node. On
     * output, the coordinates are in points.</p>
     * <p>In neato and fdp, pos can be used to set the initial position of a node. By default, the coordinates are
     * assumed to be in inches. However, the -s command line flag can be used to specify different units. As the
     * output coordinates are in points, feeding the output of a graph laid out by a Graphviz program into neato or
     * fdp will almost always require the -s flag.</p>
     * <p>When the -n command line flag is used with neato, it is assumed the positions have been set by one of the
     * layout programs, and are therefore in points. Thus, neato -n can accept input correctly without requiring a
     * -s flag and, in fact, ignores any such flag.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dpos">pos</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "pos")
    public PointOrRectangle position;

    /**
     * <p>Edges with the same head and the same samehead value are aimed at the same point on the head. This has no
     * effect on loops. Each node can have at most 5 unique samehead values.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsamehead">samehead</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "samehead")
    public String samehead;

    /**
     * <p>Edges with the same head and the same sametail value are aimed at the same point on the head. This has no
     * effect on loops. Each node can have at most 5 unique sametail values.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsametail">sametail</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "sametail")
    public String sametail;

    /**
     * <p><strong>NOTE:</strong>The styles tapered, striped and wedged are only available in release 2.30 and later.</p>
     * <p>At present, the recognized style names are "dashed", "dotted", "solid", "invis" and "bold" for nodes and
     * edges, "tapered" for edges only, and "filled", "striped", "wedged", "diagonals" and "rounded" for nodes only.
     * The styles "filled", "striped" and "rounded" are recognized for clusters. The style "radial" is recognized for
     * nodes, clusters and graphs, and indicates a radial-style gradient fill if applicable.</p>
     * <p>The style "striped" causes the fill to be done as a set of vertical stripes. The colors are specified via a
     * colorList, the colors drawn from left to right in list order. Optional color weights can be specified to
     * indicate the proportional widths of the bars. If the sum of the weights is less than 1, the remainder is
     * divided evenly among the colors with no weight. Note: The style "striped" is only supported with clusters and
     * rectangularly-shaped nodes.</p>
     * <p>The style "wedged" causes the fill to be done as a set of wedges. The colors are specified via a
     * colorList, with the colors drawn counter-clockwise starting at angle 0. Optional color weights are interpreted
     * analogously to the striped case described above. Note: The style "wedged" is allowed only for
     * elliptically-shaped nodes.</p>
     * <p>The following tables illustrate some of the effects of the style settings. Examples of tapered line styles are
     * given below. Examples of linear and radial gradient fill can be seen under colorList.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dstyle">style</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "style")
    public EdgeStyle style;

    /**
     * <p>If tailURL is defined, it is output as part of the tail label of the edge. Also, this value is used near
     * the tail node, overriding any URL value.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtailURL">tailURL</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "tailURL")
    public String tailURL;

    /**
     * <p>Position of an edge's tail label, in points. The position indicates the center of the label.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtail_lp">tail_lp</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "tail_lp")
    public PointOrRectangle centerOfTailLabel;

    /**
     * <p>If true, the tail of an edge is clipped to the boundary of the tail node; otherwise, the end of the edge
     * goes to the center of the node, or the center of a port, if applicable.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtailclip">tailclip</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "tailclip")
    public Boolean tailClip;

    /**
     * <p>Text label to be placed near tail of edge.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtaillabel">taillabel</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "taillabel")
    public String tailLabel;

    /**
     * <p>Indicates where on the head node to attach the tail of the edge.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtailport">tailport</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "tailport")
    public Port tailPort;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>If the edge has a tailURL, this attribute determines which window of the browser is used for the URL.
     * Setting it to "_graphviz" will open a new window if it doesn't already exist, or reuse it if it does.
     * If undefined, the value of the {@link AbstractAttributes#targetURL} is used.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtailtarget">tailtarget</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "tailtarget")
    public String tailTarget;

    /**
     * <p><strong>Note</strong> Only applicable to svg, map.</p>
     * <p>Tooltip annotation attached to the tail of an edge. This is used only if the edge has
     * a tailURL attribute.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtailtooltip">tailtooltip</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "tailtooltip")
    public String tailTooltip;

    /**
     * <p>Tooltip annotation attached to the node or edge. If unset, Graphviz will use the object's label if
     * defined. Note that if the label is a record specification or an HTML-like label, the resulting tooltip may be
     * unhelpful. In this case, if tooltips will be generated, the user should set a tooltip attribute explicitly.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtooltip">tooltip</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "tooltip")
    public String tooltip;

    /**
     * <p>Weight of edge. In dot, the heavier the weight, the shorter, straighter and more vertical the edge is.
     * <strong>N.B</strong>. Weights in dot must be integers. For twopi, a weight of 0 indicates the edge should not
     * be used in constructing a spanning tree from the root. For other layouts, a larger weight encourages the
     * layout to make the edge length closer to that specified by the len attribute.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dweight">weight</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "weight")
    public Integer weight;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {

        // Fail fast
        if (this == o) {
            return true;
        }
        if (!(o instanceof EdgeAttributes)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        // Delegate to internal state
        final EdgeAttributes that = (EdgeAttributes) o;
        return Double.compare(that.arrowSize, arrowSize) == 0
                && Objects.equals(penWidthInPoints, that.penWidthInPoints)
                && Objects.equals(arrowHead, that.arrowHead)
                && Objects.equals(arrowTail, that.arrowTail)
                && Objects.equals(constraint, that.constraint)
                && Objects.equals(decorate, that.decorate)
                && direction == that.direction
                && Objects.equals(edgeURL, that.edgeURL)
                && Objects.equals(edgeTarget, that.edgeTarget)
                && Objects.equals(edgeTooltip, that.edgeTooltip)
                && Objects.equals(centerOfHeadLabel, that.centerOfHeadLabel)
                && Objects.equals(headClip, that.headClip)
                && Objects.equals(headURL, that.headURL)
                && Objects.equals(headPort, that.headPort)
                && Objects.equals(headTarget, that.headTarget)
                && Objects.equals(headLabel, that.headLabel)
                && Objects.equals(headTooltip, that.headTooltip)
                && Objects.equals(labelAngle, that.labelAngle)
                && Objects.equals(labelDistance, that.labelDistance)
                && Objects.equals(labelFloat, that.labelFloat)
                && labelFontColor == that.labelFontColor
                && Objects.equals(labelFontName, that.labelFontName)
                && Objects.equals(labelFontSizeInPoints, that.labelFontSizeInPoints)
                && Objects.equals(labelTarget, that.labelTarget)
                && Objects.equals(labelTooltip, that.labelTooltip)
                && Objects.equals(layer, that.layer)
                && Objects.equals(logicalHead, that.logicalHead)
                && Objects.equals(logicalTail, that.logicalTail)
                && Objects.equals(labelPosition, that.labelPosition)
                && Objects.equals(minimumLength, that.minimumLength)
                && Objects.equals(position, that.position)
                && Objects.equals(samehead, that.samehead)
                && Objects.equals(sametail, that.sametail)
                && style == that.style
                && Objects.equals(tailURL, that.tailURL)
                && Objects.equals(centerOfTailLabel, that.centerOfTailLabel)
                && Objects.equals(tailClip, that.tailClip)
                && Objects.equals(tailLabel, that.tailLabel)
                && Objects.equals(tailPort, that.tailPort)
                && Objects.equals(tailTarget, that.tailTarget)
                && Objects.equals(tailTooltip, that.tailTooltip)
                && Objects.equals(tooltip, that.tooltip)
                && Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                arrowSize,
                arrowHead,
                arrowTail,
                constraint,
                decorate,
                direction,
                edgeURL,
                edgeTarget,
                edgeTooltip,
                centerOfHeadLabel,
                headClip,
                headURL,
                headPort,
                headTarget,
                headLabel,
                headTooltip,
                labelAngle,
                labelDistance,
                labelFloat,
                labelFontColor,
                labelFontName,
                labelFontSizeInPoints,
                labelTarget,
                labelTooltip,
                layer,
                logicalHead,
                logicalTail,
                labelPosition,
                minimumLength,
                penWidthInPoints,
                position,
                samehead,
                sametail,
                style,
                tailURL,
                centerOfTailLabel,
                tailClip,
                tailLabel,
                tailPort,
                tailTarget,
                tailTooltip,
                tooltip,
                weight);
    }
}
