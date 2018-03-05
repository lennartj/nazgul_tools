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


package se.jguru.nazgul.tools.visualization.spi.doclet.graphviz;

import com.sun.javadoc.RootDoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Facade to the Dot executable from the GraphViz distribution.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DotFacade extends AbstractExecutableFacade {

    private static final String DOT_EXECUTABLE_NAME = "dot";

    /**
     * Default constructor, using "dot" as the e
     */
    public DotFacade() {
        super(DOT_EXECUTABLE_NAME);
    }

    /**
     * <p>Creates and writes PNG and HTML imagemap files by executing 'dot', with the following arguments:</p>
     * <pre>
     *     <code>
     *         dot -Tcmapx -o [outputDir/filename].map -Tpng -o [outputDir/filename].png
     *     </code>
     * </pre>
     * <p>The {@code diagram} string is fed to the dot process.</p>
     *
     * @param rootDoc         The active {@link RootDoc} instance.
     * @param dotDiagram      The diagram source to feed into 'dot' for rendering.
     * @param outputDirectory The directory where the result should be sent.
     * @param filename        The filename of the PNG and MAP files generated.
     * @throws IOException If the files could not be properly generated.
     */
    public void writePngImageAndImageMap(final RootDoc rootDoc,
                                         final String dotDiagram,
                                         final File outputDirectory,
                                         final String filename) throws IOException {

        // Check sanity
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'filename' argument.");
        }
        if (rootDoc == null) {
            throw new IllegalArgumentException("Cannot handle null 'rootDoc' argument.");
        }

        // #0) Initialize
        if (!initialize(rootDoc)) {
            rootDoc.printWarning("Could not initialize DotFacade. Aborting writing PNG image and ImageMap files.");
        }

        // #1) Ensure that the PNG and MAP files can be written to.
        final File pngFile = new File(outputDirectory, filename + ".png");
        final File mapFile = new File(outputDirectory, filename + ".map");
        pngFile.delete();
        mapFile.delete();

        // #2) Compile the arguments used to launch Graphviz.
        final ProcessBuilder pb = new ProcessBuilder(
                getExecutableNameOrPath(),
                "-Tcmapx", "-o", mapFile.getAbsolutePath(),
                "-Tpng", "-o", pngFile.getAbsolutePath());
        pb.redirectErrorStream(true);
        if (getHomeDirectory() != null) {
            pb.directory(getHomeDirectory());
        }

        // #3) Launch Graphviz. Harvest output.
        final Process p = pb.start();
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
             final Writer out = new OutputStreamWriter(p.getOutputStream(), AbstractExecutableFacade.UTF8_ENCODING)) {

            // Send the diagram to the process, and close the stream after that.
            out.write(dotDiagram);
            out.close();

            // Got error output?
            String line = null;
            while ((line = in.readLine()) != null) {
                System.err.println(line);
            }

        } finally {

            while (true) {
                try {
                    int result = p.waitFor();
                    if (result != 0) {
                        throw new IllegalStateException("GraphViz exited with a non-zero return value: " + result);
                    }
                    break;
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        }
    }
}
