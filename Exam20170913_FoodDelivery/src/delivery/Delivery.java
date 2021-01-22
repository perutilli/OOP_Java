package delivery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Delivery {
    
    public static enum OrderStatus { NEW, CONFIRMED, PREPARATION, ON_DELIVERY, DELIVERED } 
    
    private List<Customer> customers = new ArrayList<>();
    private List<MenuItem> menu = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    
    /**
     * Creates a new customer entry and returns the corresponding unique ID.
     * 
     * The ID for the first customer must be 1.
     * 
     * 
     * @param name name of the customer
     * @param address customer address
     * @param phone customer phone number
     * @param email customer email
     * @return unique customer ID (positive integer)
     */
    public int newCustomer(String name, String address, String phone, String email) 
    		throws DeliveryException {
    	if (!this.validEmail(email)) {
    		throw(new DeliveryException());
    	}
    	int id = this.customers.size() + 1;
    	this.customers.add(new Customer(id, name, address, phone, email));
        return id;
    }
    
    /**
     * Returns a string description of the customer in the form:
     * <pre>
     * "NAME, ADDRESS, PHONE, EMAIL"
     * </pre>
     * 
     * @param customerId
     * @return customer description string
     */
    public String customerInfo(int customerId){
        return this.getCustomer(customerId).toString();
    }
    
    /**
     * Returns the list of customers, sorted by name
     * 
     */
    public List<String> listCustomers(){
        return this.customers.stream().map(Customer::toString).sorted().
        		collect(Collectors.toList());
    }
    
    /**
     * Add a new item to the delivery service menu
     * 
     * @param description description of the item (e.g. "Pizza Margherita", "Bunet")
     * @param price price of the item (e.g. 5 EUR)
     * @param category category of the item (e.g. "Main dish", "Dessert")
     * @param prepTime estimate preparation time in minutes
     */
    public void newMenuItem(String description, double price, String category, int prepTime){
        this.menu.add(new MenuItem(description, price, category, prepTime));
    }
    
    /**
     * Search for items matching the search string.
     * The items are sorted by category first and then by description.
     * 
     * The format of the items is:
     * <pre>
     * "[CATEGORY] DESCRIPTION : PRICE"
     * </pre>
     * 
     * @param search search string
     * @return list of matching items
     */
    public List<String> findItem(String search){
        return this.findMenuItem(search).stream().map(MenuItem::toString).sorted()
        		.collect(Collectors.toList());
    }
    
    /**
     * Creates a new order for the given customer.
     * Returns the unique id of the order, a progressive
     * integer number starting at 1.
     * 
     * @param customerId
     * @return order id
     */
    public int newOrder(int customerId){
    	Customer c = this.getCustomer(customerId);
    	int id = this.orders.size() + 1;
    	this.orders.add(new Order(id, c));
        return id;
    }
    
    /**
     * Add a new item to the order with the given quantity.
     * 
     * If the same item is already present in the order is adds the
     * given quantity to the current quantity.
     * 
     * The method returns the final total quantity of the item in the order. 
     * 
     * The item is found through the search string as in {@link #findItem}.
     * If none or more than one item is matched, then an exception is thrown.
     * 
     * @param orderId the id of the order
     * @param search the item search string
     * @param qty the quantity of the item to be added
     * @return the final quantity of the item in the order
     * @throws DeliveryException in case of non unique match or invalid order ID
     */
    public int addItem(int orderId, String search, int qty) throws DeliveryException {
    	List<MenuItem> items = this.findMenuItem(search);
    	if (items.size() != 1) {
    		throw(new DeliveryException());
    	}
    	return this.getOrder(orderId).addItem(items.get(0), qty);
    }
    
    /**
     * Show the items of the order using the following format
     * <pre>
     * "DESCRIPTION, QUANTITY"
     * </pre>
     * 
     * @param orderId the order ID
     * @return the list of items in the order
     * @throws DeliveryException when the order ID in invalid
     */
    public List<String> showOrder(int orderId) throws DeliveryException {
        return this.getOrder(orderId).getComposition();
    }
    
    /**
     * Retrieves the total amount of the order.
     * 
     * @param orderId the order ID
     * @return the total amount of the order
     * @throws DeliveryException when the order ID in invalid
     */
    public double totalOrder(int orderId) throws DeliveryException {
        return this.getOrder(orderId).getPrice();
    }
    
    /**
     * Retrieves the status of an order
     * 
     * @param orderId the order ID
     * @return the current status of the order
     * @throws DeliveryException when the id is invalid
     */
    public OrderStatus getStatus(int orderId) throws DeliveryException {
        return this.getOrder(orderId).getStatus();
    }
    
    /**
     * Confirm the order. The status goes from {@code NEW} to {@code CONFIRMED}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>start-up delay (conventionally 5 min)
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code NEW}
     */
    public int confirm(int orderId) throws DeliveryException {
        return 
    		this.getOrder(orderId).changeStatus(OrderStatus.CONFIRMED).getEstimateDelTime();
    }

    /**
     * Start the order preparation. The status goes from {@code CONFIRMED} to {@code PREPARATION}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code CONFIRMED}
     */
    public int start(int orderId) throws DeliveryException {
    	return 
			this.getOrder(orderId).changeStatus(OrderStatus.PREPARATION).getEstimateDelTime();
    }

    /**
     * Begins the order delivery. The status goes from {@code PREPARATION} to {@code ON_DELIVERY}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code PREPARATION}
     */
    public int deliver(int orderId) throws DeliveryException {
        return 
    		this.getOrder(orderId).changeStatus(OrderStatus.ON_DELIVERY).getEstimateDelTime();
    }
    
    /**
     * Complete the delivery. The status goes from {@code ON_DELIVERY} to {@code DELIVERED}
     * 
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code ON_DELIVERY}
     */
    public void complete(int orderId) throws DeliveryException {
    	this.getOrder(orderId).changeStatus(OrderStatus.DELIVERED);
    }
    
    /**
     * Retrieves the total amount for all orders of a customer.
     * 
     * @param customerId the customer ID
     * @return total amount
     */
    public double totalCustomer(int customerId){
        return this.orders.stream().filter(o -> 
        		o.getStatus() != OrderStatus.NEW && o.getCustomer().getId() == customerId).
    		mapToDouble(o -> o.getPrice()).sum();
    }
    
    /**
     * Computes the best customers by total amount of orders.
     *  
     * @return the classification
     */
    public SortedMap<Double,List<String>> bestCustomers(){
    	SortedMap<Double,List<String>> map = new TreeMap<>(Comparator.reverseOrder());
    	this.customers.stream().sorted((c1, c2) -> c1.toString().compareTo(c2.toString())).
    		forEach(c -> map.merge(
    				this.totalCustomer(c.getId()), 
    				newList(c.toString()),
    				(l1, l2) -> {
    					List<String> list = new ArrayList<String>();
    					list.addAll(l1);
    					list.addAll(l2);
    					return list;
    				}));
        return map;
    }
    
    private List<String> newList(String s) {
    	List<String> list = new ArrayList<String>();
		list.add(s);
		return list;
    }
    
    private boolean validEmail(String email) {
	    return !this.customers.stream().filter(c -> c.getEmail().equals(email)).
	    		findFirst().isPresent();
    }
    
    /**
     * Assumes the customer exists!!!
     * 
     * @return customer with the given id
     */
    private Customer getCustomer(int customerId) {
    	return this.customers.stream().filter(c -> c.getId() == customerId).findAny().get();
    }
    
    private Order getOrder(int orderId) throws DeliveryException{
    	Optional<Order> order = 
    		this.orders.stream().filter(o -> o.getId() == orderId).findAny();
    	if (!order.isPresent()) {
    		throw(new DeliveryException());
    	}
        return order.get();
    }
    
    private List<MenuItem> findMenuItem(String search){
   	 return this.menu.stream().
        		filter(m -> m.getDescription().toLowerCase().contains(search.toLowerCase())).
        		collect(Collectors.toList());
   }
    
// NOT REQUIRED
//
//    /**
//     * Computes the best items by total amount of orders.
//     *  
//     * @return the classification
//     */
//    public List<String> bestItems(){
//        return null;
//    }
//    
//    /**
//     * Computes the most popular items by total quantity ordered.
//     *  
//     * @return the classification
//     */
//    public List<String> popularItems(){
//        return null;
//    }

}
