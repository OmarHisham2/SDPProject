package com.example.finalcharity.main.Payment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

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
}
