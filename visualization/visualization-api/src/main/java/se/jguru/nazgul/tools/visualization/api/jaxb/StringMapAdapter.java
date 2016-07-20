package se.jguru.nazgul.tools.visualization.api.jaxb;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * JAXB {@link XmlAdapter} implementation for Maps of Strings.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlTransient
public class StringMapAdapter extends XmlAdapter<FlattenedMapEntry[], SortedMap<String, String>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<String, String> unmarshal(final FlattenedMapEntry[] transportForm) throws Exception {

        // Handle nulls
        if (transportForm == null) {
            return null;
        }

        // All Done.
        final SortedMap<String, String> toReturn = new TreeMap<>();
        Arrays.stream(transportForm).forEach(c -> toReturn.put(c.key, c.value));

        // All Done.
        return toReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FlattenedMapEntry[] marshal(final SortedMap<String, String> objectForm) throws Exception {


        // Handle nulls
        if (objectForm == null) {
            return null;
        }

        // Convert to a FlattenedMapEntry array
        final List<FlattenedMapEntry> tmp = objectForm.entrySet()
                .stream()
                .map(current -> new FlattenedMapEntry(current.getKey(), current.getValue()))
                .collect(Collectors.toList());

        // All Done.
        return tmp.toArray(new FlattenedMapEntry[tmp.size()]);
    }
}
