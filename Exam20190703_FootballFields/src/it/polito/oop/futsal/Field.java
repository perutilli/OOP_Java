package it.polito.oop.futsal;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import it.polito.oop.futsal.Fields.Features;

public class Field implements FieldOption{
	
	private Features features;
	private int id;
	private Map<LocalTime, Associate> bookings = new HashMap<>();
	
	public Field(int id, Features features) throws FutsalException {
		if(!features.indoor && (features.heating || features.ac)) {
			throw(new FutsalException());
		}
		this.features = features;
		this.id = id;
	}
	
	public Features getFeatures() {
		return this.features;
	}

	public int getField() {
		return id;
	}
	
	public void book(Associate a, LocalTime time) {
		this.bookings.put(time, a);		
	}
	
	public boolean isBooked(LocalTime time) {
		return this.bookings.containsKey(time);
	}
	
	public int getOccupation() {
		return this.bookings.size();
	}

}
