/*
 * #%L
 * nazgul-tools-validation-api
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
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

import org.junit.Assert;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ProjectTypeTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnIncorrectProjectTypeSpecification() {

        // Assemble
        final MavenProject stub = getStub("bundle", "se.jguru.foo.bar", "incognito");

        // Act & Assert
        ProjectType.getProjectType(stub);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnReactorProjectWithDependencies() {

        // Assemble
        final MavenProject reactor = getStub("pom", "se.jguru.foo.bar", "foo-reactor");

        final Dependency aDependency = new Dependency();
        aDependency.setGroupId("se.jguru.foo");
        aDependency.setArtifactId("foobar");
        aDependency.setVersion("1.0.0-SNAPSHOT");
        reactor.getModel().addDependency(aDependency);

        // Act
        ProjectType.getProjectType(reactor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnParentProjectWithModules() {

        // Assemble
        final MavenProject parent = getStub("pom", "se.jguru.foo.bar", "foo-parent");
        parent.getModel().addModule("aChildModule");

        // Act
        ProjectType.getProjectType(parent);
    }

    @Test
    public void validateParsingProjectTypes() {

        // Assemble
        final MavenProject parent = getStub("pom", "se.jguru.foo.bar", "bar-parent");
        final MavenProject reactor = getStub("pom", "se.jguru.foo.bar", "bar-reactor");
        final MavenProject model = getStub("bundle", "se.jguru.foo.bar.model", "bar-model");
        final MavenProject api = getStub("bundle", "se.jguru.foo.bar.api", "bar-api");
        final MavenProject spi = getStub("bundle", "se.jguru.foo.bar.spi.something", "bar-spi-something");
        final MavenProject impl = getStub("bundle", "se.jguru.foo.bar.impl.something", "bar-impl-something");
        final MavenProject test = getStub("jar", "se.jguru.foo.bar.test.something", "bar-test");
        final MavenProject poc = getStub("bundle", "se.jguru.foo.bar.poc.something", "bar-poc");
        final MavenProject war = getStub("war", "se.jguru.foo.applications.bar", "bar-war");
        final MavenProject ear = getStub("ear", "se.jguru.foo.applications.bar", "bar-ear");
        final MavenProject ejb = getStub("ejb", "se.jguru.foo.applications.bar", "bar-ejb");
        final MavenProject codestyle = getStub("jar", "se.jguru.foo.codestyle", "bar-codestyle");
        final MavenProject javaAgent = getStub("bundle", "se.jguru.foo.bar.agent", "bar-agent");
        final MavenProject standaloneApplication1
                = getStub("bundle", "se.jguru.foo.bar.application", "bar-application");
        final MavenProject standaloneApplication2
                = getStub("jar", "se.jguru.foo.bar.application", "bar-application");

        // Act & Assert
        Assert.assertEquals(ProjectType.PARENT, ProjectType.getProjectType(parent));
        Assert.assertEquals(ProjectType.REACTOR, ProjectType.getProjectType(reactor));
        Assert.assertEquals(ProjectType.MODEL, ProjectType.getProjectType(model));
        Assert.assertEquals(ProjectType.API, ProjectType.getProjectType(api));
        Assert.assertEquals(ProjectType.SPI, ProjectType.getProjectType(spi));
        Assert.assertEquals(ProjectType.IMPLEMENTATION, ProjectType.getProjectType(impl));
        Assert.assertEquals(ProjectType.TEST, ProjectType.getProjectType(test));
        Assert.assertEquals(ProjectType.PROOF_OF_CONCEPT, ProjectType.getProjectType(poc));
        Assert.assertEquals(ProjectType.JEE_APPLICATION, ProjectType.getProjectType(war));
        Assert.assertEquals(ProjectType.JEE_APPLICATION, ProjectType.getProjectType(ear));
        Assert.assertEquals(ProjectType.JEE_APPLICATION, ProjectType.getProjectType(ejb));
        Assert.assertEquals(ProjectType.JAVA_AGENT, ProjectType.getProjectType(javaAgent));
        Assert.assertEquals(ProjectType.STANDALONE_APPLICATION, ProjectType.getProjectType(standaloneApplication1));
        Assert.assertEquals(ProjectType.STANDALONE_APPLICATION, ProjectType.getProjectType(standaloneApplication2));
        Assert.assertEquals(ProjectType.CODESTYLE, ProjectType.getProjectType(codestyle));
    }

    @Test
    public void validateModelProjectPatterns() {

        // Act & Assert
        Assert.assertTrue(ProjectType.MODEL.isCompliantArtifactID("test-foo-model"));
        Assert.assertFalse(ProjectType.MODEL.isCompliantArtifactID("model-test"));
        Assert.assertFalse(ProjectType.MODEL.isCompliantArtifactID("foo-model-test"));

        Assert.assertTrue(ProjectType.MODEL.isCompliantGroupID("test.foo.model"));
        Assert.assertFalse(ProjectType.MODEL.isCompliantGroupID("model.test"));
        Assert.assertFalse(ProjectType.MODEL.isCompliantGroupID("foo.model.test"));

        Assert.assertFalse(ProjectType.MODEL.isCompliantPackaging("pom"));
        Assert.assertFalse(ProjectType.MODEL.isCompliantPackaging("jar"));
        Assert.assertTrue(ProjectType.MODEL.isCompliantPackaging("bundle"));
    }

    @Test
    public void validateReactorProjectPatterns() {

        // Act & Assert
        Assert.assertTrue(ProjectType.REACTOR.isCompliantArtifactID("test-foo-reactor"));
        Assert.assertFalse(ProjectType.REACTOR.isCompliantArtifactID("reactor-test"));
        Assert.assertFalse(ProjectType.REACTOR.isCompliantArtifactID("foo-reactor-test"));

        Assert.assertTrue(ProjectType.REACTOR.isCompliantGroupID("test.foo.reactor"));
        Assert.assertTrue(ProjectType.REACTOR.isCompliantGroupID("reactor.test"));
        Assert.assertTrue(ProjectType.REACTOR.isCompliantGroupID("foo.model.test"));

        Assert.assertTrue(ProjectType.REACTOR.isCompliantPackaging("pom"));
        Assert.assertFalse(ProjectType.REACTOR.isCompliantPackaging("jar"));
        Assert.assertFalse(ProjectType.REACTOR.isCompliantPackaging("bundle"));
    }

    @Test
    public void validateApiProjectPatterns() {

        // Act & Assert
        Assert.assertTrue(ProjectType.API.isCompliantArtifactID("test-foo-api"));
        Assert.assertFalse(ProjectType.API.isCompliantArtifactID("test-api-foo"));
        Assert.assertFalse(ProjectType.API.isCompliantArtifactID("api-test"));
        Assert.assertFalse(ProjectType.API.isCompliantArtifactID(null));

        Assert.assertFalse(ProjectType.API.isCompliantGroupID("test.api.foo"));
        Assert.assertFalse(ProjectType.API.isCompliantGroupID("api.test"));
        Assert.assertTrue(ProjectType.API.isCompliantGroupID("test.api"));
        Assert.assertFalse(ProjectType.API.isCompliantGroupID(null));

        Assert.assertFalse(ProjectType.API.isCompliantPackaging("pom"));
        Assert.assertFalse(ProjectType.API.isCompliantPackaging("jar"));
        Assert.assertTrue(ProjectType.API.isCompliantPackaging("bundle"));
        Assert.assertFalse(ProjectType.API.isCompliantPackaging(null));
    }

    @Test
    public void validateSpiProjectPatterns() {

        // Act & Assert
        Assert.assertFalse(ProjectType.SPI.isCompliantArtifactID("test-foo-spi"));
        Assert.assertTrue(ProjectType.SPI.isCompliantArtifactID("test-spi-foo"));
        Assert.assertFalse(ProjectType.SPI.isCompliantArtifactID("spi-test"));

        Assert.assertTrue(ProjectType.SPI.isCompliantGroupID("test.spi.foo"));
        Assert.assertFalse(ProjectType.SPI.isCompliantGroupID("spi.test"));
        Assert.assertFalse(ProjectType.SPI.isCompliantGroupID("test.spi"));

        Assert.assertFalse(ProjectType.SPI.isCompliantPackaging("pom"));
        Assert.assertFalse(ProjectType.SPI.isCompliantPackaging("jar"));
        Assert.assertTrue(ProjectType.SPI.isCompliantPackaging("bundle"));
    }

    @Test
    public void validateImplementationProjectPatterns() {

        // Act & Assert
        Assert.assertFalse(ProjectType.IMPLEMENTATION.isCompliantArtifactID("test-foo-impl"));
        Assert.assertTrue(ProjectType.IMPLEMENTATION.isCompliantArtifactID("test-impl-foo"));
        Assert.assertFalse(ProjectType.IMPLEMENTATION.isCompliantArtifactID("impl-test"));

        Assert.assertTrue(ProjectType.IMPLEMENTATION.isCompliantGroupID("test.impl.foo"));
        Assert.assertFalse(ProjectType.IMPLEMENTATION.isCompliantGroupID("impl.test"));
        Assert.assertFalse(ProjectType.IMPLEMENTATION.isCompliantGroupID("test.impl"));

        Assert.assertFalse(ProjectType.IMPLEMENTATION.isCompliantPackaging("pom"));
        Assert.assertFalse(ProjectType.IMPLEMENTATION.isCompliantPackaging("jar"));
        Assert.assertTrue(ProjectType.IMPLEMENTATION.isCompliantPackaging("bundle"));
    }

    @Test
    public void validateTestProjectPatterns() {

        // Act & Assert
        Assert.assertFalse(ProjectType.TEST.isCompliantArtifactID("test-foo-impl"));
        Assert.assertFalse(ProjectType.TEST.isCompliantArtifactID("test-impl-foo"));
        Assert.assertTrue(ProjectType.TEST.isCompliantArtifactID("foo-test"));
        Assert.assertTrue(ProjectType.TEST.isCompliantArtifactID("nazgul-core-osgi-test"));

        Assert.assertFalse(ProjectType.TEST.isCompliantGroupID("test.foo"));
        Assert.assertFalse(ProjectType.TEST.isCompliantGroupID("impl.test"));
        Assert.assertTrue(ProjectType.TEST.isCompliantGroupID("some.test.foo"));
        Assert.assertTrue(ProjectType.TEST.isCompliantGroupID("se.jguru.nazgul.test.osgi"));

        Assert.assertTrue(ProjectType.TEST.isCompliantPackaging("bundle"));
        Assert.assertTrue(ProjectType.TEST.isCompliantPackaging("jar"));
        Assert.assertTrue(ProjectType.TEST.isCompliantPackaging("war"));
    }

    @Test
    public void validatePocProjectPatterns() {

        // Act & Assert
        Assert.assertFalse(ProjectType.PROOF_OF_CONCEPT.isCompliantArtifactID("poc-foo-impl"));
        Assert.assertFalse(ProjectType.PROOF_OF_CONCEPT.isCompliantArtifactID("test-poc-foo"));
        Assert.assertTrue(ProjectType.PROOF_OF_CONCEPT.isCompliantArtifactID("foo-poc"));

        Assert.assertFalse(ProjectType.PROOF_OF_CONCEPT.isCompliantGroupID("poc.foo"));
        Assert.assertFalse(ProjectType.PROOF_OF_CONCEPT.isCompliantGroupID("impl.poc"));
        Assert.assertTrue(ProjectType.PROOF_OF_CONCEPT.isCompliantGroupID("some.poc.foo"));
    }

    @Test
    public void validateCodestyleProjectPatterns() {

        // Act & Assert
        Assert.assertFalse(ProjectType.CODESTYLE.isCompliantArtifactID("codestyle-foo"));
        Assert.assertTrue(ProjectType.CODESTYLE.isCompliantArtifactID("foo-codestyle"));

        Assert.assertFalse(ProjectType.CODESTYLE.isCompliantGroupID("codestyle.foo"));
        Assert.assertTrue(ProjectType.CODESTYLE.isCompliantGroupID("foo.codestyle"));
    }

    @Test
    public void validateExampleProjectPatterns() {

        // Act & Assert
        Assert.assertTrue(ProjectType.EXAMPLE.isCompliantArtifactID("codestyle-foo-example"));
        Assert.assertFalse(ProjectType.EXAMPLE.isCompliantArtifactID("example-codestyle"));

        Assert.assertTrue(ProjectType.EXAMPLE.isCompliantGroupID("codestyle.foo.example"));
        Assert.assertFalse(ProjectType.EXAMPLE.isCompliantGroupID("foo.example.codestyle"));
    }

    //
    // Private helpers
    //

    private MavenProject getStub(final String packaging, final String groupId, final String artifactId) {

        final MavenProject project = new MavenProject();
        project.setPackaging(packaging);
        project.setVersion("1.0.0");
        project.setArtifactId(artifactId);
        project.setGroupId(groupId);

        return project;
    }
}
