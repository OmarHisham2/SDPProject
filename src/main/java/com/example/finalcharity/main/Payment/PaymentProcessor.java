package com.example.finalcharity.main.Payment;

public abstract class PaymentProcessor {

    // Template method that defines the steps for processing a payment
    public final boolean processPayment(double amount, String cardNumber, String expiryDate, String cvv) {
        validatePaymentDetails(cardNumber, expiryDate, cvv);
        boolean paymentSuccess = authorizePayment(cardNumber, expiryDate, cvv, amount);
        if (paymentSuccess) {
            updatePaymentStatus("COMPLETED");
            notifyUser();
            return true;
        } else {
            updatePaymentStatus("FAILED");
            return false;
        }
    }

    // Step 1: Validate payment details (can be overridden by subclasses)
    protected void validatePaymentDetails(String cardNumber, String expiryDate, String cvv) {
        System.out.println("Validating payment details...");
    }

    // Step 2: Authorize payment (abstract method to be implemented by subclasses)
    protected abstract boolean authorizePayment(String cardNumber, String expiryDate, String cvv, double amount);

    // Step 3: Update payment status (can be overridden by subclasses)
    protected void updatePaymentStatus(String status) {
        System.out.println("Updating payment status to: " + status);
    }

    // Step 4: Notify the user (abstract method to be implemented by subclasses)
    protected abstract void notifyUser();
}