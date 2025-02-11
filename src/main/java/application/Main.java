package application;
	
import java.sql.Connection;
import javafx.scene.control.TextField;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Consumer;

import Generics.FormGeneric;
import application.ViewsManager.EstudiantesManager;
import application.ViewsManager.WholeAppManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	private static Connection connection = null;
	public static Connection getConnection()
	{
		if(connection == null)
		{
			try {
				Main.connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/FakePlatziStore", "root", "rscajasm1006969");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if(connection.isClosed()) {
				try {
					Main.connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/FakePlatziStore", "root", "rscajasm1006969");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	@Override
	public void start(Stage primaryStage) throws InstantiationException, IllegalAccessException {
		WholeAppManager miWholeApp = new WholeAppManager();
		miWholeApp.start(primaryStage);     
	}
	public static void main(String[] args) {
		launch(args);
	}
}
