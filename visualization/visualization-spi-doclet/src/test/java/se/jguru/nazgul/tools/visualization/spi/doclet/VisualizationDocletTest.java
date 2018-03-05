/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-spi-doclet
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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

package se.jguru.nazgul.tools.visualization.spi.doclet;

import com.sun.javadoc.RootDoc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import se.jguru.nazgul.tools.visualization.spi.doclet.helpers.RootDocWrapper;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class VisualizationDocletTest {

    // Shared state
    @Rule public TestName testName = new TestName();
    private RootDocWrapper rootDocWrapper;

    @Before
    public void setupSharedState() {
        rootDocWrapper = new RootDocWrapper(testName.getMethodName() + " UnitTest");
    }

    public void validateTrivialDoc() {

        // Assemble
        final RootDoc sampleRootDoc = rootDocWrapper.getRootDoc(
                RootDocWrapper.getTestSourceDir(),
                getUniqueTargetDirName(),
                Arrays.asList("se.jguru.nazgul.tools.visualization.impl.doclet.sample"),
                Collections.emptyList(),
                null);

        final VisualizationDoclet unitUnderTest = new VisualizationDoclet();

        // Act
        final boolean result = unitUnderTest.start(sampleRootDoc);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void validateHelpText() {

        // Assemble

        // Act

        // Assert
    }

    //
    // Private helpers
    //

    private String getUniqueTargetDirName() {
        return getClass().getSimpleName() + "_" + testName.getMethodName();
    }
}
