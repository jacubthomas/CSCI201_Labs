package jharring_CSCI201L_Lab2;

public class SalariedEmployee extends Employee {
	private double annualSalary;
	
	public SalariedEmployee(String firstName, String lastName, String birthdate,
							int employeeID, String jobTitle, String company,
							double annualSalary) {
		super(firstName, lastName, birthdate, employeeID, jobTitle, company);
		this.annualSalary = annualSalary;
	}

	@Override
	public double getAnnualSalary() {
		return annualSalary;
	}
}
