package clinic;

import java.util.Collection;
import java.util.LinkedList;

public class Doctor extends Person{
	
	private int ID;
	private String specialization;
	private Collection<Patient> patients = new LinkedList<>();
	
	public Doctor(String SSN, String name, String surname, int badge, String specialization) {
		super(SSN, name, surname);
		this.ID = badge;
		this.specialization = specialization;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getSpecialization() {
		return this.specialization;
	}
	
	public void assignPatient(Patient patient) {
		this.patients.add(patient);
	}
	
	public Collection<Patient> getPatients() {
		return this.patients;
	}
	
	@Override
	public String toString() {
		return super.toString() + " [" + this.ID + "]: " + this.specialization;
	}

}
