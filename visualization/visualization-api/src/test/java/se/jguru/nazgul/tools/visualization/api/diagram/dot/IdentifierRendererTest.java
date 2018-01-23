/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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

package se.jguru.nazgul.tools.visualization.api.diagram.dot;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.dot.IdentifierRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Identifier;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class IdentifierRendererTest extends AbstractRendererTest {

    @Test
    public void validateRendering() {

        // Assemble
        final Identifier identifier = new Identifier("sourceId", "targetId");
        final IdentifierRenderer unitUnderTest = new IdentifierRenderer();

        // Act
        final String renderedIdentifier = unitUnderTest.render(renderConfiguration, identifier);

        // Assert
        Assert.assertEquals("\"sourceId\" = \"targetId\" ;" + renderConfiguration.getNewline(), renderedIdentifier);
    }
}
