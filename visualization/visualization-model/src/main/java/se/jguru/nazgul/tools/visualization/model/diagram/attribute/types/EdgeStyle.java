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

package se.jguru.nazgul.tools.visualization.model.diagram.attribute.types;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Enumeration of basic style settings for Edges.</p>
 * <p><strong>NOTE:</strong>The styles tapered, striped and wedged are only available in release 2.30 and later.</p>
 * <p>At present, the recognized style names are "dashed", "dotted", "solid", "invis" and "bold" for nodes and edges,
 * "tapered" for edges only, and "filled", "striped", "wedged", "diagonals" and "rounded" for nodes only. The styles
 * "filled", "striped" and "rounded" are recognized for clusters. The style "radial" is recognized for nodes,
 * clusters and graphs, and indicates a radial-style gradient fill if applicable.</p>
 * <p>The style "striped" causes the fill to be done as a set of vertical stripes. The colors are specified via a
 * colorList, the colors drawn from left to right in list order. Optional color weights can be specified to indicate
 * the proportional widths of the bars. If the sum of the weights is less than 1, the remainder is divided evenly
 * among the colors with no weight. Note: The style "striped" is only supported with clusters and
 * rectangularly-shaped nodes.</p>
 * <p>The style "wedged" causes the fill to be done as a set of wedges. The colors are specified via a colorList, with
 * the colors drawn counter-clockwise starting at angle 0. Optional color weights are interpreted analogously to the
 * striped case described above. Note: The style "wedged" is allowed only for elliptically-shaped nodes.</p>
 * <p>The following tables illustrate some of the effects of the style settings. Examples of tapered line styles are
 * given below. Examples of linear and radial gradient fill can be seen under colorList.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE)
@XmlEnum(String.class)
public enum EdgeStyle implements TokenValueHolder {

    SOLID,

    DASHED,

    DOTTED,

    BOLD;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTokenValue() {
        return name().toLowerCase();
    }
}
