/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect.hierarchies.abstractsupertype;

import se.jguru.nazgul.tools.validation.api.Validatable;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class ValidatableSupertype implements Validatable {

    // Internal state
    private String name;

    protected ValidatableSupertype() {
    }

    protected ValidatableSupertype(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
