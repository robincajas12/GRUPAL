package application.Controller;

import java.io.IOException;

import Generics.FormGeneric;
import Generics.ICrud;
import Generics.TableViewGeneric;
import application.CrudForTables.UserCrud;
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
    private Button btnReload;  
    @FXML
    private TableView<User> tableView;
    @FXML
    public void handleButtonClick() throws IOException {
    	tableView.getItems().setAll(FXCollections.observableArrayList(new UsuarioAd().obtenerTodos()));
        
    }
    

	public void initialize()
	{
		
		TableViewGeneric<User> tableViewGeneric = new TableViewGeneric<>(User.class);
		tableViewGeneric.fillTableView(tableView, ()-> new UsuarioAd().obtenerTodos());
		UserCrud miCrud = new UserCrud();
		tableViewGeneric.fillbuttonBar(buttonBar, miCrud, tableView);
		tableView.getItems().setAll(FXCollections.observableArrayList(new UsuarioAd().obtenerTodos()));
		tableView.refresh();
	}
}
