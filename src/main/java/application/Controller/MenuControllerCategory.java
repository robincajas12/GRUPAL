package application.Controller;

import application.CrudForTables.CategoryCrud;
import application.Models.Category;
import application.database.CategoriaAd;
import application.database.SearchAd;
import application.service.CategoryImporter;
import application.service.CategoryService;
import application.ViewsManager.WholeAppManager;
import application.ViewsManager.EViews;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import utilitarios.FormUtils;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import Generics.TableViewGeneric;

public class MenuControllerCategory {

    @FXML
    private ButtonBar buttonBar;
    
    @FXML
    private Button btnReload;  

    @FXML
    private TableView<Category> tableView;

    @FXML
    private TextField searchField;

    @FXML
    public void handleReturnButtonClick() throws IOException {
        // Navigate back to main menu
        WholeAppManager.show(EViews.MAIN);
    }

    @FXML
    public void handleReloadButtonClick() {
        tableView.getItems().setAll(FXCollections.observableArrayList( new CategoryImporter().importCategories()));
    }

    @FXML
    public void handleSearchButtonClick() {
        String query = searchField.getText();
        System.out.print(query);
        tableView.getItems().setAll(FXCollections.observableArrayList(SearchAd.buscarCategoria(query)));
        tableView.refresh();
    }

    public void initialize() {
        // Set up the TableView with categories
        TableViewGeneric<Category> tableViewGeneric = new TableViewGeneric<>(Category.class);
        tableViewGeneric.fillTableView(tableView, () -> new CategoriaAd().obtenerTodos());
        
        // Set up CRUD operations for categories
        CategoryCrud categoryCrud = new CategoryCrud();
        tableViewGeneric.fillbuttonBar(buttonBar, categoryCrud, tableView);

        // Populate the table with all categories
        tableView.getItems().setAll(FXCollections.observableArrayList(new CategoriaAd().obtenerTodos()));
        tableView.refresh();
    }
}
