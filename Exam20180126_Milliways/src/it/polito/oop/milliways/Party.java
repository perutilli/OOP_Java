package it.polito.oop.milliways;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Party {
	
	private Map<Race, Integer> companions = new HashMap<>();
	private int num = 0;

    public void addCompanions(Race race, int num) {
		this.companions.merge(race, num, (a, b) -> a + b);
    	this.num += num;
	}

	public int getNum() {
        return this.num;
	}

	public int getNum(Race race) {
	    return this.companions.get(race);
	}

	public List<String> getRequirements() {
        return this.companions.keySet().stream().map(r -> r.getRequirements()).		
    			flatMap(list -> list.stream()).distinct().sorted(Comparator.naturalOrder()).
    			collect(Collectors.toList());
	}

    public Map<String,Integer> getDescription(){
        return this.companions.entrySet().stream().
        		collect(Collectors.toMap(e -> e.getKey().getName(), e -> e.getValue()));
    }
    
    public Map<Race, Integer> getCompanions() {
    	return this.companions;
    }

}
