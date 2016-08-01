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
import se.jguru.nazgul.tools.visualization.model.jaxb.GenericRoot;

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

    //
    // Helpers
    //

    public static Graph createStandardGraph(final boolean directed) {

        final Graph graph = new Graph("foobar", directed, false);

        final Node node1 = new Node("node1");
        final Node node2 = new Node("node2");

        graph.add(node1, node2);
        graph.add(new Identifier(node1.getId(), "foobar"));
        graph.addEdge("node1", "node2");

        // All Done.
        return graph;
    }
}
