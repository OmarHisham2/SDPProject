package com.example.finalcharity.main.CRUD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_method_types")
public class PaymentMethodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;        // "Credit Card", "PayPal"
    
    private String description;
    
    @Column(nullable = false)
    private boolean isActive = true;
}
