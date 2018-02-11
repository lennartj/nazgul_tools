package se.jguru.nazgul.tools.visualization.spi.doclet.converter;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.PackageDoc;
import se.jguru.nazgul.tools.visualization.api.factory.AbstractGraphConverter;
import se.jguru.nazgul.tools.visualization.model.diagram.Graph;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.GraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonGraphAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Subgraph;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * AbstractGraphConverter instance intended to synthesize a {@link Graph} containing
 * the full detail as required when illustrating the classes only within a package.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class PackageDocConverter extends AbstractGraphConverter<Set<ClassDoc>> {

    // Internal state
    private Package relevantPackage;

    /**
     * Compound constructor creating a {@link PackageDocConverter} which extracts information
     * only for the supplied {@link Package}.
     *
     * @param relevantPackage The package for which this AbstractGraphConverter should extract
     *                        its data.
     */
    public PackageDocConverter(final Package relevantPackage) {
        this.relevantPackage = relevantPackage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Graph doConvert(final Set<ClassDoc> toConvert) {

        final ClassDoc[] clDocs = toConvert.toArray(new ClassDoc[toConvert.size()]);
        final SortedMap<PackageDoc, SortedSet<ClassDoc>> clPerPackage = Comparators.sortClassesPerPackage(clDocs);

        final Map.Entry<PackageDoc, SortedSet<ClassDoc>> packageAndClasses = clPerPackage
                .entrySet()
                .stream()
                .filter(e -> relevantPackage.getName().equalsIgnoreCase(e.getKey().name()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No retrieved classes in expected package ["
                        + relevantPackage.getName() + "]. Found: "
                        + clPerPackage.keySet().stream().map(Doc::name).reduce((l, r) -> l + ", r")
                        .orElse("<none>")));

        final PackageDoc packageDoc = packageAndClasses.getKey();
        final SortedSet<ClassDoc> classDocs = packageAndClasses.getValue();

        // Create the Graph.
        final Graph toReturn = new Graph("Graph_" + packageDoc.name(), true, true);

        final Subgraph packageSubgraph = new Subgraph(packageDoc.name());
        final CommonGraphAttributes subgraphAttrs = new CommonGraphAttributes();
        packageSubgraph.add(subgraphAttrs);

        final GraphAttributes subAttrs = subgraphAttrs.getAttributes();
        subAttrs.label = "Package: " + packageDoc.name();

        // Add a Subgraph for the package
        toReturn.add(renderPackageSubGraph(classDocs));

        // All Done.
        return toReturn;
    }

    /**
     * Renders a Subgraph with the supplied ClassDocs.
     *
     * @param classDocs The ClassDocs to render.
     * @return The rendered SubGraph.
     */
    public Subgraph renderPackageSubGraph(final SortedSet<ClassDoc> classDocs) {

    }
}
