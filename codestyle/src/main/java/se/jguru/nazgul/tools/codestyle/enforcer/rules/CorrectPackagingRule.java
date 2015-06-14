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
package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.commons.lang.Validate;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;
import se.jguru.nazgul.tools.codestyle.enforcer.rules.source.JavaPackageExtractor;
import se.jguru.nazgul.tools.codestyle.enforcer.rules.source.PackageExtractor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Enforcer rule to enforce correct packaging for all source files within a project,
 * implying that all source files should be located within or under a package identical
 * to the groupId of the project itself.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CorrectPackagingRule extends AbstractNonCacheableEnforcerRule {

    // Constants
    private static final FileFilter DIRECTORY_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File candidate) {
            return candidate.isDirectory();
        }
    };

    /**
     * The default List of PackageExtractors used to identify packages within found source files.
     */
    public static final List<PackageExtractor> DEFAULT_PACKAGE_EXTRACTORS = Arrays.asList(
            (PackageExtractor) new JavaPackageExtractor());

    // Internal state
    private List<PackageExtractor> packageExtractors;

    /**
     * Default constructor, assigning the {@code DEFAULT_PACKAGE_EXTRACTORS} for
     * use as all known PackageExtractor instances.
     */
    public CorrectPackagingRule() {

        // Use the default package extractor List
        packageExtractors = DEFAULT_PACKAGE_EXTRACTORS;
    }

    /**
     * Delegate method, implemented by concrete subclasses.
     *
     * @param project The active MavenProject.
     * @param helper  The EnforcerRuleHelper instance, from which the MavenProject has been retrieved.
     * @throws RuleFailureException If the enforcer rule was not satisfied.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void performValidation(final MavenProject project, final EnforcerRuleHelper helper)
            throws RuleFailureException {

        // Find all java source files, and map their packages to their names.
        final List<String> compileSourceRoots = (List<String>) project.getCompileSourceRoots();
        if (compileSourceRoots.size() == 0) {
            return;
        }

        final SortedMap<String, SortedSet<String>> packageName2SourceFileNameMap
                = new TreeMap<String, SortedSet<String>>();

        for (String current : compileSourceRoots) {
            addPackages(new File(current), packageName2SourceFileNameMap);
        }

        // Retrieve the groupId of this project
        final String groupId = project.getGroupId();
        if (groupId == null || groupId.equals("")) {

            // Don't accept empty groupIds
            throw new RuleFailureException("Maven groupId cannot be null or empty.", project.getArtifact());

        } else {

            // Correct packaging everywhere?
            final SortedSet<String> incorrectPackages = new TreeSet<String>();
            for (Map.Entry<String, SortedSet<String>> currentPackage : packageName2SourceFileNameMap.entrySet()) {

                final String candidate = currentPackage.getKey();
                if (!candidate.startsWith(groupId)) {
                    incorrectPackages.add(candidate);
                }
            }

            if (incorrectPackages.size() > 0) {

                final SortedMap<String, SortedSet<String>> result = new TreeMap<String, SortedSet<String>>();
                for (String current : incorrectPackages) {
                    result.put(current, packageName2SourceFileNameMap.get(current));
                }

                throw new RuleFailureException("Incorrect packaging detected; required [" + groupId
                        + "] but found package to file names: " + result, project.getArtifact());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getShortRuleDescription() {
        return "Topmost source package must be identical to project groupId.";
    }

    /**
     * Splices the supplied packageExtractorImplementations argument, which is assumed to be a comma-separated
     * string holding fully qualified class names of the PackageExtractor implementations which should be used
     * by this CorrectPackagingRule.
     *
     * @param packageExtractorImplementations
     *         a comma-separated string holding fully qualified class names of the
     *         PackageExtractor implementations. Each such class must have a default
     *         (i.e. no-argument) constructor.
     * @throws IllegalArgumentException if the supplied packageExtractorImplementations argument could not yield an
     *                                  instantiated PackageExtractor instance.
     */
    public final void setPackageExtractors(final String packageExtractorImplementations)
            throws IllegalArgumentException {

        // Check sanity
        Validate.notEmpty(packageExtractorImplementations,
                "Cannot handle empty packageExtractorImplementations argument.");

        // Instantiate the PackageExtractor instances.
        List<PackageExtractor> extractors = new ArrayList<PackageExtractor>();
        for (String current : splice(packageExtractorImplementations)) {
            try {

                // Load the current PackageExtractor implementation class
                final Class<?> aClass = getClass().getClassLoader().loadClass(current);

                // The PackageExtractor implementation must have a default constructor.
                // Fire, and handle any exceptions.
                extractors.add((PackageExtractor) aClass.newInstance());
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not instantiate PackageExtractor from class ["
                        + current + "]. Validate that implementation has a default constructor, and implements the"
                        + PackageExtractor.class.getSimpleName() + " interface.");
            }
        }

        // Assign if non-null.
        if (extractors.size() > 0) {
            this.packageExtractors = extractors;
        }
    }

    //
    // Private helpers
    //

    /**
     * Adds all source file found by recursive search under sourceRoot to the
     * toPopulate List, using a width-first approach.
     *
     * @param fileOrDirectory      The file or directory to search for packages and [if a directory] recursively
     *                             search for further source files.
     * @param package2FileNamesMap A Map relating package names extracted by the PackageExtractors.
     */
    private void addPackages(final File fileOrDirectory,
                             final SortedMap<String, SortedSet<String>> package2FileNamesMap) {

        for (PackageExtractor current : packageExtractors) {

            final FileFilter sourceFileDefinitionFilter = current.getSourceFileFilter();

            if (fileOrDirectory.isFile() && sourceFileDefinitionFilter.accept(fileOrDirectory)) {

                // Single source file to add.
                final String thePackage = current.getPackage(fileOrDirectory);

                SortedSet<String> sourceFileNames = package2FileNamesMap.get(thePackage);
                if (sourceFileNames == null) {
                    sourceFileNames = new TreeSet<String>();
                    package2FileNamesMap.put(thePackage, sourceFileNames);
                }

                // Done.
                sourceFileNames.add(fileOrDirectory.getName());

            } else if (fileOrDirectory.isDirectory()) {

                // Add the immediate source files
                for (File currentChild : fileOrDirectory.listFiles(sourceFileDefinitionFilter)) {
                    addPackages(currentChild, package2FileNamesMap);
                }

                // Recurse into subdirectories
                for (File currentSubdirectory : fileOrDirectory.listFiles(DIRECTORY_FILTER)) {
                    addPackages(currentSubdirectory, package2FileNamesMap);
                }
            }
        }
    }
}
