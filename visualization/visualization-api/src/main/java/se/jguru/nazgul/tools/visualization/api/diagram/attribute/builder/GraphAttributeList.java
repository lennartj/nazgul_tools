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
     *
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
}
