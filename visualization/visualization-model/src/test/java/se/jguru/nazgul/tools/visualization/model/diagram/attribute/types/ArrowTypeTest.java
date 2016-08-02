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
package se.jguru.nazgul.tools.visualization.model.diagram.attribute.types;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ArrowTypeTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullShape() {
        new ArrowType(true, null);
    }

    @Test
    public void validateDefaultState() {

        // Assemble
        final ArrowType standardArrowType = new ArrowType();

        // Assert
        Assert.assertEquals(ArrowType.Shape.NORMAL, standardArrowType.getShape());
        Assert.assertTrue(standardArrowType.isFilled());
        Assert.assertNull(standardArrowType.getClipSide());
        Assert.assertEquals("ArrowType [shape: normal, filled: true]", standardArrowType.toString());
    }

    @Test
    public void validateComparisons() {

        // Assemble
        final ArrowType standardArrowType = new ArrowType();
        final ArrowType anotherStandardArrowType = new ArrowType();
        final ArrowType clippedDiamond = new ArrowType(true, ArrowType.Clip.LEFT, ArrowType.Shape.DIAMOND);
        final ArrowType clippedOpenDiamond = new ArrowType(false, ArrowType.Clip.LEFT, ArrowType.Shape.DIAMOND);

        // Act & Assert
        Assert.assertTrue(standardArrowType.equals(anotherStandardArrowType));
        Assert.assertTrue(standardArrowType.equals(standardArrowType));
        Assert.assertFalse(standardArrowType.equals(null));
        Assert.assertFalse(standardArrowType.equals("foobar"));
        Assert.assertFalse(standardArrowType.equals(clippedDiamond));
        Assert.assertFalse(clippedOpenDiamond.equals(clippedDiamond));

        Assert.assertEquals("ArrowType [shape: diamond, filled: false, clipSide: LEFT]", clippedOpenDiamond.toString());
    }
}
