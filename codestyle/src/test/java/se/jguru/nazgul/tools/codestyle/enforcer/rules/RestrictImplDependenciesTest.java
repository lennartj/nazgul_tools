/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.testing.stubs.ArtifactStub;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RestrictImplDependenciesTest {

    @Test
    public void validateIgnoreEvaluationOnEarPackaging() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject earProject = getStubWithImplDependency("ear");
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(earProject);

        // Act & Assert
        unitUnderTest.execute(mock);
    }

    @Test
    public void validateIgnoreEvaluationOnWarPackaging() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject warProject = getStubWithImplDependency("war");
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(warProject);

        // Act & Assert
        unitUnderTest.execute(mock);
    }

    @Test
    public void validateIgnoreEvaluationOnPomPackaging() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject reactorProject = getStubWithImplDependency("pom");
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(reactorProject);

        // Act & Assert
        unitUnderTest.execute(mock);
    }

    @Test(expected = EnforcerRuleException.class)
    public void validateExceptionOnCompileScopeDependency() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject jarProject = getStubWithImplDependency("jar");
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(jarProject);

        // Act & Assert
        unitUnderTest.execute(mock);
    }

    @Test
    public void validateNoExceptionOnTestScopeDependency() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject jarProject = getStubWithImplDependency("jar", Artifact.SCOPE_TEST);
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(jarProject);

        // Act & Assert
        unitUnderTest.execute(mock);
    }

    @Test
    public void validateExceptionMessage() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject jarProject = getStubWithImplDependency("jar", Artifact.SCOPE_COMPILE);
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(jarProject);

        // Act & Assert
        try {
            unitUnderTest.execute(mock);
            Assert.fail("We should acquire an exception here.");
        } catch (EnforcerRuleException e) {

            final String expectedOffendingArtifact = "# Offending artifact " +
                    "[org.apache.tomcat.foo.bar:dummy-adapter-impl:1.2.3]";
            final String expectedOffendingProject = "# Offending project " +
                    "[org.apache.tomcat.foo:ProjectArtifactID:1.0.0]";

            String message = e.getMessage();
            Assert.assertTrue(message.indexOf(expectedOffendingArtifact) != -1);
            Assert.assertTrue(message.indexOf(expectedOffendingProject) != -1);
        } catch (Exception e) {
            Assert.fail("Caught wrong kind of exception.");
        }
    }

    @Test(expected = EnforcerRuleException.class)
    public void validateExplicitInclusionOfGroupIDs() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject earProject = getStubWithImplDependency("jar", Artifact.SCOPE_COMPILE);
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(earProject);

        // Act & Assert
        unitUnderTest.setIncludedGroupIdPrefixes("org.apache.tomcat");
        unitUnderTest.execute(mock);
    }

    @Test
    public void validateExplicitExclusionOfGroupIDs() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject earProject = getStubWithImplDependency("jar", Artifact.SCOPE_COMPILE);
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(earProject);

        // Act & Assert
        unitUnderTest.setExcludedGroupIdPrefixes("org.apache.tomcat");
        unitUnderTest.execute(mock);
    }

    @Test
    public void validateExplicitExclusionOfArtifactGroupIDs() throws Exception {

        // Assemble
        final RestrictImplDependencies unitUnderTest = new RestrictImplDependencies();
        final MavenProject earProject = getStubWithImplDependency("jar", Artifact.SCOPE_COMPILE);
        final EnforcerRuleHelper mock = new MockEnforcerRuleHelper(earProject);

        // Act & Assert
        unitUnderTest.setIgnoreEvaluatingArtifactsWithGroupIdPrefixes("org.apache.tomcat");
        unitUnderTest.execute(mock);
    }

    //
    // Private helpers
    //

    private MavenProject getStubWithImplDependency(final String packaging) {
        return getStubWithImplDependency(packaging, Artifact.SCOPE_COMPILE);
    }

    private MavenProject getStubWithImplDependency(final String packaging, final String dependencyScope) {

        final MavenProject project = new MavenProjectStub();
        project.setPackaging(packaging);
        project.setVersion("1.0.0");
        project.setArtifactId("ProjectArtifactID");
        project.setGroupId("org.apache.tomcat.foo");

        final Artifact implArtifact = new ArtifactStub();
        implArtifact.setGroupId("org.apache.tomcat.foo.bar");
        implArtifact.setArtifactId("dummy-adapter-impl");
        implArtifact.setVersion("1.2.3");
        implArtifact.setScope(dependencyScope);

        final Set<Artifact> dependencyArtifacts = new HashSet<Artifact>();
        dependencyArtifacts.add(implArtifact);
        project.setDependencyArtifacts(dependencyArtifacts);

        return project;
    }
}
