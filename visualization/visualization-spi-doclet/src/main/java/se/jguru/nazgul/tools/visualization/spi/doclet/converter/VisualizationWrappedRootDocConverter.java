package se.jguru.nazgul.tools.visualization.spi.doclet.converter;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import se.jguru.nazgul.tools.visualization.api.factory.AbstractGraphConverter;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Subgraph;
import se.jguru.nazgul.tools.visualization.spi.doclet.javadoc.VisualizationWrappedRootDoc;

import java.util.SortedSet;

/**
 * Default TypeConverter to convert a {@link VisualizationWrappedRootDoc} received from
 * a JavaDoc execution into a {@link Graph} used for rendering.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class VisualizationWrappedRootDocConverter extends AbstractGraphConverter<VisualizationWrappedRootDoc> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Graph doConvert(final VisualizationWrappedRootDoc toConvert) {

        final Graph toReturn = new Graph();

        // Create subgraphs for each package and add them to the returned Graph.
        toConvert.sortClassesPerPackage().forEach((packageDoc, classDocs) ->
                toReturn.add(convertPackage(packageDoc, classDocs)));

        // All Done.
        return toReturn;
    }

    protected Subgraph convertPackage(final PackageDoc packageDoc,
                                      final SortedSet<ClassDoc> classDocs) {
        final Subgraph toReturn = new Subgraph(packageDoc.name());

    }
}
