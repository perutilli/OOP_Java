package sports;

import java.util.ArrayList;
import java.util.List;

public class Product {
	
	private String name;
	private String activity;
	private Category category;
	private List<Rating> ratings = new ArrayList<>();
	
	public Product(String name, String activity, Category category) {
		this.name = name;
		this.activity = activity;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void addRating(Rating rating) {
		this.ratings.add(rating);
	}
	
	public List<Rating> getRatings() {
		return this.ratings;
	}
}
