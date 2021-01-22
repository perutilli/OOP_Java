package groups;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
	
	private String name;
	private List<ProductType> products = new ArrayList<>();
	
	public Supplier(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addProduct(ProductType prod) {
		this.products.add(prod);
	}
	
	public List<ProductType> getProductsSupplied() {
		return this.products;
	}
	
	@Override
	public boolean equals(Object o) {
		Supplier other = (Supplier) o;
		return this.name.equals(other.name);
	}

}
