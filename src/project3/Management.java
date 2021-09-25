package project3;

import java.text.DecimalFormat;

/**
 * Management class is an extension of the Fulltime class. It is for
 * managerial employees and holds a managers title.
 * @author Shane Hoffman, Michael Li
 */
public class Management extends Fulltime{
	private boolean manager;
	private boolean departmentHead;
	private boolean director;

	private static final int MANAGER_CODE = 1;
	private static final int DEPARTMENT_HEAD_CODE = 2;
	private static final int DIRECTOR_CODE = 3;
	private static final double MANAGER_ANNUAL_BONUS = 5000;
	private static final double DEPARTMENT_HEAD_ANNUAL_BONUS = 9500;
	private static final double DIRECTOR_ANNUAL_BONUS = 12000;
	private static final int PAYPERIODS = 26;
	private static final int DIGITS = 2;
	private static final DecimalFormat df = new DecimalFormat("###,###,###.##");

	/**
	 * Creates a Management object using a profile, annual salary and manager code.
	 * @param profile - profile of an employee
	 * @param annualSalary - annual salary of an employee
	 * @param code - manager code of an employee
	 */
	public Management(Profile profile, double annualSalary, int code) {
		super(profile, annualSalary);
		switch (code) {
			case MANAGER_CODE:
				this.manager = true;
				break;

			case DEPARTMENT_HEAD_CODE:
				this.departmentHead = true;
				break;

			case DIRECTOR_CODE:
				this.director = true;
				break;
		}
	}

	/**
	 * Converts a Management object into a readable string.
	 * @return managementString - string that displays the managerial employee,
	 * period earnings, manager title, and additional manger compensation
	 */
	@Override
	public String toString() {
		df.setMinimumFractionDigits(DIGITS);
		if(this.manager)
			return super.toString() + "::Manager Compensation $" +  df.format(MANAGER_ANNUAL_BONUS/PAYPERIODS);
		else if(this.departmentHead)
			return super.toString() + "::DepartmentHead Compensation $" + df.format(DEPARTMENT_HEAD_ANNUAL_BONUS/PAYPERIODS);
		else
			return super.toString() + "::Director Compensation $" + df.format(DIRECTOR_ANNUAL_BONUS/PAYPERIODS);
	}

	/**
	 * Compares two management objects by profile to see if they are equal.
	 * @param obj - managerial employee being compared to
	 * @return true if the two part time employees are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Management) {
			Management man = (Management) obj;
			if(this.getProfile().equals(man.getProfile()))
				return true;
		}
		return false;
    }

	/**
	 * Calculates the period earnings for a part time employee.
	 */
	@Override
	public void calculatePayment() {
		if(manager)
			super.setPay(MANAGER_ANNUAL_BONUS/PAYPERIODS);
		else if(departmentHead)
			super.setPay(DEPARTMENT_HEAD_ANNUAL_BONUS/PAYPERIODS);
		else if(director)
			super.setPay(DIRECTOR_ANNUAL_BONUS/PAYPERIODS);
	}


}