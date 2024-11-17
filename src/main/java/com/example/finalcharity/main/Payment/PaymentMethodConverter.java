package com.example.finalcharity.main.Payment;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return null;
        }
        if (paymentMethod instanceof CreditCardPayment) {
            return "CREDIT_CARD";
        } else if (paymentMethod instanceof PaypalPayment) {
            return "PAYPAL";
        }
        // Add more cases if you have more payment methods
        throw new IllegalArgumentException("Unknown PaymentMethod implementation: " + paymentMethod.getClass());
    }

    @Override
    public PaymentMethod convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "CREDIT_CARD":
                // Return a default or placeholder CreditCardPayment instance
                return new CreditCardPayment();
            case "PAYPAL":
                // Return a default or placeholder PaypalPayment instance
                return new PaypalPayment();
            // Add more cases if you have more payment methods
            default:
                throw new IllegalArgumentException("Unknown database value for PaymentMethod: " + dbData);
        }
    }
}