package application.Controller;

import java.io.IOException;

import Generics.FormGeneric;
import Generics.ICrud;
import Generics.TableViewGeneric;
import application.Models.User;
import application.ViewsManager.WholeAppManager;
import application.database.UsuarioAd;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utilitarios.FormUtils;
import javafx.beans.property.Property;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Method;
public class MenuController {
	@FXML
	private ButtonBar buttonBar;
    @FXML
    private Button btnRead;  
    @FXML
    private TableView<User> tableView;
    @FXML
    public void handleButtonClick() throws IOException {
        System.out.println("¡Botón Add presionado!");
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/application/Views/Tabla.fxml"));
        BorderPane root = loader.load();
        Stage miStage = new Stage();
        miStage.setScene(new Scene(root));
        miStage.show();
        
    }
    
    class CrudUser implements ICrud<User> {
    	UsuarioAd miUserAd = new UsuarioAd();
		@Override
		public boolean Create(User item) {
			return miUserAd.crear(item) >= 0;
			
		}

		@Override
		public boolean Reload() {
			return false;
		}

		@Override
		public boolean Edit(User item) {
			if(FormUtils.verificarCamposNulos(item))
			{
				GridPane form = null;
				try {
				
					form = FormGeneric.<User>createFilledForm(User.class, o -> {
						new UsuarioAd().actualizar(new User(
							    Integer.valueOf(o.get("id").getText()),
							    (String) o.get("email").getText(),
							    (String) o.get("password").getText(),
							    (String) o.get("name").getText(),
							    (String) o.get("role").getText(),
							    (String) o.get("avatar").getText()
							));
					}, item, "", "id");
					
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					System.out.println("XDDDDDDDDDDDDDDDDD");
					e.printStackTrace();
				}
				
				Stage stage = new Stage();
	            stage.setTitle("Editar Usuario");
	            stage.setScene(new Scene(form));
	            stage.show();
				
			}
			
			return false;
		}

		@Override
		public boolean Delete(User item) {
			return miUserAd.eliminar(item.id());
		}

		@Override
		public void More() {
			// TODO Auto-generated method stub
			
		}
    	
    }

	public void initialize()
	{
		
		TableViewGeneric<User> tableViewGeneric = new TableViewGeneric<>(User.class);
		tableViewGeneric.fillTableView(tableView, ()-> new UsuarioAd().obtenerTodos());
		CrudUser miCrud = new CrudUser();
		tableViewGeneric.fillbuttonBar(buttonBar, miCrud, tableView);
		tableView.getItems().setAll(FXCollections.observableArrayList(new UsuarioAd().obtenerTodos()));
		tableView.refresh();
	}
}
