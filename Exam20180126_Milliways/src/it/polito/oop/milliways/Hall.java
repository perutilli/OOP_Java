package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hall {
	
	private int id;
	private List<String> facilities = new ArrayList<>();
	
	public Hall(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void addFacility(String facility) throws MilliwaysException {
	 	if (this.facilities.contains(facility)) {
			throw(new MilliwaysException());
		}
		this.facilities.add(facility);
	}

	public List<String> getFacilities() {
        return this.facilities.stream().sorted().collect(Collectors.toList());
	}
	
	int getNumFacilities(){
        return this.facilities.size();
	}

	public boolean isSuitable(Party party) {
		return this.facilities.containsAll(party.getRequirements());
	}

}
