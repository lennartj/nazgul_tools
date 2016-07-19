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
package se.jguru.nazgul.tools.visualization.api.diagram.statement;

import org.antlr.v4.runtime.RuleContext;
import org.junit.Assert;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.spi.dot.DotDiagramValidator;
import se.jguru.nazgul.tools.visualization.spi.dot.grammars.DotParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractGraphTest {


    protected List<String> getBaseExpectedTests(final String edgeOp) {
        return new ArrayList<>(Arrays.asList(
                "\"node1\"",
                "\"node2\"",
                "\"node1\"=\"foobar\"",
                "\"node1\"" + edgeOp + "\"node2\""));
    }

    protected Graph createStandardGraph(final boolean directed) {

        final Graph graph = new Graph("foobar", directed, false);

        final Node node1 = new Node("node1");
        final Node node2 = new Node("node2");

        graph.add(node1, node2);
        graph.add(new Identifier(node1.getId(), "foobar"));
        graph.addEdge("node1", "node2");

        // All Done.
        return graph;
    }

    protected DotParser.GraphContext validateGraph(final Graph graph,
                                                   final List<String> expectedTexts) {

        System.out.println("Got rendered Graph:\n" + graph.render());

        final InputStream in = new ByteArrayInputStream(graph.render().getBytes());
        final DotParser.GraphContext graphContext = DotDiagramValidator.validate(in);
        Assert.assertNotNull(graphContext);

        final List<DotParser.StmtContext> parsedStatements = graphContext.stmt_list().stmt();
        final List<String> parsedStatementTexts = parsedStatements.stream()
                .map(RuleContext::getText)
                .collect(Collectors.toList());

        Assert.assertEquals("Got " + parsedStatementTexts + ", but expected " + expectedTexts,
                expectedTexts.size(),
                parsedStatements.size());


        for (String current : expectedTexts) {
            Assert.assertTrue(parsedStatementTexts.contains(current));
        }

        return graphContext;
    }
}
