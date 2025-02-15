package application.service;

import application.Models.Category;
import application.Models.Product;
import application.database.CategoriaAd;
import application.database.ProductoAd;

import java.io.IOException;
import java.util.List;

public class ProductImporter {
    private ProductService productService;
    private CategoriaAd categoriaAd;
    private ProductoAd productoAd;

    public ProductImporter() {
        this.productService = new ProductService();
        this.categoriaAd = new CategoriaAd();
        this.productoAd = new ProductoAd();
    }

    public List<Product> importProducts() {
        List<Product> c = null;
        try {
            // Obtener los productos desde la API
            List<Product> products = productService.getAllProductsApi();

            for (Product product : products) {
                // Comprobar si la categoría existe en la base de datos
                Category category = categoriaAd.obtenerPorId(product.idCategory());

                // Si la categoría no existe, crearla
                if (category == null) {
                    // Crear una nueva categoría usando los datos del producto
                    category = new Category(product.idCategory(), "Unknown", "default_image_url");  // Datos ficticios, puedes ajustarlos
                    int categoryId = categoriaAd.crear(category);

                    // Si la categoría se creó correctamente, se actualiza el producto con el nuevo id
                    if (categoryId > 0) {
                        product = new Product(product.id(), product.title(), product.price(), categoryId);
                    }
                }

                // Si la categoría ya existía o se creó correctamente, crear o actualizar el producto
                if (productoAd.obtenerPorId(product.id()) == null) {
                    productoAd.crear(product);
                } else {
                    productoAd.actualizar(product);
                }
            }

            // Obtener la lista de productos
            c = productoAd.obtenerTodos();
            return c;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al importar productos desde la API.");
            return null;
        }
    }
}
