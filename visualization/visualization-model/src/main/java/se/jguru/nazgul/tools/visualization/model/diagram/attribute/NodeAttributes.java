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

package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.NodeStyle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.VerticalAlignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

/**
 * POJO implementation of an entity containing attributes relevant for
 * {@link se.jguru.nazgul.tools.visualization.model.diagram.statement.Node} objects.
 * Property descriptions copied from <a href="http://www.graphviz.org/content/attrs">The Graphviz Website's
 * description of attributes.</a>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"degreesRotated", "distortion", "fixedSize",
        "gradientAngle", "group", "image", "imageScale", "insertionOrder", "isRegular", "labelAlignment", "layer",
        "margin", "numberOfSides", "outOrdering", "penWidthInPoints", "peripheries", "position", "samplepoints",
        "shape", "size", "skew", "style", "tooltip"})
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeAttributes extends AbstractAttributes {

    /**
     * <p>Distortion factor for shape=polygon. Positive values cause top part to be larger than bottom; negative values
     * do the opposite.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#ddistortion">distortion</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "distortion")
    public Double distortion;

    /**
     * <p>If false, the size of a node is determined by smallest width and height needed to contain its label and
     * image, if any, with a margin specified by the margin attribute. The width and height must also be at least as
     * large as the sizes specified by the width and height attributes, which specify the minimum values for these
     * parameters.</p>
     * <p>If true, the node size is specified by the values of the width and height attributes only and is not expanded
     * to contain the text label. There will be a warning if the label (with margin) cannot fit within these limits.</p>
     * <p>If the fixedsize attribute is set to shape, the width and height attributes also determine the size of the
     * node shape, but the label can be much larger. Both the label and shape sizes are used when avoiding node
     * overlap, but all edges to the node ignore the label and only contact the node shape. No warning is given if
     * the label is too large.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dfixedsize">fixedsize</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "fixedsize")
    public String fixedSize;

    /**
     * <p>If a gradient fill is being used, this determines the angle of the fill. For linear fills, the colors
     * transform along a line specified by the angle and the center of the object. For radial fills, a value of zero
     * causes the colors to transform radially from the center; for non-zero values, the colors transform from a
     * point near the object's periphery as specified by the value. If unset, the default angle is 0.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dgradientangle">gradientangle</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "gradientangle")
    public Integer gradientAngle;

    /**
     * <p>If the end points of an edge belong to the same group, i.e., have the same group attribute, parameters are
     * set to avoid crossings and keep the edges straight.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dgroup">group</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "group")
    public String group;

    /**
     * <p>Maximum width and height of drawing, in inches. If only a single number is given, this is used for both the
     * width and the height. If defined and the drawing is larger than the given size, the drawing is uniformly
     * scaled down so that it fits within the given size.</p>
     * <p>If size ends in an exclamation point (i.e. if the {@link PointOrRectangle#setUnchangeable(boolean)} method
     * is called), then it is taken to be the desired size. In this case, if both dimensions of the drawing are less
     * than size, the drawing is scaled up uniformly until at least one dimension equals its dimension in size.</p>
     * <p>Note that there is some interaction between the size and ratio attributes.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsize">size</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "size")
    public PointOrRectangle size;

    /**
     * <p>Gives the name of a file containing an image to be displayed inside a node. The image file must be in one
     * of the recognized formats, typically JPEG, PNG, GIF, BMP, SVG or Postscript, and be able to be converted into
     * the desired output format.The file must contain the image size information. This is usually trivially true
     * for the bitmap formats. For PostScript, the file must contain a line starting with %%BoundingBox: followed by
     * four integers specifying the lower left x and y coordinates and the upper right x and y coordinates of the
     * bounding box for the image, the coordinates being in points. An SVG image file must contain width and height
     * attributes, typically as part of the svg element. The values for these should have the form of a floating
     * point number, followed by optional units, e.g., width="76pt". Recognized units are in, px, pc, pt, cm and mm
     * for inches, pixels, picas, points, centimeters and millimeters, respectively. The default unit is points.</p>
     * <p>Unlike with the shapefile attribute, the image is treated as node content rather than the entire node. In
     * particular, an image can be contained in a node of any shape, not just a rectangle.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dimage">image</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "image")
    public String image;

    /**
     * <p>Attribute controlling how an image fills its containing node. In general, the image is given its natural
     * size, (cf. dpi), and the node size is made large enough to contain its image, its label, its margin, and its
     * peripheries. Its width and height will also be at least as large as its minimum width and height. If,
     * however, fixedsize=true, the width and height attributes specify the exact size of the node.</p>
     * <p>During rendering, in the default case (imagescale=false), the image retains its natural size. If
     * imagescale=true, the image is uniformly scaled (i.e., its aspect ratio is preserved) to fit inside the node.
     * At least one dimension of the image will be as large as possible given the size of the node. When
     * imagescale=width, the width of the image is scaled to fill the node width. The corresponding property holds
     * when imagescale=height. When imagescale=both, both the height and the width are scaled separately to fill the
     * node.</p>
     * <p>In all cases, if a dimension of the image is larger than the corresponding dimension of the node, that
     * dimension of the image is scaled down to fit the node. As with the case of expansion, if imagescale=true,
     * width and height are scaled uniformly.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dimagescale">imagescale</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "imagescale")
    public String imageScale;

    /**
     * <p>Vertical placement of labels for nodes, root graphs and clusters.For graphs and clusters, only "t" and "b"
     * (i.e. {@link VerticalAlignment#TOP} and {@link VerticalAlignment#BOTTOM} respectively)
     * are allowed, corresponding to placement at the top and bottom, respectively. By default, root graph labels go
     * on the bottom and cluster labels go on the top. Note that a subgraph inherits attributes from its parent.
     * Thus, if the root graph sets labelloc to "b", the subgraph inherits this value.</p>
     * <p>For nodes, this attribute is used only when the height of the node is larger than the height of its label. If
     * labelloc is set to "t", "c", or "b", the label is aligned with the top, centered, or aligned with the bottom
     * of the node, respectively. In the default case, the label is vertically centered.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabelloc">labelloc</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "labelloc")
    public VerticalAlignment labelAlignment;

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
     * <p>For graphs, this sets x and y margins of canvas, in inches. If the margin is a single double, both margins
     * are set equal to the given value. Note that the margin is not part of the drawing but just empty space left
     * around the drawing. It basically corresponds to a translation of drawing, as would be necessary to center a
     * drawing on a page. Nothing is actually drawn in the margin. To actually extend the background of a drawing,
     * see the {@link GraphAttributes#padding}/"pad" attribute.</p>
     * <p>For clusters, this specifies the space between the nodes in the cluster and the cluster bounding box. By
     * default, this is 8 points.</p>
     * <p>For nodes, this attribute specifies space left around the node's label. By default, the value is 0.11,
     * 0.055.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dmargin">margin</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "margin")
    public PointOrRectangle margin;

    /**
     * <p>If the value of the attribute is "out", then the outedges of a node, that is, edges with the node as its tail
     * node, must appear left-to-right in the same order in which they are defined in the input. If the value of the
     * attribute is "in", then the inedges of a node must appear left-to-right in the same order in which they are
     * defined in the input. If defined as a graph or subgraph attribute, the value is applied to all nodes in the
     * graph or subgraph. Note that the graph attribute takes precedence over the node attribute.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dordering">ordering</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "ordering")
    public Boolean outOrdering;

    /**
     * <p>Angle, in degrees, used to rotate polygon node shapes. For any number of polygon sides, 0 degrees rotation
     * results in a flat base.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dorientation">orientation</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "orientation")
    public Integer degreesRotated;

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
     * <p>Set number of peripheries used in polygonal shapes and cluster boundaries. Note that user-defined shapes are
     * treated as a form of box shape, so the default peripheries value is 1 and the user-defined shape will be
     * drawn in a bounding rectangle. Setting peripheries=0 will turn this off. Also, 1 is the maximum peripheries
     * value for clusters.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dperipheries">peripheries</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "peripheries")
    public Integer peripheries;

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
     * <p>If true, force polygon to be regular, i.e., the vertices of the polygon will lie on a circle whose center
     * is the center of the node.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dregular">regular</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "regular")
    public Boolean isRegular;

    /**
     * <p>If the input graph defines the vertices attribute, and output is dot or xdot, this gives the number of
     * points used for a node whose shape is a circle or ellipse. It plays the same role in neato, when adjusting
     * the layout to avoid overlapping nodes, and in image maps.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsamplepoints">samplepoints</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "samplepoints")
    public Integer samplepoints;

    /**
     * <p>Set the shape of a node.</p>
     * <h2>Shape definition</h2>
     * <p>A string specifying the shape of a node. There are three main types of shapes : polygon-based,
     * record-based and user-defined. The record-based shape has largely been superseded and greatly generalized by
     * HTML-like labels. That is, instead of using shape=record, one might consider using shape=none and an
     * HTML-like label.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dshape">shape</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "shape")
    public String shape;

    /**
     * <p>Number of sides if shape=polygon.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsides">sides</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "sides")
    public Integer numberOfSides;

    /**
     * <p>Skew factor for {@link #shape}=polygon. Positive values skew top of polygon to right; negative to left.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dskew">skew</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "skew")
    public Double skew;

    /**
     * <p>If packMode indicates an array packing, this attribute specifies an insertion order among the
     * components, with smaller values inserted first.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsortv">sortv</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "sortv")
    public Integer insertionOrder;

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
    public NodeStyle style;

    /**
     * <p>Tooltip annotation attached to the node or edge. If unset, Graphviz will use the object's label if defined.
     * Note that if the label is a record specification or an HTML-like label, the resulting tooltip may be
     * unhelpful. In this case, if tooltips will be generated, the user should set a tooltip attribute explicitly.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtooltip">tooltip</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "tooltip")
    public String tooltip;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {

        // Fail fast
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        // Delegate to internal state
        final NodeAttributes that = (NodeAttributes) o;
        return Objects.equals(distortion, that.distortion) &&
                Objects.equals(fixedSize, that.fixedSize) &&
                Objects.equals(gradientAngle, that.gradientAngle) &&
                Objects.equals(group, that.group) &&
                Objects.equals(size, that.size) &&
                Objects.equals(image, that.image) &&
                Objects.equals(imageScale, that.imageScale) &&
                labelAlignment == that.labelAlignment &&
                Objects.equals(layer, that.layer) &&
                Objects.equals(margin, that.margin) &&
                Objects.equals(outOrdering, that.outOrdering) &&
                Objects.equals(degreesRotated, that.degreesRotated) &&
                Objects.equals(penWidthInPoints, that.penWidthInPoints) &&
                Objects.equals(peripheries, that.peripheries) &&
                Objects.equals(position, that.position) &&
                Objects.equals(isRegular, that.isRegular) &&
                Objects.equals(samplepoints, that.samplepoints) &&
                Objects.equals(shape, that.shape) &&
                Objects.equals(numberOfSides, that.numberOfSides) &&
                Objects.equals(skew, that.skew) &&
                Objects.equals(insertionOrder, that.insertionOrder) &&
                style == that.style &&
                Objects.equals(tooltip, that.tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                distortion,
                fixedSize,
                gradientAngle,
                group,
                size,
                image,
                imageScale,
                labelAlignment,
                layer,
                margin,
                outOrdering,
                degreesRotated,
                penWidthInPoints,
                peripheries,
                position,
                isRegular,
                samplepoints,
                shape,
                numberOfSides,
                skew,
                insertionOrder,
                style,
                tooltip);
    }
}
