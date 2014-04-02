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

import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;

/**
 * Factory-type specification for how to generate a ClassLoading descriptor.
 * Typical use-case:
 * <p/>
 * <pre>
 *     <code>
 *         final DescriptorFactory factory = ....
 *         final List&lt;Dependency&gt; dependencies = ...
 *
 *         // Add all relevant Dependencies
 *         for(Dependency current : dependencies) {
 *             factory.addDependency(current);
 *         }
 *
 *         // Generate the descriptor content
 *         final String content = factory.makeClassloadingDescriptor(theMavenProject);
 *     </code>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface DescriptorFactory {

    /**
     * Adds the supplied dependency to the state of this DescriptorFactory, which may or may
     * not use it in order to generate a ClassLoading descriptor.
     *
     * @param dependency a Maven Dependency which can be used to generate a ClassLoading descriptor.
     */
    void addDependency(Dependency dependency);

    /**
     * Builds the ClassLoading descriptor from internal data and optionally data harvested from
     * the supplied MavenProject.
     *
     * @param project The MavenProject for which the ClassLoading descriptor should be generated.
     * @return The content of the ClassLoading descriptor.
     */
    String makeClassloadingDescriptor(MavenProject project);
}
