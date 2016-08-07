/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2016 jGuru Europe AB
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
package se.jguru.nazgul.tools.visualization.api.diagram;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class RenderConfigurationTest {

    // Shared state
    private RenderConfiguration unitUnderTest;

    @Before
    public void setupSharedState() {
        unitUnderTest = new RenderConfiguration();
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNegativeIndent() {
        unitUnderTest.setIndentationLevel(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullIndentationToken() {
        unitUnderTest.setIndentationToken(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnEmptyIndentationToken() {
        unitUnderTest.setIndentationToken("");
    }

    @Test
    public void validateDefaultStringValue() {

        // Assemble
        final String expected = "RenderConfiguration: " + RenderConfiguration.NEWLINE + 
                " indentationLevel   : 0" + RenderConfiguration.NEWLINE + 
                " indentationToken   : '  '" + RenderConfiguration.NEWLINE + 
                " extraConfiguration : {}";

        // Act & Assert
        Assert.assertEquals(expected, unitUnderTest.toString());
    }
}
