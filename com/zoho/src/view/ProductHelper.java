package zohoincubation.com.zoho.ecommerce.src.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import zohoincubation.com.zoho.ecommerce.src.controller.ProductController;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Creatable;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Deletable;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Editable;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Execute;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Viewable;
import zohoincubation.com.zoho.ecommerce.src.model.Category;
import zohoincubation.com.zoho.ecommerce.src.model.Product;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class ProductHelper implements Execute, Creatable, Editable, Viewable, Deletable {
    private final Scanner sc;
    private final User loggedInUser;
    // private Category category;

    private final int  CLIENT = 1;
    private final int  SELLER = 2;
    

    public ProductHelper(Scanner sc, User loggedInUser) {
        this.sc = sc;
        this.loggedInUser = loggedInUser;
    }

    @Override
    public String getfunctionName() {
        return "Product Management";
    }

    @Override
    public void operation(Scanner sc, User loggedInUser) {
        while (true) {
            System.out.println("=========================================");
            System.out.println("          🛍️ Product Management Menu        ");
            System.out.println("=========================================");
            if (loggedInUser.getRole() == CLIENT) {
                System.out.println("1. 🔍 View Products");
                System.out.println("0. 🔙 Back (Exit)");
            }else if(loggedInUser.getRole() == SELLER) {
                System.out.println("1. ➕ Add Product");
                System.out.println("2. 🔍 View Product");
                System.out.println("3. ✏️ Update Product");
                System.out.println("4. ❌ Remove Product");
                if(!ProductController.getStockIsEmpty(loggedInUser))System.out.println("5. 📦 Re Stock");
                System.out.println("0. 🔙 Back (Exit)");
            }
            System.out.println("=========================================");
            System.out.print("Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        if (loggedInUser.getRole() == CLIENT)
                            view();
                        else
                            add();
                    }
                    case 2 -> view();
                    case 3 -> {if(loggedInUser.getRole() ==SELLER)update();}
                    case 4 -> {if(loggedInUser.getRole() ==SELLER)delete();}
                    case 5 -> {if(loggedInUser.getRole() == SELLER) reStock();}
                    case 0 -> {
                        System.out.println("🔙 Exiting to previous menu.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    @Override
    public void add() {
        ValidData validator = new ValidData(sc);

        Category category = CategoryHelper.getCategory(sc);
        if (category == null  ) {
            System.out.println("⚠️ No category available. Please add a category first.");
            return;
        }
        String productName = validator.name("📝 Enter a New Product Name:");
        String productDescription = validator.address("📝 Enter a New Product Description:");
        double productPrice = getPrice("💰 Enter the new Product Price:");
        int productStock = getStock("📦 Enter the new Product Stock:");
        // sc.nextLine();
        Product product = ProductController.createProduct(productName,productDescription,productPrice,productStock,category,loggedInUser);
        if (product == null) {
            System.out.println("❌ Product with the same name already exists.");
        } else {
            System.out.println("✅ Product added successfully: " + product.getProductName());
        }
    }

    @Override
    public void view() {
        if (loggedInUser.getRole() == SELLER) {
            sellerView();
        } 
        else {
            while (true) {
                if (!clientView()) {
                    System.out.println("⚠️ No products available. Please add a product first.");
                    return;
                }
                System.out.println("------------------------------------------------");
                System.out.println("Choose an option:");
                System.out.println("------------------------------------------------");
                System.out.println("1. 🔎 Search Product");
                System.out.println("2. ❤️ Add to Wish List");
                System.out.println("0. 🔙 Back (Exit)");
                System.out.println("------------------------------------------------");
                System.out.print("Enter your choice: ");
                try {
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1 ->  search();
                        case 2 ->  addWishList();
                        case 0 -> {
                            System.out.println("🔙 Exiting to previous menu.");
                            return;
                        }
                        default -> System.out.println("❌ Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Invalid input. Please enter a number.");
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("❌ An unexpected error occurred: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void update() {
        ValidData validator = new ValidData(sc);

        Product product = checkGetProduct();
        if (product != null) {
            System.out.println("📝 Current Product Details: " + product);
            System.out.println("1.Product Name \n2.Product Description \n3.Product Price \n4.Product Stock");

            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        product.setProductName(validator.name("📝 Enter New Product Name:"));
                        System.out.println("✅ Product name updated successfully: " + product.getProductName());
                    }
                    case 2 -> {
                        product.setDescription(validator.address("📝 Enter New Product Description:"));
                        System.out.println("✅ Product description updated successfully: " + product.getDescription());
                    }
                    case 3 -> {
                        product.setPrice(getPrice("💰 Enter New Product Price:"));
                        System.out.println("✅ Product price updated successfully: " + product.getPrice());
                    }
                    case 4 -> {
                        product.setStock(getStock("📦 Enter New Product Stock:"));
                        System.out.println("✅ Product stock updated successfully: " + product.getStock());
                    }
                    default -> {
                        System.out.println("❌ Invalid choice. Please try again.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        } else {
                System.out.println("❌ Product not found or could not be updated.");
        }
    }

    
    @Override
    public void delete() {

        Product product = checkGetProduct();
        if (product != null) {
            if (ProductController.removeProduct(product)) {
                System.out.println("✅ Product removed successfully: " + product.getProductName());
            } else {
                System.out.println("❌ Product not found or could not be removed.");
            }
        }
    }


// Common logic For Update and Delete
    private Product checkGetProduct() {

        Category category = CategoryHelper.getCategory(sc);
        if (category == null || category.getProduct().isEmpty()) {
            System.out.println("⚠️ No product is available.");
            return null;
        }
        showCategoryProducts(category);
        System.out.println("🆔 Enter the Product ID  or 0 to exit:");
        int productId = sc.nextInt();
        sc.nextLine();
        if (productId == 0) {
            System.out.println("🔙 Exiting to previous menu.");
            return null;
        } else {
            Product product = ProductController.isProductExist(productId);
            if (product != null && category.getProduct().contains(product)) {
                return product;
            } else {
                System.out.println("❌ Product not found in the selected category.");
                return null;
            }
        }
    }

    private void search() {
        System.out.println("🔎 Enter a Product Name to search:");
        String productName = sc.nextLine();
        List<Product> product = ProductController.isProductExists(productName);
        if (product == null) {
            System.out.println("❌ Product not found. Please try again.");
            return;
        }
        for (Product obj : product) {
            System.out.println("✅ Product found in Category: " + obj.getCategory().getName());
            System.out.println("📝 Product Details: \n  " + obj);
        }
        // System.out.println("****************************************");
        // System.out.println("*                                      *");
        System.out.println(" *        🔎 Product Search Options    *" );
        // System.out.println("*                                      *");
        // System.out.println("****************************************");
        System.out.println("****************************************");
        System.out.println("1. ❤️ Add to Wish List");
        System.out.println("2. 🔎 Search Product Again");
        System.out.println("3. 🔙 Back (Exit)");
        System.out.println("****************************************");
        System.out.print("Enter your choice: ");
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    if (!addingProductToCart())
                        System.out.println("🔙 Exiting to previous menu.");
                }
                case 2 -> search();
                default -> System.out.println("🔙 Exiting to previous menu.");
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
        }
    }

    private void addWishList() {
      
        while (true) {
            // showCategoryProducts(category);
            if(clientView()){
                if (!addingProductToCart()) {
                    System.out.println("🔙 Exiting to previous menu.");
                    return;
                }
            }      
        }
    }

    private boolean addingProductToCart() {
        System.out.println("🆔 Enter the Product ID to add to cart or 0 to exit:");
        int productId = sc.nextInt();
        sc.nextLine();
        if (productId == 0) return false;
        Product product = ProductController.isProductExist(productId);
        if (product == null )  {
            System.out.println("❌ Product not found in the selected category.");
            return false;
        }
        else if(product.isAvailableStock()) {
            System.out.println("⚠️ Product is out of stock.");
            return false;
        }
        // category = product.getCategory();
        System.out.println("✅ Product found: \n" + product);
        WishlistHandler addCard = new WishlistHandler(product, sc, loggedInUser);
        addCard.add();
        return true;
    }

    private  boolean clientView() {
        List<Product> product = ProductController.getProducts();
        if (product == null) return  false;
        System.out.println("🛍️ Available Products:");
        System.out.println("------------------------------------------------");
        for (Product obj : product) {
            System.out.println(obj);
        }
        return true;
    }

    private void  sellerView() {
        Category category = CategoryHelper.getCategory(sc);
        if (category == null || category.getProduct().isEmpty()) {
            System.out.println("⚠️ No product is available.");
            return;
        }
        showCategoryProducts(category);
    }
        // for view    the Category Vise Products (For Seller and Client)
        private void showCategoryProducts(Category category) {
            System.out.println("🛍️ Products in Category: " + category.getName() + "\n");
            if (loggedInUser.getRole() == SELLER) {
                List<Product> product = ProductController.getSellerProducts(category, loggedInUser);
                printCategoryProducts(product);    
            } else if (loggedInUser.getRole() == CLIENT) {
                   printCategoryProducts(category.getProduct());    
            }
        }
        // helper method for show Category products
        private void printCategoryProducts(List<Product> products) {
            if (products.isEmpty()) {
                System.out.println("⚠️ No products available in this category.");
                return;
            }
            for (Product product : products) {
                System.out.println(product);
            }
        }


       // input validation for product price(Reuse Methods for update and add)
    private double  getPrice(String info){
        System.out.println(info);
        double productPrice = sc.nextDouble();
        while (productPrice < 0) {
            System.out.println("❌ Invalid price! Please enter a valid price greater than 0.");
            productPrice = sc.nextDouble();
        }
        sc.nextLine();
        return productPrice;
    }
// input validation for product Stock(Reuse Methods for update and add)
    private  int getStock(String info){
        
        System.out.println(info);
        int productStock = sc.nextInt();
        while (productStock <= 0 || productStock > 100) {
            System.out.println("❌ Invalid stock! Please enter a valid stock between 0 and 100.");
            productStock = sc.nextInt();
        }
        sc.nextLine();
        return productStock;
    }
// restock the Seller product 
    private void reStock() { 
        List<Product> product = ProductController.getEmptyStockProducts(loggedInUser);
        while(true){
            System.out.println("1. 📦 Restock  Products\n2.View ReStock \n0.Exit");
            System.out.print("👉 Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1->  reStockAll(product);
                    case 2->  viewReStockk(product);
                    case 0->  {
                        System.out.println("🔙 Exiting to previous menu.");
                        return;
                    }
                    default-> {
                        System.out.println("❌ Invalid choice. Please try again.");
                        return;    
                    }
                }
            }catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
            catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    } 
    private void  reStockAll(List<Product> product) {
        while(true){
            viewReStockk(product);
            System.out.println("Enter a Product Id to update the stock or 0 to exit:");
            int productId = sc.nextInt();
            sc.nextLine();
            if (productId == 0) {
                System.out.println("🔙 Exiting to previous menu.");
                return;
            }
            Product selectedProduct = ProductController.isProductExist(productId);
            if (selectedProduct == null || !product.contains(selectedProduct)) {
                System.out.println("❌ Product not found in the selected category.");
                return;
            }
            selectedProduct.setStock(getStock("📦 Update  the  Product Stock for " + selectedProduct.getProductName() + ":"));
            System.out.println("✅ Product " + selectedProduct.getProductName() + " stock updated successfully to: " + selectedProduct.getStock());   
        }
       
    }
    private void  viewReStockk(List<Product> product) {  
        System.out.println("📦 Products available for restocking:");
        System.out.println("------------------------------------------------");
        for (Product obj : product) {
           System.out.println(" Product Id :"+obj.getId() +"Product Name :"+ obj.getProductName() +"  Stock :"+ obj.getStock());
        }
        System.out.println("------------------------------------------------");

    }
}
