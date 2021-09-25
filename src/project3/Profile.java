package project3;

/**
 * Profile is a class to store an employees personal information that is
 * contained within the Employee class. This class stores the
 * name of an employee, the department of an employee, and the date
 * an employee was hired.
 * @author Shane Hoffman, Michael Li
 */
public class Profile {
    private String name;
    private String department;
    private Date dateHired;

    /**
     * Creates a profile object using an employees name, department,
     * date hired.
     * @param name - name of the employee
     * @param department - department of the employee
     * @param dateHired - date hired of the employee
     */
    public Profile(String name, String department, Date dateHired) {
        this.name = name;
        this.department = department;
        this.dateHired = dateHired;
    }


    /**
     * Gets the department of a profile.
     * @return department - a profiles department
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * Gets the date hired of a profile.
     * @return dateHired - date hired of a profile
     */
    public Date getDate() {
        return this.dateHired;
    }

    /**
     * Converts a profile object into a readable string.
     * @return profileString - string that displays the content of a profile
     */
    @Override
    public String toString() {
        return String.format("%s::%s::%s::",
                this.name, this.department, this.dateHired.toString());
    }

    /**
     * Compares two profile objects by name, department, and date hired
     * to see if they are equal.
     * @param obj - profile being compared to
     * @return true if the two profiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Profile) {
            Profile prf = (Profile) obj;
            if(this.name.equals(prf.name) && this.department.equals(prf.department)
                    && this.dateHired.compareTo(prf.dateHired) == 0)
                return true;
        }
        return false;
    }

}
