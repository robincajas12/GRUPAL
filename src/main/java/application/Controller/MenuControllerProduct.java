package application.Controller;


import application.CrudForTables.ProductCrud;
import application.Models.CompleteProductSchema;
import application.Models.Product;
import application.database.ProductoAd;
import application.database.SearchAd;
import application.service.CategoryImporter;
import application.service.ProductImporter;
import application.service.ProductService;
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
import java.util.List;

import Generics.TableViewGeneric;

public class MenuControllerProduct {

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private Button btnReload;  

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TextField searchField;

    @FXML
    public void handleReturnButtonClick() throws IOException {
        // Navigate back to main menu
        WholeAppManager.show(EViews.MAIN);
    }

    @FXML
    public void handleReloadButtonClick() {
        tableView.getItems().setAll(FXCollections.observableArrayList(new ProductImporter().importProducts()));
    }

    @FXML
    public void handleSearchButtonClick() {
        // Search products based on the input text
        String query = searchField.getText();
        System.out.print(query);
        tableView.getItems().setAll(FXCollections.observableArrayList(SearchAd.buscarProducto(query)));
        tableView.refresh();
    }

    public void initialize() {
        // Set up the TableView with products
        TableViewGeneric<Product> tableViewGeneric = new TableViewGeneric<>(Product.class);
        tableViewGeneric.fillTableView(tableView, () -> new ProductoAd().obtenerTodos());
        
        // Set up CRUD operations for products
        ProductCrud productCrud = new ProductCrud();
        tableViewGeneric.fillbuttonBar(buttonBar, productCrud, tableView);

        new CategoryImporter().importCategories();
        
        // Populate the table with all products
        tableView.getItems().setAll(FXCollections.observableArrayList(new ProductoAd().obtenerTodos()));
        tableView.refresh();
    }
}
