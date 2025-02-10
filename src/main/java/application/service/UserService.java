package application.service;


import okhttp3.*;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.Models.User;

public class UserService {
   package application.Services;

import application.Models.User;
import application.DAO.UserDAO;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final RestTemplate restTemplate = new RestTemplate();

    // API URL for Escuelajs
    private static final String API_URL = "https://api.escuelajs.co/api/v1/users";

    // Synchronize user data by checking local DB first, then sync with API
    public User syncUser(int userId) {
        // Step 1: Try to get the user from the local database
        User localUser = userDAO.obtenerPorId(userId);
        if (localUser != null) {
            // If user exists in local DB, no need to call the API
            return localUser;
        }

        // Step 2: If user doesn't exist in the local DB, fetch it from the API
        User apiUser = getUserFromApi(userId);
        if (apiUser != null) {
            // Step 3: Insert the fetched user into the local DB
            userDAO.crear(apiUser);
            return apiUser;
        }

        // If no user is found, return null or throw an exception
        System.out.println("Could not sync user with ID " + userId);
        return null;
    }

    // Fetch a user from the API
    private User getUserFromApi(int userId) {
        try {
            String url = API_URL + "/" + userId;
            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, null, User.class);
            return response.getBody();
        } catch (Exception e) {
            // Handle EntityNotFoundError or other exceptions
            if (e.getMessage().contains("EntityNotFoundError")) {
                System.out.println("User with ID " + userId + " not found.");
            } else {
                e.printStackTrace();
            }
            return null;
        }
    }

    // Delete a user (both in the API and the local DB)
    public boolean deleteUser(int userId) {
        try {
            // Step 1: Delete the user from the API
            String url = API_URL + "/" + userId;
            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
            
            // Step 2: Delete the user from the local database
            return userDAO.eliminar(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create and sync a user
    public User createUser(User user) {
        User createdUser = createUserInApi(user);
        if (createdUser != null) {
            userDAO.crear(createdUser);
            return createdUser;
        }
        return null;
    }

    private User createUserInApi(User user) {
        try {
            String url = API_URL + "/";
            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, null, User.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

}
