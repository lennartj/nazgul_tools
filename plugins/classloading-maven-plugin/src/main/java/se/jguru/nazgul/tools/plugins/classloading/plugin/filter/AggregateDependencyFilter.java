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

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Aggregate DependencyFilter implementation, delegating actual work to a List of
 * internally held DependencyFilters.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class AggregateDependencyFilter extends AbstractDependencyFilter {

    // Internal state
    private SortedMap<Integer, DependencyFilter> dependencyFilterMap;

    public AggregateDependencyFilter(final Log log) {

        // Use a really low priority for this AbstractDependencyFilter
        super(log, 1);

        // Assign internal state
        this.dependencyFilterMap = new TreeMap<Integer, DependencyFilter>(Collections.reverseOrder());
    }

    /**
     * Adds the supplied DependencyFilter to this AbstractDependencyFilter.
     *
     * @param toAdd The DependencyFilter to add to this AggregateDependencyFilter.
     */
    public void addDependencyFilter(final DependencyFilter toAdd) {

        // Check sanity
        Validate.notNull(toAdd, "Cannot handle null toAdd argument.");

        // Add the supplied DependencyFilter to the known List
        int currentPriority = toAdd.getPriority();
        while(dependencyFilterMap.keySet().contains(currentPriority)) {

            if(currentPriority > 0) {
                currentPriority--;
            } else {

                // Whoops
                throw new IllegalStateException(
                        "Could not find a non-occupied positive priority for DependencyFilter [" + toAdd + "]. "
                                + toString());
            }
        }

        // Found a priority slot
        dependencyFilterMap.put(currentPriority, toAdd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldBeImported(final Dependency nonNullDependency) {

        // Check sanity
        validateFilterList("import");

        for(Map.Entry<Integer, DependencyFilter> current : dependencyFilterMap.entrySet()) {
            if(current.getValue().isImported(nonNullDependency)) {

                // The dependency should be imported.
                return true;
            }
        }

        // Not imported.
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldBeExported(final Dependency nonNullDependency) {

        // Check sanity
        validateFilterList("export");

        for(Map.Entry<Integer, DependencyFilter> current : dependencyFilterMap.entrySet()) {
            if(current.getValue().isExported(nonNullDependency)) {

                // The dependency should be imported.
                return true;
            }
        }

        // Not imported.
        return false;
    }

    /**
     * @return A debug printout containing the current priority map within this AggregateDependencyFilter.
     */
    @Override
    public String toString() {
        return "AggregateDependencyFilter with dependencyFilterMap: " + dependencyFilterMap;
    }

    //
    // Private helpers
    //

    private void validateFilterList(final String caller) {

        if(dependencyFilterMap.size() == 0) {
            log.warn("No DependencyFilters added, implying that this AggregateDependencyFilter "
                    + "cannot classify Maven Dependencies for " + caller
                    + ". Ensure that you add at least one DependencyFilter before using an AggregateDependencyFilter.");
        }
    }
}
