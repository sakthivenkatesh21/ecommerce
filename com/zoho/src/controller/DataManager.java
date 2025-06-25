package com.zoho.src.controller;

import com.zoho.src.model.Category;
import com.zoho.src.model.Order;
import com.zoho.src.model.Product;
import com.zoho.src.model.User;
import java.util.ArrayList;
import java.util.List;

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
