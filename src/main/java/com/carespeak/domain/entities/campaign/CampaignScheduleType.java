package com.carespeak.domain.entities.campaign;

public enum CampaignScheduleType {

    OCCASION("Occasion"),
    JOSLIN("Joslin"),
    PROTOCOL("Protocol");

    private String val;

    private CampaignScheduleType(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }
}
