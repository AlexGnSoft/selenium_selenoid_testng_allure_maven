package com.carespeak.domain.entities.message;

public enum Action {

    TIMED_ALERT("Timed Alert"),
    CONDITIONAL_ALERT("Timed Alert"),
    TIMED_CONDITIONAL_ALERT("Timed Conditional Alert"),
    ESCALATION_ALERT("Escalation Alert");

    private String val;

    Action(String action) {
        this.val = action;
    }

    public String getValue() {
        return val;
    }
}
