package com.example.finalcharity.main.Payment;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Get all payments
    @GetMapping
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Return the Payment if found
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Return NOT_FOUND status without body
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        PaymentMethod paymentMethod = convertToPaymentMethod(paymentRequestDTO);
        if (paymentMethod == null) {
            return new ResponseEntity<>("Invalid payment method or missing credentials", HttpStatus.BAD_REQUEST);
        }

        Payment payment = new Payment(paymentRequestDTO.getDonationId(), paymentMethod, paymentRequestDTO.getAmount());
        if (paymentMethod.pay()) {
            Payment createdPayment = paymentService.addPayment(payment);
            return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Bank transaction failed", HttpStatus.BAD_REQUEST);
    }


    // Update an existing payment
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @Valid @RequestBody Payment payment,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new Payment(), HttpStatus.BAD_REQUEST); // Return empty Payment object for bad
                                                                                // request
        }
        Optional<Payment> updatedPayment = paymentService.updatePayment(id, payment);
        return updatedPayment
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Return the updated Payment if found
                .orElseGet(() -> new ResponseEntity<>(new Payment(), HttpStatus.NOT_FOUND)); // Return an empty Payment
                                                                                             // object with NOT_FOUND
    }

    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        boolean isDeleted = paymentService.deletePayment(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content for successful deletion
        } else {
            return new ResponseEntity<>("Donation not found", HttpStatus.NOT_FOUND); // Return empty Payment object if
                                                                                     // not found
        }
    }

    // Validate payment
    @PostMapping("/{id}/validate")
    public ResponseEntity<Payment> validatePayment(@PathVariable Long id) {
        boolean isValid = paymentService.validatePayment(id);
        if (isValid) {
            return new ResponseEntity<>(new Payment(), HttpStatus.OK); // Return empty Payment object for valid payment
        } else {
            return new ResponseEntity<>(new Payment(), HttpStatus.BAD_REQUEST); // Return empty Payment object for
                                                                                // invalid payment
        }
    }

    // Refund a payment
    @PostMapping("/{id}/refund")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long id) {
        boolean isRefunded = paymentService.refund(id);
        if (isRefunded) {
            return new ResponseEntity<>(new Payment(), HttpStatus.OK); // Return empty Payment object for refunded
                                                                       // payment
        } else {
            return new ResponseEntity<>(new Payment(), HttpStatus.BAD_REQUEST); // Return empty Payment object for
                                                                                // refund failure
        }
    }

    private PaymentMethod convertToPaymentMethod(PaymentRequestDTO dto) {
        switch (dto.getPaymentMethod()) {
            case 1:
                // Credit Card Payment
                if (dto.getCardNumber() != null && dto.getCardHolder() != null &&
                        dto.getExpiryDate() != null && dto.getCvv() != null) {

                    return new CreditCardPayment(
                            dto.getCardNumber(),
                            dto.getCardHolder(),
                            dto.getExpiryDate(),
                            dto.getCvv());
                } else {
                    // Missing credit card credentials
                    return null;
                }

            case 2:
                // PayPal Payment
                if (dto.getEmail() != null && dto.getPassword() != null) {
                    return new PaypalPayment(
                            dto.getEmail(),
                            dto.getPassword());
                } else {
                    // Missing PayPal credentials
                    return null;
                }

            default:
                // Invalid payment method
                return null;
        }
    }
}
