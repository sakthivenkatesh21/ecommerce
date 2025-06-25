package zohoincubation.com.zoho.ecommerce.src.view;

import java.util.InputMismatchException;
import java.util.Scanner;
import zohoincubation.com.zoho.ecommerce.src.controller.OrderController;
import zohoincubation.com.zoho.ecommerce.src.controller.ProductController;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Execute;
import zohoincubation.com.zoho.ecommerce.src.interfaceController.Viewable;
import zohoincubation.com.zoho.ecommerce.src.model.Card;
import zohoincubation.com.zoho.ecommerce.src.model.Client;
import zohoincubation.com.zoho.ecommerce.src.model.Order;
import zohoincubation.com.zoho.ecommerce.src.model.Seller;
import zohoincubation.com.zoho.ecommerce.src.model.User;

public class OrderHelper implements Execute, Viewable {
  private final Scanner sc;
  private final User loggedInUser;

  private final int CLIENT = 1;
  private final int SELLER = 2;
 

  public OrderHelper(Scanner sc, User loggedInUser) {
    this.sc = sc;
    this.loggedInUser = loggedInUser;
  }

  @Override
  public String getfunctionName() {
    return "Order Management";
  }

  @Override
  public void operation(Scanner sc, User loggedInUser) {
    while (true) {
      try {
        if (loggedInUser.getRole() == SELLER) {
          System.out.println("\n==============================");
          System.out.println("📦 1. View Orders");
          System.out.println("🚪 0. Exit");
          System.out.println("==============================");
        } else if (loggedInUser.getRole() == CLIENT) {
          System.out.println("\n==============================");
          System.out.println("🛒 1. Checkout");
          System.out.println("📦 2. View Order");
          System.out.println("🚪 0. Exit");
          System.out.println("==============================");
        }
        System.out.print("👉 Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
          case 1 -> {
            if (loggedInUser.getRole() == SELLER) view();
            if(loggedInUser.getRole()  == CLIENT) checkout();
          }
          case 2 -> {
            if (loggedInUser.getRole() == CLIENT) view();
            else System.out.println("❌ Invalid choice. Please try again.");
          }
          case 0 -> {
            System.out.println("🚪 Exiting Order Management.");
            return;
          }
          default -> System.out.println("❌ Invalid choice. Please try again.");
        }
      } catch (InputMismatchException e) {
        System.out.println("❌ Invalid input. Please enter a valid number.");
        sc.nextLine();
      } catch (Exception e) {
        System.out.println("❌ An unexpected error occurred: " + e.getMessage());
      }
    }
  }

  private void checkout() {
    if (((Client) loggedInUser).getcard() == null || ((Client) loggedInUser).getcard().getProduct() == null || ((Client) loggedInUser).getcard().getProduct().isEmpty()) {
      System.out.println("⚠️ You must have a valid card to check out. Please add Product to a card and checkout your account.");
      return;
    }

      Card card = ((Client)loggedInUser).getcard();
      WishlistHandler wishlistHandler = new WishlistHandler(sc, loggedInUser);
      wishlistHandler.view();
      System.out.println("👉 Enter 'Yes | Y' to confirm checkout\n\t OR\n'No | N' Remove Product From Card:");
      String choice = sc.nextLine().trim().toUpperCase();

        switch (choice) {
            case "NO":
            case "N" :  
                 wishlistHandler.delete();
            case "YES":
            case  "Y" :
                System.out.println("✅ Proceeding to checkout...");
                wishlistHandler.checkQuantityExist(card.getProduct());
                if( card.getProduct().isEmpty()) {
                    System.out.println("⚠️ No products in the card to checkout.");
                    return;
                }
                double cardTotal = card.calculateCardTotal();
                String payment = PaymentHelper.paymentProcess(sc, cardTotal);
                if (payment == null) {
                    System.out.println("❌ Payment failed. Please try again.");
                    return;
                }
                Order order = OrderController.createOrder(card, cardTotal, payment, loggedInUser);
                if (order != null &&  ProductController.reduceStock(order.getProduct())) {
                    OrderStatusUpdate.flow(order);
                   
                } else {
                    System.out.println("❌ Order creation failed. Please try again.");
                    System.out.println("Amount will be refunded to your account.");
                }
              break;  
            default : System.out.println("❌ Invalid choice. Please enter 'Yes' or 'No'.");
        }
    
  }

  @Override
  public void view() {
      switch (loggedInUser.getRole()) {
          case CLIENT -> dispalyClientOrders( loggedInUser);
          case SELLER -> displaySellerOrders( loggedInUser);
          default -> System.out.println("⛔ You are not authorized to view orders.");
      }
  }


  // view orders for client
  private void dispalyClientOrders( User loggedInUser) {
    System.out.println("📋 Displaying orders for client: " + loggedInUser.getName());
    if (((Client) loggedInUser).getPreviousOrderProduct() == null || ((Client) loggedInUser).getPreviousOrderProduct().isEmpty()) {
      System.out.println("⚠️ No previous orders found for this client.");
      return;
    }
    for (int i = 0; i < ((Client) loggedInUser).getPreviousOrderProduct().size(); i++) {
      System.out.println("📦 Order " + (i + 1) );
      System.out.println(((Client) loggedInUser).getPreviousOrderProduct().get(i));
    }
  }


  // view orders for seller
  private void displaySellerOrders( User loggedInUser) {
    System.out.println("📋 Displaying placed orders for seller: " + loggedInUser.getName());
    if (((Seller) loggedInUser).getSaledList() == null || ((Seller) loggedInUser).getSaledList().isEmpty()) {
      System.out.println("⚠️ No orders found for this seller.");
      return;
    }
    for (int i = 0; i < ((Seller) loggedInUser).getSaledList().size(); i++) {
      System.out.println("📦 Order Sold " + (i + 1) );
      System.out.println(((Seller) loggedInUser).getSaledList().get(i));
    }
  }


}
