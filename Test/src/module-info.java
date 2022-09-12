module Test {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires json.simple;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base, json.simple;
}
