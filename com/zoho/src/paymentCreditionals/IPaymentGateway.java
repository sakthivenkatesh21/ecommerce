package zohoincubation.com.zoho.ecommerce.src.paymentCreditionals;

public interface IPaymentGateway {

    void processPayment();

    String generateTransactionId();

    String confirmPayment(String transactionId);
}
