/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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


package se.jguru.nazgul.tools.visualization.api.diagram.dot;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.dot.CommentRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.Comment;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommentRendererTest extends AbstractRendererTest {

    @Test
    public void validateRenderingSingleLineComment() {

        // Assemble
        final String singleLineComment = "This is a single-line comment.";
        final String expected = renderConfiguration.getNewline() + "// " + singleLineComment
                + renderConfiguration.getNewline();
        final Comment comment = new Comment(singleLineComment);

        // Act
        final String result = new CommentRenderer().render(renderConfiguration, comment);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void validateRenderingMultipleLineComment() {

        // Assemble
        final String expected = renderConfiguration.getNewline()
                + "/* " + renderConfiguration.getNewline()
                + " * Several" + renderConfiguration.getNewline()
                + " * comment" + renderConfiguration.getNewline()
                + " * lines" + renderConfiguration.getNewline()
                + " */" + renderConfiguration.getNewline();

        final Comment comment = new Comment("Several", "comment", "lines");

        // Act
        final String result = new CommentRenderer().render(renderConfiguration, comment);

        // Assert
        Assert.assertEquals(expected, result);
    }
}
