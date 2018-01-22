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
