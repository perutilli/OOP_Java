package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Product {
	
	private String code;
	private String description;
	private int quantity = 0;
	private List<Supplier> suppliers = new ArrayList<>();
	private List<Order> orders = new ArrayList<>();
	
	public Product(String code, String description) {
		this.code = code;
		this.description = description;
	}

	
	public String getCode(){
		return this.code;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	public void increaseQuantity(int delta) {
		this.quantity += delta;
	}

	public void decreaseQuantity(){
		this.quantity -= 1;
	}

	public int getQuantity(){
		return this.quantity;
	}
	
	public void addSupplier(Supplier supplier) {
		this.suppliers.add(supplier);
	}
	
	public void addOrder(Order o ) {
		this.orders.add(o);
	}

	public List<Supplier> suppliers(){
		return this.suppliers.stream().
				sorted((s1, s2) -> s1.getNome().compareTo(s2.getNome())).
				collect(Collectors.toList());
	}

	public List<Order> pendingOrders(){
		return this.orders.stream().filter(o -> (!o.delivered())).
				sorted((o1, o2) -> o2.getQuantity() - o1.getQuantity()).
				collect(Collectors.toList());
	}
}
