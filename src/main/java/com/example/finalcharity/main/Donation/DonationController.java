package com.example.finalcharity.main.Donation;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.finalcharity.main.Campaign.*;
import com.example.finalcharity.main.Sort.HighestDonationsFirst;
import com.example.finalcharity.main.Sort.NewestDonationsFirst;
import com.example.finalcharity.main.Sort.OldestDonationsFirst;
import com.example.finalcharity.main.Sort.SortingService;
import com.example.finalcharity.main.User.*;

import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    private final DonationService donationService;
    private final UserService userService;
    private final CampaignService campaignService;

    @Autowired
    public DonationController(DonationService donationService, UserService userService, CampaignService campaignService) {
        this.donationService = donationService;
        this.userService = userService;
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<Donation> getDonations() {
        return donationService.getDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDonationById(@PathVariable Long id) {
        Optional<Donation> donation = donationService.getDonationById(id);
        if (donation.isPresent()) {
            return new ResponseEntity<>(convertToDTO(donation.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Donation not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createDonation( @RequestBody DonationDTO donation, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        // Check if user exists
        Optional<User> user = userService.getUserById(donation.getUserId());
        if (!user.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Check if campaign exists
        Optional<Campaign> campaign = campaignService.getCampaignById(donation.getCampaignId());
        if (!campaign.isPresent()) {
            return new ResponseEntity<>("Campaign not found", HttpStatus.NOT_FOUND);
        }

        // Set user and campaign to the donation entity
        Donation donationEntity = convertToEntity(donation);
        donationEntity.setUser(user.get());
        donationEntity.setCampaign(campaign.get());
        
        Donation createdDonation = donationService.addDonation(donationEntity);
        return new ResponseEntity<>(createdDonation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDonation(@PathVariable Long id, @Valid @RequestBody Donation donation,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<Donation> updatedDonation = donationService.updateDonation(id, donation);
        if (updatedDonation.isPresent()) {
            return new ResponseEntity<>(updatedDonation.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Donation not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonation(@PathVariable Long id) {
        boolean isDeleted = donationService.deleteDonation(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Donation not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/donations/sorted")
    public List<Donation> getSortedDonations(
            @RequestParam String sortBy,
            @RequestParam Long campaignId) {
        SortingService sortingService;

        // Select the sorting strategy based on the query parameter
        switch (sortBy.toLowerCase()) {
            case "oldest":
                sortingService = new SortingService(new OldestDonationsFirst());
                break;
            case "highest":
                sortingService = new SortingService(new HighestDonationsFirst());
                break;
            case "newest":
                sortingService = new SortingService(new NewestDonationsFirst());
            default:
                sortingService = new SortingService(new NewestDonationsFirst());
        }

        // Fetch sorted donations for the specified campaignID
        return donationService.getSortedDonations(sortingService, campaignId);
    }

private DonationDTO convertToDTO(Donation donation) {
        DonationDTO donationDTO = new DonationDTO();
        donationDTO.setDonationID(donation.getDonationID());
        donationDTO.setAmount(donation.getAmount());
        donationDTO.setStatus(donation.getStatus());
        donationDTO.setUserId(donation.getUser().getUserId());
        donationDTO.setMessage(donation.getMessage());
        donationDTO.setCurrency(donation.getCurrency());
        donationDTO.setCampaignId(donation.getCampaign().getID());
        return donationDTO;
    }

    private Donation convertToEntity(DonationDTO donationDTO) {
        Donation donation = new Donation();
        donation.setDonationID(donationDTO.getDonationID());
        donation.setAmount(donationDTO.getAmount());
        donation.setStatus(donationDTO.getStatus());
        // Set user and campaign using their IDs
        // ...
        return donation;
    }
}
