package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.Models.IAccesoDatos;
import application.Models.Product;



public class ProductoAd implements IAccesoDatos<Product> {
	@Override
	public int crear(Product item) {
	    try {
	        PreparedStatement pstm = Main.getConnection().prepareStatement(
	            "INSERT INTO Products(title, price, id_category) VALUES(?, ?, ?);",
	            Statement.RETURN_GENERATED_KEYS
	        );
	        pstm.setString(1, item.title());
	        pstm.setDouble(2, item.price());
	        pstm.setInt(3, item.idCategory());
	        
	        int affectedRows = pstm.executeUpdate();
	        if (affectedRows == 0) {
	            return -1; 
	        }

	 
	        ResultSet generatedKeys = pstm.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int id = generatedKeys.getInt(1); 
	            return id;
	        }
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
}
