package taxmanagementsystem;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaxManagementSystem extends Application {

	Scene sceneWelcome, sceneCalculateTax, sceneSearchTax, sceneErrorEmployeeID;
	private TextField tfTax = new TextField();
	private TextField tfTax1 = new TextField();
	//private TextField tfSearchedTax = new TextField();
	private TextField tfEmployeeID = new TextField();
	private TextField tfEmployeeID1 = new TextField();
	private TextField tfAnnualIncome = new TextField();

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Saqib Saleem Corporate Limited");

		//Scene Welcome page
		Label lbWelcome= new Label("Welcome to Tax Management System of Saqib Saleem Corporate Limited");
		Label lbSelect= new Label("Please select one of the following options:");
		Button btCalculateTax = new Button("Calculate Tax");
		Button btSearchTax = new Button("Search Tax");
		Button btExit = new Button("Exit");
		btCalculateTax.setPrefWidth(110);
		btSearchTax.setPrefWidth(110);
		btExit.setPrefWidth(110);
		btCalculateTax.setOnAction(e -> primaryStage.setScene(sceneCalculateTax));
		btSearchTax.setOnAction(e -> primaryStage.setScene(sceneSearchTax));

		VBox vbWelcome = new VBox(20);     
		vbWelcome.getChildren().addAll(lbWelcome, lbSelect, btCalculateTax, btSearchTax, btExit);
		vbWelcome.setAlignment(Pos.CENTER);

		sceneWelcome= new Scene(vbWelcome, 700, 250);

		//Scene Calculate Tax
		Button btCalculate = new Button("Calculate");
		Button btBack = new Button("Back");
		GridPane gpCalculateTax = new GridPane();
		gpCalculateTax.setHgap(5);
		gpCalculateTax.setVgap(5);
		gpCalculateTax.add(new Label("Employee ID:"), 0, 0);
		gpCalculateTax.add(tfEmployeeID, 1, 0);
		gpCalculateTax.add(new Label("Annual Income:"), 0, 1);
		gpCalculateTax.add(tfAnnualIncome, 1, 1);
		gpCalculateTax.add(new Label("Result:"), 0, 2);
		gpCalculateTax.add(tfTax, 1, 2);
		tfTax.setPrefWidth(500);
		tfTax.setEditable(false);
		gpCalculateTax.add(btCalculate, 1, 4);
		gpCalculateTax.add(btBack, 2, 4);
		gpCalculateTax.setAlignment(Pos.CENTER);
		btBack.setOnAction(e -> primaryStage.setScene(sceneWelcome));
		btCalculate.setOnAction(e -> {
			try {
				calculateTheTax();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		sceneCalculateTax= new Scene(gpCalculateTax,700,250);

		// Scene Search Tax
		Button btBack1 = new Button("Back");
		Button btSearch = new Button("Search");
		GridPane gpSearchTax = new GridPane();
		gpSearchTax.setHgap(5);
		gpSearchTax.setVgap(5);
		gpSearchTax.add(new Label ("Search Employee ID"), 0, 0);
		gpSearchTax.add(tfEmployeeID1, 1, 0);
		gpSearchTax.add(new Label ("Result"), 0, 1);
		gpSearchTax.add(tfTax1, 1, 1);
		tfTax1.setPrefWidth(300);
		tfTax1.setEditable(false);
		gpSearchTax.add(btBack1, 2, 2);
		gpSearchTax.add(btSearch, 1, 2);
		gpSearchTax.setAlignment(Pos.CENTER);
		btBack1.setOnAction(e -> primaryStage.setScene(sceneWelcome));
		btSearch.setOnAction(e -> {
			searchTax(tfEmployeeID1.getText());
		});
		sceneSearchTax= new Scene(gpSearchTax, 700, 250);

		// Exit button exits the program.
		btExit.setOnAction(e -> System.exit(0));

		primaryStage.setScene(sceneWelcome);
		primaryStage.show();
	}

	private void calculateTheTax() throws FileNotFoundException {
		// Get values from text fields
		
		if (tfEmployeeID.getText().length() != 4 || !tfEmployeeID.getText().matches("[0-9]*")) {
			tfTax.setText("Error: Employee ID should be 4 digits");	
		}
		else if(tfAnnualIncome.getText().length() < 3 ||
				!(tfAnnualIncome.getText().charAt(tfAnnualIncome.getText().length()-3)+"").equals(".") ||
				!tfAnnualIncome.getText().matches("[0-9.]*")) {
			tfTax.setText("Error: Annual Income should be floating-point number with two decimal places.");	
		}
		
		else{
			int employeeID = Integer.parseInt(tfEmployeeID.getText());
			double annualIncome = Double.parseDouble(tfAnnualIncome.getText());

			//Create a new employee object. Employee is defined in different class in same directory.
			Employee employee = new Employee(employeeID, annualIncome);

			// Display tax payment 
			tfTax.setText(employee.gettax(annualIncome));

			// Storing employee information in file taxreport.txt
			try {
				DataOutputStream output = new DataOutputStream(new FileOutputStream("src/taxreport.txt", true));
				if (!employee.gettax(annualIncome).equals("Error: Please provide file src/taxrates.tax")) {
					output.writeUTF("\n" + employee.getemployeeID() + "\t\t\t" + employee.getannualIncome() + 
							"\t\t\t" + employee.gettax(annualIncome));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String searchTax(String employeeID) {
		if (employeeID.length() != 4 || !employeeID.matches("\\d+")) {
			tfTax1.setText("Error: Employee ID should be 4 digits"); return null;
		}
		HashMap<String, String> hmTaxreport = new HashMap<String, String>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File ("src/taxreport.txt"));
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split("\t");
				hmTaxreport.put(line[0], line[line.length - 1]);
			}

			if (hmTaxreport.containsKey(employeeID)) {
				tfTax1.setText("$" + hmTaxreport.get(employeeID));
			}
			else {
				tfTax1.setText("No record found");
			}
		} catch (FileNotFoundException e) {
			tfTax1.setText("Please provide the file src/taxreport.txt in Source Code directory.");
		}

		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}

}