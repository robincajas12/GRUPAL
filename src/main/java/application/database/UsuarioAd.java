package application.database;


import java.sql.Connection;
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
		System.out.println(item);
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


	        // Preparar la consulta de actualización
	        String sql = "UPDATE USER SET email = ?, password = ?, name = ?, role = ?, avatar = ? WHERE id = ?";

	        try (PreparedStatement pstm = Main.getConnection().prepareStatement(sql)) {
	            // Establecer los parámetros de la consulta
	            pstm.setString(1, item.email());         // Establecer el nuevo email
	            pstm.setString(2, item.password());      // Establecer la nueva contraseña (asegurándote de que esté hasheada)
	            pstm.setString(3, item.name());          // Establecer el nuevo nombre
	            pstm.setString(4, item.role() != null ? item.role() : "customer"); // Establecer el rol (o "customer" por defecto)
	            pstm.setString(5, item.avatar());        // Establecer el nuevo avatar
	            pstm.setInt(6, item.id());               // Establecer el ID del usuario a actualizar

	            // Ejecutar la consulta
	            int rowsUpdated = pstm.executeUpdate();

	            // Verificar si se actualizó alguna fila
	            return rowsUpdated > 0;  // Retorna verdadero si se actualizó al menos una fila
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Imprimir detalles del error si ocurre una excepción
	    }
	    return false;  // Retorna falso si ocurrió algún error
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


    @Override
    public User obtenerPorId(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        
        try (Connection connection = Main.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    String role = resultSet.getString("role");
                    String avatar = resultSet.getString("avatar");

                    return new User(email, name, avatar, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }

}
