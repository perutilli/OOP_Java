package clinic;

public abstract class Person {
	
	private String SSN;
	private String name;
	private String surname;
	
	public Person(String SSN, String name, String surname) {
		this.SSN = SSN;
		this.name = name;
		this.surname = surname;
	}

	public String getSSN() {
		return this.SSN;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	@Override
	public String toString() {
		return this.surname + " " + this.name + " (" + this.SSN + ")";
	}
}
