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
package se.jguru.nazgul.tools.visualization.api.jaxb;

import se.jguru.nazgul.tools.visualization.api.diagram.Comment;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * <p>JAXB (un-)marshallable type for transporting a Class and its corresponding Comment.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlRootElement(namespace = Graph.NAMESPACE)
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"commentLines"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassKeyedComment<T extends Statement> extends AbstractClassKeyedTransport<T> {

    // Internal state

    @XmlElementWrapper
    @XmlElement(name = "line")
    private List<String> commentLines;

    /**
     * Default constructor, reserved for framework use.
     */
    public ClassKeyedComment() {
    }

    /**
     * Compound constructor creating a {@link ClassKeyedComment} wrapping the supplied objects.
     *
     * @param aClass  A non-null Class
     * @param comment A non-null {@link Comment}
     */
    public ClassKeyedComment(final Class<T> aClass, final Comment comment) {

        super(aClass);

        this.commentLines = comment.getCommentLines();
    }

    /**
     * Re-creates the Comment from the wrapped {@link #commentLines}.
     *
     * @return the re-created Comment, containing all comment lines received from the original Comment.
     */
    public Comment getComment() {

        final String[] lines = commentLines == null || commentLines.isEmpty()
                ? new String[0]
                : commentLines.toArray(new String[commentLines.size()]);

        return new Comment(lines);
    }
}
