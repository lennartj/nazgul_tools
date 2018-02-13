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
package se.jguru.nazgul.tools.visualization.spi.doclet.converter;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.PackageDoc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Utility class collecting comparators and algorithms for working with RootDoc, ClassDoc, MemberDoc etc.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public final class Comparators {

    /**
     * Forgiving name converter which substitutes a null input with an empty string.
     */
    public static String toString(final Doc aDoc) {
        return aDoc == null ? "" : aDoc.name();
    }

    public enum MemberOrder {

        CONSTANT,

        STATIC_METHOD,

        FIELD,

        CONSTRUCTOR,

        METHOD;

        public static MemberOrder parse(final MemberDoc memberDoc) {

            MemberOrder toReturn = null;

            // #1) Sort fields.
            //
            if (memberDoc.isField()) {
                toReturn = memberDoc.isStatic() && memberDoc.isFinal() ? CONSTANT : FIELD;
            }

            // #2) Sort methods.
            //
            if (memberDoc.isMethod()) {
                toReturn = memberDoc.isStatic() ? STATIC_METHOD : METHOD;
            }

            // #3) Handle constructors.
            //
            if (memberDoc.isConstructor()) {
                toReturn = CONSTRUCTOR;
            }

            // All Done.
            return toReturn;
        }
    }

    public enum VisibilityModifier {

        PUBLIC,

        PROTECTED,

        PACKAGE_PRIVATE,

        PRIVATE;

        public static VisibilityModifier parse(final MemberDoc memberDoc) {

            VisibilityModifier toReturn = null;
            if (memberDoc.isPublic()) {
                toReturn = PUBLIC;
            } else if (memberDoc.isProtected()) {
                toReturn = PROTECTED;
            } else if (memberDoc.isPackagePrivate()) {
                toReturn = PACKAGE_PRIVATE;
            } else {
                toReturn = PRIVATE;
            }

            // All Done.
            return toReturn;
        }
    }

    /**
     * Comparator relating {@link PackageDoc}s to their names, using {@link #toString(Doc)}.
     */
    public static final Comparator<PackageDoc> PACKAGE_NAME_COMPARATOR = Comparator.comparing(Comparators::toString);

    /**
     * Comparator relating {@link ClassDoc}s to their names, using {@link #toString(Doc)}.
     */
    public static final Comparator<ClassDoc> CLASS_NAME_COMPARATOR = Comparator.comparing(Comparators::toString);

    /**
     * Comparator relating {@link MemberDoc}s to their natural sort order.
     * This comparator does not handle null values.
     */
    public static final Comparator<MemberDoc> NATURAL_ORDER_MEMBER_COMPARATOR = (l, r) -> {

        // First, compare types
        final MemberOrder leftOrder = MemberOrder.parse(l);
        final MemberOrder rightOrder = MemberOrder.parse(r);
        int toReturn = leftOrder.compareTo(rightOrder);

        // Second, compare visibilities
        if (toReturn == 0) {
            final VisibilityModifier leftVis = VisibilityModifier.parse(l);
            final VisibilityModifier rightVis = VisibilityModifier.parse(r);
            toReturn = leftVis.compareTo(rightVis);
        }

        // Third, compare names.
        if (toReturn == 0) {
            toReturn = toString(l).compareTo(toString(r));
        }

        // All Done.
        return toReturn;
    };

    /**
     * Sorts all given ClassDocs into a SortedMap keyed by their respective PackageDocs.
     *
     * @param classDocs an array of ClassDoc objects.
     * @return A SortedMap relating all ClassDocs to their respective PackageDoc.
     */
    public static SortedMap<PackageDoc, SortedSet<ClassDoc>> sortClassesPerPackage(final ClassDoc... classDocs) {

        final SortedMap<PackageDoc, SortedSet<ClassDoc>> toReturn = new TreeMap<>(Comparators.PACKAGE_NAME_COMPARATOR);

        if (classDocs != null) {

            Arrays.stream(classDocs).forEach(current -> {

                // Retrieve (or create) the SortedSet of ClassDocs for the current PackageDocs.
                final SortedSet<ClassDoc> classDocsForCurrentPackage = toReturn.computeIfAbsent(
                        current.containingPackage(),
                        k -> new TreeSet<>(Comparators.CLASS_NAME_COMPARATOR));

                // Add the current ClassDoc to the SortedSet.
                classDocsForCurrentPackage.add(current);
            });
        }

        // All Done.
        return toReturn;
    }
}
