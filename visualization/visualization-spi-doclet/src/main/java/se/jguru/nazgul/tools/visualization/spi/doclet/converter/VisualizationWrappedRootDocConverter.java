/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-spi-doclet
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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
        // final Subgraph toReturn = new Subgraph(packageDoc.name());

        // TODO: Continue here.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
