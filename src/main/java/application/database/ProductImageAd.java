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
import application.Models.ProductImage;



public class ProductImageAd implements IAccesoDatos<ProductImage> {

    @Override
    public int crear(ProductImage item) {
        try (PreparedStatement pstm = Main.getConnection().prepareStatement(
                "INSERT INTO ProductImages(id_product, image) VALUES(?, ?);",
                Statement.RETURN_GENERATED_KEYS
        )) {
            pstm.setInt(1, item.idProduct());
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
    public boolean actualizar(ProductImage item) {
        try (PreparedStatement pstm = Main.getConnection().prepareStatement(
                "UPDATE ProductImages SET idProduct = ?, image = ? WHERE id = ?"
        )) {
            pstm.setInt(1, item.idProduct());
            pstm.setString(2, item.image());
            pstm.setInt(3, item.id());
            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        try (PreparedStatement pstm = Main.getConnection().prepareStatement(
                "DELETE FROM ProductImages WHERE id = ?"
        )) {
            pstm.setInt(1, id);
            int rowsDeleted = pstm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ProductImage> obtenerTodos() {
  
        List<ProductImage> productImages = new ArrayList<>();
        try (PreparedStatement pstm = Main.getConnection().prepareStatement(
                "SELECT id, idProduct, image FROM ProductImages"
        )) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                productImages.add(new ProductImage(
                        rs.getInt("id"),
                        rs.getInt("idProduct"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productImages;
    }

	@Override
	public ProductImage obtenerPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean eliminarPorProducto(int idProduct) {
	    try (PreparedStatement pstm = Main.getConnection().prepareStatement(
	            "DELETE FROM ProductImages WHERE id_product = ?"
	    )) {
	        pstm.setInt(1, idProduct);
	        int rowsDeleted = pstm.executeUpdate();
	        return rowsDeleted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


}
