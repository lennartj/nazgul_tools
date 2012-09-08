/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidateProjectNamesTest {

    @Test
    public void validateExceptionOnUnknownProjectType() throws Exception {

        // Assemble
        final MavenProject project = getStub("jar", "se.jguru.foo.bar", "no-pattern-should-match");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectNames unitUnderTest = new ValidateProjectNames();

        // Act
        unitUnderTest.execute(mockHelper);

        // Assert
    }

    private MavenProject getStub(final String packaging, final String groupId, final String artifactId) {

        final MavenProject project = new MavenProjectStub();
        project.setPackaging(packaging);
        project.setVersion("1.0.0");
        project.setArtifactId(artifactId);
        project.setGroupId(groupId);

        return project;
    }
}
