package se.jguru.nazgul.tools.visualization.impl.doclet.helpers;

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
