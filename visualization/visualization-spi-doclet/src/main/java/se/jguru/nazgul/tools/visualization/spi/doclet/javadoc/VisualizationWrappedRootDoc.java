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

package se.jguru.nazgul.tools.visualization.spi.doclet.javadoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SourcePosition;
import se.jguru.nazgul.tools.visualization.spi.doclet.VisualizationDocletTag;
import se.jguru.nazgul.tools.visualization.spi.doclet.converter.Comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * {@link RootDoc} implementation which swallows messages related to the
 * {@link se.jguru.nazgul.tools.visualization.spi.doclet.VisualizationDoclet}.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class VisualizationWrappedRootDoc extends AbstractRootDocWrapper {

    /**
     * Constructs an {@link VisualizationWrappedRootDoc} instance around the supplied {@link RootDoc}.
     *
     * @param wrappedRootDoc A non-null {@link RootDoc} instance to be wrapped by this {@link VisualizationWrappedRootDoc}.
     */
    public VisualizationWrappedRootDoc(final RootDoc wrappedRootDoc) {
        super(wrappedRootDoc);
    }

    /**
     * Delegates to the wrapped {@link RootDoc} only if the supplied Message does not contain an
     * {@link se.jguru.nazgul.tools.visualization.spi.doclet.VisualizationDocletTag} identifier.
     * Otherwise, the message is ignored.
     *
     * @param msg The warning message.
     */
    @Override
    public void printWarning(final String msg) {

        // Swallow VisualizationDoclet warnings.
        if (!containsVisualizationDocletTag(msg)) {
            wrappedRootDoc.printWarning(msg);
        }
    }

    /**
     * Delegates to the wrapped {@link RootDoc} only if the supplied Message does not contain an
     * VisualizationDocletTag identifier. Otherwise, the message is ignored.
     *
     * @param pos The position in the Source.
     * @param msg The warning message.
     */
    @Override
    public void printWarning(final SourcePosition pos, final String msg) {

        // Swallow VisualizationDoclet warnings.
        if (!containsVisualizationDocletTag(msg)) {
            wrappedRootDoc.printWarning(pos, msg);
        }
    }

    /**
     * Checks if ths supplied message contains an {@link VisualizationDocletTag} reference, on the form
     * {@code '@nazgul_vis.xxxx '}.
     *
     * @param message The message to check.
     * @return {@code true} if the supplied message contains a {@link VisualizationDocletTag} reference.
     */
    public static boolean containsVisualizationDocletTag(final String message) {

        if (message != null) {
            for (VisualizationDocletTag current : VisualizationDocletTag.values()) {
                if (message.contains(current.toString() + " ")) {

                    // The message contains an entry on the form '@nazgul_vis.xxxx '
                    // which implies that it has to do with a VisualizationDoclet javadoc Tag.
                    return true;
                }
            }
        }

        // Nopes.
        return false;
    }

    /**
     * Finds all PackageDoc objects from the classes in this JavaDoc execution.
     *
     * @return a non-null (but possibly empty) SortedSet containing all {@link PackageDoc}s from the current execution.
     */
    public SortedSet<PackageDoc> findPackagesFromClassesInJavaDocRun() {

        final SortedSet<PackageDoc> toReturn = new TreeSet<>(Comparators.PACKAGE_NAME_COMPARATOR);
        final ClassDoc[] currentExecutionClasses = classes();

        if (currentExecutionClasses != null) {

            Arrays.stream(currentExecutionClasses)
                    .map(ProgramElementDoc::containingPackage)
                    .forEach(toReturn::add);
        }

        // All Done.
        return toReturn;
    }

    /**
     * Sorts all ClassDocs retrieved within the current JavaDoc compilation run into their respective PackageDocs.
     *
     * @return A SortedMap relating all ClassDocs to their respective PackageDoc.
     */
    public SortedMap<PackageDoc, SortedSet<ClassDoc>> sortClassesPerPackage() {
        return Comparators.sortClassesPerPackage(classes());
    }
}
