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

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining permitted values for specifying output ordering.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE)
@XmlEnum(String.class)
public enum OutputOrder {

    /**
     * The default "breadthfirst" is the simplest, but when the graph layout does not avoid
     * edge-node overlap, this mode will sometimes have edges drawn over nodes and sometimes on top of nodes.
     */
    BREADTH_FIRST("breadthfirst"),

    /**
     * If the mode "nodesfirst" is chosen, all nodes are drawn first, followed by the edges. This guarantees an
     * edge-node overlap will not be mistaken for an edge ending at a node.
     */
    NODES_FIRST("nodesfirst"),

    /**
     * On the other hand, usually for aesthetic reasons, it may be desirable that all edges appear beneath
     * nodes, even if the resulting drawing is ambiguous. This can be achieved by choosing "edgesfirst".
     */
    EDGES_FIRST("edgesfirst");

    // Internal state
    private String dotTokenValue;

    OutputOrder(final String dotTokenValue) {
        this.dotTokenValue = dotTokenValue;
    }

    /**
     * Retrieves the Token value for this {@link OutputOrder}.
     *
     * @return the Token value for this {@link OutputOrder}.
     */
    public String getDotTokenValue() {
        return dotTokenValue;
    }
}
