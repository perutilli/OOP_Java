package groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
	
	private String name;
	private ProductType product;
	private List<Customer> customers = new ArrayList<>();
	private List<Supplier> suppliers = new ArrayList<>();
	private Map<Supplier, Integer> bids = new HashMap<>(); 
	private Map<Supplier, Integer> votes = new HashMap<>(); 
	
	public Group(String name, ProductType product) {
		this.name = name;
		this.product = product;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ProductType getProductType() {
		return this.product;
	}
	
	public void addCustomer(Customer customer) {
		this.customers.add(customer);
	}
	
	public List<Customer> getCustomers() {
		return this.customers;
	}
	
	public void setSuppliers(List<Supplier> s) {
		this.suppliers.addAll(s);
	}
	
	public List<Supplier> getSuppliers() {
		return this.suppliers;
	}
	
	public void addBid(Supplier supp, int price) throws GroupHandlingException {
		if (!this.suppliers.contains(supp)) {
			throw(new GroupHandlingException("invalid bid supplier"));
		}
		this.bids.put(supp, price);
	}
	
	public Map<Supplier, Integer> getBids() {
		return this.bids;
	}
	
	public void vote(Customer c, Supplier s) throws GroupHandlingException {
		if (this.bids.get(s) == null || !this.customers.contains(c)) {
			throw(new GroupHandlingException("invalid vote"));
		}
		this.votes.merge(s, 1, (old, v) -> old + v);
	}
	
	public Map<Supplier, Integer> getVotes() {
		return this.votes;
	}

}


























