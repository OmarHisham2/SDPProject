package com.example.finalcharity.main.CRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodTypeRepository extends JpaRepository<PaymentMethodType, Integer> {
    List<PaymentMethodType> findByIsActiveTrue();
}
