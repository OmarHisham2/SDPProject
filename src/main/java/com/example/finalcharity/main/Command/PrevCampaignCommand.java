package com.example.finalcharity.main.Command;

import com.example.finalcharity.main.Campaign.Campaign;

public class PrevCampaignCommand implements Command {
    private Campaign campaign;

    public PrevCampaignCommand(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public void execute() {
        campaign.prev();
    }
}
