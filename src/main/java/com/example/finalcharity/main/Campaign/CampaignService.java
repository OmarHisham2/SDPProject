package com.example.finalcharity.main.Campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.Donation.DonationService;
import com.example.finalcharity.main.Sort.NewestDonationsFirst;
import com.example.finalcharity.main.Sort.SortingService;

import com.example.finalcharity.main.Command.*;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final DonationService donationService;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, @Lazy DonationService donationService) {
        this.campaignRepository = campaignRepository;
        this.donationService = donationService;
    }

    public Campaign createCampaign(Campaign campaign) {
        System.out.println("Creating campaign with requested active status: " + campaign.isActive());
        Campaign newCampaign = campaignRepository.save(campaign);
        System.out.println("Saved campaign with active status: " + newCampaign.isActive());
        return newCampaign;
    }

    public void donateToCampaign(Campaign campaign, double amount, int userNotificationLevel) {

        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setCampaign(campaign);
        donationService.addDonation(donation);
    }

    public Campaign getCampaignDetails(Long id) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found with ID: " + id));

        List<Donation> donations = donationService.getSortedDonations(
                new SortingService(new NewestDonationsFirst()), id);

        campaign.setDonations(donations);

        return campaign;
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll(); // Assuming JPA Repository
    }



    public Campaign updateCampaign(Long id, Campaign updatedCampaign) {
        // Fetch existing campaign
        Campaign existingCampaign = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found with ID: " + id));

        // Update fields
        existingCampaign.setName(updatedCampaign.getName());
        existingCampaign.setDescription(updatedCampaign.getDescription());
        existingCampaign.setGoalAmount(updatedCampaign.getGoalAmount());
        existingCampaign.setCurrentAmount(updatedCampaign.getCurrentAmount());
        existingCampaign.setActive(updatedCampaign.isActive());
        existingCampaign.setGoalReached(updatedCampaign.isGoalReached());

        // Save the updated campaign
        return campaignRepository.save(existingCampaign);
    }

    public void deleteCampaign(Long id) {
        // Fetch the campaign to ensure it exists
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found with ID: " + id));

        // Delete the campaign
        campaignRepository.delete(campaign);
    }

    public Optional<Campaign> getCampaignById(Long campaignId) {
        return campaignRepository.findById(campaignId);
    }

    public void nextCampaign(Campaign campaign) {
        Command command = new NextCampaignCommand(campaign);
        command.execute();
        campaignRepository.save(campaign);
    }

    public void prevCampaign(Campaign campaign) {
        Command command = new PrevCampaignCommand(campaign);
        command.execute();
        campaignRepository.save(campaign);
    }
}