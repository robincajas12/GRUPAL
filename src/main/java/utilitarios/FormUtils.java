package utilitarios;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FormUtils {

    public static <T> boolean verificarCamposNulos(T item) {
        if (item == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Objeto no selecionado");
            alert.setContentText("Selecione un item por favor.");
            alert.showAndWait();
            return false; 
        }
        return true;
    }
}

