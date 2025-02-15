package application.createData;

import java.util.List;

public class CreateProduct {

	private String title;
    private double price;
    private String description;
    private int categoryId;
    private List<String> images;
    
    public CreateProduct() {
    	
    }
    
    
	public CreateProduct(String title, double price, String description, int categoryId, List<String> images) {
		super();
		this.title = title;
		this.price = price;
		this.description = description;
		this.categoryId = categoryId;
		this.images = images;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public List<String> getImages() {
		return images;
	}


	public void setImages(List<String> images) {
		this.images = images;
	}


	@Override
	public String toString() {
		return "CreateProduct [title=" + title + ", price=" + price + ", description=" + description + ", categoryId="
				+ categoryId + ", images=" + images + "]";
	}
    
    
	
}
