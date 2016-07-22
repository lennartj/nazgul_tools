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
/**
 * Package containing domain model implementations for Graphviz/Dot attribute types, complying to the
 * specification in the <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 * All relevant classes are JAXB-annotated to ensure that the model can be converted
 * in a simple manner to and from XML and JSON formats.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlSchema(
        xmlns = {
                @XmlNs(prefix = "nazgulviz", namespaceURI = Graph.NAMESPACE),
                @XmlNs(prefix = "xs", namespaceURI = "http://www.w3.org/2001/XMLSchema"),
                @XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance"),
                @XmlNs(prefix = "vc", namespaceURI = "http://www.w3.org/2007/XMLSchema-versioning")
        }
)
@XmlAccessorType(XmlAccessType.FIELD)
package se.jguru.nazgul.tools.visualization.api.diagram.attribute.model;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;