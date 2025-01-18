package com.example.finalcharity.main.User.Admin;


import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.User.AdminUser;
import com.example.finalcharity.main.User.ConsoleColors;
import com.example.finalcharity.main.User.User;

import java.util.Map;

public class AdminProxy implements AdminOperations {

    private final User user;
    private final RealAdmin realAdmin;

    public AdminProxy(User user, RealAdmin realAdmin) {
        this.user = user;
        this.realAdmin = realAdmin;
    }

    private void validateAdminAccess() {
        if (!(user instanceof AdminUser)) {
            throw new SecurityException("Access denied. Only admin users can perform this action.");
        }
    }

    @Override
    public void createUser(User newUser) {
        validateAdminAccess();
        realAdmin.createUser(newUser);
    }

    @Override
    public void editUser(Integer userId , User updatedUser) {
        validateAdminAccess();
        realAdmin.editUser(userId,updatedUser);
    }

    @Override
    public void deleteUser(User userToDelete) {
        validateAdminAccess();
        realAdmin.deleteUser(userToDelete);
    }

    @Override
    public void createCampaign(Campaign newCampaign) {
        validateAdminAccess();
        realAdmin.createCampaign(newCampaign);
    }

    @Override
    public void editCampaign(Long campaignID,Campaign updatedCampaign) {
        validateAdminAccess();
        realAdmin.editCampaign(campaignID, updatedCampaign);
    }

    @Override
    public void deleteCampaign(Long campaignId) {
        validateAdminAccess();
        try{
            realAdmin.deleteCampaign(campaignId);

        }
        catch(Exception e )
        {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid ID.\t" + ConsoleColors.RED_BOLD + "Campaign Not Found "  + ConsoleColors.RESET);
        }
    }

    // @Override
    // public void viewCampaignDonations(Long campaignId) {
    //     validateAdminAccess();
    //     realAdmin.viewCampaignDonations(campaignId);
    // }
}
