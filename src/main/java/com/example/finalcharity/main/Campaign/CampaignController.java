package com.example.finalcharity.main.Campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalcharity.main.Donation.DonationService;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private Campaign model;
    private CampaignView view;

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private DonationService donationService;

    @GetMapping("/details")
    public Campaign getCampaignDetails(@RequestParam Long id) {
        return campaignService.getCampaignDetails(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        Campaign createdCampaign = campaignService.createCampaign(campaign);
        return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
    }

}
