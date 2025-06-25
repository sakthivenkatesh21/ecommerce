package zohoincubation.com.zoho.ecommerce.src.model;

import java.util.InputMismatchException;
import java.util.Scanner;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Execute;

public abstract class User {

    private int id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String gender;
    protected Execute[] operations;
    public User(){
    }
    
    public User(int id, String name,String phone, String email, String password, String gender) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void showMenu(Scanner sc,User loggedInUser){
        System.out.println("Welcome  " + loggedInUser.getName() + " to the E-commerce Platform");
        while (true) {
            System.out.println("\n========== MENU ==========");
            for (int i = 0; i < this.getOperations(sc, loggedInUser).length; i++) {
            System.out.println((i + 1) + ". " + this.operations[i].getfunctionName());
            }
            System.out.println("==========================\n");
            System.out.println("Enter a Choice - Zero(0) to Exit");
            
            try {
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 0) {
                System.out.println("Exiting to previous menu.");
                return;
            }
            if (choice < 1 || choice > operations.length) {
                System.out.println("Invalid Choice");
            } else {
                operations[choice - 1].operation(sc, loggedInUser);
            }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
            }
            catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    public abstract Execute[] getOperations(Scanner sc ,User loggedInUser);
    public abstract int getRole();
  
}
