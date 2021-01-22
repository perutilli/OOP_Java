package diet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	private String name;
	private Food food;
	private Map<NutritionalElement, Double> recipes = new LinkedHashMap<>();
	private List<NutritionalElement> products = new ArrayList<>();
	
	public Menu(String name, Food food) {
		this.name = name;
		this.food = food;
	}
	
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu addRecipe(String recipe, double quantity) {
		NutritionalElement r = this.food.getRecipe(recipe);
		this.recipes.put(r, quantity);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		this.products.add(this.food.getProduct(product));
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		double totCalories = 0;
		for (NutritionalElement e: this.products) {
			totCalories += e.getCalories();
		}
		for (Map.Entry<NutritionalElement, Double> e : this.recipes.entrySet()) {
			totCalories += e.getKey().getCalories() * e.getValue()/100;
		}
		return totCalories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		double totProteins = 0;
		for (NutritionalElement e: this.products) {
			totProteins += e.getProteins();
		}
		for (Map.Entry<NutritionalElement, Double> e : this.recipes.entrySet()) {
			totProteins += e.getKey().getProteins() * e.getValue()/100;
		}
		return totProteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double totCarbs = 0;
		for (NutritionalElement e: this.products) {
			totCarbs += e.getCarbs();
		}
		for (Map.Entry<NutritionalElement, Double> e : this.recipes.entrySet()) {
			totCarbs += e.getKey().getCarbs() * e.getValue()/100;
		}
		return totCarbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double totFat = 0;
		for (NutritionalElement e: this.products) {
			totFat += e.getFat();
		}
		for (Map.Entry<NutritionalElement, Double> e : this.recipes.entrySet()) {
			totFat += e.getKey().getFat() * e.getValue()/100;
		}
		return totFat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
