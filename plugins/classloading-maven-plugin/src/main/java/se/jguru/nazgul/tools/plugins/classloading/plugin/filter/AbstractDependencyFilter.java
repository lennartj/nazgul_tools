/*
 * #%L
 * Nazgul Project: nazgul-tools-classloading-maven-plugin
 * %%
 * Copyright (C) 2010 - 2014 jGuru Europe AB
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
package se.jguru.nazgul.tools.plugins.classloading.plugin.filter;

import org.apache.commons.lang.Validate;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.logging.Log;
import se.jguru.nazgul.tools.plugins.classloading.plugin.AbstractLogAware;

/**
 * Abstract DependencyFilter implementation catering for Comparability and skeleton argument checking.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractDependencyFilter extends AbstractLogAware implements DependencyFilter {

    /**
     * Default execution priority for an AbstractDependencyFilter.
     */
    public static final int DEFAULT_PRIORITY = 100;

    // Internal state
    private int priority;

    /**
     * Creates a new AbstractDependencyFilter using the supplied Maven Log, and given DEFAULT_PRIORITY.
     *
     * @param log A non-null Maven Log used by this AbstractDependencyFilter.
     */
    protected AbstractDependencyFilter(final Log log) {
        this(log, DEFAULT_PRIORITY);
    }

    /**
     * Creates a new AbstractDependencyFilter wrapping the supplied data.
     *
     * @param log      A non-null Maven Log used by this AbstractDependencyFilter.
     * @param priority The priority of this AbstractDependencyFilter. Should be a positive integer,
     *                 indicating its reverse execution order.
     */
    protected AbstractDependencyFilter(final Log log, final int priority) {

        super(log);

        // Check sanity
        Validate.isTrue(priority > 0, "Priority should be a positive integer. (Got: " + priority + ").");

        // Assign internal state
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isImported(final Dependency dependency) {

        // Check sanity and delegate.
        if (!isNull(dependency, "dependency")) {
            return shouldBeImported(dependency);
        }

        // Not imported
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isExported(final Dependency dependency) {

        // Check sanity and delegate.
        if (!isNull(dependency, "dependency")) {
            return shouldBeExported(dependency);
        }

        // Not exported
        return false;
    }

    /**
     * Indicates if the supplied Dependency should be rendered as an import/private dependency within the
     * ClassLoading descriptor generated.
     *
     * @param nonNullDependency The non-null Maven Dependency to classify.
     * @return {@code true} if the supplied Dependency should be rendered as an import/private dependency.
     */
    protected abstract boolean shouldBeImported(final Dependency nonNullDependency);

    /**
     * Indicates if the supplied Dependency should be presented as an import and as an export (i.e. publicly
     * supplied) dependency ClassLoading descriptor generated.
     *
     * @param nonNullDependency The non-null Maven Dependency to classify.
     * @return {@code true} if the supplied Dependency should be rendered as an export/publicly supplied dependency.
     */
    protected abstract boolean shouldBeExported(final Dependency nonNullDependency);

    /**
     * Compares two DependencyFilter with respect to their priority.
     *
     * @param that Another DependencyFilter with which we should compare ourselves for order.
     * @return The priority difference between two DependencyFilter instances.
     */
    @Override
    public int compareTo(final DependencyFilter that) {

        // Check sanity
        if (that == null) {
            throw new IllegalArgumentException("Cannot compare DependencyFilter of type ["
                    + getClass().getName() + "] with null.");
        }

        // Simply compare the priorities
        return getPriority() - that.getPriority();
    }
}
