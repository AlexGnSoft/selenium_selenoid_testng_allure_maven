package com.carespeak.domain.entities.patient;

public enum PatientStatus {

    INACTIVE("Inactive"),
    ACTIVE("Active"),
    PENDING("Pending"),
    STOPPED("Stopped"),
    LOCKED("Locked"),
    INCOMPLETE("Incomplete"),
    DEACTIVATED("Deactivated"),
    SUSPENDED("Suspended"),
    DOSING_INCOMPLETE("Dosing Incomplete"),
    COMPLETED("Completed");

    private String val;

    PatientStatus(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
