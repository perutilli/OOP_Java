package university;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class Subject {
	
	private String title;
	private String nameProfessor;
	private int code;
	private Student[] students;
	private int numStudents = 0;
	private List<Integer> marks = new ArrayList<>();
	
	static final int MAX_ATTENDEES = 100;
	
	public Subject(String title, String nameProfessor, int code) {
		this.title = title;
		this.nameProfessor = nameProfessor;
		this.code = code;
		
		this.students = new Student[MAX_ATTENDEES];
	}

	public String stringVersion() {
		return (this.code + " " + this.title + " " + this.nameProfessor);
	}
	
	public void addStudent(Student student) {
		this.students[this.numStudents] = student;
		this.numStudents++;
	}
	
	public String listAttendees() {
		StringBuffer res = new StringBuffer("");
		for (Student s: this.students) {
			if (s == null) break;
			res.append(s.stringVersion()).append("\n");
		}
		return res.toString();
	}
	
	public void addMark(int mark) {
		this.marks.add(mark);
	}
	
	public String avgMark() {
		OptionalDouble avg = this.marks.stream().mapToInt(m -> (int) m).average();
		if (avg.isPresent()) {
			return ("The average for the course " + this.title + " is: " + avg.getAsDouble());
		}
		return "No student has taken the exam in " + this.title;
	}
}