package se.jguru.nazgul.tools.visualization.model.diagram;

import se.jguru.nazgul.tools.visualization.model.diagram.attribute.AbstractAttributes;

/**
 * Specification for an object which can hold a set of typed attributes.
 * These attributes are wrapped within a Statement, which is created upon first access unless already present.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface TypedAttributeHolder<T extends AbstractAttributes> {

    /**
     * Retrieves the typed attributes (packaged within an {@link AbstractAttributes} container).
     *
     * @return the typed attributes (packaged within an {@link AbstractAttributes} container).
     */
    T getAttributes();
}
