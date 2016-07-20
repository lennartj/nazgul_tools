/**
 * Package containing Statements for Graphviz/Dot diagram synthesis, complying to the specification in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
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
package se.jguru.nazgul.tools.visualization.api.diagram.statement;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;