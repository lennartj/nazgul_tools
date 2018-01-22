package se.jguru.nazgul.tools.visualization.impl.doclet.sample;

import java.io.Serializable;

/**
 * Simple Person implementation.
 * 
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class Person implements Serializable {

    // Internal state
    private String firstName, lastName;
    private int age;

    /**
     * Compound constructor creating a Person wrapping the supplied data.
     *
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @param age The age of the person.
     */
    public Person(final String firstName, final String lastName, final int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * Retrieves the first name of this person.
     *
     * @return the first name of this person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the last name of this person.
     *
     * @return the last name of this person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the age of this person.
     *
     * @return the age of this person.
     */
    public int getAge() {
        return age;
    }
}
