package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Race {
	
	private String name;
	private List<String> requirements = new ArrayList<>();
	
	public Race(String name) {
		this.name = name;
	}
    
	public void addRequirement(String requirement) throws MilliwaysException {
		if (this.requirements.contains(requirement)) {
			throw(new MilliwaysException());
		}
		this.requirements.add(requirement);
	}
	
	public List<String> getRequirements() {
		this.requirements.sort(Comparator.naturalOrder());
        return this.requirements;
	}
	
	public String getName() {
        return this.name;
	}
	
	//TODO: check that it doesn't break tests
	/*
	@Override
	public String toString() {
		return this.name;
	}
	*/
}
