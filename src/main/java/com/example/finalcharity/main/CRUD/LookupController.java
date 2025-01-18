package com.example.finalcharity.main.CRUD;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lookups")
public class LookupController {
    private final LookupService lookupService;

    @Autowired
    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping("/user-types")
    public ResponseEntity<List<UserType>> getUserTypes() {
        return ResponseEntity.ok(lookupService.getAllUserTypes());
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentMethodType>> getPaymentMethods() {
        return ResponseEntity.ok(lookupService.getAllPaymentMethods());
    }

    @GetMapping("/campaign-statuses")
    public ResponseEntity<List<CampaignStatus>> getCampaignStatuses() {
        return ResponseEntity.ok(lookupService.getAllCampaignStatuses());
}
}