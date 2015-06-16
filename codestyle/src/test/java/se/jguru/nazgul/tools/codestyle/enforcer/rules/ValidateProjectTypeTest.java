/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2015 jGuru Europe AB
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

import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Test;

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
        final MavenProject project = MavenTestUtils.readPom("testdata/poms/tools-parent.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }

    @Test
    public void validateTestProjectTypePom() throws Exception {

        // Assemble
        final MavenProject project = MavenTestUtils.readPom("testdata/poms/osgi-test-pom.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }

    @Test
    public void validateAspectPom() throws Exception {

        // Assemble
        final MavenProject project = MavenTestUtils.readPom("testdata/poms/aspect-project.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }

    @Test(expected = EnforcerRuleException.class)
    public void validateExceptionOnParentPomWithModules() throws Exception {

        // Assemble
        final MavenProject project = MavenTestUtils.readPom("testdata/poms/incorrect-parent-with-modules.xml");
        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);
        final ValidateProjectType unitUnderTest = new ValidateProjectType();

        // Act & Assert
        unitUnderTest.execute(mockHelper);
    }
}
