package application.Models;



public class Product implements IValidosParaCrud {
    private final int id;
    private final String title;
    private final double price;
    private final int idCategory;

    public Product(int id, String title, double price, int idCategory) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.idCategory = idCategory;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getIdCategory() { return idCategory; }

    public int id() { return id; }
    public String title() { return title; }
    public double price() { return price; }
    public int idCategory() { return idCategory; }
}
