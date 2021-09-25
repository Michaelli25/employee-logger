package project3;

import java.text.DecimalFormat;

/**
 * Fulltime class is an extension of the Employee class. It is for
 * full time employees and holds an employees annual salary.
 * @author Shane Hoffman, Michael Li
 */
public class Fulltime extends Employee{
	private double annualSalary;

    private static final int PAYPERIODS = 26;
    private static final int DIGITS = 2;
    private static final DecimalFormat df = new DecimalFormat("###,###,###.##");

    /**
     * Creates a Fulltime object using a profile and an annual salary.
     * @param profile - profile of an employee
     * @param annualSalary - annual salary of an employee
     */
	public Fulltime(Profile profile, double annualSalary) {
		super(profile);
		this.annualSalary = annualSalary;
	}

    /**
     * Helper method for the management class setting their period payment.
     * @param compensation - additional managerial compensation
     */
	public void setPay(double compensation) {
	    super.setEarnings(annualSalary/PAYPERIODS + compensation);
    }

    /**
     * Converts a Fulltime object into a readable string.
     * @return fulltimeString - string that displays the full time employee, period
     * earnings and salary of a fulltime employee
     */
	@Override
    public String toString() {
        df.setMinimumFractionDigits(DIGITS);
        return super.toString() + "Payment $" + df.format(super.getEarnings())
        	+ "::FULL TIME::Annual Salary $" + df.format(annualSalary);
    }

    /**
     * Compares two fulltime objects by profile to see if they are equal.
     * @param obj - full time employee being compared to
     * @return true if the two full time employees are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Fulltime) {
            Fulltime full = (Fulltime) obj;
            if(this.getProfile().equals(full.getProfile()))
                return true;
        }
        return false;
    }

    /**
     * Calculates the period earnings for a full time employee.
     */
    @Override
    public void calculatePayment() {
    	super.setEarnings(annualSalary/PAYPERIODS);
    }

}