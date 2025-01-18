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
    private final StripeService stripeService;
    private final AuthorizeNetService authorizeNetService;

    @Autowired
    public PaymentController(PaymentService paymentService, StripeService stripeService, AuthorizeNetService authorizeNetService) {
        this.paymentService = paymentService;
        this.stripeService = stripeService;
        this.authorizeNetService = authorizeNetService;
    }

    // Get all payments
    @GetMapping
    public List<PaymentResponseDTO> getPayments() {
        List<Payment> payments = paymentService.getPayments();
        return payments.stream().map(this::convertToDto).toList(); // Convert each Payment to PaymentResponseDTO
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment
                .map(value -> new ResponseEntity<>(convertToDto(value), HttpStatus.OK)) // Convert to DTO before returning
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Return NOT_FOUND status if Payment is not found
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST); // Return validation errors
        }

        PaymentMethod paymentMethod = convertToPaymentMethod(paymentRequestDTO);
        if (paymentMethod == null) {
            return new ResponseEntity<>("Invalid payment method or missing credentials", HttpStatus.BAD_REQUEST);
        }

        Payment payment = new Payment(paymentRequestDTO.getDonationId(), paymentMethod, paymentRequestDTO.getAmount());
        if (paymentMethod.pay()) {
            Payment createdPayment = paymentService.addPayment(payment);
            return new ResponseEntity<>(convertToDto(createdPayment), HttpStatus.CREATED); // Return PaymentResponseDTO
        }
        return new ResponseEntity<>("Bank transaction failed", HttpStatus.BAD_REQUEST);
    }

    // Update an existing payment
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Long id, @Valid @RequestBody Payment payment,
                                                            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new PaymentResponseDTO(), HttpStatus.BAD_REQUEST); // Return empty DTO for bad request
        }
        Optional<Payment> updatedPayment = paymentService.updatePayment(id, payment);
        return updatedPayment
                .map(value -> new ResponseEntity<>(convertToDto(value), HttpStatus.OK)) // Return the updated Payment DTO if found
                .orElseGet(() -> new ResponseEntity<>(new PaymentResponseDTO(), HttpStatus.NOT_FOUND)); // Return empty DTO if not found
    }

    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        boolean isDeleted = paymentService.deletePayment(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content for successful deletion
        } else {
            return new ResponseEntity<>("Donation not found", HttpStatus.NOT_FOUND); // Return error if not found
        }
    }

    // Validate payment
    @PostMapping("/{id}/validate")
    public ResponseEntity<PaymentResponseDTO> validatePayment(@PathVariable Long id) {
        boolean isValid = paymentService.validatePayment(id);
        if (isValid) {
            return new ResponseEntity<>(new PaymentResponseDTO(), HttpStatus.OK); // Return empty DTO for valid payment
        } else {
            return new ResponseEntity<>(new PaymentResponseDTO(), HttpStatus.BAD_REQUEST); // Return empty DTO for invalid payment
        }
    }

    // Refund a payment
    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(@PathVariable Long id) {
        boolean isRefunded = paymentService.refund(id);
        if (isRefunded) {
            return new ResponseEntity<>(new PaymentResponseDTO(), HttpStatus.OK); // Return empty DTO for refunded payment
        } else {
            return new ResponseEntity<>(new PaymentResponseDTO(), HttpStatus.BAD_REQUEST); // Return empty DTO for refund failure
        }
    }

    private PaymentMethod convertToPaymentMethod(PaymentRequestDTO dto) {
        switch (dto.getPaymentMethod()) {
            case 1:
                // Credit Card Payment with Stripe
                if (dto.getCardNumber() != null && dto.getCardHolder() != null &&
                        dto.getExpiryDate() != null && dto.getCvv() != null) {
                    return new StripePaymentAdapter(stripeService,
                            dto.getCardNumber(),
                            dto.getExpiryDate(),
                            dto.getCvv(),
                            dto.getAmount());
                } else {
                    // Missing credit card credentials
                    return null;
                }

            case 2:
                // AuthorizeNet Payment
                if (dto.getCardNumber() != null && dto.getExpiryDate() != null && dto.getCvv() != null) {
                    return new AuthorizeNetPaymentAdapter(authorizeNetService,
                            dto.getCardNumber(),
                            dto.getExpiryDate(),
                            dto.getCvv(),
                            dto.getAmount());
                } else {
                    // Missing AuthorizeNet credentials
                    return null;
                }

            default:
                // Invalid payment method
                return null;
        }
    }

    // Convert Payment to PaymentResponseDTO
    private PaymentResponseDTO convertToDto(Payment payment) {
        return new PaymentResponseDTO(payment.getPaymentId(), payment.getDonationId(), payment.getAmount(), payment.getStatus());
    }
}
