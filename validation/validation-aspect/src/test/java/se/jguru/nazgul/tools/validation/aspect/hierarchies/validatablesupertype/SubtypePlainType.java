/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect.hierarchies.validatablesupertype;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class SubtypePlainType extends ValidatableSuperType {

    private int id;

    public SubtypePlainType(final int id) {
        super(id);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
