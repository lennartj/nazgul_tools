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
package se.jguru.nazgul.tools.visualization.api.diagram.attribute.builder;

import se.jguru.nazgul.tools.visualization.api.diagram.attribute.AbstractDelegatingAttributeList;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.color.StandardCssColor;

import java.math.BigDecimal;

/**
 * Attributes relevant for {@link se.jguru.nazgul.tools.visualization.api.diagram.Graph} objects.
 * Property descriptions copied from <a href="http://www.graphviz.org/content/attrs">The Graphviz Website's
 * description of attributes.</a>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphAttributeList extends AbstractDelegatingAttributeList {

    /**
     * When attached to the root graph, this color is used as the background for entire canvas.
     * When a cluster attribute, it is used as the initial background for the cluster.
     * If a cluster has a filled style, the cluster's fillcolor will overlay the background color.
     *
     * @param bgColor A non-null {@link StandardCssColor}.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withBgColor(final StandardCssColor bgColor) {

        // Add the attribute
        if (bgColor != null) {
            addAttribute("bgcolor", bgColor.getRgbValue());
        }

        // All Done.
        return this;
    }

    /**
     * If true, the drawing is centered in the output canvas.
     *
     * @param isCentered If true, the drawing is centered in the output canvas.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withCenter(final boolean isCentered) {
        return (GraphAttributeList) addAttribute("center", "" + isCentered);
    }

    /**
     * Specifies the character encoding used when interpreting string input as a text label.
     * The default value is "UTF-8". The other legal value is "iso-8859-1" or, equivalently, "Latin1".
     * The charset attribute is case-insensitive.
     * Note that if the character encoding used in the input does not match the charset value, the resulting output
     * may be very strange.
     *
     * @param charset The charset to assign.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withCharset(final String charset) {
        return (GraphAttributeList) addAttribute("charset", charset);
    }

    /**
     * Mode used for handling clusters.
     * If clusterrank is "local", a subgraph whose name begins with "cluster" is given special treatment.
     * The  subgraph is laid out separately, and then integrated as a unit into its parent graph, with a bounding
     * rectangle drawn about it.
     * If the cluster has a label parameter, this label is displayed within the rectangle.
     * Note also that there can be clusters within clusters.
     * At present, the modes "global" and "none" appear to be identical, both
     * turning off the special cluster processing.
     *
     * @param isGlobal {@code true} assigns the value "global" and {@code false} assigns the value "local".
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withGlobalClusterRank(final boolean isGlobal) {
        return (GraphAttributeList) addAttribute("clusterrank", isGlobal ? "global" : "local");
    }

    /**
     * This attribute specifies a color scheme namespace. If defined, it specifies the context for interpreting
     * color names. In particular, if a color value has form "xxx" or "//xxx", then the color xxx will be evaluated
     * according to the current color scheme. If no color scheme is set, the standard X11 naming is used. For
     * example, if colorscheme=bugn9, then color=7 is interpreted as "/bugn9/7".
     *
     * @param colorScheme A non-empty colorScheme specification.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withColorScheme(final String colorScheme) {
        return (GraphAttributeList) addAttribute("colorscheme", colorScheme);
    }

    /**
     * Comments are inserted into output. Device-dependent
     *
     * @param comment A non-empty comment.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withComment(final String comment) {
        return (GraphAttributeList) addAttribute("comment", comment);
    }

    /**
     * If true, allow edges between clusters.
     *
     * @param isCompound if {@code true}, permit edges between clusters.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withCompound(final boolean isCompound) {
        return (GraphAttributeList) addAttribute("compound", isCompound ? "true" : "false");
    }

    /**
     * If true, use edge concentrators. This merges multi-edges into a single edge and causes partially parallel
     * edges to share part of their paths.
     *
     * @param useConcentrators If true, use edge concentrators.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withConcentrators(final boolean useConcentrators) {
        return (GraphAttributeList) addAttribute("compound", useConcentrators ? "true" : "false");
    }

    /**
     * This specifies the expected number of pixels per inch on a display device. For bitmap output, this guarantees
     * that text rendering will be done more accurately, both in size and in placement. For SVG output, it is used
     * to guarantee that the dimensions in the output correspond to the correct number of points or inches.
     *
     * @param dpi A positive integer, typically 72, 96 or 144.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withDPI(final int dpi) {

        if (dpi > 0) {
            addAttribute("dpi", "" + dpi);
        }

        // All Done.
        return this;
    }

    /**
     * Color used for text.
     *
     * @param color A non-null {@link StandardCssColor}.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withFontColor(final StandardCssColor color) {

        // Add the attribute
        if (color != null) {
            addAttribute("fontcolor", color.getRgbValue());
        }

        // All Done.
        return this;
    }

    /**
     * <p>Font used for text. This very much depends on the output format and, for non-bitmap output such as PostScript
     * or SVG, the availability of the font when the graph is displayed or printed. As such, it is best to rely on
     * font faces that are generally available, such as Times-Roman, Helvetica or Courier.
     * How font names are resolved also depends on the underlying library that handles font name resolution. If
     * Graphviz was built using the fontconfig library, the latter library will be used to search for the font. See
     * the commands fc-list, fc-match and the other fontconfig commands for how names are resolved and which fonts
     * are available. Other systems may provide their own font package, such as Quartz for OS X.</p>
     * <p>
     * <p>Note that various font attributes, such as weight and slant, can be built into the font name. Unfortunately,
     * the syntax varies depending on which font system is dominant. Thus, using fontname="times bold italic" will
     * produce a bold, slanted Times font using Pango, the usual main font library. Alternatively,
     * fontname="times:italic" will produce a slanted Times font from fontconfig, while fontname="times-bold" will
     * resolve to a bold Times using Quartz. You will need to ascertain which package is used by your Graphviz
     * system and refer to the relevant documentation.</p>
     * <p>If Graphviz is not built with a high-level font library, fontname will be considered the name of a Type 1 or
     * True Type font file. If you specify fontname=schlbk, the tool will look for a file named schlbk.ttf or
     * schlbk.pfa or schlbk.pfb in one of the directories specified by the fontpath attribute. The lookup does
     * support various aliases for the common fonts.</p>
     *
     * @param fontName The non-empty font name.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withFontName(final String fontName) {
        return (GraphAttributeList) addAttribute("fontname", fontName);
    }

    /**
     * Directory list used by libgd to search for bitmap fonts if Graphviz was not built with the
     * fontconfig library. If fontpath is not set, the environment variable DOTFONTPATH is checked.
     * If that is not set, GDFONTPATH is checked. If not set, libgd uses its compiled-in font path.
     * Note that fontpath is an attribute of the root graph.
     *
     * @param fontPath A non-empty font path.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withFontPath(final String fontPath) {
        return (GraphAttributeList) addAttribute("fontpath", fontPath);
    }

    /**
     * Font size, in points, used for text.
     *
     * @param fontSizeInPoints A font size in points, required to be positive.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withFontSize(final int fontSizeInPoints) {

        // Add the attribute.
        if (fontSizeInPoints > 0) {
            addAttribute("fontsize", "" + fontSizeInPoints);
        }

        // All Done.
        return this;
    }

    /**
     * If true, all xlabel attributes are placed, even if there is some overlap with nodes or other labels.
     *
     * @param forceLabels {@code true} to force outputting labels.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withForceLabels(final boolean forceLabels) {
        return (GraphAttributeList) addAttribute("forcelabels", "" + forceLabels);
    }

    /**
     * Specifies a list of directories in which to look for image files as specified by the image attribute or using
     * the IMG element in HTML-like labels. The string should be a list of (absolute or relative) pathnames, each
     * separated by a semicolon (for Windows) or a colon (all other OS). The first directory in which a file of the
     * given name is found will be used to load the image. If imagepath is not set, relative pathnames for the image
     * file will be interpreted with respect to the current working directory.
     *
     * @param imagePath A non-empty image path.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withImagePath(final String imagePath) {
        return (GraphAttributeList) addAttribute("imagepath", imagePath);
    }


    /**
     * Justification for cluster labels. If "r", the label is right-justified within bounding rectangle; if "l",
     * left-justified; else the label is centered. Note that a subgraph inherits attributes from its parent. Thus,
     * if the root graph sets labeljust to "l", the subgraph inherits this value.
     *
     * @param adjustment One of the values {@code r, l, c}.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withLabelAdjustment(final String adjustment) {
        return (GraphAttributeList) addAttribute("labeljust", adjustment);
    }

    /**
     * <p>Vertical placement of labels for nodes, root graphs and clusters. For graphs and clusters, only "t" and "b"
     * are allowed, corresponding to placement at the top and bottom, respectively. By default, root graph labels go
     * on the bottom and cluster labels go on the top. Note that a subgraph inherits attributes from its parent.
     * Thus, if the root graph sets labelloc to "b", the subgraph inherits this value.</p>
     * <p>For nodes, this attribute is used only when the height of the node is larger than the height of its label. If
     * labelloc is set to "t", "c", or "b", the label is aligned with the top, centered, or aligned with the bottom
     * of the node, respectively. In the default case, the label is vertically centered.</p>
     *
     * @param location One of the values {@code t, c, b}.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withLabelLocation(final String location) {
        return (GraphAttributeList) addAttribute("labelloc", location);
    }

    /**
     * If true, the graph is rendered in landscape mode. Synonymous with {@code rotate=90} or
     * {@code orientation=landscape}.
     *
     * @param isLandscape If true, the graph is rendered in landscape mode.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withLandscapeOrientation(final boolean isLandscape) {
        return (GraphAttributeList) addAttribute("landscape", "" + isLandscape);
    }

    /**
     * <p>List of strings separated by characters from the layersep attribute (by default, colons, tabs or spaces),
     * defining layer names and implicitly numbered 1,2,...</p>
     * <p>The typical form for the layerList property is therefore
     * <strong><code>[layerName1][layerSeparator][layerName2][layerSeparator]...</code></strong></p>
     *
     * @param layerList A String on the form
     *                  <strong><code>[layerName1][layerSeparator][layerName2][layerSeparator]...</code></strong>
     * @return This {@link GraphAttributeList}.
     * @see #withLayerListSeparator(String)
     */
    public GraphAttributeList withLayers(final String layerList) {
        return (GraphAttributeList) addAttribute("layers", layerList);
    }

    /**
     * Specifies the separator characters used to split the layers attribute (c.f. {@link #withLayers(String)}) into
     * a list of layer names.
     *
     * @param separator Non-empty separator character(s).
     * @return This {@link GraphAttributeList}.
     * @see #withLayers(String)
     */
    public GraphAttributeList withLayerSeparator(final String separator) {
        return (GraphAttributeList) addAttribute("layersep", separator);
    }

    /**
     * <p>Specifies a list of layers defined by the layers attribute (i.e. as given by the {@link #withLayers(String)}
     * method). It consists of a list of layer intervals separated by any collection of characters from the
     * layerlistsep (c.f. {@link #withLayerListSeparator(String)}) attribute. Each layer interval is specified
     * as either a layerId or a layerIdslayerId, where layerId = "all", a decimal integer or a layer name.
     * (An integer i corresponds to layer i, layers being numbered from 1.)
     * The string s consists of 1 or more separator characters specified by the layersep attribute.</p>
     * <p>Thus, assuming the default values for layersep and layerlistsep, if layers="a:b:c:d:e:f:g:h", the layerRange
     * string layers="a:b,d,f:all" would denote the layers a b d f g h".</p>
     *
     * @param layerRange Selects a list of layers to be emitted.
     * @return This {@link GraphAttributeList}.
     * @see #withLayerListSeparator(String)
     * @see #withLayers(String)
     */
    public GraphAttributeList withLayerSelect(final String layerRange) {
        return (GraphAttributeList) addAttribute("layerselect", layerRange);
    }

    /**
     * Specifies the separator characters used to split an attribute of type <code>layerRange</code>
     * into a list of ranges. (C.f. {@link #withLayerSelect(String)}).
     *
     * @param separator Non-empty separator character(s).
     * @return This {@link GraphAttributeList}.
     * @see #withLayerSelect(String) (String)
     */
    public GraphAttributeList withLayerListSeparator(final String separator) {
        return (GraphAttributeList) addAttribute("layerlistsep", separator);
    }

    /**
     * <p>Specifies the name of the layout algorithm to use, such as "dot" or "neato". Normally, graphs should be kept
     * independent of a type of layout. In some cases, however, it can be convenient to embed the type of layout
     * desired within the graph. For example, a graph containing position information from a layout might want to
     * record what the associated layout algorithm was.</p>
     * <p>This attribute takes precedence over the -K flag or the actual command name used.</p>
     *
     * @param layoutAlgorithm The name of the layout algorithm to use.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withLayout(final String layoutAlgorithm) {
        return (GraphAttributeList) addAttribute("layout", layoutAlgorithm);
    }

    /**
     * Height of graph or cluster label, in inches.
     *
     * @param heightInInches The height in inches.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withLabelHeight(final double heightInInches) {
        return (GraphAttributeList) addAttribute("lheight", BigDecimal.valueOf(heightInInches).toString());
    }

    /**
     * Label position, in points. The position indicates the center of the label.
     * At present, most device-independent units are either inches or points, which we take as 72 points per inch.
     *
     * @param positionInPoints The position in points.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withLabelPosition(final int positionInPoints) {
        return (GraphAttributeList) addAttribute("lp", "" + positionInPoints);
    }

    /**
     * Width of graph or cluster label, in inches.
     *
     * @param widthInInches The width, in inches.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withLabelWidth(final double widthInInches) {
        return (GraphAttributeList) addAttribute("lwidth", BigDecimal.valueOf(widthInInches).toString());
    }

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
     *
     * @param horizontalMarginInInches The horizontal margin, in inches.
     * @param verticalMarginInInches   The vertical margin, in inches.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withMargin(final double horizontalMarginInInches, final double verticalMarginInInches) {

        final String horizontalMargin = BigDecimal.valueOf(horizontalMarginInInches).toPlainString();
        final String verticalMargin = BigDecimal.valueOf(verticalMarginInInches).toPlainString();

        // Add the Margin.
        return (GraphAttributeList) addAttribute("margin", horizontalMargin + "," + verticalMargin);
    }

    /**
     * <p>Multiplicative scale factor used to alter the MinQuit (default = 8) and MaxIter (default = 24) parameters
     * used during crossing minimization. These correspond to the number of tries without improvement before
     * quitting and the maximum number of iterations in each pass.</p>
     *
     * @param limit The limit fot he multiplicative scale.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withMultiplicativeScaleLimit(final double limit) {
        return (GraphAttributeList) addAttribute("mclimit", "" + limit);
    }

    /**
     * Specifies the minimum separation between all nodes.
     *
     * @param minDistance The minimum distance.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withMinimumDistanceBetweenNodes(final double minDistance) {
        return (GraphAttributeList) addAttribute("mindist", "" + minDistance);
    }

    /**
     * In dot, this specifies the minimum space between two adjacent nodes in the same rank, in inches.
     *
     * @param minimumDistanceInInches The minimum space between two adjacent nodes in the same rank.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withMinimumDistanceBetweenSameRankAdjacentNodes(final double minimumDistanceInInches) {
        return (GraphAttributeList) addAttribute("nodesep", "" + minimumDistanceInInches);
    }

    /**
     * <p>By default, the justification of multi-line labels is done within the largest context that makes sense. Thus,
     * in the label of a polygonal node, a left-justified line will align with the left side of the node (shifted by
     * the prescribed margin). In record nodes, left-justified line will line up with the left side of the enclosing
     * column of fields. If nojustify is "true", multi-line labels will be justified in the context of itself. For
     * example, if the attribute is set, the first label line is long, and the second is shorter and left-justified,
     * the second will align with the left-most character in the first line, regardless of how large the node might
     * be.</p >
     *
     * @param engageNoJustify If {@code true}, no justification is applied.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withNoJustify(final boolean engageNoJustify) {
        return (GraphAttributeList) addAttribute("nojustify", "" + engageNoJustify);
    }

    /**
     * Used to set number of iterations in network simplex applications. nslimit is used in computing node x
     * coordinates, nslimit1 for ranking nodes. If defined, # iterations = nslimit(1) * # nodes; otherwise, #
     * iterations = MAXINT.
     *
     * @param nsLimit The NsLimit.
     * @return This {@link GraphAttributeList}.
     * @see #withNsLimit1(double)
     */
    public GraphAttributeList withNsLimit(final double nsLimit) {
        return (GraphAttributeList) addAttribute("nslimit", "" + nsLimit);
    }

    /**
     * Used to set number of iterations in network simplex applications. nslimit is used in computing node x
     * coordinates, nslimit1 for ranking nodes. If defined, # iterations = nslimit(1) * # nodes; otherwise, #
     * iterations = MAXINT.
     *
     * @param nsLimit The NsLimit.
     * @return This {@link GraphAttributeList}.
     * @see #withNsLimit(double)
     */
    public GraphAttributeList withNsLimit1(final double nsLimit) {
        return (GraphAttributeList) addAttribute("nslimit1", "" + nsLimit);
    }

    /**
     * If the value of the attribute is "out", then the outedges of a node, that is, edges with the node as its tail
     * node, must appear left-to-right in the same order in which they are defined in the input. If the value of the
     * attribute is "in", then the inedges of a node must appear left-to-right in the same order in which they are
     * defined in the input. If defined as a graph or subgraph attribute, the value is applied to all nodes in the
     * graph or subgraph. Note that the graph attribute takes precedence over the node attribute.
     *
     * @param isOut If true, use "out" as the value of the attribute set. (Otherwise "in").
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withOrdering(final boolean isOut) {
        return (GraphAttributeList) addAttribute("ordering", (isOut ? "out" : "in"));
    }

    /**
     * If "[lL]*", set graph orientation to landscape. Used only if rotate is not defined.
     *
     * @param isLandscape if {@code true}, use landscape orientation.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withOrientation(final boolean isLandscape) {
        if (isLandscape) {
            addAttribute("orientation", "L");
        }

        // All Done.
        return this;
    }

    /**
     * The values permitted for specifying output ordering.
     *
     * @see #withOutputOrder(OutputOrder)
     */
    public enum OutputOrder {

        /**
         * The default "breadthfirst" is the simplest, but when the graph layout does not avoid
         * edge-node overlap, this mode will sometimes have edges drawn over nodes and sometimes on top of nodes.
         */
        breadthfirst,

        /**
         * If the mode "nodesfirst" is chosen, all nodes are drawn first, followed by the edges. This guarantees an
         * edge-node overlap will not be mistaken for an edge ending at a node.
         */
        nodesfirst,

        /**
         * On the other hand, usually for aesthetic reasons, it may be desirable that all edges appear beneath
         * nodes, even if the resulting drawing is ambiguous. This can be achieved by choosing "edgesfirst".
         */
        edgesfirst
    }

    /**
     * "breadthfirst","nodesfirst","edgesfirst" These specify the order in which nodes and edges are drawn in
     * concrete output. The default "breadthfirst" is the simplest, but when the graph layout does not avoid
     * edge-node overlap, this mode will sometimes have edges drawn over nodes and sometimes on top of nodes. If the
     * mode "nodesfirst" is chosen, all nodes are drawn first, followed by the edges. This guarantees an edge-node
     * overlap will not be mistaken for an edge ending at a node. On the other hand, usually for aesthetic reasons,
     * it may be desirable that all edges appear beneath nodes, even if the resulting drawing is ambiguous. This can
     * be achieved by choosing "edgesfirst".
     *
     * @param outputOrder A non-null specification for OutputOrdering.
     * @return This {@link GraphAttributeList}.
     * @see OutputOrder
     */
    public GraphAttributeList withOutputOrder(final OutputOrder outputOrder) {

        final OutputOrder effectiveOrder = outputOrder == null ? OutputOrder.breadthfirst : outputOrder;
        return (GraphAttributeList) addAttribute("outputorder", effectiveOrder.toString());
    }

    /**
     * This is true if the value of pack is "true" (case-insensitive) or a non-negative integer. If true, each
     * connected component of the graph is laid out separately, and then the graphs are packed together. If pack has
     * an integral value, this is used as the size, in points, of a margin around each part; otherwise, a default
     * margin of 8 is used. If pack is interpreted as false, the entire graph is laid out together. The granularity
     * and method of packing is influenced by the packmode attribute.
     * For layouts which always do packing, such a twopi, the pack attribute is just used to set the margin.
     *
     * @param pack If true, use pack setting.
     * @return This {@link GraphAttributeList}.
     * @see #withPackMode(String)
     */
    public GraphAttributeList withPack(final boolean pack) {
        return (GraphAttributeList) addAttribute("pack", "" + pack);
    }

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
     *
     * @param packMode One of the permitted packMode values, as documented above. (i.e. <strong><code>"node" ,
     *                 "clust" , "graph" , "array(_flags)?(%d)?"</code></strong>´)
     * @return This {@link GraphAttributeList}.
     * @see #withPack(boolean)
     */
    public GraphAttributeList withPackMode(final String packMode) {
        return (GraphAttributeList) addAttribute("packmode", packMode);
    }

    /**
     * <p>The pad attribute specifies how much, in inches, to extend the drawing area around the minimal area needed to
     * draw the graph. This area is part of the drawing and will be filled with the background color, if
     * appropriate.</p>
     * <p>Normally, a small pad is used for aesthetic reasons, especially when a background color is used, to avoid
     * having nodes and edges abutting the boundary of the drawn region.</p>
     *
     * @param xPad The padding in x-direction (width).
     * @param yPad The padding in y-direction (height).
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withPad(final double xPad, final double yPad) {
        return (GraphAttributeList) addAttribute("pad", "" + xPad + "," + yPad);
    }

    /**
     * If quantum > 0.0, node label dimensions will be rounded to integral multiples of the quantum.
     *
     * @param quantum The quantum to set.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withQuantum(final double quantum) {
        return (GraphAttributeList) addAttribute("quantum", "" + quantum);
    }

    /**
     * Enum specifying permitted values for defining rank direction.
     */
    public enum RankDirection {

        TopToBottom("TB"),

        LeftToRight("LR"),

        BottomToTop("BT"),

        RightToLeft("RL");

        // Internal state
        private String dotPropertyValue;

        RankDirection(final String dotPropertyValue) {
            this.dotPropertyValue = dotPropertyValue;
        }

        /**
         * @return The DOT property value.
         */
        @Override
        public String toString() {
            return super.toString();
        }
    }

    /**
     * "TB", "LR", "BT", "RL", corresponding to directed graphs drawn from top to bottom, from left to right, from
     * bottom to top, and from right to left, respectively.
     *
     * @param rankDirection A non-null {@link RankDirection} indicating the direction to use.
     * @return This {@link GraphAttributeList}.
     */
    public GraphAttributeList withRankDir(final RankDirection rankDirection) {
        return (GraphAttributeList) addAttribute("rankdir", rankDirection.toString());
    }


}
