package application.CrudForTables;

import Generics.FormGeneric;
import Generics.ICrud;
import application.Models.User;
import application.ViewsManager.WholeAppManager;
import application.database.UsuarioAd;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utilitarios.FormUtils;

public class UserCrud implements ICrud<User> {
	UsuarioAd miUserAd = new UsuarioAd();
	@Override
	public boolean Create(User item, TableView<User> t) {
		
		try {
			GridPane form = FormGeneric.createForm(User.class, o -> {
				o.forEach((key, value) -> System.out.println(key + ": " + value.getText()));

				int a = new UsuarioAd().crear(new User(
						(String) o.get("name").getText(),
					    (String) o.get("email").getText(),
					    (String) o.get("avatar").getText(),
					    (String) o.get("password").getText()
					));
				if(a >= 0) {
					FormUtils.notification("User was created", "Close this if you dont want to add more");
					
				} else {
					FormUtils.notification("User was not created", "email is already in user");
				}
			}, "id role");
			
            Stage stage = new Stage();
            stage.setTitle("Add User");
            Scene miScene = new Scene(form);
            miScene.getStylesheets().clear(); 
            miScene.getStylesheets().add(getClass().getResource("/application/Views/Form.css").toExternalForm());
            stage.setScene(miScene);
            stage.setMinHeight(300);
            stage.setMinWidth(300);
            stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}



	@Override
	public boolean Edit(User item,TableView<User> t){
		if(FormUtils.verificarCamposNulos(item))
		{
			GridPane form = null;
			try {
			
				form = FormGeneric.<User>createFilledForm(User.class, o -> {
					// Debug: Verifica que los valores en fieldMap están correctos
					o.forEach((key, value) -> System.out.println(key + ": " + value.getText()));

					boolean wasCreated = new UsuarioAd().actualizar(new User(
							(String) o.get("name").getText(),
						    (String) o.get("email").getText(),
						    (String) o.get("avatar").getText(),
						    (String) o.get("password").getText()
						));
					if(wasCreated) { 
						FormUtils.notification("NEW WAS UPDATED", "Try again later");
					}else {
						FormUtils.notification("NEW USER WAS NOT UPDATED", "Try again later");
					}
				}, item, "", "id, role");
				
				
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				System.out.println("XDDDDDDDDDDDDDDDDD");
				e.printStackTrace();
			}
			
            Stage stage = new Stage();
            stage.setTitle("Edit User");
            Scene miScene = new Scene(form);
            miScene.getStylesheets().clear(); 
            miScene.getStylesheets().add(getClass().getResource("/application/Views/Form.css").toExternalForm());
            stage.setScene(miScene);
            stage.setMinHeight(400);
            stage.setMinWidth(300);
            stage.show();
			
		}
		t.getItems().setAll(FXCollections.observableArrayList(new UsuarioAd().obtenerTodos()));
		return false;
	}

	@Override
	public boolean Delete(User item,TableView<User> t) {
		boolean wasUpdated = miUserAd.eliminar(item.id());
		t.getItems().setAll(FXCollections.observableArrayList(new UsuarioAd().obtenerTodos()));
		return wasUpdated;
	}

	@Override
	public void More(TableView<User> t) {
		// TODO Auto-generated method stub
		
	}
	
}
