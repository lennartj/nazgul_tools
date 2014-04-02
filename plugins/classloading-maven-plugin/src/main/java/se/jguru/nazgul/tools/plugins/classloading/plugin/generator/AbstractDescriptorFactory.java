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
package se.jguru.nazgul.tools.plugins.classloading.plugin.generator;

import org.apache.commons.lang.Validate;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.logging.Log;
import se.jguru.nazgul.tools.plugins.classloading.plugin.AbstractLogAware;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract DescriptorFactory implementation handling adding Maven Dependencies, and sporting
 * a commonly used Log and dependency sets
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractDescriptorFactory extends AbstractLogAware implements DescriptorFactory {

    /**
     * A Set holding all Dependencies that should be presented as both visible and exported to projects
     * importing the active project in the ClassLoading descriptor emitted by this AbstractDescriptorFactory.
     */
    protected Set<Dependency> visibleAndExported;

    /**
     * A Set holding all Dependencies that should be presented as visible to projects
     * importing the active project in the ClassLoading descriptor emitted by this AbstractDescriptorFactory.
     */
    protected Set<Dependency> visible;

    /**
     * Default constructor; creates an empty internal state for this AbstractDescriptorFactory.
     */
    protected AbstractDescriptorFactory(final Log log) {

        super(log);

        // Assign internal state
        visibleAndExported = new HashSet<Dependency>();
        visible = new HashSet<Dependency>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDependency(final Dependency dependency) {

        // Check sanity and add the dependency
        Validate.notNull(dependency, "Cannot handle null dependency argument.");
    }
}
