package com.example.finalcharity.main.facade;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Campaign.CampaignService;
import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.Donation.DonationService;
import com.example.finalcharity.main.Payment.*;
import com.example.finalcharity.main.User.User;
import com.example.finalcharity.main.User.UserService;
import com.example.finalcharity.main.facade.dto.DonationRequest;
import com.example.finalcharity.main.facade.dto.CampaignStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CharityOperationsFacade {
    private final CampaignService campaignService;
    private final DonationService donationService;
    private final PaymentService paymentService;
    private final UserService userService;

    @Autowired
    public CharityOperationsFacade(
            CampaignService campaignService,
            DonationService donationService,
            PaymentService paymentService,
            UserService userService) {
        this.campaignService = campaignService;
        this.donationService = donationService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    private PaymentMethod createPaymentMethod(DonationRequest request) {
        if (request.getPaymentMethod() == 1) {  // Credit Card
            return new CreditCardPayment(
                    request.getCardNumber(),
                    request.getCardHolder(),
                    request.getExpiryDate(),
                    request.getCvv()
            );
        } else if (request.getPaymentMethod() == 2) {  // PayPal
            return new PaypalPayment(
                    request.getEmail(),
                    request.getPassword()
            );
        }
        throw new IllegalArgumentException("Invalid payment method");
    }

    public Campaign createCampaignWithInitialDonation(Campaign campaign, DonationRequest initialDonation) {
        try {
            // 1. Create and save the campaign
            Campaign newCampaign = campaignService.createCampaign(campaign);
            System.out.println("Campaign created successfully: " + newCampaign.getID());

            // Get the user
            User donor = userService.getUserById(initialDonation.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + initialDonation.getUserId()));
            System.out.println("User found: " + donor.getName());

            // 2. Create and save donation first
            Donation donation = new Donation();
            donation.setAmount(initialDonation.getAmount());
            donation.setMessage(initialDonation.getMessage());
            donation.setCampaign(newCampaign);
            donation.setUser(donor);
            donation.setCurrency(initialDonation.getCurrency());
            donation.setStatus("PENDING");

            // Save donation first to get its ID
            Donation savedDonation = donationService.addDonation(donation);
            System.out.println("Donation saved with ID: " + savedDonation.getDonationID());

            // 3. Process payment with the saved donation ID
            PaymentMethod paymentMethod = createPaymentMethod(initialDonation);
            System.out.println("Payment method created: " + paymentMethod.getClass().getSimpleName());

            Payment payment = new Payment(savedDonation.getDonationID(), paymentMethod, initialDonation.getAmount());
            System.out.println("Payment object created");

            // 4. Save payment and update donation if successful
            if (payment.pay()) {
                paymentService.addPayment(payment);
                System.out.println("Payment processed and saved");
                savedDonation.setStatus("COMPLETED");
                campaign.setCurrentAmount(campaign.getCurrentAmount() + initialDonation.getAmount());
                donationService.updateDonation(savedDonation.getDonationID(), savedDonation);
            }

            return campaignService.updateCampaign(newCampaign.getID(), campaign);
        } catch (Exception e) {
            System.err.println("Error in createCampaignWithInitialDonation: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create campaign with initial donation: " + e.getMessage(), e);
        }
    }

    public boolean processDonation(DonationRequest request) {
        try {
            // 1. Validate campaign
            System.out.println("Looking for campaign: " + request.getCampaignId());
            Campaign campaign = campaignService.getCampaignDetails(request.getCampaignId());
            System.out.println("Found campaign: " + campaign.getName());

            if (!campaign.isActive()) {
                System.out.println("Campaign is not active");
                throw new IllegalStateException("Campaign is not active");
            }

            // Get the user
            System.out.println("Looking for user: " + request.getUserId());
            User donor = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println("Found user: " + donor.getName());

            // 2. Create and save donation first
            Donation donation = new Donation();
            donation.setAmount(request.getAmount());
            donation.setMessage(request.getMessage());
            donation.setCampaign(campaign);
            donation.setUser(donor);
            donation.setCurrency(request.getCurrency());
            donation.setStatus("PENDING");
            System.out.println("Created donation object");

            // Save donation first to get its ID
            Donation savedDonation = donationService.addDonation(donation);
            System.out.println("Saved donation with ID: " + savedDonation.getDonationID());

            // 3. Process payment with saved donation ID
            PaymentMethod paymentMethod = createPaymentMethod(request);
            System.out.println("Created payment method: " + paymentMethod.getClass().getSimpleName());

            Payment payment = new Payment(savedDonation.getDonationID(), paymentMethod, request.getAmount());
            System.out.println("Created payment object");

            // 4. Save payment and update statuses if successful
            if (payment.pay()) {
                paymentService.addPayment(payment);
                System.out.println("Payment saved successfully");

                donation.setStatus("COMPLETED");
                campaign.setCurrentAmount(campaign.getCurrentAmount() + request.getAmount());
                campaign.setGoalReached(campaign.getCurrentAmount() >= campaign.getGoalAmount());

                // Save updates
                donationService.updateDonation(savedDonation.getDonationID(), donation);
                campaignService.updateCampaign(campaign.getID(), campaign);
                System.out.println("Updated donation and campaign status");

                return true;
            } else {
                System.out.println("Payment failed to process");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error processing donation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean closeCampaign(Long campaignId) {
        try {
            Campaign campaign = campaignService.getCampaignDetails(campaignId);
            if (campaign == null) return false;

            // Process final donations
            List<Donation> pendingDonations = campaign.getDonations().stream()
                    .filter(d -> "PENDING".equals(d.getStatus()))
                    .toList();

            for (Donation donation : pendingDonations) {
                // Handle pending donations (refund or process)
                donation.setStatus("REFUNDED");
                donationService.updateDonation(donation.getDonationID(), donation);

                // Refund payment if exists
                Payment payment = new Payment();
                payment.setDonationId(donation.getDonationID());
                payment.setAmount(donation.getAmount());
                payment.setStatus("REFUNDED");
                paymentService.addPayment(payment);
            }

            // Update campaign status
            campaign.setActive(false);
            campaignService.updateCampaign(campaignId, campaign);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<CampaignStatistics> getAllCampaignStatistics() {
        List<Campaign> allCampaigns = campaignService.getAllCampaigns();
        return allCampaigns.stream()
                .map(campaign -> getCampaignStatistics(campaign.getID()))
                .toList();
    }

    public CampaignStatistics getCampaignStatistics(Long campaignId) {

        try {
        Campaign campaign = campaignService.getCampaignDetails(campaignId);

        List<Donation> donations = campaign.getDonations();

        CampaignStatistics stats = new CampaignStatistics();
        stats.setTotalDonations(donations.size());
        stats.setTotalAmount(donations.stream().mapToDouble(Donation::getAmount).sum());
        stats.setAverageDonation(stats.getTotalAmount() / stats.getTotalDonations());
        stats.setCompletionPercentage((campaign.getCurrentAmount() / campaign.getGoalAmount()) * 100);
        stats.setActive(campaign.isActive());

        return stats;
    }

    catch(Exception e)
    {

        CampaignStatistics stats = new CampaignStatistics();
        stats.setTotalDonations(-1);
        stats.setTotalAmount(-1);
        stats.setAverageDonation(-1);
        stats.setCompletionPercentage(-1);
        stats.setActive(false);

        return stats;
    }
        
    }
}