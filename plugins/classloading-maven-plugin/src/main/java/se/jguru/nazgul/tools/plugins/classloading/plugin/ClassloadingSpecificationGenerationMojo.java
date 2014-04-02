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
package se.jguru.nazgul.tools.plugins.classloading.plugin;

import org.apache.commons.lang.Validate;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import se.jguru.nazgul.tools.plugins.classloading.plugin.generator.DescriptorFactory;
import se.jguru.nazgul.tools.plugins.classloading.plugin.generator.DescriptorFactoryFactory;

/**
 * Mojo implementation for generating ClassLoading specification files.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@Mojo(name = "generateClassLoadingDescriptor",
        defaultPhase = LifecyclePhase.GENERATE_RESOURCES,
        requiresDependencyCollection = ResolutionScope.RUNTIME_PLUS_SYSTEM,
        requiresDependencyResolution = ResolutionScope.RUNTIME_PLUS_SYSTEM)
public class ClassloadingSpecificationGenerationMojo extends AbstractMojo {

    /**
     * The type of generator that should be used.
     */
    @Parameter
    private String generatorType;

    /**
     * The version of the DescriptorFactory that should be used.
     */
    @Parameter
    private String generatorVersion;

    @Component
    private MavenProject project;
    private DescriptorFactory factory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        // Check sanity
        if (project == null) {
            throw new MojoFailureException("The ClassloadingSpecificationGenerationMojo must be run as "
                    + "part of a Maven build, since it needs all dependencies of a project to generate "
                    + "the ClassLoading descriptor.");
        }

        // Find the DescriptionFactory
        final DescriptorFactory factory = DescriptorFactoryFactory.make("jboss", "v10");

    }

    //
    // Private helpers
    //


}
