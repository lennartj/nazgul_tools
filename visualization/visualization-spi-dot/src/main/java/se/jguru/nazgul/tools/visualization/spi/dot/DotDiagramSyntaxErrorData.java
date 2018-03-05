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

import org.antlr.v4.runtime.RecognitionException;

import java.io.Serializable;

/**
 * Holder of ANTLR4 syntactic error information, when parsing a stream containing
 * data not conforming to the <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DotDiagramSyntaxErrorData implements Serializable {

    // Internal state
    private Object token;
    private int line;
    private int charPositionInLine;
    private String msg;
    private RecognitionException exception;

    /**
     * Compound constructor creating a SyntaxErrorData structure wrapping the supplied data.
     *
     * @param token              The token which gave rise to the Syntax error.
     * @param line               The line (in the source file) where the error occurred.
     * @param charPositionInLine The position in the line where the error occurred.
     * @param msg                The ANTLR4 syntax error message.
     * @param exception          The {@link RecognitionException} as given by ANTLR4.
     */
    public DotDiagramSyntaxErrorData(
            final Object token,
            final int line,
            final int charPositionInLine,
            final String msg,
            final RecognitionException exception) {

        this.line = line;
        this.charPositionInLine = charPositionInLine;
        this.msg = msg;
        this.exception = exception;
        this.token = token;
    }

    /**
     * @return The line (in the source file) where the syntactic error occurred.
     */
    public int getLine() {
        return line;
    }

    /**
     * @return The position in the line where the error occurred.
     */
    public int getCharPositionInLine() {
        return charPositionInLine;
    }

    /**
     * @return The ANTLR4 syntax error message.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return The token which gave rise to the Syntax error.
     */
    public Object getToken() {
        return token;
    }

    /**
     * @return The {@link RecognitionException} as given by ANTLR4.
     */
    public RecognitionException getException() {
        return exception;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SyntaxErrorData [Token: " + getToken()
                + ", line: " + getLine()
                + ", position: " + getCharPositionInLine()
                + "] Message: '" + getMsg() + '\''
                + ", Exception: " + getException();
    }
}
