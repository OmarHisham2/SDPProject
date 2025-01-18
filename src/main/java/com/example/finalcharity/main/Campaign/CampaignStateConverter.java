package com.example.finalcharity.main.Campaign;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CampaignStateConverter implements AttributeConverter<CampaignState, String> {

    @Override
    public String convertToDatabaseColumn(CampaignState campaignState) {
        if (campaignState == null) {
            return null;
        }

        if (campaignState instanceof CampaignDraftState) {
            return "DRAFT";

        } else if (campaignState instanceof CampaignPublishedState) {
            return "PUBLISHED";

        }
        else if (campaignState instanceof CampaignClosedState) {
            return "CLOSED";
        }

        // Throw an exception if we encounter an unknown CampaignState type
        throw new IllegalArgumentException("Unknown CampaignState implementation: " + campaignState.getClass());
    }

    @Override
    public CampaignState convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "DRAFT":
                return new CampaignDraftState();
            case "PUBLISHED":
                return new CampaignPublishedState();
            case "CLOSED":
                return new CampaignClosedState();

            default:
                // If we encounter an unrecognized database value, throw an exception
                throw new IllegalArgumentException("Unknown database value for CampaignState: " + dbData);
        }
    }
}
