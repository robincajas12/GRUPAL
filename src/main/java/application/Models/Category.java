package application.Models;

public class Category implements IValidosParaCrud {
    private  int id;
    private  String name;
    private  String image;
    
    public Category() {  
    }

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
    
    
    public void setName(String name) {
		this.name = name;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getId() { return id; }
    public String getName() { return name; }
    public String getImage() { return image; }

    public int id() { return id; }
    public String name() { return name; }
    public String image() { return image; }

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", image=" + image + "]";
	}
    
    
}