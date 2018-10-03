package displayclusterrecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PIDCO extends Application{
	ArrayList<String> alCluster1 = new ArrayList<String>();
	ArrayList<String> alCluster2 = new ArrayList<String>();
	ArrayList<String> alCluster3 = new ArrayList<String>();
	ArrayList<String> alCluster4 = new ArrayList<String>();

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		File file = new File("src/Cluster.txt");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.endsWith("1")){
				String[] parts = line.split("\t");
				alCluster1.add(parts[0] + "$" + parts[1]);
			}
			else if (line.endsWith("2")){
				String[] parts = line.split("\t");
				alCluster2.add(parts[0] + "$" + parts[1]);
			}
			else if (line.endsWith("3")){
				String[] parts = line.split("\t");
				alCluster3.add(parts[0] + "$" + parts[1]);
			}
			else if (line.endsWith("4")){
				String[] parts = line.split("\t");
				alCluster4.add(parts[0] + "$" + parts[1]);
			}
		}
		//System.out.println(alCluster1.size()+alCluster2.size()+alCluster3.size()+alCluster4.size());

		primaryStage.setTitle("Records of Cluster");
		final NumberAxis xAxis = new NumberAxis(0, 8, 1);
		final NumberAxis yAxis = new NumberAxis(0, 10, 1);        
		final ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);
		xAxis.setLabel("Attribute X");                
		yAxis.setLabel("Attribute Y");
		sc.setTitle("Unique Neighborhood Set Parameter Independent Density-Based Clustering with Outlier Detection");

		// Cluster 1
		XYChart.Series cluster1 = new XYChart.Series();
		cluster1.setName("Cluster 1");
		for (int i = 0; i < alCluster1.size(); i++){
			//String[] parts = alCluster1.get(i).split("$"); Double x = Double.parseDouble(parts[0]); // here was the problem, gave InvocationTargetException
			String x = alCluster1.get(i).substring(0, alCluster1.get(i).indexOf("$"));
			String y = alCluster1.get(i).substring(alCluster1.get(i).indexOf("$") + 1, alCluster1.get(i).length());
			cluster1.getData().add(new XYChart.Data(Double.parseDouble(x), Double.parseDouble(y)));
		}

		// Cluster 2
		XYChart.Series cluster2 = new XYChart.Series();
		cluster2.setName("Cluster 2");
		for (int i = 0; i < alCluster2.size(); i++){
			String x = alCluster2.get(i).substring(0, alCluster2.get(i).indexOf("$"));
			String y = alCluster2.get(i).substring(alCluster2.get(i).indexOf("$") + 1, alCluster2.get(i).length());
			cluster2.getData().add(new XYChart.Data(Double.parseDouble(x), Double.parseDouble(y)));
		}

		// Cluster 3
		XYChart.Series cluster3 = new XYChart.Series();
		cluster3.setName("Cluster 3");
		for (int i = 0; i < alCluster2.size(); i++){
			String x = alCluster3.get(i).substring(0, alCluster3.get(i).indexOf("$"));
			String y = alCluster3.get(i).substring(alCluster3.get(i).indexOf("$") + 1, alCluster3.get(i).length());
			cluster3.getData().add(new XYChart.Data(Double.parseDouble(x), Double.parseDouble(y)));
		}

		// Cluster 4
		XYChart.Series cluster4 = new XYChart.Series();
		cluster4.setName("Cluster 4");
		for (int i = 0; i < alCluster4.size(); i++){
			String x = alCluster4.get(i).substring(0, alCluster4.get(i).indexOf("$"));
			String y = alCluster4.get(i).substring(alCluster4.get(i).indexOf("$") + 1, alCluster4.get(i).length());
			cluster4.getData().add(new XYChart.Data(Double.parseDouble(x), Double.parseDouble(y)));
		}

		sc.getData().addAll(cluster1, cluster2, cluster3, cluster4);
		Scene scene  = new Scene(sc, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.show();		
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}