package com.carespeak.domain.entities.campaign;

public enum CampaignAccess {

    PUBLIC("Public"),
    PRIVATE("Private");

    private String val;

    private CampaignAccess(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }
}
