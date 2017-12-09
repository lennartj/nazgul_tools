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
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.ClusterMode;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.HorizontalAlignment;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.Orientation;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.OutputOrder;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.RankDirection;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.SplineType;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.VerticalAlignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

/**
 * POJO implementation of an entity containing attributes relevant for
 * {@link se.jguru.nazgul.tools.visualization.model.diagram.Graph} objects.
 * Property descriptions copied from <a href="http://www.graphviz.org/content/attrs">The Graphviz Website's
 * description of attributes.</a>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"centered", "charset", "clusterRank",
        "compound", "concentrateGraph", "forceLabels",
        "labelHorizontalAlignment", "labelVerticalAlignment", "labelPosition", "labelSize", "layerListSeparator",
        "layers", "layerSelect", "layerSeparator", "layoutAlgorithm", "imagePath", "margin",
        "minimumDistanceBetweenNodes", "minimumDistanceBetweenSameRankAdjacentNodes", "multiplicativeScaleLimit",
        "nsLimit", "nsLimit1", "orientation", "outputOrder", "outOrdering",
        "degreesRotated", "insertionOrder", "pack", "packMode", "padding", "quantum",
        "rankDirection", "rankSeparation", "ratio", "resolution", "runCrossClusterMinimisationTwice", "searchSize",
        "size", "splineType", "urlOrPathToStylesheet", "useTrueColor"})
@XmlAccessorType(XmlAccessType.FIELD)
public class GraphAttributes extends AbstractAttributes {

    /**
     * <p>If true, the drawing is centered in the output canvas.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dcenter"><i>center</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "center")
    public Boolean centered;

    /**
     * <p>Specifies the character encoding used when interpreting string input as a text label.
     * The default value is "UTF-8". The other legal value is "iso-8859-1" or, equivalently, "Latin1".
     * The charset attribute is case-insensitive.
     * Note that if the character encoding used in the input does not match the charset value, the resulting output
     * may be very strange.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dcharset"><i>charset</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "charset")
    public String charset = "UTF-8";

    /**
     * <p>Mode used for handling clusters.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dclusterrank"><i>clusterrank</i></a>.</p>
     *
     * @see ClusterMode#LOCAL
     * @see ClusterMode#GLOBAL
     */
    @XmlElement
    @DotProperty(name = "clusterrank")
    public ClusterMode clusterRank;

    /**
     * If true, allow edges between clusters.
     * Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dcompound"><i>compound</i></a>.
     */
    @XmlAttribute
    @DotProperty(name = "compound")
    public Boolean compound;

    /**
     * If true, use edge concentrators. This merges multi-edges into a single edge and causes partially parallel
     * edges to share part of their paths. Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dconcentrate"><i>concentrate</i></a>.
     */
    @XmlAttribute
    @DotProperty(name = "concentrate")
    public Boolean concentrateGraph;

    /**
     * <p>This specifies the expected number of pixels per inch on a display device. For bitmap output, this guarantees
     * that text rendering will be done more accurately, both in size and in placement. For SVG output, it is used
     * to guarantee that the dimensions in the output correspond to the correct number of points or inches.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#ddpi"><i>dpi</i> or <i>resolution</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "resolution")
    public Integer resolution;

    /**
     * <p>If true, all "External Label" attributes are placed, even if there is some overlap with nodes or other labels.
     * (External Label for a node or edge := For nodes, the label will be placed outside of the node but near it. For
     * edges, the label will be placed near the center of the edge. This can be useful in dot to avoid the
     * occasional problem when the use of edge labels distorts the layout. For other layouts, the xlabel attribute
     * can be viewed as a synonym for the label attribute.</p>
     * <p>These labels are added after all nodes and edges have been placed. The labels will be placed so that they do
     * not overlap any node or label. This means it may not be possible to place all of them. To force placing all of
     * them, use the forcelabels attribute.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dforcelabels">forcelabels</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "forcelabels")
    public Boolean forceLabels;

    /**
     * <p>Specifies a list of directories in which to look for image files as specified by the image attribute or using
     * the IMG element in HTML-like labels. The string should be a list of (absolute or relative) pathnames, each
     * separated by a semicolon (for Windows) or a colon (all other OS). The first directory in which a file of the
     * given name is found will be used to load the image. If imagepath is not set, relative pathnames for the image
     * file will be interpreted with respect to the current working directory.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dimagepath">imagepath</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "imagepath")
    public String imagePath;

    /**
     * <p>Justification for cluster labels. If "r", the label is right-justified within bounding rectangle; if "l",
     * left-justified; else the label is centered. Note that a subgraph inherits attributes from its parent. Thus,
     * if the root graph sets labeljust to "l", the subgraph inherits this value.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabeljust">labeljust</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "labeljust")
    public HorizontalAlignment labelHorizontalAlignment = HorizontalAlignment.CENTER;

    /**
     * <p>Vertical placement of labels for nodes, root graphs and clusters. For graphs and clusters, only "t" and "b"
     * are allowed, corresponding to placement at the top and bottom, respectively. By default, root graph labels go
     * on the bottom and cluster labels go on the top. Note that a subgraph inherits attributes from its parent.
     * Thus, if the root graph sets labelloc to "b", the subgraph inherits this value.</p>
     * <p>For nodes, this attribute is used only when the height of the node is larger than the height of its label. If
     * labelloc is set to "t", "c", or "b", the label is aligned with the top, centered, or aligned with the bottom
     * of the node, respectively. In the default case, the label is vertically centered.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlabelloc">labelloc</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "labelloc")
    public VerticalAlignment labelVerticalAlignment;

    /**
     * <p>Indicates the orientation of the DOT graph.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlandscape">landscape</i></a>, but needs - for obvious reasons
     * - to be translated to a Boolean before use.</p>
     */
    @XmlAttribute
    @DotProperty(name = "landscape")
    public Orientation orientation;

    /**
     * <p>List of strings separated by characters from the layersep attribute (by default, colons, tabs or spaces),
     * defining layer names and implicitly numbered 1,2,...</p>
     * <p>The typical form for the layerList property is therefore
     * <strong><code>[layerName1][layerSeparator][layerName2][layerSeparator]...</code></strong></p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlayers">layers</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "layers")
    public String layers;

    /**
     * <p>Specifies the separator characters used to split the {@link #layers} attribute
     * into a list of layer names. Must consist of a non-whitespace and non-empty sequence of characters. Defaults
     * to any of the characters (space), (:) or (tab).</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlayersep">layersep</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "layersep")
    public String layerSeparator;

    /**
     * <p>Specifies a list of layers defined by the layers attribute (i.e. as given by the {@link #layers}
     * propety). It consists of a list of layer intervals separated by any collection of characters from the
     * layerlistsep (c.f. {@link #layerSeparator}) attribute. Each layer interval is specified
     * as either a layerId or a layerIdslayerId, where layerId = "all", a decimal integer or a layer name.
     * (An integer i corresponds to layer i, layers being numbered from 1.)
     * The string s consists of 1 or more separator characters specified by the layersep attribute.</p>
     * <p>Thus, assuming the default values for layersep and layerlistsep, if layers="a:b:c:d:e:f:g:h", the layerRange
     * string layers="a:b,d,f:all" would denote the layers a b d f g h".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlayerselect">layerselect</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "layerselect")
    public String layerSelect;

    /**
     * <p>Specifies the separator characters used to split an attribute of type <code>layerRange</code>
     * into a list of ranges. (C.f. {@link #layerSelect}).</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlayerlistsep">layerlistsep</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "layerlistsep")
    public String layerListSeparator;

    /**
     * <p>Specifies the name of the layout algorithm to use, such as "dot" or "neato". Normally, graphs should be kept
     * independent of a type of layout. In some cases, however, it can be convenient to embed the type of layout
     * desired within the graph. For example, a graph containing position information from a layout might want to
     * record what the associated layout algorithm was.</p>
     * <p>This attribute takes precedence over the -K flag or the actual command name used.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dlayout">layout</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "layout")
    public String layoutAlgorithm;

    /**
     * <p>Specifies the width and height of graph or cluster label, in inches.</p>
     * <p>Corresponds to the DOT properties
     * <a href="http://www.graphviz.org/content/attrs#dlwidth">lwidth</i></a> and
     * <a href="http://www.graphviz.org/content/attrs#dlheight">lheight</i></a>.</p>
     */
    @XmlElement
    @DotProperty(specialTreatment = true)
    public PointOrRectangle labelSize;

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
     * <p>For graphs, this sets x and y margins of canvas, in inches. If the margin is a single double, both margins
     * are set equal to the given value. Note that the margin is not part of the drawing but just empty space left
     * around the drawing. It basically corresponds to a translation of drawing, as would be necessary to center a
     * drawing on a page. Nothing is actually drawn in the margin. To actually extend the background of a drawing,
     * see the pad attribute.</p>
     * <p>For clusters, this specifies the space between the nodes in the cluster and the cluster bounding box.
     * By default, this is 8 points. For nodes, this attribute specifies space left around the node's label. By
     * default, the value is 0.11,0.055.</p>
     * <p>"%f,%f('!')?" representing the point (x,y). The optional '!' indicates the node position should not change
     * (input-only). If dim is 3, point may also have the format "%f,%f,%f('!')?" to represent the point (x,y,z).</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dmargin">margin</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "margin")
    public PointOrRectangle margin;

    /**
     * <p>Multiplicative scale factor used to alter the MinQuit (default = 8) and MaxIter (default = 24) parameters
     * used during crossing minimization. These correspond to the number of tries without improvement before
     * quitting and the maximum number of iterations in each pass.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dmclimit">mclimit</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "mclimit")
    public Double multiplicativeScaleLimit;

    /**
     * <p>Specifies the minimum separation between all nodes.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dmindist">mindist</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "mindist")
    public Double minimumDistanceBetweenNodes;

    /**
     * <p>Specifies the minimum space between two adjacent nodes in the same rank, in inches.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dnodesep">nodesep</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "nodesep")
    public Double minimumDistanceBetweenSameRankAdjacentNodes;

    /**
     * <p>Used to set number of iterations in network simplex applications. nslimit is used in computing node x
     * coordinates, nslimit1 for ranking nodes. If defined, # iterations = nslimit(1) * # nodes; otherwise, #
     * iterations = MAXINT.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dnslimit">nslimit</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "nslimit")
    public Double nsLimit;

    /**
     * <p>Used to set number of iterations in network simplex applications. nslimit is used in computing node x
     * coordinates, nslimit1 for ranking nodes. If defined, # iterations = nslimit(1) * # nodes; otherwise, #
     * iterations = MAXINT.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dnslimit1">nslimit1</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "nslimit1")
    public Double nsLimit1;

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
     * <p>"breadthfirst","nodesfirst","edgesfirst" These specify the order in which nodes and edges are drawn in
     * concrete output. The default "breadthfirst" is the simplest, but when the graph layout does not avoid
     * edge-node overlap, this mode will sometimes have edges drawn over nodes and sometimes on top of nodes. If the
     * mode "nodesfirst" is chosen, all nodes are drawn first, followed by the edges. This guarantees an edge-node
     * overlap will not be mistaken for an edge ending at a node. On the other hand, usually for aesthetic reasons,
     * it may be desirable that all edges appear beneath nodes, even if the resulting drawing is ambiguous. This can
     * be achieved by choosing "edgesfirst".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#doutputorder">outputorder</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "outputorder")
    public OutputOrder outputOrder;

    /**
     * <p>This is true if the value of pack is "true" (case-insensitive) or a non-negative integer. If true, each
     * connected component of the graph is laid out separately, and then the graphs are packed together. If pack has
     * an integral value, this is used as the size, in points, of a margin around each part; otherwise, a default
     * margin of 8 is used. If pack is interpreted as false, the entire graph is laid out together. The granularity
     * and method of packing is influenced by the packmode attribute.
     * For layouts which always do packing, such a twopi, the pack attribute is just used to set the margin.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dpack">pack</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "pack")
    public Boolean pack;

    /**
     * <p>The modes "node", "clust" or "graph" specify that the components should be packed together tightly, using the
     * specified granularity. A value of "node" causes packing at the node and edge level, with no overlapping of
     * these objects. This produces a layout with the least area, but it also allows interleaving, where a node of
     * one component may lie between two nodes in another component. A value of "graph" does a packing using the
     * bounding box of the component. Thus, there will be a rectangular region around a component free of elements
     * of any other component. A value of "clust" guarantees that top-level clusters are kept intact. What effect a
     * value has also depends on the layout algorithm. For example, neato does not support clusters, so a value of
     * "clust" will have the same effect as the default "node" value.</p>
     * <p>The mode "array(_flag)?(%d)?" indicates that the components should be packed at the graph level into an array
     * of graphs. By default, the components are in row-major order, with the number of columns roughly the square
     * root of the number of components. If the optional flags contains 'c', then column-major order is used.
     * Finally, if the optional integer suffix is used, this specifies the number of columns for row-major or the
     * number of rows for column-major. Thus, the mode "array_c4" indicates array packing, with 4 rows, starting in
     * the upper left and going down the first column, then down the second column, etc., until all components are
     * used.</p>
     * <p>If a graph is smaller than the array cell it occupies, it is centered by default. The optional flags may
     * contain 't', 'b', 'l', or 'r', indicating that the graphs should be aligned along the top, bottom, left or
     * right, respectively.</p>
     * <p>If the optional flags contains 'u', this causes the insertion order of elements in the array to be determined
     * by user-supplied values. Each component can specify its sort value by a non-negative integer using the sortv
     * attribute. Components are inserted in order, starting with the one with the smallest sort value. If no sort
     * value is specified, zero is used.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dpackmode">packmode</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "packmode")
    public String packMode;

    /**
     * <p>The padding attribute specifies how much, in inches, to extend the drawing area around the minimal area
     * needed to draw the graph. This area is part of the drawing and will be filled with the background color, if
     * appropriate.</p>
     * <p>Normally, a small padding is used for aesthetic reasons, especially when a background color is used, to avoid
     * having nodes and edges abutting the boundary of the drawn region.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dpad">pad</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "pad")
    public PointOrRectangle padding;

    /**
     * <p>If quantum > 0.0, node label dimensions will be rounded to integral multiples of the quantum.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dquantum">quantum</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "quantum")
    public Double quantum;

    /**
     * <p>"TB", "LR", "BT", "RL", corresponding to directed graphs drawn from top to bottom, from left to right, from
     * bottom to top, and from right to left, respectively.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#drankdir">rankdir</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "rankdir")
    public RankDirection rankDirection;

    /**
     * <p>In dot, this gives the desired rank separation, in inches. This is the minimum vertical distance between the
     * bottom of the nodes in one rank and the tops of nodes in the next. If the value contains "equally", the
     * centers of all ranks are spaced equally apart. Note that both settings are possible, e.g., ranksep = "1.2
     * equally".</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dranksep">ranksep</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "ranksep")
    public String rankSeparation;

    /**
     * <p>Sets the aspect ratio (drawing height/drawing width) for the drawing. Note that this is adjusted before the
     * size attribute constraints are enforced. In addition, the calculations usually ignore the node sizes, so the
     * final drawing size may only approximate what is desired.</p>
     * <p>If ratio is <strong><code>numeric</code></strong>, it is taken as the desired aspect ratio. Then, if the actual
     * aspect ratio is less than the desired ratio, the drawing height is scaled up to achieve the desired ratio; if
     * the actual ratio is greater than that desired ratio, the drawing width is scaled up.</p>
     * <p>If ratio = <strong><code>"fill"</code></strong> and the size attribute is set, node positions are scaled,
     * separately in both x and y, so
     * that the final drawing exactly fills the specified size. If both size values exceed the width and height of
     * the drawing, then both coordinate values of each node are scaled up accordingly. However, if either size
     * dimension is smaller than the corresponding dimension in the drawing, one dimension is scaled up so that the
     * final drawing has the same aspect ratio as specified by size. Then, when rendered, the layout will be scaled
     * down uniformly in both dimensions to fit the given size, which may cause nodes and text to shrink as well.
     * This may not be what the user wants, but it avoids the hard problem of how to reposition the nodes in an
     * acceptable fashion to reduce the drawing size.</p>
     * <p>If ratio = <strong><code>"compress"</code></strong> and the size attribute is set, dot attempts to
     * compress the initial layout to fit in the given size. This achieves a tighter packing of nodes but reduces
     * the balance and symmetry. This feature only works in dot.</p>
     * <p>If ratio = <strong><code>"expand"</code></strong>, the size attribute is set, and both the width and the
     * height of the graph are less than
     * the value in size, node positions are scaled uniformly until at least one dimension fits size exactly. Note
     * that this is distinct from using size as the desired size, as here the drawing is expanded before edges are
     * generated and all node and text sizes remain unchanged.</p>
     * <p>If ratio = <strong><code>"auto"</code></strong>, the page attribute is set and the graph cannot be drawn
     * on a single page, then size is set
     * to an "ideal" value. In particular, the size in a given dimension will be the smallest integral multiple of
     * the page size in that dimension which is at least half the current size. The two dimensions are then scaled
     * independently to the new size. This feature only works in dot.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dratio">ratio</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "ratio")
    public String ratio;

    /**
     * <p>If true and there are multiple clusters, run crossing minimization a second time.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dremincross">remincross</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "remincross")
    public Boolean runCrossClusterMinimisationTwice;

    /**
     * <p>Causes the final layout to be rotated counter-clockwise by the specified number of degrees.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#drotation">rotation</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "rotation")
    public Integer degreesRotated;

    /**
     * <p>During network simplex, maximum number of edges with negative cut values to search when looking
     * for one with minimum cut value. Defaults to <strong>30</strong>.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsearchsize">searchsize</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "searchsize")
    public Integer searchSize;

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
     * <p>If {@link #packMode}indicates an array packing, this attribute specifies an insertion order among the
     * components, with smaller values inserted first.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsortv">sortv</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "sortv")
    public Integer insertionOrder;

    /**
     * <p>Controls how, and if, Edges are represented. By default, DOT uses {@link SplineType#SPLINES}.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dsplines">splines</i></a>.</p>
     *
     * @see SplineType
     */
    @XmlAttribute
    @DotProperty(name = "splines")
    public SplineType splineType;

    /**
     * <p>A URL or pathname specifying an XML style sheet, used only for SVG output.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dstylesheet">stylesheet</i></a>.</p>
     */
    @XmlElement
    @DotProperty(name = "stylesheet")
    public String urlOrPathToStylesheet;

    /**
     * <p>If set explicitly to true or false, the value determines whether or not internal bitmap rendering relies on a
     * truecolor color model or uses a color palette. If the attribute is unset, truecolor is not used unless there
     * is a shapefile property for some node in the graph. The output model will use the input model when possible.</p>
     * <p>Use of color palettes results in less memory usage during creation of the bitmaps and smaller output files.</p>
     * <p>Usually, the only time it is necessary to specify the truecolor model is if the graph uses more than 256
     * colors. However, if one uses bgcolor=transparent with a color palette, font antialiasing can show up as a
     * fuzzy white area around characters. Using truecolor=true avoids this problem.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtruecolor">truecolor</i></a>.</p>
     */
    @XmlAttribute
    @DotProperty(name = "truecolor")
    public Boolean useTrueColor;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {

        // Fail fast
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphAttributes)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        // Delegate to internal state
        final GraphAttributes that = (GraphAttributes) o;
        return Objects.equals(degreesRotated, that.degreesRotated) &&
                Objects.equals(searchSize, that.searchSize) &&
                Objects.equals(insertionOrder, that.insertionOrder) &&
                Objects.equals(centered, that.centered) &&
                Objects.equals(charset, that.charset) &&
                clusterRank == that.clusterRank &&
                Objects.equals(compound, that.compound) &&
                Objects.equals(concentrateGraph, that.concentrateGraph) &&
                Objects.equals(resolution, that.resolution) &&
                Objects.equals(forceLabels, that.forceLabels) &&
                Objects.equals(imagePath, that.imagePath) &&
                labelHorizontalAlignment == that.labelHorizontalAlignment &&
                labelVerticalAlignment == that.labelVerticalAlignment &&
                orientation == that.orientation &&
                Objects.equals(layers, that.layers) &&
                Objects.equals(layerSeparator, that.layerSeparator) &&
                Objects.equals(layerSelect, that.layerSelect) &&
                Objects.equals(layerListSeparator, that.layerListSeparator) &&
                Objects.equals(layoutAlgorithm, that.layoutAlgorithm) &&
                Objects.equals(labelSize, that.labelSize) &&
                Objects.equals(labelPosition, that.labelPosition) &&
                Objects.equals(margin, that.margin) &&
                Objects.equals(multiplicativeScaleLimit, that.multiplicativeScaleLimit) &&
                Objects.equals(minimumDistanceBetweenNodes, that.minimumDistanceBetweenNodes) &&
                Objects.equals(minimumDistanceBetweenSameRankAdjacentNodes,
                        that.minimumDistanceBetweenSameRankAdjacentNodes) &&
                Objects.equals(nsLimit, that.nsLimit) &&
                Objects.equals(nsLimit1, that.nsLimit1) &&
                Objects.equals(outOrdering, that.outOrdering) &&
                outputOrder == that.outputOrder &&
                Objects.equals(pack, that.pack) &&
                Objects.equals(packMode, that.packMode) &&
                Objects.equals(padding, that.padding) &&
                Objects.equals(quantum, that.quantum) &&
                rankDirection == that.rankDirection &&
                Objects.equals(rankSeparation, that.rankSeparation) &&
                Objects.equals(ratio, that.ratio) &&
                Objects.equals(runCrossClusterMinimisationTwice, that.runCrossClusterMinimisationTwice) &&
                Objects.equals(size, that.size) &&
                splineType == that.splineType &&
                Objects.equals(urlOrPathToStylesheet, that.urlOrPathToStylesheet) &&
                Objects.equals(useTrueColor, that.useTrueColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                centered,
                charset,
                clusterRank,
                compound,
                concentrateGraph,
                resolution,
                forceLabels,
                imagePath,
                labelHorizontalAlignment,
                labelVerticalAlignment,
                orientation,
                layers,
                layerSeparator,
                layerSelect,
                layerListSeparator,
                layoutAlgorithm,
                labelSize,
                labelPosition,
                margin,
                multiplicativeScaleLimit,
                minimumDistanceBetweenNodes,
                minimumDistanceBetweenSameRankAdjacentNodes,
                nsLimit,
                nsLimit1,
                outOrdering,
                outputOrder,
                pack,
                packMode,
                padding,
                quantum,
                rankDirection,
                rankSeparation,
                ratio,
                runCrossClusterMinimisationTwice,
                degreesRotated,
                searchSize,
                size,
                insertionOrder,
                splineType,
                urlOrPathToStylesheet,
                useTrueColor);
    }
}
