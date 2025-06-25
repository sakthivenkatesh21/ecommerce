package zohoincubation.com.zoho.ecommerce.src.view;

import java.util.InputMismatchException;
import java.util.Scanner;
import zohoincubation.com.zoho.ecommerce.src.controller.UserController;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Editable;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Execute;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Viewable;
import zohoincubation.com.zoho.ecommerce.src.model.Client;
import zohoincubation.com.zoho.ecommerce.src.model.Seller;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class UserHelper implements Execute, Editable, Viewable {
    private final User loggedInUser;
    private final Scanner sc;

    private final int CLIENT =1;
    private final int SELLER =2;

    public UserHelper(User loggedInUser, Scanner sc) {
        this.loggedInUser = loggedInUser;
        this.sc = sc;
    }

    @Override
    public String getfunctionName() {
        return "User Management";
    }

    @Override
    public void operation(Scanner sc, User loggedInUser) {
        while (true) {
            System.out.println("====================================");
            System.out.println("         🛠️ User Management Menu       ");
            System.out.println("====================================");
            System.out.println("1️⃣ View Info");
            System.out.println("2️⃣ Update Info");
            System.out.println("0️⃣ Exit");
            System.out.println("====================================");
            System.out.print("👉 Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> view();
                    case 2 -> update();
                    case 0 -> {
                        System.out.println("👋 Exiting User Management.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    @Override
    public void view() {
        String role = loggedInUser.getRole() == CLIENT ? "👤 Client" : "🏢 Seller";
        System.out.println("""
                ╔═══════════════════════════════════════════════════╗
                |                📋 USER INFORMATION                |  
                ╚═══════════════════════════════════════════════════╝
                """);

        System.out.printf("🔑 Role              : %-30s%n", role);
        System.out.printf("🧑 Name              : %-30s%n", loggedInUser.getName());
        System.out.printf("📧 Email             : %-30s%n", loggedInUser.getEmail());
        System.out.printf("📱 Phone Number      : %-30s%n", loggedInUser.getPhone());
        System.out.printf("🏠 Address           : %-30s%n", (loggedInUser.getRole() == CLIENT ? ((Client) loggedInUser).getAddress() : ((Seller) loggedInUser).getCompanyAddress()));
        // System.out.printf("🆔 User ID           : %-30s%n", loggedInUser.getId());

        if (loggedInUser.getRole() == SELLER) {
            Seller seller = (Seller) loggedInUser;
            System.out.printf("💰 Profit Earned     : ₹%-29.2f%n", seller.getProfit());
            System.out.printf("📦 Products Sold     : %-30d%n", seller.getSoldItem());
        }

        System.out.println("═════════════════════════════════════════════════════════");
    }

    @Override
    public void update() {
        ValidData check = new ValidData(sc);
        while (true) {
            System.out.println("""
                    ╔═══════════════════════════════════════════════════╗
                    |           ✏️ UPDATE USER INFORMATION MENU          |
                    ╚═══════════════════════════════════════════════════╝
                    1️⃣  🧑  Update Name
                    2️⃣  📱  Update Phone Number
                    3️⃣  📧  Update Email
                    4️⃣  🔒  Update Password
                    5️⃣  🚻  Update Gender""");
            if (loggedInUser.getRole() == CLIENT) {
                System.out.println("6️⃣  🏠  Update Address (Client)");
            } else if (loggedInUser.getRole() == SELLER) {
                System.out.println("""
                         6️⃣  🏢  Update Company Name (Seller)
                         7️⃣  📍  Update Company Address (Seller)""");
            }
            System.out.println("""
                   0️⃣  ❌  Exit Update Menu
                    ════════════════════════════════════════════════════
                    """);
            System.out.print("👉 Enter your choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        loggedInUser.setName(check.name("🧑 Enter new Name: "));
                        System.out.println("✅ Name updated successfully." + loggedInUser.getName());
                    }
                    case 2 -> {
                        loggedInUser.setPhone(isPhoneExists(check.phone("📱 Enter new phone number: "), check,loggedInUser));
                        System.out.println("✅ Phone number updated successfully." + loggedInUser.getPhone());
                    }
                    case 3 -> {
                        loggedInUser.setEmail(isEmailExists(check.email("📧 Enter new email: "), check,loggedInUser));
                        System.out.println("✅ Email updated successfully." + loggedInUser.getEmail());
                    }
                    case 4 -> {
                        String newPass =check.password("🔒 Enter new password: ");
                        while(newPass.equals(loggedInUser.getPassword())) {
                            System.out.println("❌ New password cannot be the same as the old one. Please enter a different password.");
                            newPass = check.password("🔒 Enter new password: ");
                        }
                        loggedInUser.setPassword(newPass);
                        System.out.println("✅ Password updated successfully.");
                    }
                    case 5 -> {
                        loggedInUser.setGender(check.gender("🚻 Enter new gender: "));
                        System.out.println("✅ Gender updated successfully." + loggedInUser.getGender());
                    }
                    case 6 -> {
                        if (loggedInUser.getRole() == 1) {
                            ((Client) loggedInUser).setAddress(check.address("🏠 Enter new address: "));
                            System.out.println("✅ Address updated successfully." + ((Client) loggedInUser).getAddress());
                        } else if (loggedInUser.getRole() == 2) {
                            ((Seller) loggedInUser).setCompany(check.name("🏢 Enter new company name: "));
                            System.out.println("✅ Company name updated successfully." + ((Seller) loggedInUser).getCompany());
                        }
                    }
                    case 7 -> {
                        if (loggedInUser.getRole() == 2) {
                            ((Seller) loggedInUser).setCompanyAddress(check.address("📍 Enter new company address: "));
                            System.out.println("✅ Company address updated successfully." + ((Seller) loggedInUser).getCompanyAddress());
                        } else {
                            System.out.println("❌ Invalid choice for Client, no updates made.");
                        }
                    }
                    case 0 -> {
                        System.out.println("👋 Exiting Update Menu.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice, no updates made.");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    public static String[] getDetails(Scanner sc,User loggedInUser) {
        ValidData check = new ValidData(sc);
        String name = check.name("🧑 Enter your Name :");
        String phone = isPhoneExists(check.phone("📱 Enter a valid phone number: "), check,loggedInUser);
        String email = isEmailExists(check.email("📧 Enter a valid email address: "), check,loggedInUser);
        String password = check.password("🔒 Type your Password:");
        String gender = check.gender("🚻 Enter your Gender:");
        int  userType = 0;
        while(true){
            try{
                System.out.println("👥 Are you Signing Up as Client or Seller?\n 1️⃣ Client\n 2️⃣ Seller\n (Enter a Number)");
                userType = sc.nextInt();
                sc.nextLine();
                if(userType == 1 || userType == 2) break;
                System.out.println("❌ Invalid User Type. Please enter 1 for Client or 2 for Seller.");
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                sc.nextLine(); 
            }
        }       
        if(userType ==1){
            return new String[]{name, phone, email, password, gender, check.address("🏠 Enter Address:")};   
        }
        else {
            return new String[]{name, phone, email, password, gender, check.name("🏢 Enter a Company Name :"), check.address("📍 Enter a Company Address :")};
        }           
    }        
            
        


    private static String isEmailExists(String email, ValidData check,User loggedInUser) {
        while (UserController.isMailExists(email,loggedInUser)) {
            System.out.println("❌ Email already exists. Please try again with a different email.");
            email = check.email("📧 Enter a valid email address: ");
        }
        return email;
    }

    private static String isPhoneExists(String phone, ValidData check,User loggedInUser) {
        while (UserController.isPhoneExists(phone, loggedInUser)) {
            
            System.out.println("❌ Phone number already exists. Please try again with a different number.");
            phone = check.phone("📱 Enter a valid phone number: ");
        }
        return phone;
    }
}
