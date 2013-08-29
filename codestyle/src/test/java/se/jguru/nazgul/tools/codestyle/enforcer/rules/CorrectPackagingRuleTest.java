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
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.codestyle.enforcer.rules.source.IncorrectNoDefaultConstructorPackageExtractor;
import se.jguru.nazgul.tools.codestyle.enforcer.rules.source.JavaPackageExtractor;
import se.jguru.nazgul.tools.codestyle.enforcer.rules.source.PackageExtractor;
import se.jguru.nazgul.tools.codestyle.enforcer.rules.source.SillyPackageExtractor;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CorrectPackagingRuleTest {

    @Test
    public void validateExceptionOnIncorrectSourceCodePackaging() {

        // Assemble
        final MavenProject project = MavenTestUtils.readPom("testdata/project/incorrect/pom.xml");
        final URL compileSourceRoot = CorrectPackagingRuleTest.class.getClassLoader().getResource(
                "testdata/project/incorrect/src/main/java");
        Assert.assertNotNull("compileSourceRoot not found", compileSourceRoot);

        project.addCompileSourceRoot(compileSourceRoot.getPath());

        final EnforcerRuleHelper mockHelper = new MockEnforcerRuleHelper(project);

        final CorrectPackagingRule unitUnderTest = new CorrectPackagingRule();

        // Act & Assert
        try {
            unitUnderTest.performValidation(project, mockHelper);

            Assert.fail("CorrectPackagingRule should yield an exception for projects not "
                    + "complying with packaging rules.");

        } catch (RuleFailureException e) {

            final String message = e.getMessage();

            // Validate that the message contains the package-->fileName data
            Assert.assertTrue(
                    message.contains("se.jguru.nazgul.tools.validation.api=[Validatable.java, package-info.java]"));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnCustomPackageExtractorDoesNotImplementPackageExtractor() {

        // Assemble
        final CorrectPackagingRule unitUnderTest = new CorrectPackagingRule();

        // Act & Assert
        unitUnderTest.setPackageExtractors(MockEnforcerRuleHelper.class.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnCustomPackageExtractorHoldsNoDefaultConstructor() {

        // Assemble
        final CorrectPackagingRule unitUnderTest = new CorrectPackagingRule();

        // Act & Assert
        unitUnderTest.setPackageExtractors(IncorrectNoDefaultConstructorPackageExtractor.class.getName());
    }

    @Test
    public void validateAddingCustomPackageExtractor() throws Exception {

        // Assemble
        final CorrectPackagingRule unitUnderTest = new CorrectPackagingRule();

        // Act
        unitUnderTest.setPackageExtractors(SillyPackageExtractor.class.getName() + "," +
                "" + JavaPackageExtractor.class.getName());

        // Assert
        final Field packageExtractors = unitUnderTest.getClass().getDeclaredField("packageExtractors");
        packageExtractors.setAccessible(true);

        final List<PackageExtractor> extractors = (List<PackageExtractor>) packageExtractors.get(unitUnderTest);
        Assert.assertEquals(2, extractors.size());
        Assert.assertEquals(SillyPackageExtractor.class.getName(), extractors.get(0).getClass().getName());
        Assert.assertEquals(JavaPackageExtractor.class.getName(), extractors.get(1).getClass().getName());
    }
}
