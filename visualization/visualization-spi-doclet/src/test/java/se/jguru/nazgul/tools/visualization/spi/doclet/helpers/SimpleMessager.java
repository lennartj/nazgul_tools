/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-spi-doclet
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

package se.jguru.nazgul.tools.visualization.spi.doclet.helpers;

import com.sun.tools.javac.util.Context;
import com.sun.tools.javadoc.Messager;

import java.io.PrintWriter;

/**
 * Trivial {@link Messager} subclass which exposes public constructors.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class SimpleMessager extends Messager {
    
    /**
     * Compound constructor creating a SimpleMessager wrapping the supplied data.
     *
     * @param context       The Javac util Context.
     * @param programName   The name of the program for which this SimpleMessager should work.
     * @param errorWriter   The writer to which error messages should be sent.
     * @param warningWriter The writer to which warning messages should be sent.
     * @param noticeWriter  The writer to which notices / debug messages should be sent.
     */
    public SimpleMessager(final Context context,
                          final String programName,
                          final PrintWriter errorWriter,
                          final PrintWriter warningWriter,
                          final PrintWriter noticeWriter) {
        super(context, programName, errorWriter, warningWriter, noticeWriter);
    }
}
