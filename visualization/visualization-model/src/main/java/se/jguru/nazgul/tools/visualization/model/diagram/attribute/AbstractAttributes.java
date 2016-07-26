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
package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Abstract implementation containing common utilities for attribute entities.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"backgroundColor", "textColor"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractAttributes implements Serializable {

    /**
     * When attached to the root graph, this color is used as the background for entire canvas.
     * When a cluster attribute, it is used as the initial background for the cluster.
     * If a cluster has a filled style, the cluster's fillcolor will overlay the background color.
     */
    @XmlAttribute
    private StandardCssColor backgroundColor;

    /**
     * Color used for text.
     */
    @XmlAttribute
    private StandardCssColor textColor;

    /**
     * Comments are inserted into output. Device-dependent.
     */
    @XmlElement
    private String comment;
}
