package application.Models;

public class ProductImage implements IValidosParaCrud {
    private final int id;
    private final int idProduct;
    private final String image;

    // Constructor
    public ProductImage(int id, int idProduct, String image) {
        this.id = id;
        this.idProduct = idProduct;
        this.image = image;
    }

    // Métodos getter generados por el record
    public int id() {
        return id;
    }

    public int idProduct() {
        return idProduct;
    }

    public String image() {
        return image;
    }

    // Métodos getter adicionales
    public int getId() {
        return id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getImage() {
        return image;
    }

}
