package zohoincubation.com.zoho.ecommerce.src.controller;

import java.util.ArrayList;
import java.util.List;
import zohoincubation.com.zoho.ecommerce.src.model.Category;
import zohoincubation.com.zoho.ecommerce.src.model.Order;
import zohoincubation.com.zoho.ecommerce.src.model.Product;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class DataManager {

    private DataManager() {
        user = new ArrayList<>();
        orders = new ArrayList<>();
        product = new ArrayList<>();
        category = new ArrayList<>();
    }

    private final List<User> user;
    private final List<Order> orders;
    private final List<Product> product;
    private final List<Category> category;
    private static DataManager dataManager;

    public static DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public List<User> getUser() {
        return user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Product> getProduct() {
        return product;
    }

    public List<Category> getCategory() {
        return category;
    }


    
}
