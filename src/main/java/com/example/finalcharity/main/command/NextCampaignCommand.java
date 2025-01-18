package com.example.finalcharity.main.Command;

import com.example.finalcharity.main.Campaign.Campaign;

// Concrete Commands
public class NextCampaignCommand implements Command {
    private Campaign campaign;

    public NextCampaignCommand(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public void execute() {
        campaign.next();
    }
}
