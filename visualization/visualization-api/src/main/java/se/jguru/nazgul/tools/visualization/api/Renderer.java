package se.jguru.nazgul.tools.visualization.api;

import java.io.Serializable;

/**
 * Specification for how to render entities into formats intended for consumption by humans or applications.
 * Typically a renderer should be invoked in a simple, two-step manner:
 * <pre>
 *     <code>
 *         final Object someEntity = ...
 *         final Renderer someRenderer = new MyRendererImplementation();
 *
 *         if(someRenderer.accept(someEntity)) {
 *              // Render, and retrieve the result
 *              T result = someRenderer.render(someEntity);
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
     * Renders the supplied entity into a format intended for consumption by humans or applications.
     *
     * @param entity An accepted candidate Object which should be rendered.
     * @return The rendered result of the supplied entity.
     */
    T render(final Object entity);
}
