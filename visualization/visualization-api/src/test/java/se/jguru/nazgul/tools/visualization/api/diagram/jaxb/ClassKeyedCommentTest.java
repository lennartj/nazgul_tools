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
package se.jguru.nazgul.tools.visualization.api.diagram.jaxb;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;
import se.jguru.nazgul.tools.visualization.api.diagram.Comment;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement;
import se.jguru.nazgul.tools.visualization.api.jaxb.ClassKeyedComment;

import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ClassKeyedCommentTest {

    @Rule public PlainJaxbContextRule jaxb = new PlainJaxbContextRule();
    @Rule public XmlValidationRule xmlValidation = new XmlValidationRule();

    // Shared state
    private Class<? extends Statement> theType = Node.class;
    private Comment aComment = new Comment("This", "is", "a", "multi-line", "comment.");
    private ClassKeyedComment<? extends Statement> unitUnderTest = new ClassKeyedComment<>(theType, aComment);
    private String xmlForm = PlainJaxbContextRule.readFully("testdata/jaxb/classKeyedComment.xml");

    @Test
    public void validateMarshalling() {

        // Assemble
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        jaxb.add(ClassKeyedComment.class);

        // Act
        final String result = jaxb.marshal(classLoader, false, null, unitUnderTest);
        // System.out.println("Got: " + result);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertTrue("Non-identical: " + xmlValidation.getDiffForIdenticalStructures(xmlForm, result),
                xmlValidation.identical(xmlForm, result));

    }

    @Test
    public void validateUnmarshalling() {

        // Assemble
        final String resourcePath = "testdata/jaxb/classKeyedComment.xml";
        final String toUnmarshal = PlainJaxbContextRule.readFully(resourcePath);
        jaxb.add(ClassKeyedComment.class);

        // Act
        final Object resurrected = jaxb.unmarshal(Thread.currentThread().getContextClassLoader(), false, toUnmarshal);

        // Assert
        Assert.assertNotNull(resurrected);

        final ClassKeyedComment<? extends Statement> unmarshalled =
                (ClassKeyedComment<? extends Statement>) resurrected;
        Assert.assertEquals(unmarshalled.getResolvedClass(), theType);

        final List<String> expectedLines = aComment.getCommentLines();
        final List<String> receivedLines = unmarshalled.getComment().getCommentLines();
        Assert.assertEquals(expectedLines.size(), receivedLines.size());

        for(int i = 0; i < expectedLines.size(); i++) {
            Assert.assertEquals(expectedLines.get(i), receivedLines.get(i));
        }
    }
}
