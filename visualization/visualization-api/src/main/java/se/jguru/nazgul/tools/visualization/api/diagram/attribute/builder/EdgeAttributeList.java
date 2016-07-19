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
 * Attributes relevant for {@link se.jguru.nazgul.tools.visualization.api.diagram.statement.Edge} objects.
 * Property descriptions copied from <a href="http://www.graphviz.org/content/attrs">The Graphviz Website's
 * description of attributes.</a>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class EdgeAttributeList extends AbstractDelegatingAttributeList {

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
}
