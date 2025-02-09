module proyecto {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires java.base;
	requires okhttp3;
	requires transitive com.fasterxml.jackson.databind;

	opens application to javafx.graphics, javafx.fxml;
	opens application.Controller to javafx.fxml;
	opens application.Models to javafx.base, com.fasterxml.jackson.databind	;

	exports application.Models;
	exports application.service;
	
}
