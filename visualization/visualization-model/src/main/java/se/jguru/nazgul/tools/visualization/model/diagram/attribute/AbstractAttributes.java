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
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Objects;
import java.util.SortedMap;

/**
 * Abstract implementation containing common utilities for attribute entities.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"backgroundColor", "comment", "colorScheme",
        "fontName", "fontSizeInPoints", "label", "noMultiLineLabelJustification", "printGuideBoxesAtStart",
        "targetURL", "textColor"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractAttributes implements Serializable {

    /**
     * <p>When attached to the root graph, this color is used as the background for entire canvas.
     * When a cluster attribute, it is used as the initial background for the cluster.
     * If a cluster has a filled style, the cluster's fillcolor will overlay the background color.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dbgcolor">bgcolor</i></a>.</p>
     */
    @XmlAttribute
    @DotPropertyName(name = "bgcolor")
    public StandardCssColor backgroundColor;

    /**
     * <p>Color used for text.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dfontcolor">fontcolor</i></a>.</p>
     */
    @XmlAttribute
    @DotPropertyName(name = "fontcolor")
    public StandardCssColor textColor;

    /**
     * Comments are inserted into output. Device-dependent, and not corresponding to a DOT property.
     */
    @XmlElement
    public String comment;

    /**
     * Font size, in points (i.e. 72 points per inch), used for text. Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dfontsize">fontsize</i></a>. (C.f.
     * <a href="https://en.wikipedia.org/wiki/Point_(typography)">Wikipedia's Definition of Point</a>
     */
    @XmlAttribute
    public Integer fontSizeInPoints;

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
     * Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dfontname">fontname</i></a>.
     */
    @XmlElement
    public String fontName;

    /**
     * <p>Text label attached to objects. If a node's shape is record, then the label can have a special format
     * which describes the record layout. Note that a node's default label is "\N", so the node's name or ID becomes
     * its label. Technically, a node's name can be an HTML string but this will not mean that the node's label will
     * be interpreted as an HTML-like label. This is because the node's actual label is an ordinary string, which
     * will be replaced by the raw bytes stored in the node's name. To get an HTML-like label, the label attribute
     * value itself must be an HTML string.</p>
     */
    @XmlElement
    public String label;

    /**
     * <p>By default, the justification of multi-line labels is done within the largest context that makes sense. Thus,
     * in the label of a polygonal node, a left-justified line will align with the left side of the node (shifted by
     * the prescribed margin). In record nodes, left-justified line will line up with the left side of the enclosing
     * column of fields. If nojustify is "true", multi-line labels will be justified in the context of itself. For
     * example, if the attribute is set, the first label line is long, and the second is shorter and left-justified,
     * the second will align with the left-most character in the first line, regardless of how large the node might
     * be.</p >
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dnojustify">nojustify</i></a>.</p>
     */
    @XmlAttribute
    public Boolean noMultiLineLabelJustification;

    /**
     * <p>Print guide boxes in PostScript at the beginning of route splines if true (translated to the dot
     * parameter 1), or at the end if false (translated to the dot parameter value 2).</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dshowboxes">showboxes</i></a>.</p>
     */
    @XmlAttribute
    public Boolean printGuideBoxesAtStart;

    /**
     * This attribute specifies a color scheme namespace. If defined, it specifies the context for interpreting
     * color names. In particular, if a color value has form "xxx" or "//xxx", then the color xxx will be evaluated
     * according to the current color scheme. If no color scheme is set, the standard X11 naming is used. For
     * example, if colorscheme=bugn9, then color=7 is interpreted as "/bugn9/7"
     */
    @XmlElement
    public String colorScheme;

    /**
     * <p>If the object has a URL, this attribute determines which window of the browser is used for the URL.</p>
     * <p>Corresponds to DOT property
     * <a href="http://www.graphviz.org/content/attrs#dtarget">target</i></a>.</p>
     */
    @XmlElement
    public String targetURL;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {

        // Fail fast
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractAttributes)) {
            return false;
        }

        // Delegate to internal state
        final AbstractAttributes that = (AbstractAttributes) o;
        return backgroundColor == that.backgroundColor
                && textColor == that.textColor
                && Objects.equals(comment, that.comment)
                && Objects.equals(fontSizeInPoints, that.fontSizeInPoints)
                && Objects.equals(fontName, that.fontName)
                && Objects.equals(label, that.label)
                && Objects.equals(noMultiLineLabelJustification, that.noMultiLineLabelJustification)
                && Objects.equals(printGuideBoxesAtStart, that.printGuideBoxesAtStart)
                && Objects.equals(colorScheme, that.colorScheme)
                && Objects.equals(targetURL, that.targetURL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(backgroundColor,
                textColor,
                comment,
                fontSizeInPoints,
                fontName,
                label,
                noMultiLineLabelJustification,
                printGuideBoxesAtStart,
                colorScheme,
                targetURL);
    }
}
