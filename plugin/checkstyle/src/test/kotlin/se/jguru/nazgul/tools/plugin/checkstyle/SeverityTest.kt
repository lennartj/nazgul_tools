/*-
 * #%L
 * Nazgul Project: nazgul-tools-checkstyle-maven-plugin
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

package se.jguru.nazgul.tools.plugin.checkstyle

import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class SeverityTest {

    @Test
    fun validateSeverityLevelCheck() {

        Assert.assertTrue(Severity.ERROR.isViolation(Severity.ERROR))
        Assert.assertTrue(Severity.WARNING.isViolation(Severity.ERROR))
        Assert.assertTrue(Severity.INFO.isViolation(Severity.ERROR))

        Assert.assertFalse(Severity.ERROR.isViolation(Severity.WARNING))
        Assert.assertTrue(Severity.WARNING.isViolation(Severity.WARNING))
        Assert.assertTrue(Severity.INFO.isViolation(Severity.WARNING))

        Assert.assertFalse(Severity.ERROR.isViolation(Severity.INFO))
        Assert.assertFalse(Severity.WARNING.isViolation(Severity.INFO))
        Assert.assertTrue(Severity.INFO.isViolation(Severity.INFO))
    }

    @Test
    fun validateParse() {

        // Act & Assert
        Assert.assertSame(Severity.ERROR, Severity.parse("error"))
        Assert.assertSame(Severity.ERROR, Severity.parse("Error"))
        Assert.assertSame(Severity.ERROR, Severity.parse("eRrOR"))

        Assert.assertSame(Severity.WARNING, Severity.parse("warning"))
        Assert.assertSame(Severity.WARNING, Severity.parse("Warning"))
        Assert.assertSame(Severity.WARNING, Severity.parse("wArNiNg"))

        Assert.assertSame(Severity.INFO, Severity.parse("info"))
        Assert.assertSame(Severity.INFO, Severity.parse("Info"))
        Assert.assertSame(Severity.INFO, Severity.parse("INFO"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun validateExceptionOnParsingUnrecognizedValue() {

        // Assert
        Severity.parse("foobar!")
    }
}
