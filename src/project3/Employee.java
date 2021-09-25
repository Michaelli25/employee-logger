package project3;

/**
 * Employee is a class to store the profile of an employee, and
 * their period earnings.
 * @author Shane Hoffman, Michael Li
 */
public class Employee {
    private Profile profile;
    private double earnings;

    /**
     * Creates an employee object using a profile.
     * @param profile - profile of the employee
     */
    public Employee(Profile profile) {
        this.profile = profile;
    }

    /**
     * Method to be overridden by child classes.
     */
    public void calculatePayment() { }

    /**
     * Gets the department of an employee.
     * @return department - an employees department
     */
    public String getDepartment() {
        return this.profile.getDepartment();
    }

    /**
     * Gets the date hired of an employee.
     * @return dateHired - the date an employee was hired
     */
    public Date getDate() {
        return this.profile.getDate();
    }

    /**
     * Gets the profile of an employee.
     * @return profile - the profile of an employee
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Gets the period earnings of an employee.
     * @return earnings - the earnings of an employee
     */
    public double getEarnings() {
        return this.earnings;
    }

    /**
     * Sets the period earnings of an employee.
     * @param earnings - the earnings of an employee
     */
    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    /**
     * Converts an employee object into a readable string.
     * @return employeeString - string that displays the contents of an employee
     */
    @Override
    public String toString() {
        return this.profile.toString();
    }

    /**
     * Compares two employees by their profiles to see if they are equal.
     * @param obj - employee being compared to
     * @return true if the two employees are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Employee) {
            Employee emp = (Employee) obj;
            if(this.profile.equals(emp.profile))
                return true;
        }
        return false;
    }

}
