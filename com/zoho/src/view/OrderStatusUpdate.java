package zohoincubation.com.zoho.ecommerce.src.view;

import zohoincubation.com.zoho.ecommerce.src.model.Order;
import zohoincubation.com.zoho.ecommerce.src.model.OrderStatus;

class OrderStatusUpdate {

    private static void printOrderStatus(String productName, int orderId, String transactionId, String status) {
        System.out.printf("""
            =================== 🧾 ORDER SUMMARY ===================
            📦 Product Name    : %-30s
            🆔 Order ID        : %-30d
            🔁 Transaction ID  : %-30s
            🚚 Order Status    : %-30s
            ========================================================
            
            """, productName, orderId, transactionId, status);
    }

    public static void flow(Order order) {
        String[] statuses = new String[]{
            OrderStatus.CONFIRMED.getLabel(),
            OrderStatus.SHIPPED.getLabel(),
            OrderStatus.DELIVERED.getLabel()
        };

        for (String status : statuses) {
            printOrderStatus(order.getClient().getName(), order.getId(), order.getPayment(), status);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("⚠️ Status update interrupted.");
            }
        }
    }
}
