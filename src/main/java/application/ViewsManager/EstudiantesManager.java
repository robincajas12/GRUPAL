package application.ViewsManager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EstudiantesManager {
	private static Scene miAddManager = null;
	
	public static Scene getAddManager()
	{
			if(miAddManager == null)
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(EstudiantesManager.class.getResource("/application/Views/Estudiantes.fxml"));
				Parent root;
				try {
					root = loader.load();
					miAddManager = new Scene(root);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return miAddManager;
			
	}

}
