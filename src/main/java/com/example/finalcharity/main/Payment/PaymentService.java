package com.example.finalcharity.main.Payment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;
    private final AuthorizeNetService authorizeNetService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, StripeService stripeService, AuthorizeNetService authorizeNetService) {
        this.paymentRepository = paymentRepository;
        this.stripeService = stripeService;
        this.authorizeNetService = authorizeNetService;
    }

    // Repository-related methods (CRUD operations)
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Optional<Payment> updatePayment(Long id, Payment payment) {
        return paymentRepository.findById(id).map(existingPayment -> {
            existingPayment.setAmount(payment.getAmount());
            existingPayment.setStatus(payment.getStatus());
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            return paymentRepository.save(existingPayment);
        });
    }

    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean validatePayment(Long id) {
        return paymentRepository.findById(id).map(payment -> {
            // Sample validation logic: assume payment is valid if status is "COMPLETED"
            return "COMPLETED".equalsIgnoreCase(payment.getStatus());
        }).orElse(false);
    }

    public boolean refund(Long id) {
        return paymentRepository.findById(id).map(payment -> {
            if ("COMPLETED".equalsIgnoreCase(payment.getStatus())) {
                payment.setStatus("REFUNDED");
                paymentRepository.save(payment);
                return true;
            }
            return false;
        }).orElse(false);
    }

    // Payment processing logic using the Template Method Pattern
    public boolean processPayment(String paymentMethod, double amount, String cardNumber, String expiryDate, String cvv) {
        PaymentProcessor processor;
    
        switch (paymentMethod.toLowerCase()) {
            case "stripe":
                processor = new StripePaymentProcessor(stripeService);
                break;
            case "authorize.net":
                processor = new AuthorizeNetPaymentProcessor(authorizeNetService);
                break;
            case "paypal":
                processor = new PaypalPaymentProcessor();
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    
        // Process the payment using the template method
        boolean paymentSuccess = processor.processPayment(amount, cardNumber, expiryDate, cvv);
    
        // Save the payment to the repository if successful
        if (paymentSuccess) {
            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setStatus("COMPLETED");
    
            if (processor instanceof PaymentMethod) {
                payment.setPaymentMethod((PaymentMethod) processor); // Cast processor to PaymentMethod
            } else {
                throw new IllegalArgumentException("Processor does not implement PaymentMethod interface.");
            }
    
            paymentRepository.save(payment);
        }
    
        return paymentSuccess;
    }
    
}