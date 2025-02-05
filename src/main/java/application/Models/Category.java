package application.Models;

public class Category implements IValidosParaCrud {
    private final int id;
    private final String name;
    private final String image;

    public Category(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getImage() { return image; }

    public int id() { return id; }
    public String name() { return name; }
    public String image() { return image; }
}