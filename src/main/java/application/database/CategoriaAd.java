package application.database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.Models.Category;
import application.Models.IAccesoDatos;



public class CategoriaAd implements IAccesoDatos<Category> {
	@Override
	public int crear(Category item) {
	    try (PreparedStatement pstm = Main.getConnection().prepareStatement(
	            "INSERT INTO Categories(name, image) VALUES(?, ?);",
	            Statement.RETURN_GENERATED_KEYS
	    )) {
	        pstm.setString(1, item.name());
	        pstm.setString(2, item.image());

	        int affectedRows = pstm.executeUpdate();
	        if (affectedRows == 0) {
	            return -1; 
	        }

	        try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1); 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; 
	}


    @Override
    public boolean actualizar(Category item) {
        try {
            PreparedStatement pstm = Main.getConnection().prepareStatement("UPDATE Categories SET name = ?, image = ? WHERE id = ?");
            pstm.setString(1, item.name());
            pstm.setString(2, item.image());
            pstm.setInt(3, item.id());
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
            PreparedStatement pstm = Main.getConnection().prepareStatement("DELETE FROM Categories WHERE id = ?");
            pstm.setInt(1, id);
            pstm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Category> obtenerTodos() {
        List<Category> lista = new ArrayList<>();
        try {
            Statement stm = Main.getConnection().createStatement();
            if (stm.execute("SELECT * FROM Categories")) {
                ResultSet res = stm.getResultSet();
                while (res.next()) {
                    lista.add(new Category(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("image")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

