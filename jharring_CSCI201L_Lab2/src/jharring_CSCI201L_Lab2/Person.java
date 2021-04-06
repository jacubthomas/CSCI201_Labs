package jharring_CSCI201L_Lab2;

public class Person {
	private String firstName = null;
	private String lastName = null;
	private String birthdate = null;
	
	public Person(String firstName, String lastName, String birthdate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;

}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public String getBirthdate() {
		return this.birthdate;
	}
}
