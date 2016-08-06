/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2016 jGuru Europe AB
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

import java.io.Serializable;

/**
 * Specification for how to render entities into formats intended for consumption by humans or applications.
 * Typically a renderer should be invoked in a simple, two-step manner:
 * <pre>
 *     <code>
 *         final Object someEntity = ...
 *         final RenderConfiguration config = ...
 *         final Renderer someRenderer = new MyRendererImplementation();
 *
 *         if(someRenderer.accept(someEntity)) {
 *
 *              // Render the entity, retrieve the result.
 *              T result = someRenderer.render(config, someEntity);
 *
 *              // Now, do something with the result.
 *              // ....
 *         }
 *
 *     </code>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface Renderer<T> extends Serializable {

    /**
     * Determines if the supplied object is accepted (for rendering) by this Renderer.
     *
     * @param entity An entity Object to be tested for rendering by this Renderer.
     * @return true if the supplied candidate object can be accepted for processing/rendering by this Renderer.
     */
    boolean accept(final Object entity);

    /**
     * <p>Renders the supplied entity into a format intended for consumption by humans or applications, optionally
     * reading configuration parameters from the supplied RenderConfiguration. Rendering should normally be done in
     * three steps, where the prologue and epilogue steps normally are used only when the standard rendering needs
     * some context:</p>
     * <ol>
     * <li>Prologue</li>
     * <li>Standard</li>
     * <li>Epilogue</li>
     * </ol>
     *
     * @param configuration A non-null configuration for the prologue rendering of the supplied entity.
     * @param entity        A non-null and {@link #accept(Object)}'ed entity which should be rendered.
     * @return The rendered result of the supplied entity.
     */
    T render(final RenderConfiguration configuration, final Object entity);
}
