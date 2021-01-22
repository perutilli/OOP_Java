package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {
	
	private List<Restaurant> restaurants = new ArrayList<>();
	private List<User> users = new ArrayList<>();
	private List<Order> orders = new ArrayList<>();

	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		this.restaurants.add(r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		List<String> names = this.restaurants.stream().map(r -> r.getName())
				.collect( Collectors.toList() );
		return names;
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User u = new User(lastName, firstName, email, phoneNumber);
		this.users.add(u);
		return u;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users(){
		this.users.sort((u1, u2) -> {
			int comp = u1.getLastName().compareTo(u2.getLastName());
			if (comp == 0) {
				comp = u1.getFirstName().compareTo(u2.getFirstName());
			}
			return comp;
		});
		return this.users;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Restaurant res = this.getRestaurant(restaurantName);
		String ora = String.format("%02d", h) + ":" + String.format("%02d", m);
		ora = res.getTime(ora);
		Order o = new Order(res, user, ora);
		this.orders.add(o);
		res.addOrder(o);
		return o;
	}
	
	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		Collection<Restaurant> openedRestaurants = 
				this.restaurants.stream().filter(r -> r.isOpen(time)).
				sorted((r1, r2) -> (r1.getName().compareTo(r2.getName()))).
				collect(Collectors.toList());
		return openedRestaurants;
	}
	
	private Restaurant getRestaurant(String name) {
		Restaurant res = null;
		for (Restaurant r: this.restaurants) {
			if (r.getName().equals(name)) {
				res = r;
				break;
			}
		}
		return res;
	}

	
	
}
