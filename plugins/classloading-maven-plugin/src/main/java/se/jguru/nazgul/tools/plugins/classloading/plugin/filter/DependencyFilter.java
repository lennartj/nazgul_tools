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

import org.apache.maven.model.Dependency;

/**
 * Specification for how to treat and classify Maven Dependencies.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface DependencyFilter extends Comparable<DependencyFilter> {

    /**
     * Indicates if the supplied Dependency should be rendered as an import/private dependency within the
     * ClassLoading descriptor generated.
     *
     * @param dependency The Maven Dependency to classify.
     * @return {@code true} if the supplied Dependency should be rendered as an import/private dependency.
     */
    boolean isImported(Dependency dependency);

    /**
     * Indicates if the supplied Dependency should be presented as an import and as an export (i.e. publicly
     * supplied) dependency ClassLoading descriptor generated.
     *
     * @param dependency The Maven Dependency to classify.
     * @return {@code true} if the supplied Dependency should be rendered as an export/publicly supplied dependency.
     */
    boolean isExported(Dependency dependency);

    /**
     * @return A priority indicator, defining in which order this DependencyFilter should be executed in
     * relation to other DependencyFilter implementations. Higher priority implies earlier execution.
     */
    int getPriority();
}
