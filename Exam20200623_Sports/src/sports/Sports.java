package sports;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {
	
	private List<String> activities = new ArrayList<>();
	private List<Category> categories = new ArrayList<>();
	private List<Product> products = new ArrayList<>();
	private List<Rating> ratings = new ArrayList<>();

    //R1
    /**
     * Define the activities types treated in the portal.
     * The method can be invoked multiple times to add different activities.
     * 
     * @param actvities names of the activities
     * @throws SportsException thrown if no activity is provided
     */
    public void defineActivities (String... activities) throws SportsException {
    	if(activities.length == 0) {
    		throw(new SportsException("NO activity passed"));
    	}
    	for(String a: activities) {
    		this.activities.add(a);
    	}
    }

    /**
     * Retrieves the names of the defined activities.
     * 
     * @return activities names sorted alphabetically
     */
    public List<String> getActivities() {
        return this.activities.stream().sorted().collect(Collectors.toList());
    }


    /**
     * Add a new category of sport products and the linked activities
     * 
     * @param name name of the new category
     * @param activities reference activities for the category
     * @throws SportsException thrown if any of the specified activity does not exist
     */
    public void addCategory(String name, String... linkedActivities) throws SportsException {
    	Category c = new Category(name);
    	for(String act: linkedActivities) {
    		if(!this.activities.contains(act)) {
    			throw(new SportsException("Activity does not exists"));
    		}
    		c.addActivity(act);    	
		} 	
    	this.categories.add(c);
    }

    /**
     * Retrieves number of categories.
     * 
     * @return categories count
     */
    public int countCategories() {
        return this.categories.size();
    }

    /**
     * Retrieves all the categories linked to a given activity.
     * 
     * @param activity the activity of interest
     * @return list of categories (sorted alphabetically)
     */
    public List<String> getCategoriesForActivity(String activity) {
        return this.categories.stream().filter(c -> c.getLinkedActivities().contains(activity)).
        		map(c -> c.getName()).sorted().collect(Collectors.toList());
    }

    //R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws SportsException thrown in case of duplicate name
     */
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
    	if(this.getProduct(name) != null) {
    		throw(new SportsException("Product already exists"));
    	}
    	Product p = new Product(name, activityName, this.getCategory(categoryName));
    	this.products.add(p);
    }
    
    private Product getProduct(String name) {
    	return this.products.stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);    	
    }
    
    private Category getCategory(String name) {
    	return this.categories.stream().filter(c -> c.getName().equals(name)).findAny().orElse(null);    	
    }

    /**
     * Retrieves the list of products for a given category.
     * The list is sorted alphabetically.
     * 
     * @param categoryName name of the category
     * @return list of products
     */
    public List<String> getProductsForCategory(String categoryName){
    	Category c = this.getCategory(categoryName);
        return this.products.stream().filter(p -> p.getCategory().equals(c)).map(Product::getName).
        		sorted().collect(Collectors.toList());
    }

    /**
     * Retrieves the list of products for a given activity.
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @return list of products
     */
    public List<String> getProductsForActivity(String activityName){
        return this.products.stream().filter(p -> p.getActivity().equals(activityName)).
        		map(Product::getName).
        		sorted().collect(Collectors.toList());
    }

    /**
     * Retrieves the list of products for a given activity and a set of categories
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @param categoryNames names of the categories
     * @return list of products
     */
    public List<String> getProducts(String activityName, String... categoryNames){
    	List<Category> cat = new ArrayList<>();
    	for(String c: categoryNames) {
    		cat.add(this.getCategory(c));
    	}
        return this.products.stream().filter(p -> p.getActivity().equals(activityName)).
        		filter(p -> cat.contains(p.getCategory())).map(Product::getName).
        		sorted().collect(Collectors.toList());
    }

    //    //R3
    /**
     * Add a new product rating
     * 
     * @param productName name of the product
     * @param userName name of the user submitting the rating
     * @param numStars score of the rating in stars
     * @param comment comment for the rating
     * @throws SportsException thrown numStars is not correct
     */
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
    	Product p = this.getProduct(productName);
    	Rating r = new Rating(p, userName, numStars, comment);
    	this.ratings.add(r);
    	p.addRating(r);
    }



    /**
     * Retrieves the ratings for the given product.
     * The ratings are sorted by descending number of stars.
     * 
     * @param productName name of the product
     * @return list of ratings sorted by stars
     */
    public List<String> getRatingsForProduct(String productName) {
    	Product p = this.getProduct(productName);
        return p.getRatings().stream().
        		sorted(Comparator.comparing(Rating::getNumStars).reversed()).
        		map(Rating::toString).collect(Collectors.toList());
    }


    //R4
    /**
     * Returns the average number of stars of the rating for the given product.
     * 
     * 
     * @param productName name of the product
     * @return average rating
     */
    public double getStarsOfProduct (String productName) {
    	Product p = this.getProduct(productName);
        return p.getRatings().stream().mapToInt(Rating::getNumStars).average().orElse(-1);
    }

    /**
     * Computes the overall average stars of all ratings
     *  
     * @return average stars
     */
    public double averageStars() {
        return this.ratings.stream().mapToInt(Rating::getNumStars).average().getAsDouble();

    }

    //R5 Statistiche
    /**
     * For each activity return the average stars of the entered ratings.
     * 
     * Activity names are sorted alphabetically.
     * 
     * @return the map associating activity name to average stars
     */
    public SortedMap<String, Double> starsPerActivity() {
    	SortedMap<String, Double> map = new TreeMap<>();
    	for (String a: this.activities) {
    		double avg = this.ratings.stream().
    				filter(r -> r.getProduct().getActivity().equals(a)).
    				mapToInt(Rating::getNumStars).average().orElse(-1);
    		if(avg != -1) {
    			map.put(a, avg);
    		}
    	}
        return map;
    }

    /**
     * For each average star rating returns a list of
     * the products that have such score.
     * 
     * Ratings are sorted in descending order.
     * 
     * @return the map linking the average stars to the list of products
     */
    public SortedMap<Double, List<String>> getProductsPerStars () {
    	return this.products.stream().filter(p -> this.getStarsOfProduct(p.getName()) >= 0).
    		sorted(Comparator.comparing(Product::getName)).
    		collect(Collectors.groupingBy(
    				p -> this.getStarsOfProduct(p.getName()),
    				() -> {
    					TreeMap<Double, List<String>> m = new TreeMap<>(
    							(d1, d2) -> -Double.compare(d1, d2));
    					return m;
    					
					},
    				Collectors.mapping(Product::getName, Collectors.toList())));
    }

}