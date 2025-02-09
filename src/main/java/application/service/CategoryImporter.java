package application.service;

import application.Models.Category;
import application.database.CategoriaAd;

import java.io.IOException;
import java.util.List;

public class CategoryImporter {
    private final CategoryService categoryService;
    private final CategoriaAd categoryAd;

    public CategoryImporter() {
        this.categoryService = new CategoryService();
        this.categoryAd = new CategoriaAd();
    }

    public void importCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            for (Category category : categories) {
                if (categoryAd.obtenerPorId(category.id()) == null) {
                    categoryAd.crear(category);
                } else {
                    categoryAd.actualizar(category);
                }
            }
            System.out.println("Importación de categorías completada.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al importar categorías desde la API.");
        }
    }
}
