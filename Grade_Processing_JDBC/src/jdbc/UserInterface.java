package jdbc;

import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class UserInterface extends Application{
	Scene sceneTableExist, sceneWelcome, sceneCalculateOrInsert, sceneSearchorUpdate;
	TableView<Student> table;

	@Override
	public void start(Stage primaryStage) throws Exception {
		GradeDBMC.initializeDB();
		primaryStage.setTitle("Students Grade Management System");

		/**
		 * Scene table exist
		 */
		VBox vbTableExist = new VBox(20);
		if (GradeDBMC.checkTableExist()) {
			Label lbTableExist= new Label("Java2 table already exist");
			Button btCreateNew = new Button("Create new table");
			Button btUseExisting = new Button("Use existing table");
			vbTableExist.getChildren().addAll(lbTableExist, btCreateNew, btUseExisting);
			vbTableExist.setAlignment(Pos.CENTER);
			btCreateNew.setOnAction(e -> {
				GradeDBMC.deleteTable();
				GradeDBMC.createTable();
			});
			btUseExisting.setOnAction(e -> primaryStage.setScene(sceneWelcome));
		}
		else {
			GradeDBMC.createTable();
			Label lbTableExist= new Label("Java2 table didn't exist, thus new table is created.");
			Button btOk = new Button("Ok");
			vbTableExist.getChildren().addAll(lbTableExist, btOk);
			vbTableExist.setAlignment(Pos.CENTER);
			btOk.setOnAction(e -> primaryStage.setScene(sceneWelcome));

		}
		sceneTableExist= new Scene(vbTableExist, 1000, 500);

		/**
		 * Scene Welcome page
		 */
		Label lbWelcome= new Label("Welcome to Students Grade Management System");
		Label lbSelect= new Label("Please select one of the following options:");
		Button btCalculateOrInsert = new Button("Calculate or Insert Record");
		Button btSearchorUpdate = new Button("Search or Update Record");
		Button btExit = new Button("Exit");

		btCalculateOrInsert.setPrefWidth(180);
		btSearchorUpdate.setPrefWidth(180);
		btExit.setPrefWidth(180);

		btCalculateOrInsert.setOnAction(e -> primaryStage.setScene(sceneCalculateOrInsert));
		btSearchorUpdate.setOnAction(e -> primaryStage.setScene(sceneSearchorUpdate));
		btExit.setOnAction(e -> System.exit(0));

		VBox vbWelcome = new VBox(20);     
		vbWelcome.getChildren().addAll(lbWelcome, lbSelect, btCalculateOrInsert, btSearchorUpdate, btExit);
		vbWelcome.setAlignment(Pos.CENTER);

		sceneWelcome= new Scene(vbWelcome, 1000, 500);


		/**
		 * Scene Insert or Calcuate Record
		 */
		TextField tfStudentName = new TextField();
		TextField tfStudentID = new TextField();
		TextField tfQuiz = new TextField();
		TextField tfA1 = new TextField();
		TextField tfA2 = new TextField();
		TextField tfA3 = new TextField();
		TextField tfExam = new TextField();
		TextField tfResult = new TextField();
		TextField tfGrade = new TextField();
		TextField tfMessage = new TextField();

		Button btInsert = new Button("Insert Record");
		Button btCalculateResult = new Button("Calculate Result");
		Button btBack = new Button("Back");

		GridPane gpInsertRecord = new GridPane();
		gpInsertRecord.setHgap(5);
		gpInsertRecord.setVgap(5);

		gpInsertRecord.add(new Label("Student ID:"), 0, 0);
		gpInsertRecord.add(tfStudentID, 1, 0);
		gpInsertRecord.add(new Label("Student ID should be 8 digits long."), 2, 0);

		gpInsertRecord.add(new Label("Student Name:"), 0, 1);
		gpInsertRecord.add(tfStudentName, 1, 1);

		gpInsertRecord.add(new Label("Quiz:"), 0, 2);
		gpInsertRecord.add(tfQuiz, 1, 2);
		gpInsertRecord.add(new Label("Quiz marks should be between 0 and 100 (incusive)"), 2, 2);

		gpInsertRecord.add(new Label("A1:"), 0, 3);
		gpInsertRecord.add(tfA1, 1, 3);
		gpInsertRecord.add(new Label("A1 marks should be between 0 and 100 (incusive)"), 2, 3);

		gpInsertRecord.add(new Label("A2:"), 0, 4);
		gpInsertRecord.add(tfA2, 1, 4);
		gpInsertRecord.add(new Label("A2 marks should be between 0 and 100 (incusive)"), 2, 4);

		gpInsertRecord.add(new Label("A3:"), 0, 5);
		gpInsertRecord.add(tfA3, 1, 5);
		gpInsertRecord.add(new Label("A3 marks should be between 0 and 100 (incusive)"), 2, 5);

		gpInsertRecord.add(new Label("Exam:"), 0, 6);
		gpInsertRecord.add(tfExam, 1, 6);
		gpInsertRecord.add(new Label("Exam marks should be between 0 and 100 (incusive)"), 2, 6);

		gpInsertRecord.add(new Label("Result:"), 0, 7);
		gpInsertRecord.add(tfResult, 1, 7);
		tfResult.setEditable(false);

		gpInsertRecord.add(new Label("Grade:"), 0, 8);
		gpInsertRecord.add(tfGrade, 1, 8);
		tfGrade.setEditable(false);

		gpInsertRecord.add(btInsert, 1, 9);
		gpInsertRecord.add(btCalculateResult, 2, 9);
		gpInsertRecord.add(btBack, 3, 9);

		gpInsertRecord.add(new Label("Message:"), 0, 11);
		gpInsertRecord.add(tfMessage, 1, 10, 2, 2);
		tfMessage.setEditable(false);

		gpInsertRecord.setAlignment(Pos.CENTER);


		// Setting action on back button
		btBack.setOnAction(e -> primaryStage.setScene(sceneWelcome));

		// Setting action on Insert record button
		btInsert.setOnAction(e -> {
			if (tfStudentID.getText().length() != 8){
				tfMessage.setText("ERROR: Student id should be 8 digits long.");
			}
			else if (GradeDBMC.checkRecordExist(Integer.parseInt(tfStudentID.getText()))){
				tfMessage.setText("ERROR: Record already exist on this student id.");
			}
			else if (tfStudentName.getText().equals("")) {
				tfMessage.setText("ERROR: Insert student name.");
			}
			else if (tfQuiz.getText().equals("") || Integer.parseInt(tfQuiz.getText()) < 0 || Integer.parseInt(tfQuiz.getText()) > 100 ) {
				tfMessage.setText("ERROR: Quiz marks should be between 0 and 100 (incusive).");
			}
			else if (tfA1.getText().equals("") || Integer.parseInt(tfA1.getText()) < 0 || Integer.parseInt(tfA1.getText()) > 100 ) {
				tfMessage.setText("ERROR: A1 marks should be between 0 and 100 (incusive).");
			}
			else if (tfA2.getText().equals("") || Integer.parseInt(tfA2.getText()) < 0 || Integer.parseInt(tfA2.getText()) > 100 ) {
				tfMessage.setText("ERROR: A2 marks should be between 0 and 100 (incusive).");
			}
			else if (tfA3.getText().equals("") || Integer.parseInt(tfA3.getText()) < 0 || Integer.parseInt(tfA3.getText()) > 100 ) {
				tfMessage.setText("ERROR: A3 marks should be between 0 and 100 (incusive).");
			}
			else if (tfExam.getText().equals("") || Integer.parseInt(tfExam.getText()) < 0 || Integer.parseInt(tfExam.getText()) > 100 ) {
				tfMessage.setText("ERROR: Exam marks should be between 0 and 100 (incusive).");
			}
			else {
				// Insert record
				GradeDBMC.insertRecord(Integer.parseInt(tfStudentID.getText()), tfStudentName.getText(), 
						Integer.parseInt(tfQuiz.getText()), Integer.parseInt(tfA1.getText()), 
						Integer.parseInt(tfA2.getText()), Integer.parseInt(tfA3.getText()), 
						Integer.parseInt(tfExam.getText()));

				// Update result in text field
				tfResult.setText("" + GradeDBMC.calculateResult(Integer.parseInt(tfQuiz.getText()), 
						Integer.parseInt(tfA1.getText()), Integer.parseInt(tfA2.getText()), 
						Integer.parseInt(tfA3.getText()), Integer.parseInt(tfExam.getText())));

				// Update grade in text field
				tfGrade.setText(GradeDBMC.calculateGrade(Double.parseDouble(tfResult.getText())));

				// Update message in text field
				tfMessage.setText("Record successfully inserted");
			}
		});

		// Setting action on calculate button
		btCalculateResult.setOnAction(e -> {
			if (tfStudentID.getText().length() != 8){
				tfMessage.setText("ERROR: Student id should be 8 digits long.");
			}
			else if (tfStudentName.getText().equals("")) {
				tfMessage.setText("ERROR: Insert student name.");
			}
			else if (tfQuiz.getText().equals("") || Integer.parseInt(tfQuiz.getText()) < 0 || Integer.parseInt(tfQuiz.getText()) > 100 ) {
				tfMessage.setText("ERROR: Quiz marks should be between 0 and 100 (incusive).");
			}
			else if (tfA1.getText().equals("") || Integer.parseInt(tfA1.getText()) < 0 || Integer.parseInt(tfA1.getText()) > 100 ) {
				tfMessage.setText("ERROR: A1 marks should be between 0 and 100 (incusive).");
			}
			else if (tfA2.getText().equals("") || Integer.parseInt(tfA2.getText()) < 0 || Integer.parseInt(tfA2.getText()) > 100 ) {
				tfMessage.setText("ERROR: A2 marks should be between 0 and 100 (incusive).");
			}
			else if (tfA3.getText().equals("") || Integer.parseInt(tfA3.getText()) < 0 || Integer.parseInt(tfA3.getText()) > 100 ) {
				tfMessage.setText("ERROR: A3 marks should be between 0 and 100 (incusive).");
			}
			else if (tfExam.getText().equals("") || Integer.parseInt(tfExam.getText()) < 0 || Integer.parseInt(tfExam.getText()) > 100 ) {
				tfMessage.setText("ERROR: Exam marks should be between 0 and 100 (incusive).");
			}
			else {
				// Update result in text field
				Double result = GradeDBMC.calculateResult(Integer.parseInt(tfQuiz.getText()), 
						Integer.parseInt(tfA1.getText()), Integer.parseInt(tfA2.getText()), 
						Integer.parseInt(tfA3.getText()), Integer.parseInt(tfExam.getText()));
				tfResult.setText(new DecimalFormat("#.00").format(result));

				// Update grade in text field
				tfGrade.setText(GradeDBMC.calculateGrade(Double.parseDouble(tfResult.getText())));

				// Update message in text field
				tfMessage.setText("Record successfully calculated, but not inserted yet.");
			}
		});

		sceneCalculateOrInsert= new Scene(gpInsertRecord,1000,500);

		/**
		 * Scene Search and Update Record
		 */ 
		// Declaring variables
		TextField tfSStudentName = new TextField();
		TextField tfSStudentID = new TextField();
		TextField tfSQuiz = new TextField();
		TextField tfSA1 = new TextField();
		TextField tfSA2 = new TextField();
		TextField tfSA3 = new TextField();
		TextField tfSExam = new TextField();
		TextField tfSResult = new TextField();
		TextField tfSGrade = new TextField();
		Button btSSearch = new Button("Search");
		Button btSBack = new Button("Back");

		// creating gridpane for search menu
		GridPane gpSearchMenu = new GridPane();
		gpSearchMenu.setHgap(5);
		gpSearchMenu.setVgap(5);

		gpSearchMenu.add(new Label("Student ID:"), 0, 0);
		gpSearchMenu.add(tfSStudentID, 1, 0);

		gpSearchMenu.add(new Label("Student Name:"), 0, 1);
		gpSearchMenu.add(tfSStudentName, 1, 1);

		gpSearchMenu.add(new Label("Quiz:"), 0, 2);
		gpSearchMenu.add(tfSQuiz, 1, 2);

		gpSearchMenu.add(new Label("A1:"), 0, 3);
		gpSearchMenu.add(tfSA1, 1, 3);

		gpSearchMenu.add(new Label("A2:"), 0, 4);
		gpSearchMenu.add(tfSA2, 1, 4);

		gpSearchMenu.add(new Label("A3:"), 0, 5);
		gpSearchMenu.add(tfSA3, 1, 5);

		gpSearchMenu.add(new Label("Exam:"), 0, 6);
		gpSearchMenu.add(tfSExam, 1, 6);

		gpSearchMenu.add(new Label("Result:"), 0, 7);
		gpSearchMenu.add(tfSResult, 1, 7);

		gpSearchMenu.add(new Label("Grade:"), 0, 8);
		gpSearchMenu.add(tfSGrade, 1, 8);

		gpSearchMenu.add(btSSearch, 1, 9);
		gpSearchMenu.add(btSBack, 0, 9);

		// creating gridpane for bottom note
		GridPane gpBottomNote = new GridPane();
		gpBottomNote.add(new Label("Note: In order to update a record; 1. Single click on any field (except ID, as ID is not allowed to edit), 2. Type new value, 3. Press enter."), 0, 0);
		gpBottomNote.add(new Label("Upon pressing the search button again, the updated grade and result will be shown."), 0, 1);

		// setting table to put searched records
		table = new TableView<>();

		// Create coloumn ID
		TableColumn<Student, Integer> tcStudentID = new TableColumn<>("ID");
		tcStudentID.setMinWidth(100);
		tcStudentID.setCellValueFactory(new PropertyValueFactory<>("id"));

		// Create coloumn Student Name
		TableColumn<Student, String> tcStudentName = new TableColumn<>("Student Name");
		tcStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
		// Update coloumn Student Name
		tcStudentName.setCellFactory(TextFieldTableCell.forTableColumn());
		tcStudentName.setOnEditCommit(event -> {
			Student student = event.getRowValue();
			student.setStudentName(event.getNewValue());
			GradeDBMC.updateDataString("STUDENTNAME", event.getNewValue(), student.getId());

		});

		// Create coloumn Quiz
		TableColumn<Student, Integer> tcQuiz = new TableColumn<>("Quiz");
		tcQuiz.setCellValueFactory(new PropertyValueFactory<>("quiz"));
		// Update coloumn Quiz
		tcQuiz.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcQuiz.setOnEditCommit(event -> {
			Student student = event.getRowValue();
			student.setQuiz(event.getNewValue().intValue());
			GradeDBMC.updateDataInteger("QUIZ", event.getNewValue(), student.getId());
		});

		// Create coloumn A1
		TableColumn<Student, Integer> tcA1 = new TableColumn<>("A1");
		tcA1.setCellValueFactory(new PropertyValueFactory<>("a1"));
		// Update coloumn A1
		tcA1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcA1.setOnEditCommit(event -> {
			Student student = event.getRowValue();
			student.setQuiz(event.getNewValue().intValue());
			GradeDBMC.updateDataInteger("A1", event.getNewValue(), student.getId());

		});

		// Create coloumn A2
		TableColumn<Student, Integer> tcA2 = new TableColumn<>("A2");
		tcA2.setCellValueFactory(new PropertyValueFactory<>("a2"));
		// Update coloumn A1
		tcA2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcA2.setOnEditCommit(event -> {
			Student student = event.getRowValue();
			student.setQuiz(event.getNewValue().intValue());
			GradeDBMC.updateDataInteger("A2", event.getNewValue(), student.getId());

		});

		// Create coloumn A3
		TableColumn<Student, Integer> tcA3 = new TableColumn<>("A3");
		tcA3.setCellValueFactory(new PropertyValueFactory<>("a3"));
		// Update coloumn A1
		tcA3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcA3.setOnEditCommit(event -> {
			Student student = event.getRowValue();
			student.setQuiz(event.getNewValue().intValue());
			GradeDBMC.updateDataInteger("A3", event.getNewValue(), student.getId());

		});

		// Create coloumn Exam
		TableColumn<Student, Integer> tcExam = new TableColumn<>("Exam");
		tcExam.setCellValueFactory(new PropertyValueFactory<>("exam"));
		// Update coloumn A1
		tcExam.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcExam.setOnEditCommit(event -> {
			Student student = event.getRowValue();
			student.setQuiz(event.getNewValue().intValue());
			GradeDBMC.updateDataInteger("EXAM", event.getNewValue(), student.getId());

		});

		// Create coloumn Results
		TableColumn<Student, Double> tcResult = new TableColumn<>("Results");
		tcResult.setCellValueFactory(new PropertyValueFactory<>("result"));

		// Create coloumn Grade
		TableColumn<Student, String> tcGrade = new TableColumn<>("Grade");
		tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

		// Setting action on back button
		btSBack.setOnAction(e -> primaryStage.setScene(sceneWelcome));

		// Setting action on search button
		btSSearch.setOnAction(e -> {
			table.getItems().clear();
			table.getColumns().clear();
			table.setEditable(true);
			
			// Putting searched items in table
			table.setItems(
					GradeDBMC.searchRecord(tfSStudentID.getText(), tfSStudentName.getText(), 
							tfSQuiz.getText(), tfSA1.getText(), tfSA2.getText(), tfSA3.getText(), 
							tfSExam.getText(), tfSResult.getText(), tfSGrade.getText())
					);
			table.getColumns().addAll(tcStudentID, tcStudentName, tcQuiz, tcA1, tcA2, tcA3, tcExam, tcResult, tcGrade);
		});


		// Creating boarder pane to hold search menu on left and searched results table in center.
		BorderPane bpSearchRecord = new BorderPane();
		bpSearchRecord.setLeft(gpSearchMenu);
		bpSearchRecord.setCenter(table);
		bpSearchRecord.setBottom(gpBottomNote);

		sceneSearchorUpdate = new Scene(bpSearchRecord, 1000, 500);

		// setting tableExist as primarty stage scene
		primaryStage.setScene(sceneTableExist);
		primaryStage.show();
	} // start method end

	public static void main(String[] args) {
		launch(args);
	}
}
