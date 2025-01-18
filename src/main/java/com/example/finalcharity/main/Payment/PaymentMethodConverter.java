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
        // Handle adapter cases (StripePaymentAdapter and AuthorizeNetPaymentAdapter)
        if (paymentMethod instanceof StripePaymentAdapter) {
            // We persist as "CREDIT_CARD" for StripePaymentAdapter
            return "CREDIT_CARD";
        } else if (paymentMethod instanceof AuthorizeNetPaymentAdapter) {
            // We persist as "PAYPAL" for AuthorizeNetPaymentAdapter
            return "PAYPAL";
        }
        // Handle original payment method cases
        else if (paymentMethod instanceof CreditCardPayment) {
            return "CREDIT_CARD";
        } else if (paymentMethod instanceof PaypalPayment) {
            return "PAYPAL";
        }

        // Throw an exception if we encounter an unknown PaymentMethod type
        throw new IllegalArgumentException("Unknown PaymentMethod implementation: " + paymentMethod.getClass());
    }

    @Override
    public PaymentMethod convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "CREDIT_CARD":
                // Return a default or placeholder CreditCardPayment or StripePaymentAdapter instance
                return new CreditCardPayment(); // Could return new StripePaymentAdapter() depending on context
            case "PAYPAL":
                // Return a default or placeholder PaypalPayment or AuthorizeNetPaymentAdapter instance
                return new PaypalPayment(); // Could return new AuthorizeNetPaymentAdapter() depending on context
            default:
                // If we encounter an unrecognized database value, throw an exception
                throw new IllegalArgumentException("Unknown database value for PaymentMethod: " + dbData);
        }
    }
}
