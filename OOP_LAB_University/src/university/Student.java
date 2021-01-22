package university;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class Student {
	
	private String name;
	private String surname;
	private int matricola;
	private Subject[] courses;
	private int numCourses = 0;
	private List<Integer> marks = new ArrayList<>();
	
	static final int MAX_COURSES = 25;
	
	public Student(String name, String surname, int matricola) {
		this.name = name;
		this.surname = surname;
		this.matricola = matricola;
		
		this.courses = new Subject[MAX_COURSES];
	}
	
	public String stringVersion() {
		return (this.matricola + " " + this.name + " " + this.surname);
	}
	
	
	public void addCourse(Subject course) {
		this.courses[this.numCourses] = course;
		this.numCourses++;
	}
	
	public String studyPlan() {
		StringBuffer res = new StringBuffer("");
		for (Subject c: this.courses) {
			if (c == null) break;
			res.append(c.stringVersion()).append("\n");
		}
		return res.toString();
	}	
	
	public void addMark(int mark) {
		this.marks.add(mark);
	}
	
	public String avgMark() {
		OptionalDouble avg = this.marks.stream().mapToInt(m -> (int) m).average();
		if (avg.isPresent()) {
			return ("Student " + this.matricola + " : " + avg.getAsDouble());
		}
		return "Student " + this.matricola + " hasn't taken any exams";
	}
	
//	public double awardScore() {
//		
//	}

}

