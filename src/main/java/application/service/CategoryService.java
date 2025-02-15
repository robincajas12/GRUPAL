package application.service;

import okhttp3.*;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.Models.Category;
import application.createData.CreateCategory;

public class CategoryService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://api.escuelajs.co/api/v1/categories";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public CategoryService() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // Método para obtener todas las categorías
    public List<Category> getAllCategories() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Category.class));
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }
   
    // Método para obtener una categoría por ID
    public Category getCategoryById(int categoryId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + categoryId)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), Category.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // Método para crear una nueva categoría
    public Category createCategory(CreateCategory category) throws IOException {
        String json = objectMapper.writeValueAsString(category);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), Category.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // Método para actualizar una categoría
    public Category updateCategory(int categoryId, Category category) throws IOException {
        String json = objectMapper.writeValueAsString(category);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + categoryId)
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), Category.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // Método para eliminar una categoría
    public boolean deleteCategory(int categoryId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + categoryId)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }
}