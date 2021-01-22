package delivery;

public class MenuItem {
	
	private String description;
	private double price;
	private String category;
	private int prepTime;
	
	public MenuItem(String description, double price, String category, int prepTime) {
		this.description = description;
		this.price = price;
		this.category = category;
		this.prepTime = prepTime;		
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getPrepTime() {
		return this.prepTime;
	}
	
	@Override
	public String toString() {
		return "[" + this.category + "] " + this.description + " : " + 
				String.format("%.2f", this.price);
	}

}
