/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *        http://www.jguru.se/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class T_AbstractEnforcerRuleTest {

    @Test
    public void validateStringSplicing() {

        // Assemble
        final String toSplice = "foo,bar,baz";

        // Act
        final List<String> result = AbstractEnforcerRule.splice(toSplice);

        // Assert
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("foo", result.get(0));
        Assert.assertEquals("bar", result.get(1));
        Assert.assertEquals("baz", result.get(2));
    }

    @Test
    public void validateSkippingEmptyParameter() {

        // Assemble
        final String toSplice = "foo,,baz";

        // Act
        final List<String> result = AbstractEnforcerRule.splice(toSplice);

        // Assert
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("foo", result.get(0));
        Assert.assertEquals("baz", result.get(1));
    }

    @Test
    public void validateStringSplicingOfEmptyString() {

        // Assemble
        final String toSplice = "";

        // Act
        final List<String> result = AbstractEnforcerRule.splice(toSplice);

        // Assert
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void validateStringSplicingOfSingleString() {

        // Assemble
        final String toSplice = "singleString";

        // Act
        final List<String> result = AbstractEnforcerRule.splice(toSplice);

        // Assert
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(toSplice, result.get(0));
    }

    @Test
    public void validateNormalContainmentPrefixes() {

        // Assemble
        final List<String> source = new ArrayList<String>();
        source.add("se.jguru.foo");

        // Act & Assert
        Assert.assertFalse(AbstractEnforcerRule.containsPrefix(source, "se.jguru"));
        Assert.assertTrue(AbstractEnforcerRule.containsPrefix(source, "se.jguru.foo"));
        Assert.assertTrue(AbstractEnforcerRule.containsPrefix(source, "se.jguru.foo.bar"));
        Assert.assertFalse(AbstractEnforcerRule.containsPrefix(source, "se.jguru.bar"));
    }
}
