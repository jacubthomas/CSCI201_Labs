package jharring_CSCI201L_Lab2;

public abstract class Employee extends Person {
	private int employeeID;
	private String jobTitle;
	private String company;
	
	public abstract double getAnnualSalary();
	
	public Employee(String firstName,  String lastName, String birthdate, 
						int employeeID, String jobTitle, String company) {
		super(firstName, lastName, birthdate);
		this.employeeID = employeeID;
		this.jobTitle = jobTitle;
		this.company = company;
	}
	
	public String getJobTitle() {
		return this.jobTitle;
	}

	public String getCompany() {
		return this.company;
	}
	
	public int getEmployeeID() {
		return this.employeeID;
	}
}
