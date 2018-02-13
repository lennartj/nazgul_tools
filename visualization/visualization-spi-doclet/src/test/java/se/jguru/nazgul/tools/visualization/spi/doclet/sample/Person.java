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
package se.jguru.nazgul.tools.visualization.spi.doclet.sample;

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
