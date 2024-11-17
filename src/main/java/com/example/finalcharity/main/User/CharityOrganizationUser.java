package com.example.finalcharity.main.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Donation.Donation;

import jakarta.persistence.*;


@Entity
@Table(name = "charity_organization_users")  // Changed table name to 'charity_organization_users'
public class CharityOrganizationUser extends User {
    @OneToMany
    private List<Donation> donations = new ArrayList<>();

    @OneToMany
    private List<Campaign> campaigns = new ArrayList<>();

    public CharityOrganizationUser(int userId, String name, String email, String phone) {
        super(userId, name, email, phone);
    }

    // No-argument constructor required by JPA
    public CharityOrganizationUser() {
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    @Override
    public void register(Map<String, String> userData) {
        // Registration logic for CharityOrganizationUser
    }
}