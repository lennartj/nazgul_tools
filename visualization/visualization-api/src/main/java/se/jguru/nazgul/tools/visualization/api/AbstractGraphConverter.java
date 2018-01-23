/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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
package se.jguru.nazgul.tools.visualization.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statement;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Abstract TypeConverter implementation handling conversion to a {@link Graph}, and sporting
 * a small registry of {@link AbstractStatementConverter}s to convert any sub-nodes.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractGraphConverter<From> implements TypeConverter<From, Graph> {

    // Our log
    private static final Logger log = LoggerFactory.getLogger(AbstractGraphConverter.class);

    /**
     * Comparator for a sorted map using Class objects for Keys.
     */
    public static final Comparator<Class<?>> CLASS_COMPARATOR = (l, r) -> {

        final String leftClassName = l == null ? "" : l.getName();
        final String rightClassName = r == null ? "" : r.getName();

        // All Done.
        return leftClassName.compareTo(rightClassName);
    };

    /**
     * Comparator for a sorted map using Package objects for Keys.
     */
    public static final Comparator<Package> PACKAGE_COMPARATOR = (l, r) -> {

        final String leftClassName = l == null ? "" : l.getName();
        final String rightClassName = r == null ? "" : r.getName();

        // All Done.
        return leftClassName.compareTo(rightClassName);
    };

    /**
     * The map relating Concept classes - typically something like RootDoc/ClassDoc/PackageDoc from JavaDoc executions,
     * or Class/Package/Method from the java reflection structure. The important thing is that a single
     * {@link AbstractStatementConverter} should be retrieved for each leaf Concept class.
     */
    protected SortedMap<Class<?>, AbstractStatementConverter> statementConverters;

    /**
     * Default constructor.
     */
    protected AbstractGraphConverter() {

        // Create internal state
        this.statementConverters = new TreeMap<>(CLASS_COMPARATOR);
    }

    /**
     * Retrieves an {@link AbstractStatementConverter} subclass which can convert the given Concept
     * class into a {@link Statement}. Null arguments are not permitted.
     *
     * @param conceptClass The class to convert into a Statement.
     * @param <Concept>    The type to convert.
     * @param <S>          The Statement subclass.
     * @return The {@link AbstractStatementConverter} subclass which manages the conversion - or null should none exist.
     */
    public <Concept, S extends Statement> AbstractStatementConverter<Concept, S> get(
            final Class<Concept> conceptClass) {

        // Check sanity
        if (conceptClass == null) {
            throw new NullPointerException("Cannot handle null 'conceptClass' argument.");
        }

        // Fetch stashed converters
        final AbstractStatementConverter<Concept, S> toReturn = statementConverters.get(conceptClass);
        if (toReturn == null) {

            if (log.isDebugEnabled()) {
                log.debug("No AbstractStatementConverter to convert [" + conceptClass.getName()
                        + "] into a Statement subtype was registered.");
            }
        }

        // All Done.
        return toReturn;
    }

    /**
     * Adds the supplied {@link AbstractStatementConverter} for the given Concept class.
     * Null arguments are not permitted.
     *
     * @param converter    The Converter to add.
     * @param conceptClass The class to convert into a Statement.
     * @param <Concept>    The type to convert.
     * @param <S>          The Statement subclass.
     */
    public <Concept, S extends Statement> void put(final Class<Concept> conceptClass,
                                                   final AbstractStatementConverter<Concept, S> converter) {

        // Check sanity
        if (conceptClass == null) {
            throw new NullPointerException("Cannot handle null 'conceptClass' argument.");
        }
        if (converter == null) {
            throw new NullPointerException("Cannot handle null 'converter' argument.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Graph convert(final From toConvert) {

        // Handle nulls
        if (toConvert == null && !acceptsNullValues()) {
            throw new IllegalArgumentException("Converter ["
                    + getClass().getName() + "] cannot accept null 'toConvert' arguments.");
        }

        // All Done
        return doConvert(toConvert);
    }

    /**
     * Override this method to convert the supplied non-null toConvert object into a {@link Graph}.
     *
     * @param toConvert The non-null object to convert.
     * @return The converted result.
     */
    protected abstract Graph doConvert(final From toConvert);
}
