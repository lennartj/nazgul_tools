/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-impl-doclet
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

package se.jguru.nazgul.tools.visualization.impl.doclet;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class VisualizationDocletTagTest {

    @Test
    public void validatePrefixStartOfVisualizationTags() {

        // Act & Assert
        for (VisualizationDocletTag current : VisualizationDocletTag.values()) {
            Assert.assertTrue(current.toString().startsWith(VisualizationDocletTag.TAG_PREFIX));
        }
    }
}
