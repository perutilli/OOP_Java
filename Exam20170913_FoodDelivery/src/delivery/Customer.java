package delivery;

public class Customer {
	
	private int id;
	private String name;
	private String address;
	private String phone;
	private String email;
	
	public Customer(int id, String name, String address, String phone, String email) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public String toString() {
		return this.name + ", " + this.address + ", " + this.phone + ", " + this.email;
	}
}
