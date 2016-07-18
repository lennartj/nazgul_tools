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
package se.jguru.nazgul.tools.visualization.api.diagram;

import se.jguru.nazgul.tools.visualization.api.StringRenderable;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a Comment using either C-style single-line '//' or multiple-line delimiters,
 * depending on how many lines were added to this Comment.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class Comment implements StringRenderable {

    // Internal state
    private List<String> commentLines;

    /**
     * Creates a new Comment wrapping the supplied lines
     *
     * @param lines One or more comment lines.
     */
    public Comment(final String ... lines) {

        // Create internal state
        commentLines = new ArrayList<>();

        // Add all supplied lines.
        add(lines);
    }

    /**
     * If non-null, adds one or more lines to this comment.
     * Also, {@link #NEWLINE} strings are removed from each line.
     *
     * @param lines one or more lines to add to this comment.
     */
    public final void add(final String ... lines) {

        if (lines != null) {
            for(String current : lines) {
                commentLines.add(current.replace(NEWLINE, ""));
            }
        }
    }

    /**
     * Renders this Comment as a C++ style single line (in case only 1 line has been added)
     * or C-style multiple line (in case more than 1 line has been added).
     *
     * @return A Graphviz/Dot compliant comment line.
     */
    @Override
    public String render() {

        // Check sanity
        final StringBuilder builder = new StringBuilder("");
        if (commentLines.size() == 1) {

            // Use a '//' single-line comment
            builder.append("// ").append(commentLines.get(0)).append(NEWLINE);

        } else if (commentLines.size() > 1) {

            // Use a '/*' ... '*/' multi-line comment
            builder.append("/* ").append(NEWLINE);
            for (String current : commentLines) {
                builder.append(" * ").append(current).append(NEWLINE);
            }
            builder.append(" */").append(NEWLINE);
        }

        // All Done.
        return builder.toString();
    }
}
