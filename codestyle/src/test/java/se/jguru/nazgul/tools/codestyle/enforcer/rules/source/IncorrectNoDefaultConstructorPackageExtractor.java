/*
 * Copyright (c) jGuru Europe AB
 * All rights reserved.
 */
package se.jguru.nazgul.tools.codestyle.enforcer.rules.source;

import java.io.File;
import java.io.FileFilter;

/**
 * An incorrect PackageExtractor implementation, which does not have a default constructor.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class IncorrectNoDefaultConstructorPackageExtractor implements PackageExtractor {

    public IncorrectNoDefaultConstructorPackageExtractor(final String someConstructorArgument) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackage(final File sourceFile) {
        return "unimportant";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileFilter getSourceFileFilter() {
        return null;
    }
}
