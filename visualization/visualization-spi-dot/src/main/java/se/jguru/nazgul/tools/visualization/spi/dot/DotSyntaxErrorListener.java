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

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link org.antlr.v4.runtime.ANTLRErrorListener} implementation which handles only
 * syntactic errors within the Dot file being inspected.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DotSyntaxErrorListener extends BaseErrorListener {

    // Internal state
    private List<DotDiagramSyntaxErrorData> errorDataList;

    /**
     * Default constructor, creating the errorDataList instance.
     */
    public DotSyntaxErrorListener() {
        this.errorDataList = new ArrayList<>();
    }

    /**
     * Retrieves the List containing all SyntaxErrorData objects harvested.
     *
     * @return the List containing all SyntaxErrorData objects harvested. The List may be empty but never null.
     */
    public List<DotDiagramSyntaxErrorData> getErrorDataList() {
        return errorDataList;
    }

    /**
     * Convenience method indicating if an error was encountered when parsing a source file.
     *
     * @return {@code true} if a syntax error was found during parsing of a source file.
     */
    public boolean isErrorState() {
        return !errorDataList.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void syntaxError(final Recognizer<?, ?> recognizer,
            final Object offendingSymbol,
            final int line,
            final int charPositionInLine,
            final String msg,
            final RecognitionException e) {

        // Add the error data
        this.errorDataList.add(new DotDiagramSyntaxErrorData(offendingSymbol, line, charPositionInLine, msg, e));
    }
}
