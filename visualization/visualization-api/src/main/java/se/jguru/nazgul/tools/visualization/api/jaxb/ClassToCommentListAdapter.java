package se.jguru.nazgul.tools.visualization.api.jaxb;

import se.jguru.nazgul.tools.visualization.api.diagram.Comment;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statements;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class ClassToCommentListAdapter
        extends XmlAdapter<FlattenedStringToListEntry[], Map<Class<? extends Statement>, Comment>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public FlattenedStringToListEntry[] marshal(final Map<Class<? extends Statement>, Comment> objectForm)
            throws Exception {

        // Handle nulls
        if (objectForm == null) {
            return null;
        }

        // Convert the Map to the transport form
        final List<FlattenedStringToListEntry> flattened = objectForm.entrySet().stream()
                .map(c -> new FlattenedStringToListEntry(c.getKey().getName(), c.getValue().getCommentLines()))
                .collect(Collectors.toList());

        // All Done.
        return flattened.toArray(new FlattenedStringToListEntry[flattened.size()]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Statement>, Comment> unmarshal(final FlattenedStringToListEntry[] transportForm)
            throws Exception {

        // Handle nulls
        if (transportForm == null) {
            return null;
        }

        final Map<Class<? extends Statement>, Comment> toReturn = new TreeMap<>(
                Statements.DEFAULT_STATEMENT_COMPARATOR);

        // Convert the transport form to a Map.
        for (FlattenedStringToListEntry current : transportForm) {

            // Load the Class of the current statement
            final Class<Statement> currentClass = (Class<Statement>) Thread.currentThread()
                    .getContextClassLoader()
                    .loadClass(current.key);

            // Convert the comment strings
            final List<String> commentLines = current.items.stream()
                    .filter(c -> c instanceof String)
                    .map(c -> (String) c)
                    .collect(Collectors.toList());

            // Resurrect the Comment
            final Comment comment = new Comment(commentLines.toArray(new String[commentLines.size()]));

            // Add the entry to the return Map
            toReturn.put(currentClass, comment);
        }

        // All Done.
        return toReturn;
    }
}
