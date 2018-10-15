package try1;

public class Student implements java.io.Serializable {
	  private String name;
	  private String message;

	  public Student(String name, String message) {
	    this.name = name;
	    this.message = message;
	  }
	  
	  public String getName() {
	    return name;
	  }
	  
	  public String getMessage() {
	    return message;
	  }
	}