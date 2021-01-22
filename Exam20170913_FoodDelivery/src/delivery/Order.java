package delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delivery.Delivery.OrderStatus;

public class Order {
	
	private int id;
	private Customer customer;
	private Map<MenuItem, Integer> composition = new HashMap<>();
	private OrderStatus status = OrderStatus.NEW;
	private int initialDelay = 5;
	private int transportTime = 15;
	
	public Order(int id, Customer customer) {
		this.id = id;
		this.customer = customer;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public OrderStatus getStatus() {
		return this.status;
	}
	
	public int addItem(MenuItem item, int qty) {
		this.composition.merge(item, qty, (prev, q) -> prev + q);
		return this.composition.get(item);
	}
	
	public List<String> getComposition() {
		List<String> list = new ArrayList<>();
		this.composition.entrySet().stream().forEach(e ->
			list.add(e.getKey().getDescription() + ", " + e.getValue()));
		return list;	
	}
	
	public double getPrice() {
		return this.composition.entrySet().stream().
			mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
	}
	
	public Order changeStatus(OrderStatus status) throws DeliveryException{
		//checks valid status transition
		switch(status) {
		case CONFIRMED:
			if (this.status != OrderStatus.NEW) {
				throw(new DeliveryException());
			}
			break;
		case PREPARATION:
			if (this.status != OrderStatus.CONFIRMED) {
				throw(new DeliveryException());
			}
			break;
		case ON_DELIVERY:
			if (this.status != OrderStatus.PREPARATION) {
				throw(new DeliveryException());
			}
			break;
		case DELIVERED:
			if (this.status != OrderStatus.ON_DELIVERY) {
				throw(new DeliveryException());
			}
			break;
		}
		this.status = status;
		return this;
	}
	
	public int getEstimateDelTime() throws DeliveryException{
		int time = 0;
		int maxPrepTime = 
			this.composition.keySet().stream().mapToInt(MenuItem::getPrepTime).
			max().getAsInt();
		switch(this.status) {
		case NEW:
			throw(new DeliveryException());
		case CONFIRMED:
			time = this.initialDelay + maxPrepTime + this.transportTime;
			break;
		case PREPARATION:
			time = maxPrepTime + this.transportTime;
			break;
		case ON_DELIVERY:
			time = this.transportTime;
			break;
		}
		return time;
		
	}
	
	/* NOT NEEDED
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		
		this.composition.entrySet().stream().forEach(e ->
			sb.append(e.getKey().getDescription()).append(", ").
			append(e.getValue()).append("\n"));
		
		return sb.toString();
	}
	*/

}
