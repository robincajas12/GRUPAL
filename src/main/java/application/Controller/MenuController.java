package application.Controller;

import java.io.IOException;

import Generics.FormGeneric;
import Generics.ICrud;
import Generics.TableViewGeneric;
import application.CrudForTables.UserCrud;
import application.Models.User;
import application.ViewsManager.EViews;
import application.ViewsManager.WholeAppManager;
import application.database.SearchAd;
import application.database.UsuarioAd;
import application.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TextField searchField;
    @FXML
    public void handleReturnButtonClick() throws IOException {
    	WholeAppManager.show(EViews.MAIN);
    }
    @FXML
    public void handleReloadButtonClick()
    {
    	tableView.getItems().setAll(FXCollections.observableArrayList(new UsuarioAd().obtenerTodos()));
    }
    @FXML
    public void handleSearchButtonClick()
    {
    	 String query = searchField.getText();
    	 System.out.print(query);
    	tableView.getItems().setAll(FXCollections.observableArrayList(SearchAd.buscarUser(query)));
 		tableView.refresh();
    }
    @FXML
    public void handleSincronizeButtonClick()
    {
        new UserService().fetchUsers();
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
