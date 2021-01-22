package groups;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class GroupHandling {
	
	private List<ProductType> products = new ArrayList<>();
	private List<Supplier> suppliers = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();
	private List<Customer> customers = new ArrayList<>();
	
	
//R1	
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if (this.getProductType(productTypeName) != null) {
			throw(new GroupHandlingException());
		}
		ProductType p = new ProductType(productTypeName);
		this.products.add(p);
		for (String supName: supplierNames) {
			Supplier s = this.getSupplier(supName);
			if (s == null) {
				s = new Supplier(supName);
				this.suppliers.add(s);
			}
			p.addSupplier(s);
			s.addProduct(p);
		}
	}
	
	public List<String> getProductTypes (String supplierName) {
		Supplier s = this.getSupplier(supplierName);
		return s.getProductsSupplied().stream().map(ProductType::getName).sorted().
				collect(Collectors.toList());
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		ProductType p = this.getProductType(productName);
		if (p == null || this.getGroup(name) != null) {
			throw(new GroupHandlingException("unknown product"));
		}
		Group g = new Group(name, p);
		this.groups.add(g);
		for(String cName: customerNames) {
			Customer c = this.getCustomer(cName);
			if (c == null) {
				c = new Customer(cName);
				this.customers.add(c);
			}
			g.addCustomer(c);
			c.addGroup(g);			
		}
	}
	
	public List<String> getGroups (String customerName) {
        return this.getCustomer(customerName).getGroups().stream().map(Group::getName).
        		sorted().collect(Collectors.toList());
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		Group g = this.getGroup(groupName);
		List<Supplier> suppliers = new ArrayList<>();
		for (String suppName: supplierNames) {
			Supplier s = this.getSupplier(suppName);
			if(s == null || !s.getProductsSupplied().contains(g.getProductType())) {
				throw(new GroupHandlingException("invalid supplier"));
			}
			suppliers.add(s);
		}
		g.setSuppliers(suppliers);
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		Group g = this.getGroup(groupName);
		Supplier s = this.getSupplier(supplierName);
		g.addBid(s, price);
	}
	
	public String getBids (String groupName) {
		Group g = this.getGroup(groupName);
		StringBuffer sb = new StringBuffer("");
        g.getBids().entrySet().stream().sorted((e1, e2) -> {
        		int comp = e1.getValue() - e2.getValue();
        		if (comp == 0) {
        			comp = e1.getKey().getName().compareTo(e2.getKey().getName());
        		}
        		return comp;
        	}).
        	map(e -> e.getKey().getName() + ":" + e.getValue() + ",").
    		forEach(s -> sb.append(s));
        return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		Group g = this.getGroup(groupName);
		Supplier s = this.getSupplier(supplierName);
		Customer c = this.getCustomer(customerName);
		g.vote(c, s);
	}
	
	public String  getVotes (String groupName) {
		Group g = this.getGroup(groupName);
		StringBuffer sb = new StringBuffer("");
        g.getVotes().entrySet().stream().filter(e -> e.getValue() > 0).
        	sorted((e1, e2) -> e1.getKey().getName().compareTo(e2.getKey().getName())).
        	forEach(e -> sb.append(e.getKey().getName()).
        		append(":").append(e.getValue()).append(","));
        return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	public String getWinningBid (String groupName) {
		Group g = this.getGroup(groupName);
		if(g.getBids().size() == 0) return null;
		List<Supplier> maxVotes = g.getVotes().entrySet().stream().filter(e -> e.getValue() ==
				g.getVotes().values().stream().mapToInt(v->v).max().getAsInt()).
			map(e -> e.getKey()).collect(Collectors.toList());
		int minBid = g.getBids().entrySet().stream().filter(s -> maxVotes.contains(s.getKey())).
			mapToInt(e -> e.getValue()).min().getAsInt();
        Supplier winner =  g.getBids().entrySet().stream().
        		filter(s -> maxVotes.contains(s.getKey())).
        		filter(e -> e.getValue() == minBid).map(e -> e.getKey()).findFirst().get();
        return winner.getName() + ":" + g.getVotes().get(winner);
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
		SortedMap<String, Integer> map = new TreeMap<>();
		this.groups.stream().forEach(g -> {
				if (g.getBids().size() != 0) {
					int max = g.getBids().values().stream().mapToInt(i -> i).max().getAsInt();
					map.merge(g.getProductType().getName(), max, (v1, v2) -> v1 > v2 ? v1 : v2);
				}
			});
        return map;
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
		SortedMap<Integer, List<String>> map = new TreeMap<>();
		/*
		Map<Supplier, Long> map1 = 
			this.groups.stream().flatMap(g -> g.getSuppliers().stream()).
			collect(Collectors.groupingBy(s -> s.getName(), Collectors.counting()));
		*/
        return null;
	}
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
		SortedMap<String, Long> map = new TreeMap<>();
		this.groups.stream().forEach(g -> map.merge(
				g.getProductType().getName(), 
				Integer.toUnsignedLong(g.getCustomers().size()), 
				(v1, v2) -> v1 + v2));
        return map;
	}
	
//Private methods
	
	private ProductType getProductType(String name) {
		Optional<ProductType> prod = 
				this.products.stream().filter(p -> p.getName().equals(name)).findAny();
		if (!prod.isPresent()) {
			return null;
		}
		return prod.get();
	}
	
	private Supplier getSupplier(String name) {
		Optional<Supplier> sup = 
				this.suppliers.stream().filter(s -> s.getName().equals(name)).findAny();
		if (!sup.isPresent()) {
			return null;
		}
		return sup.get();
	}
	
	private Group getGroup(String name) {
		Optional<Group> group = 
				this.groups.stream().filter(g -> g.getName().equals(name)).findAny();
		if (!group.isPresent()) {
			return null;
		}
		return group.get();
	}
	
	private Customer getCustomer(String name) {
		Optional<Customer> customer = 
				this.customers.stream().filter(c -> c.getName().equals(name)).findAny();
		if (!customer.isPresent()) {
			return null;
		}
		return customer.get();
	}
	
}












