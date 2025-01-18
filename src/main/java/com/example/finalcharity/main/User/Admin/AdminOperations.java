package com.example.finalcharity.main.User.Admin;

import java.util.Map;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.User.User;

public interface AdminOperations {
    
void createUser(User newUser);

 void editUser(Integer userId , User updatedUser);
    void deleteUser(User userToDelete);

    void createCampaign(Campaign newCampaign);

     void editCampaign(Long campaignID,Campaign updatedCampaign);

    void deleteCampaign(Long campaignId);

    // void viewCampaignDonations(ٍLong campaignID);

    // void deleteCampaignDonations(Donation donation);
    
} 