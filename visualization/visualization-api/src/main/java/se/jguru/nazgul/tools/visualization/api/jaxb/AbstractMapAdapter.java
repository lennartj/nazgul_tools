package se.jguru.nazgul.tools.visualization.api.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract specification for generic JAXB XmlAdapters for Maps, rendering them in a
 * slightly more sensible way than the JAXB standard.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 * @see FlattenedMapEntry
 */
public abstract class AbstractMapAdapter<K, V> extends XmlAdapter<FlattenedMapEntry[], Map<K, V>> {

    /**
     * Retrieves a Function mapping K objects to Strings for JAXB marshalling.
     *
     * @return a non-null Function converting K objects to Strings.
     */
    protected abstract Function<K, String> getKeyMarshalConverter();

    /**
     * Retrieves a Function mapping V objects to Strings for JAXB marshalling.
     *
     * @return a non-null Function converting V objects to Strings.
     */
    protected abstract Function<V, String> getValueMarshalConverter();

    /**
     * Retrieves a Function mapping Strings to K objects for JAXB unmarshalling.
     *
     * @return a non-null Function converting Strings to K objects.
     */
    protected abstract Function<String, K> getKeyUnmarshalConverter();

    /**
     * Retrieves a Function mapping Strings to V objects for JAXB unmarshalling.
     *
     * @return a non-null Function converting Strings to V objects.
     */
    protected abstract Function<String, V> getValueUnmarshalConverter();

    /**
     * {@inheritDoc}
     */
    @Override
    public final FlattenedMapEntry[] marshal(final Map<K, V> objectForm) throws Exception {

        // Handle nulls
        if (objectForm == null) {
            return null;
        }

        // Convert to a FlattenedMapEntry array
        final List<FlattenedMapEntry> tmp = objectForm.entrySet()
                .stream()
                .map(current -> {

                    final String convertedKey = getKeyMarshalConverter().apply(current.getKey());
                    final String convertedValue = getValueMarshalConverter().apply(current.getValue());

                    return new FlattenedMapEntry(convertedKey, convertedValue);
                })
                .collect(Collectors.toList());

        // All Done.
        return tmp.toArray(new FlattenedMapEntry[tmp.size()]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<K, V> unmarshal(final FlattenedMapEntry[] transportForm) throws Exception {

        // Handle nulls
        if (transportForm == null) {
            return null;
        }

        // All Done.
        return Arrays.stream(transportForm)
                .collect(Collectors.toMap(
                        current -> getKeyUnmarshalConverter().apply(current.key),
                        current -> getValueUnmarshalConverter().apply(current.value)));
    }
}
