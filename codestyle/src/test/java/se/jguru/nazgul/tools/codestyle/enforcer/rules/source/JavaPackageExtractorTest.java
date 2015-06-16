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
package se.jguru.nazgul.tools.codestyle.enforcer.rules.source;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class JavaPackageExtractorTest {

    private static final String JAVA_PACKAGES = "/testdata/packages/lang_java";

    @Test
    public void validatePackagePatternMatching() {

        // Assemble
        final List<String> validPackages = Arrays.asList("se", "se.jguru", "se.jguru.nazgul");
        final List<String> invalidPackages = Arrays.asList("se.", "se..jguru", ".se.jguru.nazgul");

        // Act & Assert
        for (String current : validPackages) {

            String packageLine = "package " + current + ";";
            Assert.assertTrue("Valid package line [" + packageLine + "] did not match.",
                    JavaPackageExtractor.PACKAGE_STATEMENT.matcher(packageLine).matches());
        }

        for (String current : invalidPackages) {

            String packageLine = "package " + current + ";";
            Assert.assertTrue("Invalid package line [" + packageLine + "] did match.",
                    !JavaPackageExtractor.PACKAGE_STATEMENT.matcher(packageLine).matches());
        }
    }

    @Test
    public void validatePatternExtraction() {

        // Assemble
        final URL resource = JavaPackageExtractorTest.class.getResource(JAVA_PACKAGES + "/okPackage.java");
        final File packageDir = new File(resource.getPath()).getParentFile();

        final JavaPackageExtractor unitUnderTest = new JavaPackageExtractor();
        final Map<String, String> packageNames = new TreeMap<String, String>();

        // Act
        for (File current : packageDir.listFiles(unitUnderTest.getSourceFileFilter())) {
            packageNames.put(current.getName(), unitUnderTest.getPackage(current));
        }

        // Assert
        Assert.assertEquals(3, packageNames.size());
        Assert.assertEquals("se.jguru.nazgul.tools.codestyle", packageNames.get("okEvenLongerPackage.java"));
        Assert.assertEquals("se.jguru.nazgul", packageNames.get("okLongerPackage.java"));
        Assert.assertEquals("se.jguru", packageNames.get("okPackage.java"));
    }

    @Test
    public void validateDefaultPackageReturnedOnMalformedPackageStatement() {

        // Assemble
        final URL resource = JavaPackageExtractorTest.class.getResource(
                JAVA_PACKAGES + "/incorrect/nokNotAPackage.txt");
        final File packageDir = new File(resource.getPath()).getParentFile();

        final JavaPackageExtractor unitUnderTest = new JavaPackageExtractor();
        final Map<String, String> packageNames = new TreeMap<String, String>();

        // Act
        for (File current : packageDir.listFiles()) {
            if (current.isFile()) {
                packageNames.put(current.getName(), unitUnderTest.getPackage(current));
            }
        }

        // Assert
        Assert.assertEquals(1, packageNames.size());
        Assert.assertEquals("", packageNames.get("nokNotAPackage.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnSubmittingDirectoriesToPackageExtractor() {

        // Assemble
        final URL resource = JavaPackageExtractorTest.class.getResource(JAVA_PACKAGES + "/incorrect");
        final JavaPackageExtractor unitUnderTest = new JavaPackageExtractor();

        // Act
        unitUnderTest.getPackage(new File(resource.getPath()));
    }
}
