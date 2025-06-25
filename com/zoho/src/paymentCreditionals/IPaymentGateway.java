package com.zoho.src.paymentCreditionals;

public interface IPaymentGateway {

    void processPayment();

    String generateTransactionId();

    String confirmPayment(String transactionId);
}
