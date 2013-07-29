/*
 * Copyright (c) jGuru Europe AB
 * All rights reserved.
 */
package se.jguru.nazgul.tools.codestyle.enforcer.rules.source;

import java.io.File;
import java.io.FileFilter;

/**
 * A silly - but correctly implemented PackageExtractor, having a default constructor.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class SillyPackageExtractor implements PackageExtractor {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackage(final File sourceFile) {
        return "silly";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileFilter getSourceFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(final File aFile) {
                return aFile.isFile();
            }
        };
    }
}
