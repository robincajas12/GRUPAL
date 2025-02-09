package application.Controller;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import application.ViewsManager.EViews;
import application.ViewsManager.WholeAppManager;
import application.service.ProductImporter;
import application.service.UserImporter;

public class MainMenuController {

    @FXML
    private Button usersButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button productsButton;

    @FXML
    private Button otherViewButton;
    //@FXML
    //private Button btnImport;
    @FXML
    private void handleUsersButton() {
        WholeAppManager.show(EViews.USERS);
    }
    /*@FXML
    private void handleImportButtonClick()
    {
    	new UserImporter().importUsers();
    	new ProductImporter().importProducts();
    }*/
    @FXML
    private void handleCategoryButton() {
    	WholeAppManager.show(EViews.CATEGORY);
    }

    @FXML
    private void handleProductsButton() {
    	WholeAppManager.show(EViews.PRODUCTS);
    }

    @FXML
    private void handleOtherViewButton() {
    	WholeAppManager.show(EViews.STORE);
    }

    private void openTableView(String tableName) {
    }
}