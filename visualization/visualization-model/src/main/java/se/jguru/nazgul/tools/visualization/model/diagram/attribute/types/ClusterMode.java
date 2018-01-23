/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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

package se.jguru.nazgul.tools.visualization.model.diagram.attribute.types;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Mode used for handling clusters. If clusterrank is "local", a subgraph whose name begins with "cluster" is given
 * special treatment. The subgraph is laid out separately, and then integrated as a unit into its parent graph, with
 * a bounding rectangle drawn about it. If the cluster has a label parameter, this label is displayed within the
 * rectangle. Note also that there can be clusters within clusters. At present, the modes "global" and "none" appear
 * to be identical, both turning off the special cluster processing.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE)
@XmlEnum(String.class)
public enum ClusterMode implements TokenValueHolder {

    /**
     * If clusterrank is "local", a subgraph whose name begins with "cluster" is given
     * special treatment. The subgraph is laid out separately, and then integrated as a unit into its parent graph,
     * with a bounding rectangle drawn about it. If the cluster has a label parameter, this label is displayed
     * within the rectangle.
     */
    LOCAL,

    /**
     * At present, the modes "global" and "none" appear to be identical, both turning off the special cluster
     * processing.
     */
    GLOBAL,

    /**
     * At present, the modes "global" and "none" appear to be identical, both turning off the special cluster
     * processing.
     */
    NONE;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTokenValue() {
        return name().toLowerCase();
    }
}
