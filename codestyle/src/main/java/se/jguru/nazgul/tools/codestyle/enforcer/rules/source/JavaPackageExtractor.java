/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *        http://www.jguru.se/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package se.jguru.nazgul.tools.codestyle.enforcer.rules.source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.regex.Pattern;

/**
 * Java-flavoured PackageExtractor implementation, validating performance over
 * complete (i.e. AST) correctness.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class JavaPackageExtractor implements PackageExtractor {

    /**
     * The 'package' reserved Java word.
     */
    public static final String PACKAGE_WORD = "package";

    /**
     * Regular expression to search for a package statement line.
     * Does not perform full Java AST construction of the source file given - instead
     * preferring parsing speed.
     */
    public static final Pattern PACKAGE_STATEMENT = Pattern.compile(
            "^\\s*" + PACKAGE_WORD + "\\s*([a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*)?\\s*;\\s*$");

    /**
     * FileFilter which identifies a normal Java source file.
     */
    public static final FileFilter JAVA_SOURCE_FILEFILTER = new FileFilter() {
        @Override
        public boolean accept(final File aFile) {
            return aFile != null && (aFile.isFile() && aFile.getName().toLowerCase().trim().endsWith(".java"));
        }
    };

    /**
     * Retrieves the package definition from the supplied sourceFile.
     *
     * @param sourceFile The sourceFile from which the package definition should be extracted.
     * @return The package of the sourceFile.
     */
    @Override
    public String getPackage(final File sourceFile) {

        try {
            final BufferedReader bufferedReader
                    = Files.newBufferedReader(sourceFile.toPath(), Charset.defaultCharset());
            String aLine = null;
            while ((aLine = bufferedReader.readLine()) != null) {

                if (PACKAGE_STATEMENT.matcher(aLine.trim()).matches()) {
                    return aLine.substring(
                            aLine.indexOf(PACKAGE_WORD) + PACKAGE_WORD.length(), aLine.indexOf(";"))
                            .trim();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not determine package from file ["
                    + sourceFile.getAbsolutePath() + "]. Not a Java file?", e);
        }

        // No package statement found.
        // Return default package.
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileFilter getSourceFileFilter() {
        return JAVA_SOURCE_FILEFILTER;
    }
}
