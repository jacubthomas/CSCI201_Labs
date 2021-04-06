package jharring_CSCI201L_Lab2;

public class CommissionEmployee extends SalariedEmployee{
	private double salesTotal;
	private double commissionPercentage;
	
	public CommissionEmployee(String firstName, String lastName, String birthdate,
			int employeeID, String jobTitle, String company, double annualSalary, 
				double salesTotal, double commissionPercentage) {
		super(firstName, lastName, birthdate, employeeID, jobTitle, company, annualSalary);
		this.salesTotal = salesTotal;
		this.commissionPercentage  = commissionPercentage;
	}
	@Override
	public double getAnnualSalary() {
		return (salesTotal * commissionPercentage) + super.getAnnualSalary();
	}
}
