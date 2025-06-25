package com.zoho.src.controller;

import com.zoho.src.model.Category;
import java.util.List;

public class CategoryController {
    private static final List<Category> category = DataManager.getDataManager().getCategory();
    private static int idGenerator;

    
    public static boolean isCategoryEmpty() {
        return category.isEmpty();
    }
// check if duplicate Category exists by name
    public static boolean isCategoryExists(String categoryName) {
        for (Category obj : category) {
            if (obj.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }
// get all Categories
    public static List<Category> getCategories() {
        return category;
    }
// creating Category
    public static Category createCategory(String categoryName, String categoryDescription) {
        if (isCategoryExists(categoryName)) {
            return null;
        }
        Category newCategory = new Category(++idGenerator, categoryName, categoryDescription);
        category.add(newCategory);
        return newCategory;
    }
// rempve Category and its products
    public static boolean removeCategory(Category obj) {
        if (obj != null) {
            category.remove(obj);
            if(obj.getProduct() == null || obj.getProduct().isEmpty()) {
                return true;
            }
            return ProductController.removeProductByCategory(obj.getProduct());
        }
        return false;
    }
}
