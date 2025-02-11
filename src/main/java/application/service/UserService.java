package application.service;


import okhttp3.*;
import utilitarios.FormUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.Models.User;
import application.database.UsuarioAd;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserService {

    private static final String API_URL = "https://api.escuelajs.co/api/v1/users";
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson JSON parser
    private UsuarioAd usuarioAd = new UsuarioAd();
    // Method to fetch all users from the API and print them to the screen
    public void fetchUsers() {
        try {
            // Create the connection to the API
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Parse the response into an array of User objects using Jackson
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                UserJSON[] users = objectMapper.readValue(reader, UserJSON[].class);
            
                // Print all users to the screen
                for (UserJSON user : users) {
                    System.out.println(user);
                    User miUser = usuarioAd.obtenerPorId(user.getId());
                    if(miUser  == null){
                        usuarioAd.crear(new User(user.getId(), user.getEmail(), user.getName(), user.getAvatar(), user.getPassword()));
                    } else{
                        usuarioAd.actualizar(new User(user.getId(), user.getEmail(), user.getName(), user.getAvatar(), user.getPassword()));
                    }
                }
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get a user by ID
    public UserJSON getUserById(int userId) {
        try {
            String url = API_URL + "/" + userId;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Parse the response into a User object using Jackson
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                UserJSON user = objectMapper.readValue(reader, UserJSON.class);
                return user;
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }   
public UserJSON createUser(User user) {
    try {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Create the UserJSON object
        UserJSON newUser = new UserJSON(user.getEmail(), user.getPassword(), user.getName(), user.getAvatar());

        // Convert the UserJSON object to a JSON string
        String jsonInputString = objectMapper.writeValueAsString(newUser);
        System.out.println("Sending JSON: " + jsonInputString); // Debugging: Print the JSON payload

        // Send the JSON payload
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Check the response code
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode); // Debugging: Print the response code

        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            return objectMapper.readValue(reader, UserJSON.class);
        } else {
            // Print the error response from the server
            InputStream errorStream = connection.getErrorStream();
            if (errorStream != null) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    FormUtils.notification("User was not created", errorLine);
                    System.out.println("Error Response: " + errorLine);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public UserJSON updateUser(User updatedUser) {
    try {
        // Construct the URL for the specific user
        String url = API_URL + "/" + updatedUser.getId();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Create a UserJSON object with the updated data
        UserJSON userUpdate = new UserJSON(
            updatedUser.getEmail(),
            updatedUser.getPassword(),
            updatedUser.getName(),
            updatedUser.getAvatar()
        );
        userUpdate.setId(updatedUser.getId()); // Set the ID for the update

        // Convert the UserJSON object to a JSON string
        String jsonInputString = objectMapper.writeValueAsString(userUpdate);
        System.out.println("Sending JSON: " + jsonInputString); // Debugging: Print the JSON payload

        // Send the JSON payload
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Check the response code
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode); // Debugging: Print the response code

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Parse the response into a UserJSON object
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            return objectMapper.readValue(reader, UserJSON.class);
        } else {
            // Print the error response from the server
            InputStream errorStream = connection.getErrorStream();
            if (errorStream != null) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    FormUtils.notification("User was not updated", errorLine);
                    System.out.println("Error Response: " + errorLine);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


//--------------------------------------
    @JsonIgnoreProperties(ignoreUnknown = true) // This will ignore properties not defined in the class
static public class UserJSON {
    private int id;
    private String email;
    private String password;
    private String name;
    @JsonIgnore
    private String role;
    private String avatar;
    @JsonIgnore
    private String creationAt;  // If you need this field

    // Default constructor (required by Jackson)
    public UserJSON() {}
    public UserJSON(int id, String email, String password, String name, String role, String avatar, String creationAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.avatar = avatar;
        this.creationAt = creationAt;
    }
    

    // Constructor without the creationAt field (optional)
    public UserJSON(String email, String password, String name, String avatar) {
        this(0, email, password, name, "customer", avatar, null);  // Passing null for creationAt
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreationAt() {
        return creationAt;
    }

    public void setCreationAt(String creationAt) {
        this.creationAt = creationAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", avatar='" + avatar + '\'' +
                ", creationAt='" + creationAt + '\'' +
                '}';
    }
}

}
