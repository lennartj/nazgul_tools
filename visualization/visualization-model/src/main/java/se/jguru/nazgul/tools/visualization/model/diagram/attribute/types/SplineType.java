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
import java.io.Serializable;

/**
 * <p>An enumeration containing the possible values for the spline type definition as
 * specified by the <a href="http://www.graphviz.org/content/attrs#dsplines">Dot spline definition</a>.
 * From the Dot documentation:</p>
 *
 * <p>Controls how, and if, edges are represented. If true, edges are drawn as splines routed around nodes; if false,
 * edges are drawn as line segments. If set to none or "", no edges are drawn at all.
 * <ul>
 * <li>(1 March 2007) The values line and spline can be used as synonyms for false and true, respectively. In
 * addition, the value polyline specifies that edges should be drawn as polylines.</li>
 * <li>(28 Sep 2010) The value ortho specifies edges should be routed as polylines of axis-aligned segments.
 * Currently, the routing does not handle ports or, in dot, edge labels.</li>
 * <li>(25 Sep 2012) The value curved specifies edges should be drawn as curved arcs.</li>
 * <li>By default, the attribute is unset. How this is interpreted depends on the layout. For dot, the default is to
 * draw edges as splines. For all other layouts, the default is to draw edges as line segments. Note that for
 * these latter layouts, if splines="true", this requires non-overlapping nodes (cf. overlap). If fdp is used
 * for layout and splines="compound", then the edges are drawn to avoid clusters as well as nodes.</li>
 * </ul>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE)
@XmlEnum(String.class)
public enum SplineType implements Serializable, TokenValueHolder {

    /**
     * No spline type at all.
     */
    NONE("none"),

    /**
     * Splines are drawn using lines.
     */
    LINE("line"),

    /**
     * Splines are drawn using several straight lines.
     */
    POLYLINE("polyline"),

    /**
     * Splines are drawn using curves.
     */
    CURVED("curved"),

    /**
     * Splines are drawn using orthogonal lines.
     */
    ORTHOGONAL("ortho"),

    /**
     * Splines are drawn using <a href="https://en.wikipedia.org/wiki/Spline_(mathematics)">spline algorithms</a>.
     */
    SPLINES("splines");

    // Internal state
    private String dotConfigValue;

    SplineType(final String dotConfigValue) {
        this.dotConfigValue = dotConfigValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTokenValue() {
        return dotConfigValue;
    }
}
