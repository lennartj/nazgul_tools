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

import org.junit.Assert;
import org.junit.Test;

import static se.jguru.nazgul.tools.visualization.api.StringRenderable.NEWLINE;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommentTest {

    @Test
    public void validateCreatingSingleLineComment() {

        // Assemble
        final String standardComment = "This is a Standard Comment.";
        final String commentWithNewline = "Here is a " + NEWLINE + " (newline).";

        // Act
        final Comment singleLineComment = new Comment(standardComment);
        final Comment strippedNewlineComment = new Comment(commentWithNewline);

        // Assert
        Assert.assertEquals("// " + standardComment + NEWLINE, singleLineComment.render());
        Assert.assertEquals("// Here is a  (newline)." + NEWLINE, strippedNewlineComment.render());
    }

    @Test
    public void validateCreatingMultilineComment() {

        // Assemble
        final String expected = "/* " + NEWLINE
                + " * This" + NEWLINE
                + " * is" + NEWLINE
                + " * a" + NEWLINE
                + " * multi-line" + NEWLINE
                + " * comment." + NEWLINE
                + " */" + NEWLINE;
        final Comment unitUnderTest = new Comment("This", "is", "a", "multi-line", "comment.");

        // Act
        final String result = unitUnderTest.render();

        // Assert
        Assert.assertEquals(expected, result);
    }
}
