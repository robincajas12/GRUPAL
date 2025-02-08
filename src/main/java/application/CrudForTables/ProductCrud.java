package application.CrudForTables;



import Generics.FormGeneric;
import Generics.ICrud;
import application.Models.Product;
import application.database.ProductoAd;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utilitarios.FormUtils;

public class ProductCrud implements ICrud<Product> {
    ProductoAd miProductoAd = new ProductoAd();

    @Override
    public boolean Create(Product item, TableView<Product> t) {
        try {
            // Create the form for creating a new product
            GridPane form = FormGeneric.createForm(Product.class, o -> {
                o.forEach((key, value) -> System.out.println(key + ": " + value.getText()));

                // Insert new product into the database
                int result = miProductoAd.crear(new Product(0,
                        (String) o.get("title").getText(),
                        Double.parseDouble(o.get("price").getText()),
                        Integer.parseInt(o.get("idCategory").getText())
                ));

                // Display appropriate notification
                if (result >= 0) {
                    FormUtils.notification("Product was created", "Close this if you don't want to add more");
                } else {
                    FormUtils.notification("Product was not created", "Check the data and try again");
                }
            }, "id");

            Stage stage = new Stage();
            stage.setTitle("Add Product");
            Scene miScene = new Scene(form);
            miScene.getStylesheets().clear(); 
            miScene.getStylesheets().add(getClass().getResource("/application/Views/Form.css").toExternalForm());
            stage.setScene(miScene);
            stage.setMinHeight(300);
            stage.setMinWidth(300);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean Edit(Product item, TableView<Product> t) {
        if (FormUtils.verificarCamposNulos(item)) {
            GridPane form = null;
            try {
                // Create form for editing the product
                form = FormGeneric.<Product>createFilledForm(Product.class, o -> {
                    o.forEach((key, value) -> System.out.println(key + ": " + value.getText()));

                    boolean wasUpdated = miProductoAd.actualizar(new Product(
                            item.id(),
                            (String) o.get("title").getText(),
                            Double.parseDouble(o.get("price").getText()),
                            Integer.parseInt(o.get("idCategory").getText())
                    ));

                    if (wasUpdated) {
                        FormUtils.notification("Product was updated", "Try again later");
                    } else {
                        FormUtils.notification("Product was not updated", "Try again later");
                    }
                }, item, "id", "title, price, idCategory");

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setTitle("Edit Product");
            Scene miScene = new Scene(form);
            miScene.getStylesheets().clear(); 
            miScene.getStylesheets().add(getClass().getResource("/application/Views/Form.css").toExternalForm());
            stage.setScene(miScene);
            stage.setMinHeight(300);
            stage.setMinWidth(300);
            stage.show();
        }

        // Update table data after editing
        t.getItems().setAll(FXCollections.observableArrayList(miProductoAd.obtenerTodos()));
        return false;
    }

    @Override
    public boolean Delete(Product item, TableView<Product> t) {
        boolean wasDeleted = miProductoAd.eliminar(item.id());
        t.getItems().setAll(FXCollections.observableArrayList(miProductoAd.obtenerTodos()));
        return wasDeleted;
    }

    @Override
    public void More(TableView<Product> t) {
    }
}

