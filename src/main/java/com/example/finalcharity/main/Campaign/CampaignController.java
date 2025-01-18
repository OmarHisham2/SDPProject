package com.example.finalcharity.main.Campaign;

import com.example.finalcharity.main.facade.CharityOperationsFacade;
import com.example.finalcharity.main.facade.dto.CampaignCreationRequest;
import com.example.finalcharity.main.facade.dto.DonationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.finalcharity.main.facade.dto.CampaignStatistics;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    private final CampaignService campaignService;
    private final CharityOperationsFacade charityFacade;

    @Autowired
    public CampaignController(CampaignService campaignService,
                              CharityOperationsFacade charityFacade) {
        this.campaignService = campaignService;
        this.charityFacade = charityFacade;
    }

    @GetMapping("/details")
    public ResponseEntity<Campaign> getCampaignDetails(@RequestParam Long id) {
        try {
            Campaign campaign = campaignService.getCampaignDetails(id);
            return new ResponseEntity<>(campaign, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        try {
            Campaign createdCampaign = campaignService.createCampaign(campaign);
            return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-with-donation")
    public ResponseEntity<Campaign> createCampaignWithDonation(
            @RequestBody CampaignCreationRequest request) {
        try {
            Campaign created = charityFacade.createCampaignWithInitialDonation(
                    request.getCampaign(), request.getInitialDonation());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable Long id,
            @RequestBody Campaign updatedCampaign) {
        try {
            Campaign updated = campaignService.updateCampaign(id, updatedCampaign);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        try {
            campaignService.deleteCampaign(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<String> closeCampaign(@PathVariable Long id) {
        try {
            boolean success = charityFacade.closeCampaign(id);
            if (success) {
                return new ResponseEntity<>("Campaign closed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to close campaign",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error closing campaign: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<CampaignStatistics> getCampaignStatistics(@PathVariable Long id) {
        try {
            CampaignStatistics stats = charityFacade.getCampaignStatistics(id);
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/next")
    public ResponseEntity<String> nextCampaignState(@PathVariable Long id) {
        try {
            Campaign campaign = campaignService.getCampaignById(id).get(); // Fetch campaign from database
            campaignService.nextCampaign(campaign);
            return ResponseEntity
                    .ok(String.format("Campaign state is %s", campaign.getState().getClass().getSimpleName()));
        } catch (Exception e) {
            return ResponseEntity.ok("Campaign not found.");
        }
    }

    @PostMapping("/{id}/prev")
    public ResponseEntity<String> prevCampaignState(@PathVariable Long id) {
        try {
            Campaign campaign = campaignService.getCampaignById(id).get(); // Fetch campaign from database
            campaignService.prevCampaign(campaign);
            return ResponseEntity
                    .ok(String.format("Campaign state is %s", campaign.getState().getClass().getSimpleName()));
        } catch (Exception e) {
            return ResponseEntity.ok("Campaign not found.");
        }
    }
}