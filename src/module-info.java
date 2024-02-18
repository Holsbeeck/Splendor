module splendor_g128{
	exports gui;
	exports main;
	exports domein;
	
	requires javafx.base;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires java.sql;
	requires junit;
	requires org.junit.jupiter.params;
	requires org.junit.jupiter.api;
	requires java.desktop;
	
	opens main to javafx.fxml, javafx.graphics;
	opens gui to javafx.fxml, javafx.graphics;
}