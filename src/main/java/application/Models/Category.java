package application.Models;

public class Category implements IValidosParaCrud {
    private  int id;
    private  String name;
    private  String image;

    public Category(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
		this.image = "";
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getImage() { return image; }

    public int id() { return id; }
    public String name() { return name; }
    public String image() { return image; }
}