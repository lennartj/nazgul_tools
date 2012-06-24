/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
 */

package se.jguru.nazgul.tools.validation.aspect.hierarchies.validatablesubtype;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class SupertypePlainType {

    // Internal state
    private String name;

    public SupertypePlainType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
