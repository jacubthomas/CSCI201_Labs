package jharring_CSCI201L_Lab2;

public class HourlyEmployee extends Employee {

	private double hourlyRate;
	private int numberHoursPerWeek;
	
	public HourlyEmployee(String firstName, String lastName, String birthdate,
						int employeeID, String jobTitle, String company,
						double hourlyRate, int numberHoursPerWeek) {
		super(firstName, lastName, birthdate, employeeID, jobTitle, company);
		this.hourlyRate = hourlyRate;
		this.numberHoursPerWeek = numberHoursPerWeek;
	}
	
	@Override
	public double getAnnualSalary() {
		return hourlyRate * numberHoursPerWeek * 52;
	}

}
