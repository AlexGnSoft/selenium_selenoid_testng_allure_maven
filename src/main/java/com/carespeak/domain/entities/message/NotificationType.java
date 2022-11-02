package com.carespeak.domain.entities.message;

public enum NotificationType {
    DEPRESSION("Depression"),
    GENERAL("General"),
    PAIN("Pain"),
    STRESS("Stress");

    private String val;

    private NotificationType(String notification) {
        this.val = notification;
    }

    public String getValue() {
        return val;
    }




}
