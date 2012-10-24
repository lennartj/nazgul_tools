/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ValidateProjectTypeTest {

    @Test
    public void validateCacheability() {

        // Assemble
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        Assert.assertNull(unitUnderTest.getCacheId());
        Assert.assertFalse(unitUnderTest.isResultValid(null));
    }

    @Test
    public void validateCorrectPom() throws Exception {

        // Assemble
        final MavenProject project = readPom("testdata/poms/tools-parent.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }

    @Test
    public void validateTestProjectTypePom() throws Exception {

        // Assemble
        final MavenProject project = readPom("testdata/poms/osgi-test-pom.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }

    @Test
    public void validateAspectPom() throws Exception {

        // Assemble
        final MavenProject project = readPom("testdata/poms/aspect-project.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void validateExceptionOnParentPomWithModules() throws Exception {

        // Assemble
        final MavenProject project = readPom("testdata/poms/incorrect-parent-with-modules.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }

    private MavenProject readPom(final String filePath) {

        try {
            final URL pomResource = getClass().getClassLoader().getResource(filePath);
            final Reader pomReader = new InputStreamReader(pomResource.openStream());
            return new MavenProject(new MavenXpp3Reader().read(pomReader));
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not read pom from [" + filePath + "]", e);
        }
    }
}
