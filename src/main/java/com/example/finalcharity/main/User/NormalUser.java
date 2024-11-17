package com.example.finalcharity.main.User;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Donation.Donation;


@Entity
@Table(name = "normal_users")  // Changed table name to 'normal_users'
public class NormalUser extends User {
    @OneToMany
    private List<Donation> donations = new ArrayList<>();

    @OneToMany
    private List<Campaign> campaigns = new ArrayList<>();

    public NormalUser(int userId, String name, String email, String phone) {
        super(userId, name, email, phone);
    }

    // No-argument constructor required by JPA
    public NormalUser() {
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
        // Registration logic for NormalUser
    }
}