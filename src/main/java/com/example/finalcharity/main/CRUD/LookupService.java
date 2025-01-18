package com.example.finalcharity.main.CRUD;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LookupService {
    private final UserTypeRepository userTypeRepo;
    private final PaymentMethodTypeRepository paymentMethodTypeRepo;
    private final CampaignStatusRepository campaignStatusRepo;

    @Autowired
    public LookupService(UserTypeRepository userTypeRepo,
                        PaymentMethodTypeRepository paymentMethodTypeRepo,
                        CampaignStatusRepository campaignStatusRepo) {
        this.userTypeRepo = userTypeRepo;
        this.paymentMethodTypeRepo = paymentMethodTypeRepo;
        this.campaignStatusRepo = campaignStatusRepo;
    }

    // User Types
    public List<UserType> getAllUserTypes() {
        return userTypeRepo.findByIsActiveTrue();
    }

    public Optional<UserType> getUserType(Integer id) {
        return userTypeRepo.findById(id);
    }

    // Payment Methods
    public List<PaymentMethodType> getAllPaymentMethods() {
        return paymentMethodTypeRepo.findByIsActiveTrue();
    }

    public Optional<PaymentMethodType> getPaymentMethod(Integer id) {
        return paymentMethodTypeRepo.findById(id);
    }

    // Campaign Statuses
    public List<CampaignStatus> getAllCampaignStatuses() {
        return campaignStatusRepo.findByIsActiveTrue();
    }

    public Optional<CampaignStatus> getCampaignStatus(Integer id) {
        return campaignStatusRepo.findById(id);}
}