package application.ViewsManager;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import Generics.FormGeneric;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WholeAppManager extends Application{
	public static Stage stage = null;
	private Stage  primaryStage = null;
    Consumer<Map<String, TextField>> miConsumer = fieldMap -> {
        
        
    };
    @Override
	public void start(Stage primaryStage) throws InstantiationException, IllegalAccessException
	{
    	if(stage == null)
    	{
    		stage =primaryStage;
    	}
    	this.primaryStage =  primaryStage;
		GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
    
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(EstudiantesManager.class.getResource("/application/Views/Tabla.fxml"));
		Parent root;
		Scene miAddManager = null;
		try {
			root = loader.load();
			miAddManager = new Scene(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        WholeAppManager.stage.setTitle("Form");
        WholeAppManager.stage.setScene(miAddManager);
        WholeAppManager.stage.show();
        
	}
}
