package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.Models.Category;
import application.Models.Product;
import application.Models.User;

public class SearchAd {
	public static  List<Category> buscarCategoria(String secuencia) {
	    List<Category> resultado = new ArrayList<>();
	    String buscarCategoriaSQL = "SELECT id, name, image FROM Categories WHERE name LIKE ?";

	    try (PreparedStatement statement = Main.getConnection().prepareStatement(buscarCategoriaSQL)) {
	        statement.setString(1, "%" + secuencia + "%"); 
	        
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	        	
	            int id = resultSet.getInt("id");
	            String name = resultSet.getString("name");
	            String image = resultSet.getString("image");

	            resultado.add(new Category(id, name, image));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return resultado;
	}

	public static List<Product> buscarProducto(String secuencia) {
	    List<Product> resultado = new ArrayList<>();
	    String buscarProductoSQL = "SELECT id, title, price, id_category FROM Products WHERE title LIKE ?";

	    try (PreparedStatement statement = Main.getConnection().prepareStatement(buscarProductoSQL)) {
	    	statement.setString(1, "%" + secuencia + "%");

	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String title = resultSet.getString("title");
	            double price = resultSet.getDouble("price");
	            int idCategory = resultSet.getInt("id_category");

	            resultado.add(new Product(id, title, price, idCategory));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return resultado;
	}


	public static List<User> buscarUser(String secuencia) {
	    List<User> lista = new ArrayList<User>();
	    try {
	        // Prepare the SQL query with a parameterized statement
	        String query = "SELECT * FROM USER WHERE name LIKE ?";
	        PreparedStatement stm = Main.getConnection().prepareStatement(query);
	        stm.setString(1, "%" + secuencia + "%"); 

	        ResultSet res = stm.executeQuery();

	        while(res.next()) {
	            lista.add(new User(
	                    res.getInt(1),
	                    res.getString(2),
	                    res.getString(3),
	                    res.getString(4),
	                    res.getString(5),
	                    res.getString(6)
	            ));
	        }
	    } catch(SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

}
