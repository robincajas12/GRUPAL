package application.service;


import application.Models.Category;
import application.Models.CompleteProductSchema;
import application.Models.Product;
import application.Models.ProductImage;
import application.database.*;

import java.io.IOException;
import java.util.List;

public class ProductImporter {
    private ProductService productService;
    private CategoriaAd categoriaAd;
    private ProductoAd productoAd;
    private ProductImageAd productImageAd;

    public ProductImporter() {
        this.productService = new ProductService();
        this.categoriaAd = new CategoriaAd();
        this.productoAd = new ProductoAd();
        this.productImageAd = new ProductImageAd();
    }

    public void importProducts() {
        try {
            // Obtener todos los productos de la API
            List<CompleteProductSchema> products = productService.getAllProducts();
            for (CompleteProductSchema product : products) {
                // Verificar si el producto ya existe en la base de datos
                if (productoAd.obtenerPorId(product.getId()) == null) {
                    // Guardar la categoría si no existe
                    Category category = product.getCategory();
                    if (categoriaAd.obtenerPorId(category.getId()) == null) {
                        categoriaAd.crear(category);
                    }

                    // Guardar el producto
                    Product productToSave = new Product(
                            product.getId(),
                            product.getTitle(),
                            product.getPrice(),
                            category.getId()
                    );
                    productoAd.crear(productToSave);

                    // Guardar las imágenes del producto
                    List<String> images = product.getImages();
                    for (String imageUrl : images) {
                        ProductImage imageToSave = new ProductImage(
                                0, // El ID se generará automáticamente
                                product.getId(),
                                imageUrl
                        );
                        productImageAd.crear(imageToSave);
                    }
                } else {
                    // Actualizar el producto si ya existe
                    Product productToUpdate = new Product(
                            product.getId(),
                            product.getTitle(),
                            product.getPrice(),
                            product.getCategory().getId()
                    );
                    productoAd.actualizar(productToUpdate);

                    // Actualizar las imágenes del producto (eliminar las antiguas y agregar las nuevas)
                    productImageAd.eliminarPorProducto(product.getId());
                    List<String> images = product.getImages();
                    for (String imageUrl : images) {
                        ProductImage imageToSave = new ProductImage(
                                0, // El ID se generará automáticamente
                                product.getId(),
                                imageUrl
                        );
                        productImageAd.crear(imageToSave);
                    }
                }
            }
            System.out.println("Importación de productos completada.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al importar productos desde la API.");
        }
    }
}