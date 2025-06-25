package zohoincubation.com.zoho.ecommerce.src.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public  class Navigation {

    private final Scanner sc;
    private final LoggingCredentials credentials;


    public Navigation() {
        sc = new Scanner(System.in);
        credentials = new LoggingCredentials();
        InputProduct inputProduct = new InputProduct();
    }

    public void menu() {
        System.out.println("🌟 Welcome to E - Commerce 🌟");
        while (true) {
            try {
                System.out.println("=================================");
                System.out.println("       🌟 E - Commerce Menu 🌟    ");
                System.out.println("=================================");
                System.out.println("1. ✍️ Sign Up");
                System.out.println("2. 🔑 Sign In");
                System.out.println("3. ❌ Exit");
                System.out.println("=================================");
                System.out.print("👉 Please enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> credentials.signUp(sc);
                    case 2 -> credentials.logIn(sc);
                    case 3 -> exit(sc);
                    default -> System.out.println("Invalid Choice");
                }
            } catch (InputMismatchException ime) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine(); 
            } catch (Exception e) {
                System.out.println("Error: An unexpected error occurred.");
            }
        }
    }
    private void exit(Scanner sc) {
        try (sc) {
            System.out.println("Thank you for using E - Commerce");
        }
        System.exit(0);
    }
}
