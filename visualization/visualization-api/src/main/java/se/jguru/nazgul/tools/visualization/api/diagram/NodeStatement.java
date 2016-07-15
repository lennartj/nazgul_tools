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

/**
 * Node statement Renderer, complying to the specification in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeStatement extends AbstractStringIdentifiable {

    // Internal state
    private NodeID nodeID;
    private SortedAttributeList attributeList;

    /**
     * Convenience constructor creating a {@link NodeStatement} with the supplied ID.
     *
     * @param id a non-null and non-empty identifier, assumed to be unique within a Graph.
     */
    public NodeStatement(final String id) {
        this(new NodeID(id, null));
    }

    /**
     * Compound constructor creating a {@link NodeStatement} wrapping the supplied {@link NodeID}.
     *
     * @param nodeID A non-null {@link NodeID} instance.
     */
    public NodeStatement(final NodeID nodeID) {
        super(nodeID.getId());

        this.nodeID = nodeID;
        this.attributeList = new SortedAttributeList();
    }

    /**
     * Retrieves the {@link SortedAttributeList} of this {@link NodeStatement}.
     *
     * @return the non-null {@link SortedAttributeList} of this {@link NodeStatement}.
     */
    public SortedAttributeList getAttributeList() {
        return attributeList;
    }

    /**
     * Retrieves the non-null {@link NodeID} for this {@link NodeStatement}.
     *
     * @return the non-null {@link NodeID} for this {@link NodeStatement}.
     */
    public NodeID getNodeID() {
        return nodeID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render() {
        return nodeID.render() + " " + attributeList.render();
    }
}
