package fonttester;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FontTester extends Application{
	protected Text text = new Text(50, 50, "Programming if fun");


	protected BorderPane getPane() {
		/** Boarder pane */
		BorderPane pane = new BorderPane();

		/** Center pane for text */
		Pane paneForText = new Pane();
		paneForText.getChildren().add(text);
		
		// pane position
		pane.setCenter(paneForText); 
		text.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 8));

		/** Top pane for font name and font size */
		HBox paneForComboBox = new HBox(20);
		ComboBox<String> cbFontName = new ComboBox<>();
		ComboBox<String> cbFontSize = new ComboBox<>();
		paneForComboBox.getChildren().addAll(new Label("Font Name "), cbFontName, new Label("Font Size "), cbFontSize);
		paneForComboBox.setAlignment(Pos.CENTER);

		// Pane position
		pane.setTop(paneForComboBox);

		// Font Names
		cbFontName.setValue("Verdana");
		cbFontName.getItems().addAll("Courier", "Courier New", "Cochin", "Verdana", 
				"Arial", "Times New Roman","Avenir", "SansSerif", "Serif", "Monospaced");
//
		// Font Size
		cbFontSize.setValue("8");
		cbFontSize.getItems().addAll("8", "12", "16", "24", "30", "36", "42", "48", "56");

		/** Bottom pane for bold and italic */
		HBox paneForCheckBox = new HBox(20);
		CheckBox chkBold = new CheckBox("Bold");
		CheckBox chkItalic = new CheckBox("Italic");    
		paneForCheckBox.getChildren().addAll(chkBold, chkItalic);
		paneForCheckBox.setAlignment(Pos.CENTER);

		// Pane position
		pane.setBottom(paneForCheckBox); 

		// Event Handler
		EventHandler<ActionEvent> handler = e -> {
			if (chkBold.isSelected() && chkItalic.isSelected()){
				text.setFont(Font.font(cbFontName.getValue(), FontWeight.BOLD, 
						FontPosture.ITALIC, Double.parseDouble(cbFontSize.getValue())));
			}
			else if (chkBold.isSelected()) {
				text.setFont(Font.font(cbFontName.getValue(), FontWeight.BOLD, 
						FontPosture.REGULAR, Double.parseDouble(cbFontSize.getValue())));
			}
			else if (chkItalic.isSelected()) {
				text.setFont(Font.font(cbFontName.getValue(), FontWeight.NORMAL, 
						FontPosture.ITALIC, Double.parseDouble(cbFontSize.getValue())));
			}
			else{
				text.setFont(Font.font(cbFontName.getValue(), FontWeight.NORMAL, 
						FontPosture.REGULAR, Double.parseDouble(cbFontSize.getValue())));
			}
			
		};

		// Initiating events
		cbFontName.setOnAction(handler);
		cbFontSize.setOnAction(handler);
		chkBold.setOnAction(handler);
		chkItalic.setOnAction(handler);

		return pane;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create a scene and place it in the stage
		Scene scene = new Scene(getPane(), 700, 200);
		primaryStage.setTitle("Font Tester"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

	}

	public static void main(String[] args) {
		launch(args);
	}

}
