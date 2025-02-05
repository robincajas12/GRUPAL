module proyecto {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires java.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.Controller to javafx.fxml;
	opens application.Models to javafx.base;
	exports application.Models;
}
