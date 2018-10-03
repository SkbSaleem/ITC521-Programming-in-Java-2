package jdbc;

public class Student {
	private int id;
	private String studentName;
	private int quiz;
	private int a1;
	private int a2;
	private int a3;
	private int exam;
	private Double result;
	private String grade;
	
	public Student() {
		this.id = 0;
		this.studentName = "";
		this.quiz = 0;
		this.a1 = 0;
		this.a2 = 0;
		this.a3 = 0;
		this.exam = 0;
		this.result = 0.0;
		this.grade = "";
	}
	
	public Student(int id, String studentName, int quiz, int a1, int a2, int a3, int exam, Double result, String grade) {
		this.id = id;
		this.studentName = studentName;
		this.quiz = quiz;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.exam = exam;
		this.result = result;
		this.grade = grade;
	}
	
	public int getId() {
		return id;
	}
	// I had capital "d" in getId and setId and it took me 4 hours to find this bug. Future me, be careful.
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public int getQuiz() {
		return quiz;
	}
	
	public void setQuiz(int quiz) {
		this.quiz = quiz;
	}
	
	public int getA1() {
		return a1;
	}
	
	public void setA1(int a1) {
		this.a1 = a1;
	}
	
	public int getA2() {
		return a2;
	}
	
	public void setA2(int a2) {
		this.a2 = a2;
	}
	
	public int getA3() {
		return a3;
	}
	
	public void setA3(int a3) {
		this.a3 = a3;
	}
	
	public int getExam() {
		return exam;
	}
	
	public void setExam(int exam) {
		this.exam = exam;
	}
	
	public double getResult() {
		return result;
	}
	
	public void setResult(double result) {
		this.result = result;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
