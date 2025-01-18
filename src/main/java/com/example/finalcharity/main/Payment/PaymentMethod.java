package com.example.finalcharity.main.Payment;

public interface PaymentMethod {
    boolean pay();
    boolean validatePaymentDetails();
    boolean refund();
}