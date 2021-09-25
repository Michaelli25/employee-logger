package project3;

import java.text.DecimalFormat;

/**
 * Parttime class is an extension of the Employee class. It is for
 * part time employees and holds an employees pay rate, hours worked.
 * @author Shane Hoffman, Michael Li
 */
public class Parttime extends Employee{
	private double payRate;
	private int hoursWorked;
	private int overtimeHours;

	private static final int MAX_REGULAR_HOURS = 80;
	private static final double OVERTIME_RATE = 1.5;
	private static final int DIGITS = 2;
	private static final DecimalFormat df = new DecimalFormat("###,###,###.##");

	/**
	 * Creates a Parttime object using a profile and a pay rate.
	 * @param profile - profile of an employee
	 * @param payRate - hourly pay rate of an employee
	 */
	public Parttime(Profile profile, double payRate) {
		super(profile);
		this.payRate = payRate;
	}

	/**
	 * Creates a Parttime object for the setHours method using a profile and
	 * the number of hours worked.
	 * @param profile - profile of an employee
	 * @param hours - hours worked by an employee
	 */
	public Parttime(Profile profile, int hours) {
		super(profile);
		this.hoursWorked = hours;
	}

	/**
	 * Sets the hours a part time employee worked. If the hours are over the max then
	 * overtime hours are also set.
	 * @param hours - hours worked by an employee
	 */
	public void setHours(int hours) {
		if(hours > MAX_REGULAR_HOURS)
			this.overtimeHours = hours - MAX_REGULAR_HOURS;
		this.hoursWorked = hours;
	}

	/**
	 * Gets the working hours of a part time employee.
	 * @return hours - hours worked by an employee
	 */
	public int getHours() {
		return this.hoursWorked;
	}

	/**
	 * Converts a Parttime object into a readable string.
	 * @return parttimeString - string that displays the part time employee, period
	 * earnings, pay rate, and hours the hours worked of a parttime employee
	 */
	@Override
	public String toString() {
		df.setMinimumFractionDigits(DIGITS);
		return super.toString() + "Payment $" + df.format(super.getEarnings())
        	+ "::PART TIME::Hourly Rate $" + df.format(this.payRate) +
        	"::Hours worked this period: " + this.hoursWorked;
	}

	/**
	 * Compares two parttime objects by profile to see if they are equal.
	 * @param obj - part time employee being compared to
	 * @return true if the two part time employees are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Parttime) {
			Parttime part = (Parttime) obj;
			if(this.getProfile().equals(part.getProfile()))
				return true;
		}
		return false;
	}

	/**
	 * Calculates the period earnings for a part time employee.
	 */
	@Override
	public void calculatePayment() {
		if(this.hoursWorked > MAX_REGULAR_HOURS)
			super.setEarnings((MAX_REGULAR_HOURS * this.payRate) +
					(this.overtimeHours * this.payRate * OVERTIME_RATE));
		else
			super.setEarnings(this.hoursWorked * this.payRate);
	}


}
