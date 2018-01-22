package se.jguru.nazgul.tools.visualization.impl.doclet.helpers;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Options;
import com.sun.tools.javadoc.JavadocTool;
import com.sun.tools.javadoc.ModifierFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RootDocWrapper {

    // Our log
    private static final Logger log = LoggerFactory.getLogger(RootDocWrapper.class);

    // Internal state
    private Context context;
    private Options options;
    private SimpleMessager messager;
    private List<String> errors;
    private List<String> warnings;
    private List<String> debugs;

    public RootDocWrapper(final String programName) {

        // Create internal state
        this.context = new Context();
        this.options = Options.instance(this.context);

        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.debugs = new ArrayList<>();

        this.messager = new SimpleMessager(
                this.context,
                programName,
                new PrintWriter(new DelegatingWriter(Level.ERROR, errors), true),
                new PrintWriter(new DelegatingWriter(Level.WARN, warnings), true),
                new PrintWriter(new DelegatingWriter(Level.DEBUG, debugs), true));
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public List<String> getDebugs() {
        return debugs;
    }

    public void addJavaDocOption(final String option, final String value) {
        options.put(option, value);
    }

    public RootDoc getRootDoc(final File sourceDir,
                              final String targetDirectoryName,
                              final List<String> packageNames,
                              final List<File> fileNames,
                              final Iterable<? extends JavaFileObject> javaFileObjects) {

        if (log.isDebugEnabled()) {
            log.debug("Using sourceDirectory [" + sourceDir.getAbsolutePath() + "]");
        }

        //
        // Add some standard JavaDoc options, to tailor where the sources
        // are read and where the resulting javadoc goes.
        //
        addJavaDocOption("-sourcepath", sourceDir.getAbsolutePath());
        addJavaDocOption("-d", getRelativeDirectory(
                "target/javadoc/" + targetDirectoryName,
                true).getAbsolutePath());

        final ListBuffer<String> javaNames = new ListBuffer<>();
        for (File fileName : fileNames) {

            if (log.isDebugEnabled()) {
                log.debug("Adding file to documentation path: " + fileName.getAbsolutePath());
            }
            javaNames.append(fileName.getPath());
        }

        ListBuffer<String> subPackages = new ListBuffer<String>();
        for (String packageName : packageNames) {

            if (log.isDebugEnabled()) {
                log.debug("Adding sub-packages to documentation path: " + packageName);
            }
            subPackages.append(packageName);
        }

        // Create the JavadocTool, as done within the JDK's implementation.
        final JavadocTool javadocTool = JavadocTool.make0(this.context);

        final RootDoc toReturn;

        try {
            toReturn = javadocTool.getRootDocImpl(
                    "",
                    null,
                    new ModifierFilter(ModifierFilter.ALL_ACCESS),
                    javaNames.toList(),
                    new ListBuffer<String[]>().toList(),
                    javaFileObjects == null ? Collections.emptyList() : javaFileObjects,
                    false,
                    subPackages.toList(),
                    new ListBuffer<String>().toList(),
                    false,
                    false,
                    false);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        if (log.isDebugEnabled()) {
            for (ClassDoc classDoc : toReturn.classes()) {
                log.debug("Parsed Javadoc class source: " + classDoc.position() + " with inline tags: "
                        + classDoc.inlineTags().length);
            }
        }

        // All Done.
        return toReturn;
    }

    public static File getBasedir() {

        final URL logbackTestURL = Thread.currentThread().getContextClassLoader().getResource("logback-test.xml");
        final File targetDir = new File(logbackTestURL.getPath()).getParentFile().getParentFile();

        final boolean isTargetDirectory = targetDir.exists()
                && targetDir.isDirectory()
                && targetDir.getName().trim().equalsIgnoreCase("target");
        if (!isTargetDirectory) {
            throw new IllegalStateException("Could not retrieve the target directory. [Got: "
                    + targetDir.getAbsolutePath() + "]");
        }

        // All Done.
        return targetDir.getParentFile();
    }

    public static File getTestSourceDir() {
        return getRelativeDirectory("src/test/java", false);
    }

    public static File getRelativeDirectory(final String basedirRelativePath, final boolean createIfNonExistent) {

        // Synthesize the directory File.
        final File toReturn = new File(getBasedir(), basedirRelativePath);

        // Create it?
        if (!toReturn.exists() && createIfNonExistent) {
            toReturn.mkdirs();
        }

        // All Done.
        return toReturn;
    }

    protected class DelegatingWriter extends Writer {

        // Internal state
        private Level logLevel;
        private List<String> messageSink;

        DelegatingWriter(final Level logLevel, final List<String> messageSink) {
            this.logLevel = logLevel;
            this.messageSink = messageSink;
        }

        public void write(char[] chars, int offset, int length) throws IOException {

            // Extract the text to log.
            final String toLog = new String(Arrays.copyOf(chars, length));
            if (!toLog.equals("\n")) {

                // Copy the message into the List.
                messageSink.add(toLog);

                // Now perform normal SLF4J logging.
                switch (logLevel) {
                    case DEBUG:
                        log.debug(toLog);
                        break;

                    case INFO:
                        log.info(toLog);
                        break;

                    case WARN:
                        log.warn(toLog);
                        break;

                    case ERROR:
                        log.error(toLog);
                        break;

                    case TRACE:
                        log.trace(toLog);
                        break;
                }
            }
        }

        public void flush() throws IOException {
        }

        public void close() throws IOException {
        }
    }
}
