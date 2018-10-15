package jdbc;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GradeDBMC {
	// JDBC driver name and database url
	static final String DB_URL = "jdbc:mysql://localhost/GradeProcessing";

	// Database creditionals
	// IMPORTANT NOTE: USER and PASS are removed from submitted code for security reasons. Please fill in the information accordingly to run the code.
	static final String USER = "";
	static final String PASS = "";

	static Connection con = null;
	
	//Following method check if a student id already exist in table
	public static boolean checkRecordExist (Integer id) {
		Boolean recordExist = false;
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("select STUDENTNAME from java2 where ID = ?");
			stmt.setInt(1, id);
			ResultSet rs=stmt.executeQuery();
			if (rs.next()) {            
				recordExist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recordExist;
	} // checkRecordExist end

	// following method update string data (like student name and grade) in MySQL database
	static void updateDataString(String column, String newValue, Integer id) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE java2 SET "+column+" = ? WHERE ID = ? ");
			stmt.setString(1, newValue);
			stmt.setInt(2, id);
			stmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error");
			ex.printStackTrace(System.err);
		}
	} // updateDataString end

	// following method update Integer data (like quiz,a1 and exam) in MySQL database
	public static void updateDataInteger(String column, Integer newValue, Integer id) {
		try { 
			PreparedStatement pstNewValue = con.prepareStatement("UPDATE java2 SET "+column+" = ? WHERE ID = ? ");
			pstNewValue.setInt(1, newValue);
			pstNewValue.setInt(2, id);
			pstNewValue.execute();
			// Update result in database as per updated marks
			PreparedStatement pstResult = con.prepareStatement("UPDATE java2 SET RESULTS = (QUIZ * 0.05)+(A1* 0.15) +(A2* 0.2) + (A3* 0.10) + (EXAM * 0.5) WHERE ID = ? ");
			pstResult.setInt(1, id);
			pstResult.execute();
			// Update grade in database as per updated marks
			PreparedStatement pstGrade = con.prepareStatement("UPDATE Java2 SET GRADE = CASE WHEN RESULTS >= 85 THEN 'HD' WHEN RESULTS >= 75 AND RESULTS < 85 THEN 'DI' WHEN RESULTS >= 65 AND RESULTS < 75 THEN 'CR' WHEN RESULTS >= 50 AND RESULTS < 65 THEN 'PS' ELSE 'FL' END WHERE ID = ?");
			pstGrade.setInt(1, id);
			pstGrade.execute();
		} catch (SQLException ex) {
			System.err.println("Error");
			ex.printStackTrace(System.err);
		}		
	} // updateDataInteger end

	// following method insert record of student in MySQL database
	public static void insertRecord (Integer id, String studentName, Integer quiz, Integer a1, Integer a2, Integer a3, Integer exam){
		Double result = calculateResult (quiz, a1, a2, a3, exam);		
		String grade = calculateGrade(result);
		try {
			// the mysql insert statement
			String sql = " insert into java2 (ID, STUDENTNAME, QUIZ, A1, A2, A3, EXAM, RESULTS, GRADE)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(sql);
			preparedStmt.setInt (1, id);
			preparedStmt.setString (2, studentName);
			preparedStmt.setInt (3, quiz);
			preparedStmt.setInt (4, a1);
			preparedStmt.setInt (5, a2);
			preparedStmt.setInt (6, a3);
			preparedStmt.setInt (7, exam);
			preparedStmt.setDouble (8, result);
			preparedStmt.setString (9, grade);
			// execute the preparedstatement
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // insertRecord method end

	// Following method perform calculate the result marks
	public static Double calculateResult (Integer quiz, Integer a1, Integer a2, Integer a3, Integer exam){
		Double result = (quiz * 0.05)+(a1* 0.15) +(a2* 0.2) + (a3* 0.10) + (exam * 0.5);
		return result;
	} // calculateResult method end

	// Following method calculate the grade
	public static String calculateGrade (Double result){
		String grade = "";
		if (result >=85) {
			grade = "HD";
		}
		else if (75 <= result && result < 85) {
			grade = "DI";
		}
		else if (65 <= result && result < 75) {
			grade = "CR";
		}
		else if (50 <= result && result < 65) {
			grade = "PS";
		}
		else {
			grade = "FL";
		}
		return grade;
	} // calculateGrade method end
	
	// Following method search a record in MySQL database
	public static ObservableList<Student> searchRecord (String id, String studentName, String quiz, String a1, String a2, 
			String a3, String exam, String result, String grade) {
		ObservableList<Student> olSearchedData = FXCollections.observableArrayList();
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM JAVA2 WHERE ID LIKE '%"+ id +"%' "
					+ "AND STUDENTNAME LIKE '%" + studentName + "%' "
					+ "AND QUIZ LIKE '%" + quiz + "%' "
					+ "AND A1 LIKE '%" + a1 + "%' "
					+ "AND A2 LIKE '%" + a2 + "%' "
					+ "AND A3 LIKE '%" + a3 + "%' "
					+ "AND EXAM LIKE '%" + exam + "%' "
					+ "AND RESULTS LIKE '%" + result + "%' "
					+ "AND GRADE LIKE '%" + grade + "%'";
			ResultSet rs = stmt.executeQuery(sql);
			while((rs != null) && (rs.next())) {
				olSearchedData.add(new Student(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 
						rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8), rs.getString(9)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return olSearchedData;

	} // searchRecord method end

	// Following method initialize MySql database connection
	public static void initializeDB(){
		try {
			// 1. get a connection to database
			con = DriverManager.getConnection(DB_URL + "?useLegacyDatetimeCode=false&serverTimezone=UTC" 
					+ "&user=" + USER + "&password=" + PASS);
			System.out.println("Connection Succesfull");       
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("An error has occured on Table Creation with Table ");
		}
	} // initializeDB method end

	// Following method checks if java2 table already exist in database
	public static boolean checkTableExist() {
		DatabaseMetaData dbm;
		try {
			dbm = con.getMetaData();
			// check if "java2" table is there
			ResultSet tables = dbm.getTables(null, null, "java2", null);
			// Table exists
			if (tables.next()) {
				return true;
			}
			// Table does not exist
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// Folloing method delete table from database
	public static void deleteTable() {
		// TODO Auto-generated method stub
		// deleting table if already exist
		try {
			con.createStatement().executeUpdate("DROP TABLE IF EXISTS Java2");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Following method create table in database
	public static void createTable() {
		// TODO Auto-generated method stub
		// Creating a database schema
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "CREATE TABLE if not exists Java2 " +
					"(ID INTEGER(8) not NULL, " +
					" StudentName TEXT not NULL, " + 
					" Quiz INTEGER not NULL, " + 
					" A1 INTEGER not NULL, " + 
					" A2 INTEGER not NULL, " +
					" A3 INTEGER not NULL, " +
					" Exam INTEGER not NULL, " +
					" Results INTEGER not NULL, " +
					" Grade TEXT not NULL, " +
					" PRIMARY KEY ( ID ))"; 
			stmt.executeUpdate(sql);
			System.out.println("Java2 table created.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // createTable method end
}