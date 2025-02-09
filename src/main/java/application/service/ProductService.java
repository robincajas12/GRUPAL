package application.service;
import okhttp3.*;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.Models.CompleteProductSchema;
import application.Models.Product;

public class ProductService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://api.escuelajs.co/api/v1/products";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ProductService() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // Método para obtener todos los productos
    public List<CompleteProductSchema> getAllProducts() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, CompleteProductSchema.class));
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // Método para obtener un producto por ID
    public CompleteProductSchema getProductById(int productId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + productId)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), CompleteProductSchema.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // Método para crear un nuevo producto
    public CompleteProductSchema createProduct(CompleteProductSchema product) throws IOException {
        String json = objectMapper.writeValueAsString(product);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), CompleteProductSchema.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // Método para actualizar un producto
    public CompleteProductSchema updateProduct(int productId, CompleteProductSchema product) throws IOException {
        String json = objectMapper.writeValueAsString(product);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + productId)
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), CompleteProductSchema.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // Método para eliminar un producto
    public boolean deleteProduct(int productId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + productId)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }
}