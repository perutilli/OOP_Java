package it.polito.oop.futsal;

public class Associate {
	
	private int id;
	private String name;
	private String surname;
	private String phone;
	private boolean hasBooked = false;
	private static int nAssociates = 0;
	
	public Associate(String name, String surname, String phone) {
		nAssociates++;
		this.id = nAssociates;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getPhone() {
		return phone;
	}

	public int getId() {
		return id;
	}

	public boolean hasBooked() {
		return hasBooked;
	}

	public void book() {
		this.hasBooked = true;
	}
	
}
