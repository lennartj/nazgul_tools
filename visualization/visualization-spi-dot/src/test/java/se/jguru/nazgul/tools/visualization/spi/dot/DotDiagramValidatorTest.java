/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-spi-dot
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

package se.jguru.nazgul.tools.visualization.spi.dot;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DotDiagramValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullStream() {

        // Act & Assert
        DotDiagramValidator.validate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLexerCreationExceptionOnNullStream() {

        // Act & Assert
        DotDiagramValidator.createLexer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateParserCreationExceptionOnNullStream() {

        // Act & Assert
        DotDiagramValidator.createParser(null);
    }

    @Test
    public void validateOkDotFile() {

        // Assemble
        final InputStream unixesStream = getClass().getClassLoader().getResourceAsStream("testdata/unixes.dot");

        // Act
        final DotParser.GraphContext graphContext = DotDiagramValidator.validate(unixesStream);

        // Assert
        Assert.assertNotNull(graphContext);

        final TerminalNode digraph = graphContext.DIGRAPH();
        Assert.assertNotNull(digraph);

        final TerminalNode graph = graphContext.GRAPH();
        Assert.assertNull(graph);

        final TerminalNode strict = graphContext.STRICT();
        Assert.assertNull(strict);
    }

    @Test
    public void validateExceptionOnSyntacticError() {

        // Assemble
        final InputStream malformedDocumentStream = getClass().getClassLoader()
                .getResourceAsStream("testdata/malformed.dot");

        try {
            DotDiagramValidator.validate(malformedDocumentStream);
            Assert.fail("Expected an Exception parsing a malformed Document.");

        } catch (DotDiagramSyntaxException e) {

            // We should only get 1 error here.
            final List<DotDiagramSyntaxErrorData> errors = e.getSyntaxErrorData();
            Assert.assertEquals(1, errors.size());

            // Check some error state.
            final DotDiagramSyntaxErrorData errorData = errors.get(0);
            Assert.assertEquals(1, errorData.getCharPositionInLine());
            Assert.assertEquals(7, errorData.getLine());
            Assert.assertNotNull(errorData.toString());
        }
    }
}
