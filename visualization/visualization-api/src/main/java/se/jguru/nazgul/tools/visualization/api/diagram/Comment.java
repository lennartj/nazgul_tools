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

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderable;
import se.jguru.nazgul.tools.visualization.api.Renderable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates a Comment using either C-style single-line '//' or multiple-line delimiters,
 * depending on how many lines were added to this Comment.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Renderable.NAMESPACE, propOrder = {"commentLines"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Comment extends AbstractStringRenderable {

    // Internal state
    @XmlElementWrapper
    @XmlElement(name = "line")
    private List<String> commentLines;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Comment() {
        // Do nothing.
    }

    /**
     * Creates a new Comment wrapping the supplied lines
     *
     * @param lines One or more comment lines.
     */
    public Comment(final String... lines) {

        // Delegate
        super(0);

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
    public final void add(final String... lines) {

        if (lines != null) {
            for (String current : lines) {
                commentLines.add(current.replace(NEWLINE, ""));
            }
        }
    }

    /**
     * Retrieves an unmodifiable List containing the wrapped comment lines.
     *
     * @return an <strong>unmodifiable</strong> List containing the wrapped comment lines.
     */
    public List<String> getCommentLines() {
        return Collections.unmodifiableList(commentLines);
    }

    /**
     * Renders this Comment as a C++ style single line (in case only 1 line has been added)
     * or C-style multiple line (in case more than 1 line has been added).
     *
     * @return A Graphviz/Dot compliant comment line.
     */
    @Override
    public String doRender() {

        // Check sanity
        final StringBuilder builder = new StringBuilder("");
        if (commentLines.size() == 1) {

            // Use a '//' single-line comment
            builder.append("// ").append(commentLines.get(0));

        } else if (commentLines.size() > 1) {

            // Use a '/*' ... '*/' multi-line comment
            builder.append("/* ").append(NEWLINE);
            for (String current : commentLines) {
                builder.append(getIndentation()).append(" * ").append(current).append(NEWLINE);
            }
            builder.append(getIndentation()).append(" */");
        }

        // All Done.
        return builder.toString();
    }
}
