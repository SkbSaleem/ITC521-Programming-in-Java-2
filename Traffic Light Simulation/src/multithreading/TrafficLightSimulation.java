package multithreading;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TrafficLightSimulation extends Application{
	Scene main;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Traffic Light Simulation");

		// User Input
		TextField tfGreen = new TextField();
		TextField tfYellow = new TextField();
		TextField tfRed = new TextField();
		Button btStart = new Button("Start");
		Button btStop = new Button("Stop");

		GridPane gpInput = new GridPane();
		gpInput.setHgap(5);
		gpInput.setVgap(5);

		gpInput.add(new Label("Green"), 0, 0);
		gpInput.add(tfGreen, 1, 0);
		gpInput.add(new Label("Yellow"), 0, 1);
		gpInput.add(tfYellow, 1, 1);
		gpInput.add(new Label("Red"), 0, 2);
		gpInput.add(tfRed, 1, 2);
		gpInput.add(btStart, 0, 3);
		gpInput.add(btStop, 1, 3);

		// Light Model
		Circle cGreen = new Circle(0, 0, 100);
		cGreen.setFill(javafx.scene.paint.Color.GREEN);
		cGreen.visibleProperty().set(false);
		Circle cYellow = new Circle(0, 0, 100);
		cYellow.setFill(javafx.scene.paint.Color.YELLOW);
		cYellow.visibleProperty().set(false);
		Circle cRed = new Circle(0, 0, 100);
		cRed.setFill(javafx.scene.paint.Color.RED);
		cRed.visibleProperty().set(false);

		GridPane gpLight = new GridPane();
		gpLight.setHgap(5);
		gpLight.setVgap(5);

		gpLight.add(cGreen, 0, 0);
		gpLight.add(cYellow, 1, 0);
		gpLight.add(cRed, 2, 0);

		// Border pane to put everything in for main scene
		BorderPane bpMain = new BorderPane();
		bpMain.setLeft(gpInput);
		bpMain.setCenter(gpLight);

		// Thread
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					while (true) {
						System.out.println("change light color");
						Platform.runLater(new Runnable() {
							@Override 
							public void run() {
								cGreen.visibleProperty().set(true);
								cYellow.visibleProperty().set(false);
								cRed.visibleProperty().set(false);
							}
						});


						for (int i = Integer.parseInt(tfGreen.getText()); i > 0; i--) {
							System.out.println("Now light: 1 after "+ (i-1) +" seconds will change color");
							Thread.sleep(1000);
						}
						System.out.println("change light color");
						Platform.runLater(new Runnable() {
							@Override 
							public void run() {
								cGreen.visibleProperty().set(false);
								cYellow.visibleProperty().set(true);
								cRed.visibleProperty().set(false);
							}
						});
						for (int i = Integer.parseInt(tfYellow.getText()); i > 0; i--) {
							System.out.println("Now light: 2 after "+ (i-1) +" seconds will change color");
							Thread.sleep(1000);
						}
						System.out.println("change light color");
						Platform.runLater(new Runnable() {
							@Override 
							public void run() {
								cGreen.visibleProperty().set(false);
								cYellow.visibleProperty().set(false);
								cRed.visibleProperty().set(true);
							}
						});
						for (int i = Integer.parseInt(tfRed.getText()); i > 0; i--) {
							System.out.println("Now light: 3 after "+ (i-1) +" seconds will change color");
							Thread.sleep(1000);
						}

					}

					//
				}
				catch (InterruptedException ex) {
				}
			}
		});
		btStart.setOnAction(e -> {
			// Thread start
			if (t1.isAlive()) {
				t1.resume();
			} else {
				t1.start();
			}
		});

		btStop.setOnAction(e -> {
			t1.suspend();
		});

		main = new Scene(bpMain, 1000, 500);
		primaryStage.setScene(main);
		primaryStage.show();
	}











	public static void main(String[] args) {
		launch(args);
	}
}
