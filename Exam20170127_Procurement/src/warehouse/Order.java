package warehouse;

public class Order {
	
	private String code;
	private Product product;
	private Supplier supplier;
	private int quantity;
	private static int numOrder = 1;
	private boolean delivered = false;
	
	public Order(Product product, Supplier supplier, int quantity) {
		this.code = "ORD" + numOrder;
		numOrder++;
		this.product = product;
		this.supplier = supplier;
		this.quantity = quantity;
	}

	public String getCode(){
		return this.code;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public Supplier getSupplier() {
		return this.supplier;
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	public boolean delivered(){
		return this.delivered;
	}

	public void setDelivered() throws MultipleDelivery {
		if(this.delivered) {
			throw(new MultipleDelivery());
		}
		this.delivered = true;
		this.product.increaseQuantity(this.quantity);
	}
	
	public String toString(){
		return "Order " + this.code + " for " + this.quantity + " of " +
				this.product.getCode() + " : " + this.product.getDescription() + " from " +
				this.supplier.getNome();
	}
}
