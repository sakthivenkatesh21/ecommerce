package zohoincubation.com.zoho.ecommerce.src.view;


import java.util.List;
import zohoincubation.com.zoho.ecommerce.src.controller.CategoryController;
import zohoincubation.com.zoho.ecommerce.src.controller.DataManager;
import zohoincubation.com.zoho.ecommerce.src.controller.ProductController;
import zohoincubation.com.zoho.ecommerce.src.controller.UserController;
import zohoincubation.com.zoho.ecommerce.src.model.Category;
import zohoincubation.com.zoho.ecommerce.src.model.Product;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class InputProduct {
    private static List<Product> productList = DataManager.getDataManager().getProduct();
    private static List<User> userList = DataManager.getDataManager().getUser();
    private static List<Category> categoryList = DataManager.getDataManager().getCategory();
    public  InputProduct() {
        
        createCategoryData();
        createSeller();
        createProductData();
    }
    public  static   void  createProductData() {
        // Products by User 1
        ProductController.createProduct( "Poco X7 Smartphone", "Latest model smartphone", 800.00, 20, categoryList.get(0), userList.get(0));
        ProductController.createProduct( "Boat Headphones", "Noise-cancelling headphones", 200.00, 15, categoryList.get(0), userList.get(0));
        ProductController.createProduct( "Dell Inspiron Laptop", "15-inch laptop with SSD", 1000.00, 12, categoryList.get(0), userList.get(0));
        ProductController.createProduct( "Sony WH-1000XM4", "Premium noise-cancelling headphones", 350.00, 12, categoryList.get(0), userList.get(0));
        ProductController.createProduct( "JBL Flip 5", "Portable Bluetooth speaker", 100.00, 25, categoryList.get(0), userList.get(0));
        ProductController.createProduct( "Fossil Smartwatch", "Stylish smartwatch with fitness tracking", 250.00, 18, categoryList.get(2), userList.get(0));

        // Products by User 2
        ProductController.createProduct( "Apple Smartwatch", "Feature-rich smartwatch", 300.00, 25, categoryList.get(0), userList.get(1));
        ProductController.createProduct( "Samsung Galaxy Tab", "High-performance tablet", 600.00, 30, categoryList.get(0), userList.get(1));
        ProductController.createProduct( "HP Pavilion Laptop", "14-inch laptop with touchscreen", 1100.00, 10, categoryList.get(0), userList.get(1));
        ProductController.createProduct( "Kindle Paperwhite", "E-reader with adjustable light", 130.00, 22, categoryList.get(4), userList.get(1));
        ProductController.createProduct( "Fitbit Charge 4", "Fitness tracker with GPS", 150.00, 20, categoryList.get(2), userList.get(1));
        ProductController.createProduct( "Asus ROG Gaming Laptop", "High-performance gaming laptop", 2500.00, 6, categoryList.get(0), userList.get(1));

    // Products by User 3
        ProductController.createProduct("Nike Running Shoes", "Comfortable and durable running shoes", 120.00, 30, categoryList.get(6), userList.get(2));
        ProductController.createProduct("Philips Air Fryer", "Healthy cooking appliance", 150.00, 20, categoryList.get(3), userList.get(2));
        ProductController.createProduct("Ray-Ban Sunglasses", "Stylish sunglasses for outdoor use", 200.00, 15, categoryList.get(4), userList.get(2));
        ProductController.createProduct("Samsung Refrigerator", "Energy-efficient refrigerator", 800.00, 10, categoryList.get(3), userList.get(2));
        ProductController.createProduct("Loreal Shampoo", "Hair care product", 15.00, 50, categoryList.get(7), userList.get(2));
        ProductController.createProduct("Adidas Sports Jersey", "High-quality sports jersey", 50.00, 25, categoryList.get(6), userList.get(2));
        ProductController.createProduct("The Alchemist", "Inspirational book by Paulo Coelho", 10.00, 40, categoryList.get(5), userList.get(2));
        ProductController.createProduct("Sony PlayStation 5", "Next-gen gaming console", 500.00, 8, categoryList.get(0), userList.get(2));
        // Products by User 4

        ProductController.createProduct("Canon DSLR Camera", "High-quality DSLR camera", 1200.00, 10, categoryList.get(0), userList.get(3));
        ProductController.createProduct("Leather Wallet", "Premium leather wallet", 50.00, 20, categoryList.get(1), userList.get(3));
        ProductController.createProduct("Apple AirPods Pro", "Wireless noise-cancelling earbuds", 250.00, 15, categoryList.get(2), userList.get(3));
        ProductController.createProduct("Dyson Vacuum Cleaner", "Powerful and efficient vacuum cleaner", 400.00, 12, categoryList.get(3), userList.get(3));
        ProductController.createProduct("Gucci Handbag", "Luxury designer handbag", 1500.00, 5, categoryList.get(4), userList.get(3));
        ProductController.createProduct("Harry Potter Series", "Complete book series by J.K. Rowling", 100.00, 30, categoryList.get(5), userList.get(3));
        ProductController.createProduct("Wilson Tennis Racket", "Professional-grade tennis racket", 200.00, 10, categoryList.get(6), userList.get(3));
        ProductController.createProduct("Maybelline Lipstick", "Long-lasting lipstick", 20.00, 50, categoryList.get(7), userList.get(3));
    }
    public static void createCategoryData() {
        CategoryController.createCategory( "Electronics", "Devices and gadgets");
        CategoryController.createCategory( "Accessories", "Additional items for devices");
        CategoryController.createCategory( "Wearables", "Smart wearable technology");
        CategoryController.createCategory( "Home Appliances", "Appliances for home use");
        CategoryController.createCategory( "Fashion", "Clothing and accessories");
        CategoryController.createCategory( "Books", "Printed and digital books");
        CategoryController.createCategory( "Sports", "Sports equipment and apparel");
        CategoryController.createCategory( "Beauty", "Beauty and personal care products");
    }
    public static void createSeller() {
        UserController.createUser("Karthi","9025149404","karthi@gmail.com","Karthi@1","Male","Karthi Enterprises","Chennai");
        UserController.createUser("Mani", "6869585858", "mani@gmail.com", "Mani@1", "Male", "Mani Industries", "Coimbatore");
        UserController.createUser("Tanishka", "9585859685", "tanishka@gmail.com", "Tanishka@1", "Female", "Tanishka Creations", "Bangalore");
        UserController.createUser("Muya", "8567890678", "muya@gmail.com", "Muya@1", "Female", "Muya Fashion", "Mumbai");      
    }
}
