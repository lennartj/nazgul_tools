/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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

package se.jguru.nazgul.tools.visualization.api;

import se.jguru.nazgul.tools.visualization.model.diagram.statement.Statement;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.Subgraph;

/**
 * Abstract implementation of a Renderer yielding String results, and sporting facilities for pretty
 * printing/indenting the String result.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractStringRenderer<T> implements Renderer<String> {

    // Internal state
    private Class<T> acceptedType;

    /**
     * Compound constructor creating an {@link AbstractStringRenderer} wrapping the supplied acceptedType.
     *
     * @param acceptedType A non-null Class defining the type of entity acceptable to this
     *                     {@link AbstractStringRenderer}.
     */
    protected AbstractStringRenderer(final Class<T> acceptedType) {

        // Check sanity
        if (acceptedType == null) {
            throw new IllegalArgumentException("Cannot handle null 'acceptedType' argument.");
        }

        // Assign internal state
        this.acceptedType = acceptedType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String render(final RenderConfiguration configuration, final Object entity) {

        // Check sanity
        if (configuration == null) {
            throw new IllegalArgumentException("Cannot handle null 'configuration' argument.");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Cannot handle null 'entity' argument.");
        }
        if (!accept(entity)) {
            throw new IllegalArgumentException("Entity of type [" + entity.getClass().getName()
                    + "] was not accepted for rendering. Only [" + acceptedType.getName()
                    + "] types can be rendered by this " + getClass().getSimpleName() + " renderer.");
        }

        // Find out the combination of newline and ';' which should be emitted after rendering.
        String epilogue = "";
        if (entity instanceof Subgraph) {
            epilogue = configuration.getNewline();
        } else if (entity instanceof Statement) {
            epilogue = " ;" + configuration.getNewline();
        }

        // All Done.
        final T castEntity = acceptedType.cast(entity);
        return doRender(configuration, castEntity) + epilogue;
    }

    /**
     * Implement this method to provide the algorithms rendering the supplied (and typed) entity as a String.
     * Do not add a {@link RenderConfiguration#NEWLINE} afterwards, as it is done by the render method itself.
     *
     * @param config The non-null RenderConfiguration.
     * @param entity The non-null entity of type T.
     * @return The rendered value for the supplied entity.
     */
    protected abstract String doRender(final RenderConfiguration config, final T entity);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final Object entity) {
        return entity != null && acceptedType.isAssignableFrom(entity.getClass());
    }

    /**
     * Quotes the supplied string (i.e. surrounds it with "'s).
     *
     * @param toQuote The string to quote.
     * @return the supplied string surrounded with double quotes.
     */
    protected final String quote(final String toQuote) {
        return "\"" + toQuote + "\"";
    }

    /**
     * @return A standard string representation of this {@link AbstractStringRenderer}.
     */
    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "]";
    }
}
