package com.zoho.src.controller;

import com.zoho.src.internal.Gpay;
import com.zoho.src.internal.MayPay;
import com.zoho.src.internal.Paytm;
import com.zoho.src.paymentCreditionals.IPaymentGateway;
import com.zoho.src.paymentCreditionals.PaymentProcessing;

public class PaymentController {
    private static PaymentProcessing paymentProcessing;

// Method  overloaded to handle different payment methods
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
// common logic for pay method to process payment
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
