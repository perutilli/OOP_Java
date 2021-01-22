package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Restaurant {
	
	private Collection<Race> races = new ArrayList<>();
	private Collection<Party> parties = new ArrayList<>();
	private List<Hall> halls = new ArrayList<>(); //do not change order!
	private Map<Party, Hall> allocations = new HashMap<>();

    public Restaurant() {
	}
	
	public Race defineRace(String name) throws MilliwaysException{
		if(this.races.stream().filter(r->r.getName().equals(name)).findFirst().isPresent()) {
			throw(new MilliwaysException());
		}
		Race r = new Race(name);
		this.races.add(r);
	    return r;
	}
	
	public Party createParty() {
		Party p = new Party();
		this.parties.add(p);
	    return p;
	}
	
	public Hall defineHall(int id) throws MilliwaysException{
		if(this.halls.stream().filter(r->r.getId()==id).findFirst().isPresent()) {
			throw(new MilliwaysException());
		}
		Hall h = new Hall(id);
		this.halls.add(h);
		return h;
	}

	public List<Hall> getHallList() {
		return halls;
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
		if (!hall.isSuitable(party)) {
			throw(new MilliwaysException());
		}
		this.allocations.put(party, hall);
        return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
		Optional<Hall> hall = this.halls.stream().filter(h -> h.isSuitable(party)).findFirst();
        if(!hall.isPresent()) {
        	throw(new MilliwaysException());
        }
        this.allocations.put(party, hall.get());
		return hall.get();
	}

	public Map<Race, Integer> statComposition() {
		Map<Race, Integer> map = new HashMap<>();
		this.allocations.keySet().stream().
			flatMap(p -> p.getCompanions().entrySet().stream()).
			forEach(e -> map.merge(e.getKey(), e.getValue(), (old, num) -> old + num));	
        return map;
	}

	public List<String> statFacility() {
		Map<String, Long> stringMap = 
				this.halls.stream().flatMap(h -> h.getFacilities().stream()).collect(
						Collectors.groupingBy(s -> s, Collectors.counting()));
        return 
    		stringMap.entrySet().stream().sorted((e1, e2) -> {
					int comp = (int) (e2.getValue() - e1.getValue());
					if (comp == 0) {
						comp = e1.getKey().compareTo(e2.getKey());
					}
					return comp;
				}).map(e -> e.getKey()).collect(Collectors.toList());
	}
	
	public Map<Integer,List<Integer>> statHalls() {
		Map<Integer,List<Integer>> map =
			this.halls.stream().
				sorted((h1, h2) -> h1.getFacilities().size() - h2.getFacilities().size()).
				collect(Collectors.groupingBy(
						h -> h.getFacilities().size(), 
						Collectors.mapping(Hall::getId, Collectors.toList())));
		map.values().stream().forEach(l -> l.sort((i1, i2) -> (i1 - i2)));
        return map;
	}

}
