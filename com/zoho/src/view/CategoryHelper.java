package zohoincubation.com.zoho.ecommerce.src.view;

import java.util.InputMismatchException;
import java.util.Scanner;
import zohoincubation.com.zoho.ecommerce.src.controller.CategoryController;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Creatable;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Deletable;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Editable;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Execute;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Viewable;
import zohoincubation.com.zoho.ecommerce.src.model.Category;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class CategoryHelper implements Execute, Creatable, Editable, Viewable, Deletable {
    private final Scanner sc;
    private final User loggedInUser;

    private final int CLIENT = 1;
    private final int SELLER = 2;

    public CategoryHelper(Scanner sc, User loggedInUser) {
        this.sc = sc;
        this.loggedInUser = loggedInUser;
    }

    @Override
    public String getfunctionName() {
        return "Category Management";
    }

    @Override
    public void operation(Scanner sc, User loggedInUser) {
        System.out.println("🌟✨ Welcome to Category Management ✨🌟");
        System.out.println("👤    Name: " + loggedInUser.getName());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        while (true) {
            try {
                if (loggedInUser.getRole() == CLIENT) {
                    view();
                    return;
                    // System.out.println("1️⃣ View Categories Products \n0️⃣ Exit");
                } else if (loggedInUser.getRole() == SELLER) {
                    System.out.println("1️⃣ Add Category \n2️⃣ View Categories \n3️⃣ Delete Category \n4️⃣ Update Category \n0️⃣ Exit");
                }
                System.out.println("🔢 Enter your choice:");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> add();
                    case 2 -> view();
                    case 3 -> delete();
                    case 4 -> update();
                    case 0 -> {
                        System.out.println("🚪 Exiting Category Management. Goodbye! 👋");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
                sc.nextLine();
            }
        }
    }

    @Override
    public void add() {
        ValidData validData = new ValidData(sc);
        String categoryName = validData.name("📝 Enter the Category Name:");
        String categoryDescription = validData.address("📝 Enter the Category Description:");
        if (CategoryController.createCategory(categoryName, categoryDescription) != null) {
            System.out.println("✅ Category Created Successfully 🎉");
        } else {
            System.out.println("⚠️ Category with name '" + categoryName + "' already exists.");
        }
    }

    @Override
    public void view() {
        if (CategoryController.isCategoryEmpty()) {
            System.out.println("📂 No categories available. Please add a category first.");
            return;
        }
        if(loggedInUser.getRole() == CLIENT) {
            while(true){
                System.out.println("1. View Categories BY Products\n2. View All Categories\n0. Exit");
                System.out.println("🔢 Enter your choice:");
                try {
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch(choice){
                        case 1-> viewCategoryForProducts();
                        case 2-> viewAllCategories();
                        case 0-> {
                            System.out.println("🚪 Exiting Category View.");
                            return;
                        }
                        default -> System.out.println("❌ Invalid choice. Please try again.");
                    }
                }catch (InputMismatchException e) {
                    System.out.println("❌ Invalid input. Please enter a valid number.");
                }         
            }
        }
        else{
            viewAllCategories();
        }
    }
    // help methods for view prining all categories 
    private  void viewAllCategories(){
        System.out.println("📋 Available Categories:");
        for (int i = 0; i < CategoryController.getCategories().size(); i++) {
            System.out.println((i + 1) + ". " + CategoryController.getCategories().get(i).getName());
        }
    }
    // help methods for view prining all categories and products in a category
    private void viewCategoryForProducts(){
        Category category = getCategory(sc);
        if (category == null) {
            System.out.println("❌ No categories available to view products.");
            return;
        }
        if (category.getProduct().isEmpty()) {
            System.out.println("❌ No products available in this category.");
        } else {
            System.out.println("📦 Products in Category: " + category.getName());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            for (int i = 0; i < category.getProduct().size(); i++) {
                System.out.println((i + 1) + ". " + category.getProduct().get(i));
            }
        }
    }

    @Override
    public void update() {
        if (CategoryController.isCategoryEmpty()) {
            System.out.println("📂 No categories available to update.");
            return;
        }
        Category category = checkGetCategory();
        if (category == null) {
            System.out.println("❌ No category found with the given number.");
            return;
        }
        System.out.println("✏️ You are about to update the category: " + category.getName());
        System.out.println("1.Category Name\n2.Category Description\n3.Exit\nEnter the number of the field you want to update:");
        ValidData validData = new ValidData(sc);
        try {
            switch (sc.nextInt()) {
                case 1 -> category.setName(validData.name("📝 Enter the new Category Name:"));
                case 2 -> category.setDescription(validData.address("📝 Enter the new Category Description:"));
                case 3 -> {
                    System.out.println("🚪 Exiting update operation.");
                    return;
                }
                default -> {
                    System.out.println("❌ Invalid choice. Please select 1 or 2.");
                    return;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a valid number.");
        }
        System.out.println("✅ Category updated successfully.");
    }

    @Override
    public void delete() {
        if (CategoryController.isCategoryEmpty()) {
            System.out.println("❌ No categories available to delete.");
            return;
        }
        Category category = checkGetCategory();
        if (category == null) {
            System.out.println("❌ No category found with the given number.");
            return;
        }

        System.out.println("🗑️ You have selected to delete the category: " + category.getName());
        System.out.println("This action can delete Your Product List Completely \n \t(yes/no) || (y/n)");
        String confirmation = sc.nextLine().trim().toLowerCase();
        if (confirmation.equals("yes") || confirmation.equals("y")) {
            if (CategoryController.removeCategory(category))
                System.out.println("✅ Category deleted successfully.");
            else
                System.out.println("❌ Failed to delete category. Please try again.");
        } else {
            System.out.println("🚫 Category deletion cancelled.");
        }
    }
// help methods for  showing category in  for a product helper
    public static Category getCategory(Scanner sc) {
        if (CategoryController.isCategoryEmpty()) {
            return null;
        }
        System.out.println("📋 Available Categories:");
        for (int i = 0; i < CategoryController.getCategories().size(); i++) {
            System.out.println((i + 1) + ". " + CategoryController.getCategories().get(i).getName());
        }
        int categoryIndex;
        do {
            System.out.println("🔢 Select a category by entering the corresponding number:");
            categoryIndex = sc.nextInt() - 1;
            sc.nextLine();
        } while (categoryIndex < 0 || categoryIndex >= CategoryController.getCategories().size());

        return CategoryController.getCategories().get(categoryIndex);
    }

//  common logic for update and delete
    private Category checkGetCategory() {
        viewAllCategories();
        System.out.println("🔢 Enter the number of the category you want:");
        int categoryIndex = sc.nextInt() - 1;
        sc.nextLine();
        if (categoryIndex < 0 || categoryIndex >= CategoryController.getCategories().size()) {
            System.out.println("❌ Invalid category number. Please try again.");
            return null;
        }
        return CategoryController.getCategories().get(categoryIndex);
    }
}
