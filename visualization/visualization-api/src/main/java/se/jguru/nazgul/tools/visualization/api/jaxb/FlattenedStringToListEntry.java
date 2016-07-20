package se.jguru.nazgul.tools.visualization.api.jaxb;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"key", "items"})
@XmlAccessorType(XmlAccessType.FIELD)
public class FlattenedStringToListEntry implements Serializable {

    @XmlAttribute(required = true)
    public String key;

    @XmlElementWrapper
    @XmlElement(name = "item")
    public List<?> items;

    /**
     * JAXB-friendly constructor.
     */
    public FlattenedStringToListEntry() {
    }

    /**
     * Compound constructor creating a {@link FlattenedStringToListEntry} wrapping the supplied data.
     *
     * @param key   The non-null Map key.
     * @param items The optional (i.e. nullable) List containing items to wrap. The objects in the
     *              items List must be JAXB-annotated to prevent exceptions.
     */
    public FlattenedStringToListEntry(final String key, final List<?> items) {
        this.key = key;
        this.items = items == null ? new ArrayList<>() : items;
    }
}
