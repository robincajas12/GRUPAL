package application.Models;



public class Product implements IValidosParaCrud {
    private  int id;
    private String title;
    private double price;
    private int idCategory;
    
    public Product() {
    }

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
    
    

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + ", idCategory=" + idCategory + "]";
	}
    
    
}
