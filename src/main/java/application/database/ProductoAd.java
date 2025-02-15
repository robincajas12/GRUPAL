package application.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.Models.Category;
import application.Models.CompleteProductSchema;
import application.Models.IAccesoDatos;
import application.Models.Product;
import okhttp3.Request;
import okhttp3.Response;



public class ProductoAd implements IAccesoDatos<Product> {
	@Override
	public int crear(Product item) {
	    try {
	        PreparedStatement pstm = Main.getConnection().prepareStatement(
	            "INSERT INTO Products(id, title, price, id_category) VALUES(?, ?, ?, ?);",
	            Statement.RETURN_GENERATED_KEYS
	        );
	        pstm.setInt(1, item.getId());
	        pstm.setString(2, item.title());
	        pstm.setDouble(3, item.price());
	        pstm.setInt(4, item.idCategory());
	        
	        int affectedRows = pstm.executeUpdate();
	        if (affectedRows == 0) {
	            return -1; 
	        }


	        return 1;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; 
	}


    @Override
    public boolean actualizar(Product item) {
        try {
            PreparedStatement pstm = Main.getConnection().prepareStatement("UPDATE Products SET title = ?, price = ?, id_category = ? WHERE id = ?");
            pstm.setString(1, item.title());
            pstm.setDouble(2, item.price());
            pstm.setInt(3, item.idCategory());
            pstm.setInt(4, item.id());
            pstm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        try {
            PreparedStatement pstm = Main.getConnection().prepareStatement("DELETE FROM Products WHERE id = ?");
            pstm.setInt(1, id);
            pstm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> obtenerTodos() {
        List<Product> lista = new ArrayList<>();
        try {
            Statement stm = Main.getConnection().createStatement();
            if (stm.execute("SELECT * FROM Products")) {
                ResultSet res = stm.getResultSet();
                while (res.next()) {
                    lista.add(new Product(
                        res.getInt("id"),
                        res.getString("title"),
                        res.getDouble("price"),
                        res.getInt("id_category")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

	
	public List<CompleteProductSchema> getFullSchema() {
		
		List<CompleteProductSchema> lista = new ArrayList<>();
	    Map<Integer, CompleteProductSchema> productMap = new HashMap<>();

	    String query = "SELECT p.id, p.title, p.price, " +
	                   "c.id AS category_id, c.name AS category_name, " +
	                   "pi.image " +
	                   "FROM Products p " +
	                   "LEFT JOIN Categories c ON p.id_category = c.id " +
	                   "LEFT JOIN ProductImages pi ON p.id = pi.id_product";

	    try (PreparedStatement pstm = Main.getConnection().prepareStatement(query);
	         ResultSet res = pstm.executeQuery()) {

	        while (res.next()) {
	            int productId = res.getInt("id");

	            if (!productMap.containsKey(productId)) {
	                CompleteProductSchema product = new CompleteProductSchema();
	                product.setId(productId);
	                product.setTitle(res.getString("title"));
	                product.setPrice(res.getInt("price"));
	                product.setDescription("");
	                
	                Category category = new Category(res.getInt("category_id"), res.getString("category_name"));
	                product.setCategory(category);

	                product.setImages(new ArrayList<>());
	                productMap.put(productId, product);
	            }

	            String imageUrl = res.getString("image");
	            if (imageUrl != null) {
	                productMap.get(productId).getImages().add(imageUrl);
	            }
	        }

	        lista.addAll(productMap.values());

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
		
		
	}


	@Override
	public Product obtenerPorId(int id) {
		String query = "SELECT * FROM Products WHERE id = ?";
        
        try (Connection connection = Main.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    double price = resultSet.getDouble("price");
                    int id_category = resultSet.getInt("id_category");

                    return new Product(id, title, price, id_category);
                }
            }
        } catch (SQLException e) {
        	return null;
//        	e.printStackTrace();
        }
        return null;
    }
}
