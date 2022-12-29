package com.carespeak.domain.entities.program;

public enum ProgramType {

    APPOINTMENTS("Appointments"),
    REGULAR_PROGRAM("Regular Program"),
    CAREGIVER_PROGRAM("Caregiver program");

    private String val;

    private ProgramType(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }
}
