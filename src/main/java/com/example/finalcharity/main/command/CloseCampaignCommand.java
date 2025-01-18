package com.example.finalcharity.main.Command;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Campaign.CampaignClosedState;

// Concrete Commands
public class CloseCampaignCommand implements Command {
    private Campaign campaign;

    public CloseCampaignCommand(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public void execute() {
        campaign.setState(new CampaignClosedState());
    }
}
