/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2015 jGuru Europe AB
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

import java.io.File;
import java.io.FileFilter;

/**
 * Specification for extracting a package definition from the supplied sourceFile.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface PackageExtractor {

    /**
     * Retrieves the package definition from the supplied sourceFile.
     *
     * @param sourceFile The sourceFile from which the package definition should be extracted.
     * @return The package of the sourceFile.
     */
    String getPackage(final File sourceFile);

    /**
     * Retrieves a FileFilter which identifies the source files that can be handled by this PackageExtractor.
     *
     * @return a non-null FileFilter which identifies the source files that can be handled by this PackageExtractor.
     */
    FileFilter getSourceFileFilter();
}