/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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


package se.jguru.nazgul.tools.visualization.model.diagram.attribute.types;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class PointOrRectangleTest {

    @Test
    public void validateComparisons() {

        // Assemble
        final PointOrRectangle x1y2 = new PointOrRectangle(1.0, 2.0);
        final PointOrRectangle x2y1 = new PointOrRectangle(2.0, 1.0);
        final PointOrRectangle x1y1 = new PointOrRectangle(1.0, 1.0);
        final PointOrRectangle anotherX1y1 = new PointOrRectangle(1.0, 1.0);
        final PointOrRectangle anUnmodifiableX1y1 = new PointOrRectangle(1.0, 1.0, true);

        // Act & Assert
        Assert.assertTrue(x1y1.equals(anotherX1y1));
        Assert.assertFalse(x1y1.equals(null));
        Assert.assertFalse(x1y1.equals("x1y1"));
        Assert.assertFalse(x1y1.equals(x1y2));
        Assert.assertFalse(x1y1.equals(x2y1));
        Assert.assertFalse(x1y1.equals(anUnmodifiableX1y1));

        Assert.assertEquals("[PointOrRectangle]: (1.0,1.0)", x1y1.toString());
        Assert.assertEquals("[PointOrRectangle]: (1.0,1.0)!", anUnmodifiableX1y1.toString());
    }
}
