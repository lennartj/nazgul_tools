/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
 * %%
 * Copyright (C) 2010 - 2017 jGuru Europe AB
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
public class StandardCssColorTest {

    @Test
    public void validateComparingColors() {

        // Assemble
        final StandardCssColor silver = StandardCssColor.Silver;
        final StandardCssColor antiqueWhite = StandardCssColor.AntiqueWhite;

        // Act & Assert
        Assert.assertNotEquals(silver.getRgbValue(), antiqueWhite.getRgbValue());
        Assert.assertTrue(silver.getRgbValue().startsWith("#"));
    }

    @Test
    public void validateParsingColors() {

        // Assemble
        final String silverRgb = "C0C0C0";
        final String silverRgbWithHash = "#C0C0C0";
        final String silverNameCaseMatch = "Silver";
        final String silverNameUpperCase = "SILVER";

        // Act
        final StandardCssColor silver1 = StandardCssColor.convert(silverRgb);
        final StandardCssColor silver2 = StandardCssColor.convert(silverRgbWithHash);
        final StandardCssColor silver3 = StandardCssColor.convert(silverNameCaseMatch);
        final StandardCssColor silver4 = StandardCssColor.convert(silverNameUpperCase);

        // Assert
        Assert.assertSame(silver1, silver2);
        Assert.assertSame(silver1, silver3);
        Assert.assertSame(silver1, silver4);

        Assert.assertNull(StandardCssColor.convert("notAColor"));
        Assert.assertNull(StandardCssColor.convert(null));
        Assert.assertNull(StandardCssColor.convert(""));
    }
}
