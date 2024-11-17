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

    // PUT endpoint to update an existing campaign
    @PutMapping("/update/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign updatedCampaign) {
        Campaign updated = campaignService.updateCampaign(id, updatedCampaign);
        return new ResponseEntity<>(updated, HttpStatus.OK); // Return status 200 OK
    }

    // DELETE endpoint to delete a campaign
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return status 204 No Content
    }

    public void updateView() {
        view.displayCampaignDetails(model.getName(), model.getDescription(), model.getGoalAmount(),
                model.getCurrentAmount());
    }

}
