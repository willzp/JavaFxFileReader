package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();

			Button openFileBtn = new Button("Open file");

			TableView table = new TableView();
			root.setCenter(table);

			openFileBtn.setOnMouseClicked(e -> openFileBtnClicked(primaryStage, table));
			root.setTop(openFileBtn);

			Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openFileBtnClicked(Stage stage, TableView table) {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(stage);
		String fileName = file.getName();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		System.out.println(fileExtension);
		if (fileExtension.equals("csv")) {

			try {
				BufferedReader br = new BufferedReader(new FileReader(file));

				String line = br.readLine();
				String[] columns = line.split(",");
				for (int i = 0; i < columns.length; i++) {
					TableColumn column = new TableColumn(columns[i]);
					column.setCellValueFactory(new PropertyValueFactory<>(columns[i]));
					column.setSortable(true);
					table.getColumns().add(column);
				}

				while ((line = br.readLine()) != null) {
					String[] row = line.split(",");
					Order order = new Order(row[0], row[1], row[2], row[3], row[4], Float.parseFloat(row[5]),
							Float.parseFloat(row[6]));
					table.getItems().add(order);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fileExtension.equals("json")) {
			JSONParser parser = new JSONParser();

			try {
				JSONArray jsonBlocks = (JSONArray) parser.parse(new FileReader(file));

				JSONObject first = (JSONObject) jsonBlocks.get(0);
				first.keySet().forEach(keystring -> {
					TableColumn column = new TableColumn(keystring.toString());
					column.setCellValueFactory(new PropertyValueFactory<>(keystring.toString()));
					column.setSortable(true);
					table.getColumns().add(column);
				});

				for (Object jsonBlock : jsonBlocks) {

					JSONObject entry = (JSONObject) jsonBlock;

					String orderDate = entry.get("OrderDate").toString();
					String region = entry.get("Region").toString();
					String rep1 = entry.get("Rep1").toString();
					String rep2 = entry.get("Rep2").toString();
					String item = entry.get("Item").toString();
					String units = entry.get("Units").toString();
					String unitCost = entry.get("UnitCost").toString();
					String total = entry.get("Total").toString();

					Order order = new Order(orderDate, region, rep1, rep2, item, Float.parseFloat(units),
							Float.parseFloat(unitCost));
					table.getItems().add(order);

				}
			} catch (IOException e) {

			} catch (ParseException e) {

				System.err.println("file not found!");
			}
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
