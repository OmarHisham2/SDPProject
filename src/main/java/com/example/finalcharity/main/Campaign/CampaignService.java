package com.example.finalcharity.main.Campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.Donation.DonationService;
import com.example.finalcharity.main.Sort.NewestDonationsFirst;
import com.example.finalcharity.main.Sort.SortingService;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final DonationService donationService;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, DonationService donationService) {
        this.campaignRepository = campaignRepository;
        this.donationService = donationService;
    }

    public Campaign createCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public void donateToCampaign(Long campaignId, double amount, int userNotificationLevel) {

        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setCampaignId(campaignId);

    }

    public Campaign getCampaignDetails(Long id) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found with ID: " + id));

        List<Donation> donations = donationService.getSortedDonations(
                new SortingService(new NewestDonationsFirst()), id);

        campaign.setDonations(donations);

        return campaign;
    }

}