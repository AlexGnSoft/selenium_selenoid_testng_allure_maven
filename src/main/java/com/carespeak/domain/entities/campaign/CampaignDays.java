package com.carespeak.domain.entities.campaign;

public enum CampaignDays {

    BEFORE("Before"),
    AFTER("After");

    private String val;

    private CampaignDays(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }
}
