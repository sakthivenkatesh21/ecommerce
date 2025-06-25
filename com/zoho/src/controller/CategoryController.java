package zohoincubation.com.zoho.ecommerce.src.controller;

import java.util.List;
import zohoincubation.com.zoho.ecommerce.src.model.Category;

public class CategoryController {
    private static List<Category> category = DataManager.getDataManager().getCategory();
    private static int idGenerator;

    public static boolean isCategoryEmpty() {
        return category.isEmpty();
    }

    public static boolean isCategoryExists(String categoryName) {
        for (Category obj : category) {
            if (obj.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public static List<Category> getCategories() {
        return category;
    }

    public static Category createCategory(String categoryName, String categoryDescription) {
        if (isCategoryExists(categoryName)) {
            return null;
        }
        Category newCategory = new Category(++idGenerator, categoryName, categoryDescription);
        category.add(newCategory);
        return newCategory;
    }

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
