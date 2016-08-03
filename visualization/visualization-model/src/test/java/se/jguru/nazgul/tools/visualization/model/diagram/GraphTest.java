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
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.model.AbstractEntityTest;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Identifier;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Node;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statement;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class GraphTest extends AbstractEntityTest {

    // Shared state
    private Graph unitUnderTest;

    @Before
    public void setupSharedState() {

        jaxb.add(Graph.class);
        unitUnderTest = createStandardGraph(true);
    }

    @Test
    public void validateMarshalling() {

        // Assemble

        // Act
        final String marshalledXml = marshalAsXml(unitUnderTest);
        final String marshalledJson = marshalAsJson(unitUnderTest);

        // System.out.println("Got: " + marshalledXml);
        // System.out.println("Got: " + marshalledJson);

        // Assert
        validateIdenticalXml("testdata/simpleGraph.xml", marshalledXml);
        validateIdenticalJson("testdata/simpleGraph.json", marshalledJson);
    }

    @Test
    public void validateUnmarshalling() {

        // Assemble

        // Act
        final Graph fromXml = unmarshalFromXml(Graph.class, "testdata/simpleGraph.xml");
        final Graph fromJSon = unmarshalFromJson(Graph.class, "testdata/simpleGraph.json");

        // Assert
        Assert.assertEquals(unitUnderTest.isDigraph(), fromXml.isDigraph());
        Assert.assertEquals(unitUnderTest.isDigraph(), fromJSon.isDigraph());

        Assert.assertEquals(unitUnderTest.isStrict(), fromXml.isStrict());
        Assert.assertEquals(unitUnderTest.isStrict(), fromJSon.isStrict());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullIdentifier() {

        new Graph(null, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnEmptyIdentifier() {

        new Graph("", true, true);
    }

    @Test
    public void validateComparisons() {

        // Assemble
        final Graph gnatGraph = createStandardGraph("gnat", true);
        final Graph anotherGnatGraph = createStandardGraph("gnat", true);

        // Act & Assert
        Assert.assertTrue(gnatGraph.equals(gnatGraph));
        Assert.assertTrue(gnatGraph.equals(anotherGnatGraph));
        Assert.assertFalse(gnatGraph.equals("gnat"));
        Assert.assertFalse(gnatGraph.equals(null));
        Assert.assertFalse(gnatGraph.equals(unitUnderTest));
        Assert.assertEquals(gnatGraph.hashCode(), anotherGnatGraph.hashCode());
    }

    @Test
    public void validateAddingRightSideEdge() {

    }

    //
    // Helpers
    //

    public static Graph createStandardGraph(final boolean directed) {
        return createStandardGraph("foobar", directed);
    }

    public static Graph createStandardGraph(final String id, final boolean directed) {

        final Graph graph = new Graph(id, directed, false);

        final Node node1 = new Node("node1");
        final Node node2 = new Node("node2");

        graph.add((Statement) null);
        graph.add(node1, node2);
        graph.add(new Identifier(node1.getId(), "foobar"));
        graph.addEdge("node1", "node2");

        // All Done.
        return graph;
    }
}
