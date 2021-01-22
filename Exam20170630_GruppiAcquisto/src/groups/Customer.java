package groups;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private String name;
	private List<Group> groups = new ArrayList<>();
	
	public Customer(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addGroup(Group group) {
		this.groups.add(group);
	}
	
	public List<Group> getGroups() {
		return this.groups;
	}
	
	@Override
	public boolean equals(Object o) {
		Customer other = (Customer) o;
		return this.name.equals(other.name);
	}

}
