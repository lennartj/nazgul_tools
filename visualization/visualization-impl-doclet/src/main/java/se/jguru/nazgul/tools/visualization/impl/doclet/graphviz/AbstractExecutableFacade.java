/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-impl-doclet
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
package se.jguru.nazgul.tools.visualization.impl.doclet.graphviz;

import com.sun.javadoc.RootDoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract facade to a graphviz executable program.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractExecutableFacade {

    /**
     * Regular expression to identify that an executable actually is Graphviz.
     */
    public static final String GRAPHVIZ_EXECUTABLE_FIRST_LINE_CHECK = "^.*[Gg][Rr][Aa][Pp][Hh][Vv][Ii][Zz].*$";

    /**
     * The System property key defining the graphviz home.
     */
    public static final String HOMEDIR_SYSTEM_PROPERTY = "graphviz.home";

    /**
     * The environment key defining the graphviz home.
     */
    public static final String HOMEDIR_ENV_PROPERTY = "GRAPHVIZ_HOME";

    /**
     * The surrounding operating system's name.
     */
    public static final String OS_NAME = System.getProperty("os.name");

    /**
     * Standard encoding for emitted files is UTF8.
     */
    public static final String UTF8_ENCODING = "UTF-8";

    // Internal state
    private List<String> discoveryLog;
    private String executableNameOrPath;
    private File homeDirectory;
    private boolean isInitializedOK;

    /**
     * @param executableName
     */
    protected AbstractExecutableFacade(final String executableName) {

        // Check sanity
        if (executableName == null || executableName.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'executableName' argument.");
        }

        // Assign internal state
        this.discoveryLog = new ArrayList<>();

        // Find the Graphvis home directory
        try {

            // #1) Attempt to find the Graphviz directory from a System property
            String graphvizHome = System.getProperty(HOMEDIR_SYSTEM_PROPERTY);
            if (graphvizHome != null) {
                discoveryLog.add("GraphViz home [" + graphvizHome + "] found using the '"
                        + HOMEDIR_SYSTEM_PROPERTY + "' system property.");
            } else {

                discoveryLog.add("The '" + HOMEDIR_SYSTEM_PROPERTY + "' system property was not specified.");

                // #2) Attempt to find the Graphviz directory from an environment property
                graphvizHome = System.getenv(HOMEDIR_ENV_PROPERTY);
                if (graphvizHome != null) {
                    discoveryLog.add("GraphViz home [" + graphvizHome + "] found using the '"
                            + HOMEDIR_ENV_PROPERTY + "' environment variable.");
                } else {
                    discoveryLog.add("The '" + HOMEDIR_ENV_PROPERTY + "' environment variable was not specified.");
                }
            }

            // Check sanity
            if (graphvizHome != null) {

                homeDirectory = new File(graphvizHome);

                if (!homeDirectory.exists() || !homeDirectory.isDirectory()) {
                    discoveryLog.add("The specified GraphViz home directory does not exist: "
                            + homeDirectory.getPath());
                    homeDirectory = null;
                }
            }

            if (homeDirectory == null) {
                discoveryLog.add("System path will be used as GraphViz home directory was not specified.");
            }
        } catch (Exception e) {
            // Ignore this.
        }

        // Find the actual executable name, compensating for the operating system path eccentricities.
        if (OS_NAME != null && OS_NAME.contains("Windows")) {

            // Windows
            this.executableNameOrPath = (homeDirectory != null ? homeDirectory.getAbsolutePath() + File.separator : "")
                    + executableName + ".exe";
        } else {

            // Linux/Mac
            this.executableNameOrPath = executableName;
        }
    }

    /**
     * <p>This method should be invoked from within subclasses' worker methods, to validate that the executable found
     * is actually a GraphViz program.</p>
     * <p>The method fires the command <b><code><i>executableNameOrPath</i> -V</code></b>, capturing the output to determine
     * if it contains the pattern {@link #GRAPHVIZ_EXECUTABLE_FIRST_LINE_CHECK}, in which case it is deemed to be a
     * proper GraphViz executable. For instance, the two executables dot and neato responds as follows:</p>
     * <ul>
     * <li><i>dot -V</i>: <code>dot - graphviz version 2.36.0 (20140111.2315)</code></li>
     * <li><i>neato -V</i>: <code>neato - graphviz version 2.36.0 (20140111.2315)</code></li>
     * </ul>
     *
     * @param rootDoc The non-null RootDoc received from the JavaDoc execution context.
     * @return {@link true} if this {@link AbstractExecutableFacade} is properly initialized and can accept commands.
     */
    protected boolean initialize(final RootDoc rootDoc) {

        // #0) Don't initialize twice, unless required to.
        if (isInitializedOK) {
            return true;
        }

        // #1) Output the discovery log.
        for (String current : discoveryLog) {
            rootDoc.printNotice(current);
        }

        rootDoc.printNotice("GraphViz home: " + getHomeDirectory().getAbsolutePath());
        rootDoc.printNotice("Executable   : " + getExecutableNameOrPath());

        // #2) Create a ProcessBuilder which synthesizes a call to the executable and determine its version.
        //
        final ProcessBuilder pb = new ProcessBuilder(getExecutableNameOrPath(), "-V");
        pb.redirectErrorStream(true);
        if (homeDirectory != null) {
            pb.directory(homeDirectory);
        }

        Process p;
        try {

            // Launch the executable
            p = pb.start();
        } catch (IOException e) {

            rootDoc.printWarning(e.getMessage());
            return false;
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
             OutputStream out = p.getOutputStream();) {

            // We don't need to send any more commands to the process, so close the output stream.
            out.close();

            // Read all input from the process, searching for the pattern "graphviz" ignoring case.
            String line = null;
            while ((line = in.readLine()) != null) {

                if (line.matches(GRAPHVIZ_EXECUTABLE_FIRST_LINE_CHECK)) {

                    // We found the expected output from the executable.
                    rootDoc.printNotice("GraphViz Version: " + line);
                    isInitializedOK = true;
                    return true;

                } else {

                    // Unknown output. Printout a warning and die.
                    rootDoc.printWarning("Unknown GraphViz output: " + line);
                }
            }

            // Cache the result.
            isInitializedOK = false;
            return false;

        } catch (IOException e) {

            // Nopes.
            rootDoc.printWarning("Problem detecting GraphViz: " + e.getMessage());
            isInitializedOK = false;
            return false;

        } finally {

            for (; ; ) {
                try {
                    p.waitFor();
                    break;
                } catch (InterruptedException e) {
                    // Ignore this
                }
            }
        }
    }

    //
    // Private helpers
    //

    /**
     * @return The name or path to the Executable.
     */
    protected String getExecutableNameOrPath() {
        return executableNameOrPath;
    }

    /**
     * @return The File to the HomeDirectory.
     */
    protected File getHomeDirectory() {
        return homeDirectory;
    }
}
