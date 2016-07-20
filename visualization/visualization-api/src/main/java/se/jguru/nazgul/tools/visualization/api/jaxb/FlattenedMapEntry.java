package se.jguru.nazgul.tools.visualization.api.jaxb;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

/**
 * <p>JAXB marshalled entry for converting the default JAXB marshalled structure of a
 * string-to-string Map into something a bit more sensible, namely:</p>
 * <pre>
 *     <code>
 *         // Example marshalled Map structure using FlattenedMapEntry converters.
 *         &lt;map&gt;
 *             &lt;item key="key1"&gt;value1&lt;/item&gt;
 *             &lt;item key="key2"&gt;value2&lt;/item&gt;
 *         &lt;/map&gt;
 *     </code>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"key", "value"})
@XmlAccessorType(XmlAccessType.FIELD)
public class FlattenedMapEntry implements Serializable {

    @XmlAttribute(required = true)
    public String key;

    @XmlValue
    public String value;

    /**
     * Default constructor, reserved for framework use.
     */
    public FlattenedMapEntry() {
    }

    /**
     * Compound constructor creating a {@link FlattenedMapEntry} wrapping the supplied data.
     *
     * @param key   A String key element.
     * @param value A String value element.
     */
    public FlattenedMapEntry(final String key, final String value) {
        this.key = key;
        this.value = value;
    }
}
