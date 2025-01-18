package com.example.finalcharity.main.User.Admin;


import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Campaign.CampaignService;
import com.example.finalcharity.main.User.ConsoleColors;
import com.example.finalcharity.main.User.User;

import com.example.finalcharity.main.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.util.Map;

@Component
public class RealAdmin implements AdminOperations {

    @Autowired
    private final CampaignService campaignService;
    private final UserService userService;
    @Autowired
    public RealAdmin(UserService userService, CampaignService campaignService) {
        this.userService = userService;
        this.campaignService = campaignService;
    }
    @Override
    public void createUser(User newUser) {
        System.out.println("User created: " + newUser.getName());
        userService.addUser(newUser);
    }

    @Override
    public void editUser(Integer userId , User updatedUser) {

        userService.updateUser(userId, updatedUser);
    }

    @Override
    public void deleteUser(User userToDelete) {
        if (userService.deleteUser(userToDelete.getUserId())) {
            System.out.println(ConsoleColors.GREEN_BOLD + "User " + ConsoleColors.GREEN_BOLD_BRIGHT + userToDelete.getName() + ConsoleColors.GREEN_BOLD + " Deleted Successfully!" +  ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED_BOLD + "Failed to delete user: " + ConsoleColors.RED_BOLD_BRIGHT +  userToDelete.getName() + ConsoleColors.RESET);
        }
    }

    @Override
    public void createCampaign(Campaign newCampaign) {
        System.out.println("Campaign created: " + newCampaign.getName());
        // Actual campaign creation logic here
    }

    @Override
    public void editCampaign(Long campaignID,Campaign updatedCampaign) {
        campaignService.updateCampaign(campaignID, updatedCampaign);
    }

    @Override
    public void deleteCampaign(Long campaignId) {
        campaignService.deleteCampaign(campaignId);
        System.out.println(ConsoleColors.GREEN_BOLD+ "Campaign with ID " + ConsoleColors.GREEN_UNDERLINED + campaignId + ConsoleColors.RESET + ConsoleColors.GREEN_BOLD + " Deleted Successfully"  + ConsoleColors.RESET);

    }

    // @Override
    // public void viewCampaignDonations(Long campaignId) {

    //     System.out.println("Campaign deleted with ID: " + campaignId);
    // }
}
