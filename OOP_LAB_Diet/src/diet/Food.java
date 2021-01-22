package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {
	
	private List<NutritionalElement> rawMaterials = new ArrayList<>();
	private List<NutritionalElement> products = new ArrayList<>();
	private List<NutritionalElement> recipes = new ArrayList<>();
	private List<NutritionalElement> menus = new ArrayList<>();

	/**
	 * Define a new raw material.
	 * 
	 * The nutritional values are specified for a conventional 100g amount
	 * @param name 		unique name of the raw material
	 * @param calories	calories per 100g
	 * @param proteins	proteins per 100g
	 * @param carbs		carbs per 100g
	 * @param fat 		fats per 100g
	 */
	public void defineRawMaterial(String name,
									  double calories,
									  double proteins,
									  double carbs,
									  double fat){
		this.rawMaterials.add(new RawMaterial(name, calories, proteins, carbs, fat));
	}
	
	/**
	 * Retrieves the collection of all defined raw materials
	 * 
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials(){
		this.rawMaterials.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
		return this.rawMaterials;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * 
	 * @param name  name of the raw material
	 * 
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name){
		NutritionalElement elem = null;
		for (NutritionalElement e: this.rawMaterials) {
			if (e.getName().equals(name)) {
				elem = e;
				break;
			}
		}
		return elem;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * 
	 * @param name 		unique name of the product
	 * @param calories	calories for a product unit
	 * @param proteins	proteins for a product unit
	 * @param carbs		carbs for a product unit
	 * @param fat 		fats for a product unit
	 */
	public void defineProduct(String name,
								  double calories,
								  double proteins,
								  double carbs,
								  double fat){
		this.products.add(new Product(name, calories, proteins, carbs, fat));
	}
	
	/**
	 * Retrieves the collection of all defined products
	 * 
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products(){
		this.products.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
		return this.products;
	}
	
	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name){
		NutritionalElement elem = null;
		for (NutritionalElement e: this.products) {
			if (e.getName().equals(name)) {
				elem = e;
				break;
			}
		}
		return elem;
	}
	
	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * 
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe r = new Recipe(name, this);
		recipes.add(r);
		return r;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * 
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes(){
		recipes.sort((r1, r2) -> r1.getName().compareTo(r2.getName()));
		return recipes;
	}
	
	/**
	 * Retrieves a specific recipe, given its name
	 * 
	 * @param name  name of the recipe
	 * 
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name){		
		NutritionalElement elem = null;
		for (NutritionalElement e: this.recipes) {
			if (e.getName().equals(name)) {
				elem = e;
				break;
			}
		}
		return elem;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu menu = new Menu(name, this);
		this.menus.add(menu);
		return menu;
	}
	
}
