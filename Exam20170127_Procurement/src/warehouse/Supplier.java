package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Supplier {
	
	private String codice;
	private String nome;
	private List<Product> supplies = new ArrayList<>();
	
	public Supplier(String codice, String nome) {
		this.codice = codice;
		this.nome = nome;
	}

	public String getCodice(){
		return this.codice;
	}

	public String getNome(){
		return this.nome;
	}
	
	public void newSupply(Product product){
		this.supplies.add(product);
		product.addSupplier(this);
	}
	
	public List<Product> supplies(){
		return this.supplies.stream().
				sorted((p1, p2) -> p1.getDescription().compareTo(p2.getDescription())).
				collect(Collectors.toList());
	}
	
	@Override
	public boolean equals(Object o) {
		Supplier other = (Supplier) o;
		return this.codice.equals(other.codice);
	}
}
