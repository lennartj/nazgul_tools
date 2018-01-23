/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-impl-doclet
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
package se.jguru.nazgul.tools.visualization.impl.doclet.sample;

/**
 * Employee person implementation.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class Employee extends Person {

    // Internal state
    private String title;
    private double monthlySalary;

    /**
     * Compound constructor creating an Employee wrapping the supplied data.
     *
     * @param firstName     The first name of the employee.
     * @param lastName      The last name of the employee.
     * @param age           The age of the employee.
     * @param title         The title of the employee.
     * @param monthlySalary The salary of the employee.
     */
    public Employee(final String firstName,
                    final String lastName,
                    final int age,
                    final String title,
                    final double monthlySalary) {

        super(firstName, lastName, age);
        this.title = title;
        this.monthlySalary = monthlySalary;
    }

    /**
     * Retrieves the title of this employee.
     *
     * @return the title of this employee.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the monthly salary of this employee.
     *
     * @return the monthly salary of this employee.
     */
    public double getMonthlySalary() {
        return monthlySalary;
    }
}
