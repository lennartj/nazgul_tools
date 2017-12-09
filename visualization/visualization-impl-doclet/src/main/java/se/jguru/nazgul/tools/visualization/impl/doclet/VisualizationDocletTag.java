/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-impl-doclet
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

package se.jguru.nazgul.tools.visualization.impl.doclet;

/**
 * Enumeration containing definitions of JavaDoc Tags custom to the {@link VisualizationDoclet}.
 * All tags have the prefix {@code nazgul_vis}, to differentiate against other Doclet implementations.
 * Hence, the in-code JavaDoc tags typically have forms similar to <strong><code>nazgul_vis.uses</code></strong>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public enum VisualizationDocletTag {

    /**
     * nazgul_vis.stereotype (name)
     */
    STEREOTYPE("<name>"),

    /**
     * nazgul_vis.uses (FQCN) [(sourceLabel) (targetLabel) [(edgeLabel)]]
     */
    USES("<FQCN> [<sourceLabel> <targetLabel> [<edgeLabel>]]"),

    /**
     * nazgul_vis.has (FQCN) [oneway] [(sourceLabel) (targetLabel) [(edgeLabel)]]
     */
    HAS("<FQCN> [oneway] [<sourceLabel> <targetLabel> [<edgeLabel>]]"),

    /**
     * nazgul_vis.owns (FQCN) [(sourceLabel) (targetLabel) [(edgeLabel)]]
     */
    OWNS("<FQCN> [<sourceLabel> <targetLabel> [<edgeLabel>]]"),

    /**
     * nazgul_vis.composedOf (FQCN) [(sourceLabel) (targetLabel) [(edgeLabel)]]
     */
    COMPOSED_OF("composedOf", "<FQCN> [<sourceLabel> <targetLabel> [<edgeLabel>]]"),

    /**
     * nazgul_vis.landmark
     */
    LANDMARK(""),

    /**
     * nazgul_vis.hidden
     */
    HIDDEN(""),

    /**
     * nazgul_vis.exclude (regex)
     */
    EXCLUDE("<regex>"),

    /**
     * nazgul_vis.excludeSubtypes
     */
    EXCLUDE_SUBTYPES("excludeSubtypes", ""),

    /**
     * nazgul_vis.inherit
     */
    INHERIT(""),

    /**
     * nazgul_vis.category (categoryname)
     */
    CATEGORY("<categoryName>");

    // Internal state
    private String nonstandardTagSuffix;
    private String arguments;

    VisualizationDocletTag(final String arguments) {
        this(null, arguments);
    }

    VisualizationDocletTag(final String nonstandardTagSuffix, final String arguments) {
        this.nonstandardTagSuffix = nonstandardTagSuffix;
        this.arguments = arguments;
    }

    /**
     * The prefix of all VisualizationDoclet javadoc tags.
     */
    public static final String TAG_PREFIX = "@nazgul_vis.";

    /**
     * Retrieves the full javadoc tag name, including prefix.
     *
     * @return The full VisualizationDoclet (JavaDoc) tag name.
     */
    @Override
    public String toString() {
        return TAG_PREFIX + (nonstandardTagSuffix == null ? name().toLowerCase() : nonstandardTagSuffix);
    }

    /**
     * Retrieves a (brief) usage description of this {@link VisualizationDocletTag}.
     *
     * @return a (brief) usage description of this {@link VisualizationDocletTag}.
     */
    public String getTagUsage() {
        return toString() + " " + arguments;
    }
}
