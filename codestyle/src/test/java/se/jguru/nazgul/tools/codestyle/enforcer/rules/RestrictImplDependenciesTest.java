/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
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

import org.apache.maven.artifact.Artifact;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.testing.stubs.ArtifactStub;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RestrictImplDependenciesTest {

    // Shared state
    private final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
    private MavenProjectStub fooApiProject;
    private MavenProjectStub fooSpiProject;
    private ArtifactStub fooImplementation;

    @Before
    public void setupSharedState() {

        // Create a correctly named implementation project Artifact
        fooImplementation = new ArtifactStub();
        fooImplementation.setGroupId("se.jguru.nazgul.foo.impl.something");
        fooImplementation.setArtifactId("foo-impl-something");
        fooImplementation.setVersion("1.0.0");
        fooImplementation.setScope(Artifact.SCOPE_COMPILE);
        fooImplementation.setType("bundle");

        // Create a standard-formatted api mavenProject
        fooApiProject = new MavenProjectStub();
        fooApiProject.setGroupId("se.jguru.nazgul.foo.api");
        fooApiProject.setArtifactId("nazgul-foo-api");
        fooApiProject.setVersion("1.0.0");
        fooApiProject.setPackaging("bundle");

        // Create a standard-formatted spi mavenProject
        fooSpiProject = new MavenProjectStub();
        fooSpiProject.setGroupId("se.jguru.nazgul.foo.spi.bar");
        fooSpiProject.setArtifactId("foo-spi-bar");
        fooSpiProject.setVersion("1.0.0");
        fooSpiProject.setPackaging("bundle");

        // Make sure we have non-null DependencyArtifacts sets.
        fooApiProject.setDependencyArtifacts(new HashSet<Artifact>());
        fooSpiProject.setDependencyArtifacts(new HashSet<Artifact>());
    }

    @Test
    public void validateCacheability() {

        // Act & Assert
        Assert.assertNull(unitUnderTest.getCacheId());
        Assert.assertFalse(unitUnderTest.isResultValid(null));
    }

    
	@Test
	@SuppressWarnings("unchecked")
    public void validateIgnoreEvaluationOnEarPackaging() throws Exception {

        // Assemble
        final MavenProject earProject = getNazgulGroupIdStub("someApplication", "ear");
        earProject.getDependencyArtifacts().add(fooImplementation);
        
        // Act & Assert
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(earProject);
        unitUnderTest.execute(mock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void validateIgnoreEvaluationOnWarPackaging() throws Exception {

        // Assemble
        final MavenProject warProject = getNazgulGroupIdStub("someWebappliation", "war");
        warProject.getDependencyArtifacts().add(fooImplementation);

        // Act & Assert
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(warProject);
        unitUnderTest.execute(mock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void validateIgnoreEvaluationOnPomPackaging() throws Exception {

        // Assemble
        final MavenProject reactorProject = getStub("se.jguru.nazgul.foo", "nazgul-foo-reactor", "1.0.0", "pom");
        reactorProject.getDependencyArtifacts().add(fooImplementation);

        // Act & Assert
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(reactorProject);
        unitUnderTest.execute(mock);
    }

    @Test(expected = EnforcerRuleException.class)
    @SuppressWarnings("unchecked")
    public void validateExceptionOnCompileScopeImplementationDependency() throws Exception {

        // Assemble
        fooApiProject.getDependencyArtifacts().add(fooImplementation);

        // Act & Assert
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(fooApiProject);
        unitUnderTest.execute(mock);
    }

    @Test(expected = EnforcerRuleException.class)
    @SuppressWarnings("unchecked")
    public void validateExceptionOnCompileScopeImplementationDependencyInSpi() throws Exception {

        // Assemble
        fooSpiProject.getDependencyArtifacts().add(fooImplementation);

        // Act & Assert
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(fooSpiProject);
        unitUnderTest.execute(mock);
    }

    @Test(expected = EnforcerRuleException.class)
    @SuppressWarnings("unchecked")
    public void validateExceptionOnCompileScopeImplementationDependencyInImpl() throws Exception {

        // Assemble
        fooSpiProject.getDependencyArtifacts().add(fooImplementation);

        // Act & Assert
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(fooSpiProject);
        unitUnderTest.execute(mock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void validateNoExceptionOnTestScopeDependency() throws Exception {

        // Assemble
        fooImplementation.setScope(Artifact.SCOPE_TEST);
        fooApiProject.getDependencyArtifacts().add(fooImplementation);

        // Act & Assert
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(fooApiProject);
        unitUnderTest.execute(mock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void validateExceptionMessage() throws Exception {

        // Assemble
        fooApiProject.getDependencyArtifacts().add(fooImplementation);
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(fooApiProject);

        // Act & Assert
        try {
            unitUnderTest.execute(mock);
            Assert.fail("We should acquire an exception here.");
        } catch (EnforcerRuleException e) {

            final String expectedOffendingArtifact = "# Offending artifact " +
                    "[se.jguru.nazgul.foo.impl.something:foo-impl-something:1.0.0]";
            final String expectedOffendingProject = "# Offending project " +
                    "[se.jguru.nazgul.foo.api:nazgul-foo-api:1.0.0]";

            final String message = e.getMessage();
            Assert.assertTrue(message.contains(expectedOffendingArtifact));
            Assert.assertTrue(message.contains(expectedOffendingProject));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught wrong kind of exception.");
        }
    }

    @Test(expected = EnforcerRuleException.class)
    public void validateExceptionOnExecutingEnforcementRuleOnUnknownProjectType() throws Exception {

        // Assemble
        final MavenProject incorrectProject = new MavenProjectStub();
        incorrectProject.setGroupId("se.jguru.nazgul.incorrect");
        incorrectProject.setArtifactId("nazgul-foo-incorrect");
        incorrectProject.setVersion("1.0.0");
        incorrectProject.setPackaging("bundle");

        // Act & Assert
        unitUnderTest.execute(new MockEnforcerRuleHelper(incorrectProject));
    }

    @Test
    public void validateRegexpMatching() {

        // Assemble
        // final String ptn = "^se\\.jguru\\.nazgul\\.*\\.generated\\.*";
        // final String ptn = "^se\\.jguru\\.nazgul\\.*";
        final String ptn = "se\\.jguru\\.nazgul\\..*\\.generated\\..*";
        final Pattern pattern = Pattern.compile(ptn);

        // Act & Assert
        Assert.assertTrue(pattern.matcher("se.jguru.nazgul.foo.generated.impl.bar").matches());
    }

    @Test
    public void validateExclusionOfGroupIDsDoesNotYieldExceptionWhenIllegalImportsExist() throws Exception {

        // Assemble
        final MavenProjectStub excludedProject = new MavenProjectStub();
        excludedProject.setGroupId("se.jguru.nazgul.foo.generated.bar.api");
        excludedProject.setArtifactId("bar-api");
        excludedProject.setVersion("1.0.0");
        excludedProject.setPackaging("bundle");

        final HashSet<Artifact> dependencyArtifacts = new HashSet<Artifact>();
        dependencyArtifacts.add(fooImplementation);
        excludedProject.setDependencyArtifacts(dependencyArtifacts);

        // Act & Assert
        unitUnderTest.setExcludedGroupIdPatterns("^se\\.jguru\\.nazgul\\..*\\.generated\\..*");
        unitUnderTest.execute(new MockEnforcerRuleHelper(excludedProject));
    }

    @Test
    public void validateNoEvaluationIfProjectIsNotIncluded() throws Exception {

        // Assemble
        final MavenProjectStub excludedProject = new MavenProjectStub();
        excludedProject.setGroupId("org.foobar.api");
        excludedProject.setArtifactId("bar-api");
        excludedProject.setVersion("1.0.0");
        excludedProject.setPackaging("bundle");

        // Act & Assert
        unitUnderTest.execute(new MockEnforcerRuleHelper(excludedProject));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void validateNoEnforcementOfArtifactsWithIgnoredGroupIDs() throws Exception {

        // Assemble
        final ArtifactStub ignoredImplementationDependency = new ArtifactStub();
        ignoredImplementationDependency.setGroupId("se.jguru.nazgul.foo.generated.impl.something");
        ignoredImplementationDependency.setArtifactId("foo-impl-something");
        ignoredImplementationDependency.setVersion("1.0.0");
        ignoredImplementationDependency.setScope(Artifact.SCOPE_COMPILE);
        ignoredImplementationDependency.setType("bundle");

        final ArtifactStub nonIncludedDependency = new ArtifactStub();
        nonIncludedDependency.setGroupId("org.foobar.something");
        nonIncludedDependency.setArtifactId("something");
        nonIncludedDependency.setVersion("11.2");
        nonIncludedDependency.setScope(Artifact.SCOPE_COMPILE);
        nonIncludedDependency.setType("jar");

        fooApiProject.getDependencyArtifacts().add(ignoredImplementationDependency);
        fooApiProject.getDependencyArtifacts().add(nonIncludedDependency);

        // Act & Assert
        unitUnderTest.execute(new MockEnforcerRuleHelper(fooApiProject));
    }

    @Test(expected = EnforcerRuleException.class)
    @SuppressWarnings("unchecked")
    public void validateExceptionOnFindingApplicationDependenciesInAPIs() throws Exception {

        // Assemble
        final ArtifactStub ignoredImplementationDependency = new ArtifactStub();
        ignoredImplementationDependency.setGroupId("se.jguru.nazgul.foo");
        ignoredImplementationDependency.setArtifactId("fooWarApplicaion");
        ignoredImplementationDependency.setVersion("1.0.0");
        ignoredImplementationDependency.setScope(Artifact.SCOPE_COMPILE);
        ignoredImplementationDependency.setType("war");

        fooApiProject.getDependencyArtifacts().add(ignoredImplementationDependency);

        // Act & Assert
        unitUnderTest.execute(new MockEnforcerRuleHelper(fooApiProject));
    }

    @Test(expected = EnforcerRuleException.class)
    @SuppressWarnings("unchecked")
    public void validateExceptionOnFindingTestUtilityDependenciesInAPIs() throws Exception {

        // Assemble
        final ArtifactStub ignoredImplementationDependency = new ArtifactStub();
        ignoredImplementationDependency.setGroupId("se.jguru.nazgul.test.foo");
        ignoredImplementationDependency.setArtifactId("foobar-test");
        ignoredImplementationDependency.setVersion("1.0.0");
        ignoredImplementationDependency.setScope(Artifact.SCOPE_COMPILE);
        ignoredImplementationDependency.setType("jar");

        fooApiProject.getDependencyArtifacts().add(ignoredImplementationDependency);

        // Act & Assert
        unitUnderTest.execute(new MockEnforcerRuleHelper(fooApiProject));
    }

    @Test(expected = EnforcerRuleException.class)
    @SuppressWarnings("unchecked")
    public void validateExceptionOnFindingPocDependenciesInAPIs() throws Exception {

        // Assemble
        final ArtifactStub ignoredImplementationDependency = new ArtifactStub();
        ignoredImplementationDependency.setGroupId("se.jguru.nazgul.poc.foo");
        ignoredImplementationDependency.setArtifactId("foobar-poc");
        ignoredImplementationDependency.setVersion("1.0.0");
        ignoredImplementationDependency.setScope(Artifact.SCOPE_COMPILE);
        ignoredImplementationDependency.setType("jar");

        fooApiProject.getDependencyArtifacts().add(ignoredImplementationDependency);

        // Act & Assert
        unitUnderTest.execute(new MockEnforcerRuleHelper(fooApiProject));
    }

    //
    // Private helpers
    //

    private MavenProject getNazgulGroupIdStub(final String artifactID,
                                              final String packaging) {
        return getStub("se.jguru.nazgul.foo", artifactID, "1.0.0", packaging);
    }

    private MavenProject getStub(final String groupID,
                                 final String artifactID,
                                 final String version,
                                 final String packaging) {

        final MavenProject toReturn = new MavenProjectStub();
        toReturn.setGroupId(groupID);
        toReturn.setArtifactId(artifactID);
        toReturn.setVersion(version);
        toReturn.setPackaging(packaging);

        // Make sure we have a non-null DependencyArtifacts set.
        toReturn.setDependencyArtifacts(new HashSet<Object>());

        return toReturn;
    }
}
