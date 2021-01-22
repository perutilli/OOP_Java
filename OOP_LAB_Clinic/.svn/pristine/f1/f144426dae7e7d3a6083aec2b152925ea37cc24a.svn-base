package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	
	private Collection<Patient> patients = new ArrayList<>();
	private Collection<Doctor> doctors = new ArrayList<>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		// TODO Complete method
		this.patients.add(new Patient(ssn, first, last));
		

	}
	
	private Patient getPat(String ssn) throws NoSuchPatient {
		Optional<Patient> p = 
				this.patients.stream().filter(s -> s.getSSN().equals(ssn)).findFirst();
		if (!p.isPresent()) {
			throw(new NoSuchPatient());
		}
		return p.get();
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		return this.getPat(ssn).toString();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		this.doctors.add(new Doctor(ssn, first, last, docID, specialization));

	}
	
	private Doctor getDoc(int docID) throws NoSuchDoctor {
		Optional<Doctor> d = 
				this.doctors.stream().filter(doc -> doc.getID() == docID).findFirst();
		if (!d.isPresent()) {
			throw(new NoSuchDoctor());
		}
		return d.get();
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		return this.getDoc(docID).toString();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Doctor d = this.getDoc(docID);
		Patient p = this.getPat(ssn);
		d.assignPatient(p);
		p.assignDoctor(d);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		return this.getPat(ssn).getDoctor().getID();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		return this.getDoc(id).getPatients().stream()
				.map(Patient::getSSN).collect(Collectors.toList());
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		// TODO Complete method
		BufferedReader b = new BufferedReader(reader);
		String line;
		while ((line = b.readLine()) != null) {
			String[] tok = line.split(";");
			switch(tok[0]) {
			
			case "P":
				if (tok.length != 4) {
					this.readLineErr();
					continue;
				}
				
				this.addPatient(tok[1], tok[2], tok[3]);
				break;
				
			case "M":
				if (tok.length != 6) {
					this.readLineErr();
					continue;
				}
				int docID;
				try {
					docID = Integer.parseInt(tok[1]);
				} catch(NumberFormatException e) {
					this.readLineErr();
					continue;
				}
				this.addDoctor(tok[2], tok[3], tok[4], docID, tok[5]);
				break;
				
			default:
				continue;
			}
		}
	}
	
	private void readLineErr() {
		System.err.println("Line with wrong format");
	}


	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		return this.doctors.stream().filter(d -> d.getPatients().size() == 0).
				sorted((d1, d2) -> {
					int comp = d1.getSurname().compareTo(d2.getSurname());
					if (comp == 0) {
						comp = d1.getName().compareTo(d2.getName());
					}
					return comp;
				}).
				map(Doctor::getID).collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		OptionalDouble avg = 
				this.doctors.stream().mapToInt(d -> d.getPatients().size()).average();
		if (!avg.isPresent()) {
			return null;
		}
		return this.doctors.stream().filter(d -> d.getPatients().size() > avg.getAsDouble()).
				map(Doctor::getID).collect(Collectors.toList());
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		Collection<String> coll = new ArrayList<>();
		this.doctors.stream().
			sorted((d1, d2) -> d2.getPatients().size() - d1.getPatients().size()).
			forEach(d -> {
				StringBuffer sb = new StringBuffer("");
				sb.append(String.format("%3d", d.getPatients().size())).
						append(" : ").append(d.getID()).append(" ").append(d.getSurname()).
						append(" ").append(d.getName());
				coll.add(sb.toString());
			});
		return coll;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		SortedMap<Integer, String> map = new TreeMap<>(Collections.reverseOrder());
		for (String specialization: this.doctors.
				stream().map(Doctor::getSpecialization).collect(Collectors.toList())) {
			int num = 
				this.doctors.stream().filter(d -> d.getSpecialization().equals(specialization)).
					mapToInt(d -> d.getPatients().size()).sum();
			map.put(num, specialization);
			
		}
		return map.entrySet().stream().map(e -> e.getKey() + " - " + e.getValue()).
				collect(Collectors.toList());
	}
	
}
