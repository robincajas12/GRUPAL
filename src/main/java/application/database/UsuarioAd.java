package application.database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.Models.IAccesoDatos;
import application.Models.User;



public class UsuarioAd implements IAccesoDatos<User>{

	@Override
	public int crear(User item) {
	    try (PreparedStatement pstm = Main.getConnection().prepareStatement(
	            "INSERT INTO USER(email, password, name, role, avatar) VALUES(?,?,?,?,?);",
	            Statement.RETURN_GENERATED_KEYS
	    )) {
	        pstm.setString(1, item.email());
	        pstm.setString(2, item.password());
	        pstm.setString(3, item.name());
	        pstm.setString(4, item.role() != null ? item.role() : "customer");
	        pstm.setString(5, item.avatar());

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
	public boolean actualizar(User item) {
		try {
			PreparedStatement pstm = Main.getConnection().prepareStatement("UPDATE USER SET email = ? ,password = ?, name = ?, role = ?, avatar = ? where id = ?");
			 pstm.setString(1, item.email());
	         pstm.setString(2, item.password());
	         pstm.setString(3, item.name());  
	         pstm.setString(4, item.role() != null ? item.role() : "customer"); 
	         pstm.setString(5, item.avatar()); 
	         pstm.setInt(6, item.id());
	         pstm.execute();
	         return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean eliminar(int id) {
	    try {
	        PreparedStatement pstm = Main.getConnection().prepareStatement("DELETE FROM USER WHERE id = ?");
	        pstm.setInt(1, id);
	        pstm.execute();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	@Override
	public List<User> obtenerTodos() {
		List<User> lista = new ArrayList<User>();
		try {
			Statement stm = Main.getConnection().createStatement();
			if(stm.execute("SELECT * FROM USER")) {
				ResultSet res = stm.getResultSet();
				while(res.next())
				{
					lista.add(new User(
							res.getInt(1),
							res.getString(2),
							res.getString(3),
							res.getString(4),
							res.getString(5),
							res.getString(6)
					));
				}
			}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return lista;
	}

}
