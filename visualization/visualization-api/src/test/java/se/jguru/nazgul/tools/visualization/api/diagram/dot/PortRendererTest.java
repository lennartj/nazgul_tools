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
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.api.dot.PortRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.Port;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class PortRendererTest extends AbstractRendererTest {

    @Test
    public void validateRendering() {

        // Assemble
        final Port portWithId = new Port("foobar", Port.CompassPoint.NORTH);
        final Port anonymousPort = new Port(Port.CompassPoint.EAST);

        final PortRenderer unitUnderTest = new PortRenderer();

        // Act
        final String idResult = unitUnderTest.render(renderConfiguration, portWithId);
        final String anonymousResult = unitUnderTest.render(renderConfiguration, anonymousPort);

        // Assert
        Assert.assertEquals(": \"foobar\" : n", idResult);
        Assert.assertEquals(": e", anonymousResult);
    }
}
