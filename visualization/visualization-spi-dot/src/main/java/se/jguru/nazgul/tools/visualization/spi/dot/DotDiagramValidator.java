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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotLexer;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Validation implementation for Graphviz/Dot files, complying to the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public final class DotDiagramValidator {

    /*
     * Hide constructor for utility classes.
     */
    private DotDiagramValidator() {
        // Do nothing
    }

    /**
     * Validates that the supplied InputStream contains well-formed Graph data, complying to the
     * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>. If the
     *
     * @param dotDiagramData A non-null InputStream containing a Graph or DiGraph definition.
     * @return A {@link se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser.GraphContext} containing the
     * parsed Graph from the parsed DOT data.
     * @throws DotDiagramSyntaxException if the supplied InputStream contained syntax errors.
     */
    public static DotParser.GraphContext validate(final InputStream dotDiagramData) throws DotDiagramSyntaxException {

        // Check sanity
        if (dotDiagramData == null) {
            throw new IllegalArgumentException("Cannot handle null 'dotDiagramData' argument.");
        }

        // Create the parser
        final DotLexer lexer = createLexer(dotDiagramData);
        final DotParser parser = createParser(lexer);

        // Ensure we can harvest all syntax errors.
        final DotSyntaxErrorListener errorListener = new DotSyntaxErrorListener();
        parser.addErrorListener(errorListener);

        final DotParser.GraphContext toReturn = parser.graph();

        // Check sanity
        if (errorListener.isErrorState()) {
            throw new DotDiagramSyntaxException(errorListener.getErrorDataList());
        }

        // All Done.
        return toReturn;
    }

    /**
     * Factory method creating a DotLexer from the supplied InputStream, assumed to contain Graphviz/DOT diagram data.
     *
     * @param dotDiagramData The InputStream containing Graphviz/DOT data, complying
     *                       with <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
     * @return A {@link DotLexer} using the supplied InputStream as source of tokens.
     */
    public static DotLexer createLexer(final InputStream dotDiagramData) {

        // Check sanity
        if (dotDiagramData == null) {
            throw new IllegalArgumentException("Cannot handle null 'dotDiagramData' argument.");
        }

        try {

            // All Done.
            return new DotLexer(new ANTLRInputStream(dotDiagramData));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not create DotLexer", e);
        }
    }

    /**
     * Creates a {@link DotParser} reading data from the supplied {@link DotLexer}.
     *
     * @param lexer A non-null {@link DotLexer} instance holding all Graphviz/DOT diagram data.
     * @return a {@link DotParser} hooked up with a {@link org.antlr.v4.runtime.TokenStream} to the
     * supplied {@link DotLexer}.
     */
    public static DotParser createParser(final DotLexer lexer) {

        // Check sanity
        if (lexer == null) {
            throw new IllegalArgumentException("Cannot handle null 'lexer' argument.");
        }

        // All Done.
        return new DotParser(new CommonTokenStream(lexer));
    }
}
