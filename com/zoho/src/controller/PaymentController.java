package zohoincubation.com.zoho.ecommerce.src.controller;

import zohoincubation.com.zoho.ecommerce.src.internal.Gpay;
import zohoincubation.com.zoho.ecommerce.src.internal.MayPay;
import zohoincubation.com.zoho.ecommerce.src.internal.Paytm;
import zohoincubation.com.zoho.ecommerce.src.paymentCreditionals.IPaymentGateway;
import zohoincubation.com.zoho.ecommerce.src.paymentCreditionals.PaymentProcessing;

public class PaymentController {
    private static PaymentProcessing paymentProcessing;

    public static String pay(double amount, String paymentMethod, String credentials) {
        return switch (paymentMethod) {
            case "GPay" -> 
                 process(new Gpay(amount, paymentMethod, credentials));
            case "Paytm" ->
                 process(new Paytm(amount, paymentMethod, credentials));
            default->
                 null;
        };
    }

    private static String process(IPaymentGateway paymentGateway) {
        paymentProcessing = new PaymentProcessing(paymentGateway);
        return paymentProcessing.processPayment();
    }

    public static String pay(double amount, String paymentMethod, String credentials, String viaMode) {
        return switch (viaMode) {
            case "Upi" -> 
                process(new MayPay(amount, viaMode, credentials));
            case "NetBanking" -> 
                process(new MayPay(amount, viaMode, credentials));
            default -> 
                null;
        }; 
    }
}
