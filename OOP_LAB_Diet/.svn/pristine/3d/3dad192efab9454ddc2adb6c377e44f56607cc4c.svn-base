package diet;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	
	private String name;
	// ingredients are raw materials so nutr values are all per100g
	private Map<NutritionalElement, Double> ingredients = new LinkedHashMap<>();
	private Food food;
	private double mass = 0;
	
	public Recipe(String name, Food food) {
		this.name = name;
		this.food = food;
		
	}
    

	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		this.ingredients.put(this.food.getRawMaterial(material), quantity);
		this.mass += quantity;
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		double totCalories = 0;
		for (Map.Entry<NutritionalElement, Double> e: this.ingredients.entrySet()) {
			totCalories += e.getKey().getCalories() * e.getValue()/100;
		}
		return totCalories * 100/this.mass;
	}

	@Override
	public double getProteins() {
		double totProteins = 0;
		for (Map.Entry<NutritionalElement, Double> e: this.ingredients.entrySet()) {
			totProteins += e.getKey().getProteins() * e.getValue()/100;
		}
		return totProteins * 100/this.mass;
	}

	@Override
	public double getCarbs() {
		double totCarbs = 0;
		for (Map.Entry<NutritionalElement, Double> e: this.ingredients.entrySet()) {
			totCarbs += e.getKey().getCarbs() * e.getValue()/100;
		}
		return totCarbs * 100/this.mass;
	}

	@Override
	public double getFat() {
		double totFat = 0;
		for (Map.Entry<NutritionalElement, Double> e: this.ingredients.entrySet()) {
			totFat += e.getKey().getFat() * e.getValue()/100;
		}
		return totFat * 100/this.mass;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		for (Map.Entry<NutritionalElement, Double> e: this.ingredients.entrySet()) {
			sb.append(String.format("%s : %3.1f\n", e.getKey().getName(), e.getValue()));
		}
		return sb.toString();
	}
}
