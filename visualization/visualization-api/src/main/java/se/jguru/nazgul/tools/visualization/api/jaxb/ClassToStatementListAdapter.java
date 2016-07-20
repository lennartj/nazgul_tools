package se.jguru.nazgul.tools.visualization.api.jaxb;

import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statements;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
public class ClassToStatementListAdapter
        extends XmlAdapter<FlattenedStringToListEntry[], Map<Class<? extends Statement>, List<Statement>>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Statement>, List<Statement>> unmarshal(final FlattenedStringToListEntry[] transportForm)
            throws Exception {

        // Handle nulls
        if(transportForm == null) {
            return null;
        }

        final Map<Class<? extends Statement>, List<Statement>> toReturn = new TreeMap<>(
                Statements.DEFAULT_STATEMENT_COMPARATOR);

        for(FlattenedStringToListEntry current : transportForm) {

            // Load the Class of the current statement
            final Class<Statement> currentClass = (Class<Statement>) Thread.currentThread()
                    .getContextClassLoader()
                    .loadClass(current.key);

            final List<Statement> statementList = new ArrayList<>();
            if(current.items != null) {

                // Copy only Statements into the statementList.
                current.items
                        .stream()
                        .filter(c -> c instanceof Statement)
                        .map(c -> (Statement) c)
                        .forEach(statementList::add);
            }

            // Add the resurrected map entry.
            toReturn.put(currentClass, statementList);
        }

        // All Done.
        return toReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FlattenedStringToListEntry[] marshal(final Map<Class<? extends Statement>, List<Statement>> objectForm)
            throws Exception {

        // Handle nulls
        if(objectForm == null) {
            return null;
        }

        // Convert the Map to a List of FlattenedStringToListEntry objects
        final List<FlattenedStringToListEntry> sequence = objectForm.entrySet()
                .stream()
                .map(c -> new FlattenedStringToListEntry(c.getKey().getName(), c.getValue()))
                .collect(Collectors.toList());


        // All Done.
        return sequence.toArray(new FlattenedStringToListEntry[sequence.size()]);
    }
}
