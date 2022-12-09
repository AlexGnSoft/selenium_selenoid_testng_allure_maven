package com.carespeak.domain.entities.campaign;

public enum CampaignAnchor {

    EVENT_DATE("Event Date"),
    FIXED_DATE("Fixed Date");

    private String val;

    private CampaignAnchor(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }
}
