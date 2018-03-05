/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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


package se.jguru.nazgul.tools.visualization.model.diagram;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.AbstractEntityTest;
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommentTest extends AbstractEntityTest {

    @Before
    public void setupSharedState() {
        jaxb.add(GenericRoot.class, Comment.class);
    }

    @Test
    public void validateIgnoringAddingNulls() {

        // Assemble
        final String aComment = "This is a Standard Comment.";

        // Act
        final Comment singleLineComment = new Comment(aComment);
        singleLineComment.add(null);
        final String marshalled = marshalAsXml(singleLineComment);

        // Assert
        // System.out.println("Got: " + marshalled);
        validateIdenticalXml("testdata/singleLineComment.xml", marshalled);
    }

    @Test
    public void validateCreatingSingleLineComment() {

        // Assemble
        final String commentWithNewline = "Here is a " + Comment.NEWLINE + " (newline).";
        final Comment strippedNewlineComment = new Comment(commentWithNewline);
        final GenericRoot rootObject = new GenericRoot(strippedNewlineComment);

        // Act
        final String marshalledXml = marshalAsXml(rootObject);
        final String marshalledJson = marshalAsJson(rootObject);

        // System.out.println("Got: " + marshalledXml);
        // System.out.println("Got: " + marshalledJson);

        // Assert
        validateIdenticalXml("testdata/strippedNewlineSingleLineComment.xml", marshalledXml);
        validateIdenticalJson("testdata/strippedNewlineSingleLineComment.json", marshalledJson);
    }


    @Test
    public void validateCreatingMultilineComment() {

        // Assemble
        final Comment unitUnderTest = new Comment("This", "is", "a", "multi-line", "comment.");

        // Act
        final String marshalledXML = marshalAsXml(new GenericRoot(unitUnderTest));
        final String marshalledJSON = marshalAsJson(new GenericRoot(unitUnderTest));

        // System.out.println("Got: " + marshalledXML);
        // System.out.println("Got: " + marshalledJSON);

        // Assert
        validateIdenticalXml("testdata/multilineComment.xml", marshalledXML);
        validateIdenticalJson("testdata/multilineComment.json", marshalledJSON);
    }

    @Test
    public void validateUnmarshallingComment() {

        // Assemble
        final Comment expected = new Comment("This", "is", "a", "multi-line", "comment.");

        // Act
        final GenericRoot unmarshalledFromXml = unmarshalFromXml(GenericRoot.class, "testdata/multilineComment.xml");
        final GenericRoot unmarshalledFromJSon = unmarshalFromJson(GenericRoot.class, "testdata/multilineComment.json");

        // Assert
        Assert.assertArrayEquals(getCommentLines(expected), getCommentLines(getCommentFrom(unmarshalledFromXml)));
        Assert.assertArrayEquals(getCommentLines(expected), getCommentLines(getCommentFrom(unmarshalledFromJSon)));
    }

    //
    // Private helpers
    //

    private Comment getCommentFrom(final GenericRoot genericRoot) {
        return (Comment) genericRoot.getItems().get(0);
    }

    private String[] getCommentLines(final Comment aComment) {

        final List<String> commentLines = aComment.getCommentLines();
        return commentLines.toArray(new String[commentLines.size()]);
    }
}
