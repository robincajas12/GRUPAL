package application.CrudForTables;


import Generics.FormGeneric;
import Generics.ICrud;
import application.Models.Category;
import application.ViewsManager.EstudiantesManager;
import application.database.CategoriaAd;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utilitarios.FormUtils;

public class CategoryCrud implements ICrud<Category> {
    CategoriaAd miCategoryAd = new CategoriaAd();

    @Override
    public boolean Create(Category item, TableView<Category> t) {
        try {
            // Create the form for creating a new category
            GridPane form = FormGeneric.createForm(Category.class, o -> {
                o.forEach((key, value) -> System.out.println(key + ": " + value.getText()));

                // Insert new category into the database
                int result = miCategoryAd.crear(new Category(0,
                        (String) o.get("name").getText(),
                        (String) o.get("image").getText()
                ));

                // Display appropriate notification
                if (result >= 0) {
                    FormUtils.notification("Category was created", "Close this if you don't want to add more");
                } else {
                    FormUtils.notification("Category was not created", "Check the data and try again");
                }
            }, "id");

            Stage stage = new Stage();
            stage.setTitle("Add Category");
            Scene miScene = new Scene(form);
            miScene.getStylesheets().clear(); 
            miScene.getStylesheets().add(getClass().getResource("/application/Views/Form.css").toExternalForm());
            stage.setScene(miScene);
            stage.setMinHeight(200);
            stage.setMinWidth(300);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean Edit(Category item, TableView<Category> t) {
        if (FormUtils.verificarCamposNulos(item)) {
            GridPane form = null;
            try {
                // Create form for editing the category
                form = FormGeneric.<Category>createFilledForm(Category.class, o -> {
                    o.forEach((key, value) -> System.out.println(key + ": " + value.getText()));

                    boolean wasUpdated = miCategoryAd.actualizar(new Category(
                            item.id(),
                            (String) o.get("name").getText(),
                            (String) o.get("image").getText()
                    ));

                    if (wasUpdated) {
                        FormUtils.notification("Category was updated", "Try again later");
                    } else {
                        FormUtils.notification("Category was not updated", "Try again later");
                    }
                }, item, "id", "name, image");

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            // Show the form to edit the category
            Stage stage = new Stage();
            stage.setTitle("Edit Category");
            Scene miScene = new Scene(form);
            miScene.getStylesheets().clear(); 
            miScene.getStylesheets().add(getClass().getResource("/application/Views/Form.css").toExternalForm());
            stage.setScene(miScene);
            stage.setMinHeight(200);
            stage.setMinWidth(300);
            stage.show();
        }

        // Update table data after editing
        t.getItems().setAll(FXCollections.observableArrayList(miCategoryAd.obtenerTodos()));
        return false;
    }

    @Override
    public boolean Delete(Category item, TableView<Category> t) {
        boolean wasDeleted = miCategoryAd.eliminar(item.id());
        t.getItems().setAll(FXCollections.observableArrayList(miCategoryAd.obtenerTodos()));
        return wasDeleted;
    }

    @Override
    public void More(TableView<Category> t) {
        // You can add additional functionality here if needed
    }
}
