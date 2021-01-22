package it.polito.oop.futsal;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
    
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
        
        @Override
        public boolean equals(Object o) {
        	Features f = (Features) o;
        	return (this.indoor == f.indoor && this.heating == f.heating && 
        			this.ac == f.ac);
        }
        
        public boolean compatible(Features f) {
        	return (this.indoor == f.indoor &&
        			(!f.heating || this.heating) &&
        			(!f.ac || this.ac));
        }
    }
    
    private List<Field> fields = new ArrayList<>();
    private List<Associate> associates = new ArrayList<>();
    private LocalTime openingTime;
    private LocalTime closingTime;
    
    public void defineFields(Features... features) throws FutsalException {
        for(Features f: features) {
        	Field field = new Field(this.fields.size() + 1, f);
        	this.fields.add(field);
        }
    }
    
    public long countFields() {
        return (long) this.fields.size();
    }

    public long countIndoor() {
        return this.fields.stream().filter(f -> f.getFeatures().indoor).count();
    }
    
    public String getOpeningTime() {
        return this.openingTime.toString();
    }
    
    public void setOpeningTime(String time) {
    	this.openingTime = LocalTime.parse(time);
    }
    
    public String getClosingTime() {
        return this.closingTime.toString();
    }
    
    public void setClosingTime(String time) {
    	this.closingTime = LocalTime.parse(time);
    }

    public int newAssociate(String first, String last, String mobile) {
    	Associate a = new Associate(first, last, mobile);
    	this.associates.add(a);
        return a.getId();
    }
    
    private Associate getAssociate(int id) {
    	return this.associates.stream().filter(a -> a.getId() == id).findAny().
    			orElse(null);
    }
    
    public String getFirst(int associate) throws FutsalException {
    	Associate a = this.getAssociate(associate);
    	if(a == null) throw(new FutsalException());
        return a.getName();
    }
    
    public String getLast(int associate) throws FutsalException {
    	Associate a = this.getAssociate(associate);
    	if(a == null) throw(new FutsalException());
        return a.getSurname();
    }
    
    public String getPhone(int associate) throws FutsalException {
    	Associate a = this.getAssociate(associate);
    	if(a == null) throw(new FutsalException());
        return a.getPhone();
    }
    
    public int countAssociates() {
        return this.associates.size();
    }
    
    private Field getField(int id) {
    	return this.fields.stream().filter(f -> f.getField() == id).findAny().
    			orElse(null);
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    	Field f = this.getField(field);
    	Associate a = this.getAssociate(associate);
    	LocalTime t = LocalTime.parse(time);
    	if(f == null || a == null || 
    			t.getMinute() - this.openingTime.getMinute() != 0) {
    		throw(new FutsalException());
    	}
    	f.book(a, t);
    	a.book();
    }

    public boolean isBooked(int field, String time) {
    	Field f = this.getField(field);
    	LocalTime t = LocalTime.parse(time);
        return f.isBooked(t);
    }
    

    public int getOccupation(int field) {
    	Field f = this.getField(field);
        return f.getOccupation();
    }
    
    
    public List<FieldOption> findOptions(String time, Features required) {
    	LocalTime t = LocalTime.parse(time);
        return this.fields.stream().filter(
        			f -> !f.isBooked(t) && f.getFeatures().compatible(required)).
        		sorted(Comparator.comparingInt(Field::getOccupation).
        				thenComparing(Field::getField)).
        		collect(Collectors.toList());
    }
    
    public long countServedAssociates() {
        return this.associates.stream().filter(Associate::hasBooked).count();
    }
    
    public Map<Integer,Long> fieldTurnover() {
    	Map<Integer,Long> map = new HashMap<>();
        this.fields.stream().forEach(f -> map.put(f.getField(), 
        		Long.valueOf(f.getOccupation())));
        return map;
    }
    
    public double occupation() {
    	int used = this.fields.stream().mapToInt(Field::getOccupation).sum();
        return (double) used / (this.fields.size() * (
        		this.closingTime.getHour() - this.openingTime.getHour()
        		));
    }
    
}
