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
package se.jguru.nazgul.tools.visualization.model.diagram;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of a Comment containing a List of string lines.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"commentLines"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Comment implements Serializable {

    /**
     * OS-neutral newline String/char.
     */
    public static final String NEWLINE = System.getProperty("line.separator");

    /**
     * The list of strings/lines within this Comment. May be empty but not null.
     */
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
}
