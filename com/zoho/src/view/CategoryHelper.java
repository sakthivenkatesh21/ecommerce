package com.zoho.src.view;

import com.zoho.src.controller.CategoryController;
import com.zoho.src.interfaceController.Creatable;
import com.zoho.src.interfaceController.Deletable;
import com.zoho.src.interfaceController.Editable;
import com.zoho.src.interfaceController.Execute;
import com.zoho.src.interfaceController.Viewable;
import com.zoho.src.model.Category;
import com.zoho.src.model.User;
import java.util.InputMismatchException;
import java.util.Scanner;

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
                        case 1-> viewCategoryForProducts(sc);
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
    // help methods ->for view  all categories  only 
    private  static  void viewAllCategories(){
        System.out.println("📋 Available Categories:");
        for (int i = 0; i < CategoryController.getCategories().size(); i++) {
            System.out.println((i + 1) + ". " + CategoryController.getCategories().get(i).getName());
        }
    }

    // help methods -> for view  all categories with products in a category
    public static  boolean  viewCategoryForProducts(Scanner sc){
        Category category = getCategory(sc);
        if (category == null) {
            System.out.println("❌ No categories available to view products.");
            return false;
        }
        if (category.getProduct().isEmpty()) {
            System.out.println("❌ No products available in this category.");
            return false;
        } else {
            System.out.println("📦 Products in Category: " + category.getName());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            for (int i = 0; i < category.getProduct().size(); i++) {
                System.out.println((i + 1) + ". " + category.getProduct().get(i));
            }
        }
        return true;    
    }

    @Override
    public void update() {
        Category category = getCategory(sc);
        if (category == null) {
            System.out.println("📂 No categories available to update");
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
        Category category = getCategory(sc);
        if (category == null) {
            System.out.println("❌ No categories available to delete.");
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

// help methods ;
//->  for update() and delete()  common logic  ( return the selected categories for update or delete)
//->  for add() product before   choosing  category(return the selected category to Product helper)
//->  for viewAllTheCategoryProduct   its  return the category to viewCategoryForProducts()

    public static  Category getCategory(Scanner sc) {
        if (CategoryController.isCategoryEmpty()) {
            return null;
        }
        viewAllCategories();// display all categories
        int categoryIndex;
        do {
            System.out.println("🔢 Select a category by entering the corresponding number:");
            categoryIndex = sc.nextInt() - 1;
            sc.nextLine();
        } while (categoryIndex < 0 || categoryIndex >= CategoryController.getCategories().size());

        return CategoryController.getCategories().get(categoryIndex);
    }
}
