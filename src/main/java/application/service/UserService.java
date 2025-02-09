package application.service;


import okhttp3.*;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.Models.User;

public class UserService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://api.escuelajs.co/api/v1/users";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public UserService() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<User> getAllUsers() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    public User getUserById(int userId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + userId)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), User.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    public User createUser(User user) throws IOException {
        String json = objectMapper.writeValueAsString(user);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), User.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    public User updateUser(int userId, User user) throws IOException {
        String json = objectMapper.writeValueAsString(user);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + userId)
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), User.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    public boolean deleteUser(int userId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + userId)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }
}