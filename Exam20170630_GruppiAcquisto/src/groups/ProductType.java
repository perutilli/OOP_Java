package groups;

import java.util.ArrayList;
import java.util.List;

public class ProductType {
	
	private String name;
	private List<Supplier> suppliers = new ArrayList<>();
	
	public ProductType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addSupplier(Supplier sup) {
		this.suppliers.add(sup);
	}
	
	@Override
	public boolean equals(Object o) {
		ProductType other = (ProductType) o;
		return this.name.equals(other.name);
	}

}
