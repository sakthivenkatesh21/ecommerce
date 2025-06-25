package zohoincubation.com.zoho.ecommerce.src.controller;

import java.util.ArrayList;
import java.util.List;
import zohoincubation.com.zoho.ecommerce.src.model.CardProduct;
import zohoincubation.com.zoho.ecommerce.src.model.Category;
import zohoincubation.com.zoho.ecommerce.src.model.Product;
import zohoincubation.com.zoho.ecommerce.src.model.Seller;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class ProductController {
    private static int idGenerator;
    private static final List<Product> products = DataManager.getDataManager().getProduct();

    public static Product createProduct(String productName, String productDescription, double price, int stock, Category category, User loggedInUser) {
        if (!products.isEmpty() && isProductExists(productName,loggedInUser) != null) {
            return null;
        }
        Product newProduct = new Product(++idGenerator, productName, productDescription, price, stock, category, (Seller) loggedInUser);
        products.add(newProduct);
        category.getProduct().add(newProduct);
        ((Seller)loggedInUser).getSellerProduct().add(newProduct);
        return newProduct;
    }

    public static boolean updateProduct(int id, String productName, String productDescription, double price, int stock) {
        Product product = isProductExist(id);
        if (product != null) {
            product.setProductName(productName);
            product.setDescription(productDescription);
            product.setPrice(price);
            product.setStock(stock);
            return true;
        }
        return false;
    }

    public static boolean removeProduct(Product product) {
        if (product != null) {
            products.remove(product);
            Category category = product.getCategory();
            if (category != null) {
                category.getProduct().remove(product);
                return true;
            }
        }
        return false;
    }

    public static Product isProductExist(int productId) {
        for (Product obj : products) {
            if (obj.getId() == productId) {
                return obj;
            }
        }
        return null;
    }

    public static Product isProductExists(String productName, User loggedInUser) {
        for (Product obj : products) {
            if (obj.getProductName().equals(productName) && obj.getSeller().getId() == loggedInUser.getId()) {
                return obj;
            }
        }
        return null;
    }
    public static List<Product> isProductExists(String productName) {
        List<Product> searchProducts = new ArrayList<>();
        for (Product obj : products) {
            if (obj.getProductName().equals(productName)) {
                searchProducts.add(obj);
            }
        }
        return searchProducts.isEmpty() ? null : searchProducts;
    }
      

    public static boolean removeProductByCategory(List<Product> productList) {
        int removeCount = 0;
        int  size = productList.size();
        for (Product obj : productList) {
            if (products.contains(obj)) {
                products.remove(obj);
                removeCount++;
            }
         }
        return removeCount == size;
      
    }
    public static List<Product> getSellerProducts(Category category , User loggedInUser) {
        List<Product> selleProducts = new ArrayList<>();
         for (Product obj : category.getProduct()) {
            if (obj.getSeller().getId() == ((Seller) loggedInUser).getId()) {
                selleProducts.add(obj);
            }
        }
        return selleProducts;
    }
    public static   boolean isProductExistCard(List<CardProduct> product , int id) {
        for (CardProduct obj : product) {
            if (obj.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public static List<Product> getProducts() {
        if (products.isEmpty()) {
            return null;
        }
        return products;
    }

    public static  boolean  reduceStock(List<CardProduct> cardProducts){
        for(CardProduct obj : cardProducts){
            Product product = isProductExist(obj.getId());
            if(product==null){
                return false;
            }
            product.setStock(product.getStock() - obj.getQuantity());
        }
        return true;
    }

    public  static boolean  getStockIsEmpty(User loggedInUser) {
        
        for (Product product : products) {
           if(product.getSeller().getId() == loggedInUser.getId() && product.isAvailableStock()){   
                return false;
           }
        }
        return true;
    }
    public static List<Product>  getEmptyStockProducts(User loggedInUser) {
        List<Product> emptyStockProducts = new ArrayList<>();
        for (Product product : products) {
            if(product.getSeller().getId() == loggedInUser.getId() && product.isAvailableStock()) {
                emptyStockProducts.add(product);
            }          
        }
        return  emptyStockProducts;
    }      
}
