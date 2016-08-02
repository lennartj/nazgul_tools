/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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
package se.jguru.nazgul.tools.visualization.model.diagram;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class PortTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullCompassPoint() {

        // Act & Assert
        new Port(null);
    }

    @Test
    public void validateComparisonsWithPorts() {

        // Assemble
        final Port identifiedPort = new Port("foo", Port.CompassPoint.CENTER);
        final Port anotherIdentifiedPort = new Port("foo", Port.CompassPoint.CENTER);
        final Port anonymousPort1 = new Port(Port.CompassPoint.CENTER);
        final Port anonymousPort2 = new Port(Port.CompassPoint.CENTER);

        // Act & Assert
        Assert.assertFalse(identifiedPort.equals(null));
        Assert.assertFalse(identifiedPort.equals("foo"));
        Assert.assertTrue(identifiedPort.equals(identifiedPort));
        Assert.assertTrue(anonymousPort1.equals(anonymousPort2));
        Assert.assertFalse(identifiedPort.equals(anonymousPort2));
        Assert.assertTrue(identifiedPort.equals(anotherIdentifiedPort));

        Assert.assertEquals(anonymousPort1.hashCode(), anonymousPort2.hashCode());
        Assert.assertNotEquals(identifiedPort.hashCode(), anonymousPort2.hashCode());

        Assert.assertEquals("[Port]: foo, CompassPoint: CENTER", identifiedPort.toString());
        Assert.assertEquals("[Port]: <anonymous>, CompassPoint: CENTER", anonymousPort1.toString());
    }
}
