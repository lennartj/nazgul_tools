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
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Identifier;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.spi.dot.DotDiagramValidator;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphTest {

    @Test
    public void validateCreatingDigraph() {

        // Assemble
        final Graph unitUnderTest = new Graph("foobar", true, true);

        final Node node1 = new Node("node1");
        final Node node2 = new Node("node2");

        unitUnderTest.add(node1, node2);
        unitUnderTest.add(new Identifier(node1.getId(), "foobar"), null);
        unitUnderTest.add(null);
        unitUnderTest.addEdge("node1", "node2");

        // Act
        final String result = unitUnderTest.render();
        final InputStream in = new ByteArrayInputStream(result.getBytes());

        /*
        strict digraph "foobar" {

        // Node statements.
        "node1"  ;
        "node2"  ;

        // Identifier statements.
        "node1"  = "foobar"  ;

        // Edge statements.
        "node1"  ->  "node2"  ;
        }
        */

        // Assert
        final DotParser.GraphContext graphContext = DotDiagramValidator.validate(in);
        Assert.assertNotNull(graphContext);

        final List<DotParser.StmtContext> parsedStatements = graphContext.stmt_list().stmt();
        Assert.assertEquals(4, parsedStatements.size());

        final SortedMap<Integer, String> expectedTexts = new TreeMap<>();
        expectedTexts.put(0, "\"node1\"");
        expectedTexts.put(1, "\"node2\"");
        expectedTexts.put(2, "\"node1\"=\"foobar\"");
        expectedTexts.put(3, "\"node1\"->\"node2\"");
    }
}
