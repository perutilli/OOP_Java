package sports;

import java.util.ArrayList;
import java.util.List;

public class Category {
	
	private String name;
	private List<String> linkedActivities = new ArrayList<>();
	
	public Category(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getLinkedActivities() {
		return linkedActivities;
	}
	
	public void addActivity(String activity) {
		this.linkedActivities.add(activity);
	}
	

	
	

}
