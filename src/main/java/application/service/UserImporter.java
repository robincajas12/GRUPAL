package application.service;

import application.Models.User;
import java.io.IOException;
import java.util.List;
import application.database.UsuarioAd;
public class UserImporter {
    private  UserService userService;
    private  UsuarioAd userAd;

    public UserImporter() {
        this.userService = new UserService();
        this.userAd = new UsuarioAd();
    }

    public void importUsers() {
        try {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                if (userAd.obtenerPorId(user.id()) == null) { 
                    userAd.crear(user);
                } else {
                    userAd.actualizar(user);
                }
            }
            System.out.println("Importación de usuarios completada.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al importar usuarios desde la API.");
        }
    }
}
