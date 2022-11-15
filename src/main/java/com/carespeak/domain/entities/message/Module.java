package com.carespeak.domain.entities.message;

import java.util.*;

public enum Module {

    CHECK_ALL("Check all"),
    APPOINTMENT("Appointment"),
    BIOMETRIC("Biometric"),
    EDUCATION("Education"),
    INCENTIVE("Incentive"),
    JOURNAL("Journal"),
    MEDICATION("Medication"),
    MOTIVATION("Motivation"),
    RULES("Rules"),
    SURVEY("Survey");


    private String val;

    Module(String moduleType) {
        this.val = moduleType;
    }

    public String getValue() {
        return val;
    }

    //Returns all modules except Check All value
    public static List<Module> getAllModules() {
        return Arrays.asList(APPOINTMENT, BIOMETRIC, EDUCATION, JOURNAL, MEDICATION, MOTIVATION, RULES, SURVEY);
    }

    //Returns all modules except Check All value
    public static Module getModule(String shortName) {
        for (Module module : getAllModules()) {
            if (shortName.toUpperCase().substring(0, 2).equals(module.name().substring(0, 2).toUpperCase())) {
                return module;
            }
        }
        throw new NoSuchElementException("Cannot find module by short name: " + shortName);
    }

    //Returns all modules except Check All value
    public static List<Module> getModules(List<String> shortNames) {
        List<Module> res = new ArrayList<>();
        for (String shortName : shortNames) {
            res.add(getModule(shortName));
        }
        return res;
    }

}
