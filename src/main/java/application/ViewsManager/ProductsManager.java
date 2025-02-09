package application.ViewsManager;

import application.Models.CompleteProductSchema;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class ProductsManager {

    private static final int IMAGE_SIZE = 150;

    public static ScrollPane createProductGrid(List<CompleteProductSchema> products) {
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10));
        tilePane.setHgap(15);
        tilePane.setVgap(15);
        tilePane.setPrefColumns(3);
        tilePane.setStyle("-fx-alignment: center;");

        for (CompleteProductSchema product : products) {
            
            if (product.getImages().isEmpty() || product.getImages().get(0) == null || product.getImages().get(0).isEmpty()) {
                continue;
            }

            VBox productBox = createProductBox(product);
            tilePane.getChildren().add(productBox);
        }

        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }


    private static VBox createProductBox(CompleteProductSchema product) {
        
        String imageUrl = product.getImages().isEmpty() ? "default.jpg" : product.getImages().get(0);
        ImageView imageView = new ImageView(new Image(imageUrl, IMAGE_SIZE, IMAGE_SIZE, true, true));

        
        Text title = new Text(product.getTitle());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text price = new Text("Precio: $" + product.getPrice());
        price.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Text category = new Text("Categoría: " + product.getCategory().getName());
        category.setFont(Font.font("Arial", FontWeight.LIGHT, 12));

        VBox box = new VBox(5, imageView, title, price, category);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-alignment: center;");
        return box;
    }
}
