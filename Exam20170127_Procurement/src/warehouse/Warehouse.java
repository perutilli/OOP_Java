package warehouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Warehouse {
	
	private Collection<Product> products = new ArrayList<>();
	private Collection<Supplier> suppliers = new ArrayList<>();
	private Collection<Order> orders = new ArrayList<>();
	
	public Product newProduct(String code, String description){
		Product p = new Product(code, description);
		products.add(p);
		return p;
	}
	
	public Collection<Product> products(){
		return this.products;
	}

	public Product findProduct(String code){
		return this.products.stream().filter(p -> p.getCode().equals(code)).
				findAny().orElse(null);
	}

	public Supplier newSupplier(String code, String name){
		Supplier s = new Supplier(code, name);
		this.suppliers.add(s);
		return s;
	}
	
	public Supplier findSupplier(String code){
		return this.suppliers.stream().filter(s -> s.getCodice().equals(code)).
				findAny().orElse(null);
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp)
		throws InvalidSupplier {
		if(!prod.suppliers().contains(supp)) {
			throw(new InvalidSupplier());
		}
		Order o = new Order(prod, supp, quantity);
		this.orders.add(o);
		prod.addOrder(o);
		return o;
	}

	public Order findOrder(String code){
		return this.orders.stream().filter(o -> o.getCode().equals(code)).
				findAny().orElse(null);
	}
	
	public List<Order> pendingOrders(){
		return this.orders.stream().filter(o -> (!o.delivered())).sorted((o1, o2) -> 
				o1.getProduct().getCode().compareTo(o2.getProduct().getCode())).
			collect(Collectors.toList());
	}

	public Map<String,List<Order>> ordersByProduct(){
		return this.orders.stream().collect(
				Collectors.groupingBy(o -> o.getProduct().getCode()));
	}
	
	public Map<String,Long> orderNBySupplier(){
	    return this.orders.stream().sorted((o1, o2) -> 
				o1.getSupplier().getNome().compareTo(o2.getSupplier().getNome())).
    		collect(Collectors.groupingBy(
				o -> o.getSupplier().getNome(), 
				Collectors.counting()));
	}
	
	public List<String> countDeliveredByProduct(){
	    return this.orders.stream().filter(o -> o.delivered()).
    		collect(Collectors.groupingBy(
				o -> o.getProduct().getCode(),
				Collectors.counting()
			)).entrySet().stream().
			sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue())).
			map(e -> e.getKey() + " - " + e.getValue()).
			collect(Collectors.toList());
	}
}
