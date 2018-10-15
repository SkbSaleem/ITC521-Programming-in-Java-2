package try1;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatClientUI extends Application {
	private TextField tfName = new TextField();
	private TextField tfMessage = new TextField();

	// Button for sending a student to the server
	private Button btSend = new Button("Send");

	// Host name or ip
	String host = "localhost";

	@Override
	public void start(Stage primaryStage) {		
		// Grid pane for all the messages
		GridPane paneMessages = new GridPane();
		

		//  Grid pane for send button
		GridPane paneBelow = new GridPane();
		paneBelow.add(new Label("Name"), 0, 0);
		paneBelow.add(tfName, 1, 0);   

		paneBelow.add(new Label("Message"), 0, 1);
		paneBelow.add(tfMessage, 1, 1);

		paneBelow.add(btSend, 1, 3);
		GridPane.setHalignment(btSend, HPos.RIGHT);

		paneBelow.setAlignment(Pos.CENTER);   
		tfName.setPrefColumnCount(15);
		tfMessage.setPrefColumnCount(15);

		btSend.setOnAction(e -> {
			
		});

		// Border grid pane to hold all other panes
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(paneMessages);
		borderPane.setBottom(paneBelow);

		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 500, 500);
		primaryStage.setTitle("Client"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}







	/**
	 * The main method is only needed for the IDE with limited
	 * JavaFX support. Not needed for running from the command line.
	 */
	public static void main(String[] args) {
		//ChatClient client = new ChatClient("0.0.0.0", 8002);
		launch(args);
	}
}
