/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules.source;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Kotlin-flavoured PackageExtractor implementation, validating performance over
 * complete (i.e. AST) correctness.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class KotlinPackageExtractor implements PackageExtractor {

    /**
     * The 'package' reserved Kotlin word.
     */
    public static final String PACKAGE_WORD = "package";

    /**
     * Regular expression to search for a package statement line.
     * Does not perform full Kotlin AST construction of the source file given - instead
     * preferring parsing speed.
     */
    public static final Pattern PACKAGE_STATEMENT = Pattern.compile(
            "^\\s*" + PACKAGE_WORD + "\\s*([a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*)?\\s*;\\s*$");

    /**
     * FileFilter which identifies a normal Java source file.
     */
    public static final FileFilter KOTLIN_SOURCE_FILEFILTER = aFile -> aFile != null
            && (aFile.isFile() && aFile.getName().toLowerCase().trim().endsWith(".kt"));

    /**
     * Retrieves the package definition from the supplied sourceFile.
     *
     * @param sourceFile The sourceFile from which the package definition should be extracted.
     * @return The package of the sourceFile.
     */
    @Override
    public String getPackage(final File sourceFile) {

        String aLine = JavaPackageExtractor.getPackage(sourceFile, PACKAGE_STATEMENT);
        if (aLine != null) return aLine;

        // No package statement found.
        // Return default package.
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileFilter getSourceFileFilter() {
        return KOTLIN_SOURCE_FILEFILTER;
    }
}
