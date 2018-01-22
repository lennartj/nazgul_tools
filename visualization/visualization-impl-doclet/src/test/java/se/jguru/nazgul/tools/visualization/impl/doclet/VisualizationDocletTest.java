package se.jguru.nazgul.tools.visualization.impl.doclet;

import com.sun.javadoc.RootDoc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import se.jguru.nazgul.tools.visualization.impl.doclet.helpers.RootDocWrapper;

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

    @Test
    public void validateTrivialDoc() {

        // Assemble
        final RootDoc sampleRootDoc = rootDocWrapper.getRootDoc(
                RootDocWrapper.getTestSourceDir(),
                getUniqueTargetDirName(),
                Arrays.asList("se.jguru.nazgul.tools.visualization.impl.doclet.sample",
                        "se/jguru/nazgul/tools/visualization/impl/doclet/sample"),
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
