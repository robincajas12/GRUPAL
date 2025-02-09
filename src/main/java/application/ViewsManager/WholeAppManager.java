package application.ViewsManager;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import Generics.FormGeneric;
import application.database.ProductoAd;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WholeAppManager extends Application{
	public static Stage previusStage = null;
	public static Stage stage = null;
	public static  Stage  primaryStage = null;
	public static Stage changeStage(Stage stage)
	{
		WholeAppManager.previusStage = WholeAppManager.stage;
		WholeAppManager.stage = stage;
		return stage;
		
	}
	
	public static void show (EViews views)
	{
		FXMLLoader loader = new FXMLLoader();
		switch(views)
		{
			case EViews.USERS:
				loader.setLocation(EstudiantesManager.class.getResource("/application/Views/TablaUsers.fxml"));
				break;
			case EViews.MAIN:
				loader.setLocation(EstudiantesManager.class.getResource("/application/Views/MainMenu.fxml"));
				break;
			case EViews.CATEGORY:
				loader.setLocation(EstudiantesManager.class.getResource("/application/Views/TableCategory.fxml"));
				break;
			case EViews.PRODUCTS:
				loader.setLocation(EstudiantesManager.class.getResource("/application/Views/TableProducts.fxml"));
				break;
			case EViews.STORE:
				ProductsManager.createProductGrid(new ProductoAd().getFullSchema());
				return;
				
				
		default:
			loader.setLocation(EstudiantesManager.class.getResource("/application/Views/MainMenu.fxml"));
			break;
		}
		Parent root;
		Scene miAddManager = null;
		try {
			root = loader.load();
			miAddManager = new Scene(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        WholeAppManager.stage.setTitle("Main");
        WholeAppManager.stage.setScene(miAddManager);
        javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();


     WholeAppManager.stage.setWidth(screenBounds.getWidth());
     WholeAppManager.stage.setHeight(screenBounds.getHeight());
        WholeAppManager.stage.show();
        
	}
    @Override
	public void start(Stage primaryStage) throws InstantiationException, IllegalAccessException
	{
    	ProductsManager.createProductGrid(new ProductoAd().getFullSchema());
    	if(stage == null)
    	{
    		stage =primaryStage;
    	}
    	this.primaryStage =  primaryStage;
		GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
    
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(EstudiantesManager.class.getResource("/application/Views/MainMenu.fxml"));
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
