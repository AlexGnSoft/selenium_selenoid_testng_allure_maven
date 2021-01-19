package com.carespeak.domain.entities.program;

public enum AutoRespondersStatus {

    DISABLED("Disabled"),
    OVERRIDDEN("Overridden"),
    INHERITED("Inherited");

    private String value;

    AutoRespondersStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
