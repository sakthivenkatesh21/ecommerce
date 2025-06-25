package zohoincubation.com.zoho.ecommerce.src.view;

import java.util.Scanner;
import zohoincubation.com.zoho.ecommerce.src.controller.SignIn;
import zohoincubation.com.zoho.ecommerce.src.controller.UserController;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class LoggingCredentials {
    private User logInUser;

    public void logIn(Scanner sc) {
        System.out.println("===================================");
        System.out.println("          🚪 LOGIN PAGE 🚪         ");
        System.out.println("===================================");
        System.out.println("Please enter your credentials below:");
        System.out.print("📧 Email: ");
        String email = sc.nextLine();
        System.out.print("🔒 Password: ");
        String password = sc.nextLine();
        System.out.println("===================================");

        logInUser = SignIn.validateLogIn(email, password);

        sigIn(sc);
    }

    public void signUp(Scanner sc) {
        String[] user = UserHelper.getDetails(sc,logInUser);
        switch (user.length) {
            case 6 -> logInUser = UserController.createUser(user[0], user[1], user[2], user[3], user[4], user[5]);
            case 7 -> logInUser = UserController.createUser(user[0], user[1], user[2], user[3], user[4], user[5], user[6]);
            default -> {
                System.out.println("Invalid User");
                return;
            }
        }
        if (logInUser != null) {
            sigIn(sc);
        } else {
            System.out.println("User creation failed. Please try again.");
        }
    }

    private void sigIn(Scanner sc) {
        if (logInUser != null) {
            logInUser.showMenu(sc, logInUser);
        } else {
            System.out.println("Invalid Email or Password");
            System.out.println("Please try again");
        }
    }
}
