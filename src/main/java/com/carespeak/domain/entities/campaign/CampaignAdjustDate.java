package com.carespeak.domain.entities.campaign;

public enum CampaignAdjustDate {

    NEXT_MONDAY("Next Monday"),
    NEXT_TUESDAY("Next Tuesday"),
    NEXT_WEDNESDAY("Next Wednesday"),
    NEXT_THURSDAY("Next Thursday"),
    NEXT_FRIDAY("Next Friday"),
    NEXT_SATURDAY("Next Saturday"),
    NEXT_SUNDAY("Next Sunday");

    private String val;

    private CampaignAdjustDate(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }
}
