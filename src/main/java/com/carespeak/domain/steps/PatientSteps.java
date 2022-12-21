package com.carespeak.domain.steps;

public interface PatientSteps extends BaseSteps {

    /**
     * Creates patient list
     *
     * @param clientName        client name
     * @param patientListName   patient list name
     * @return ProgramSteps object
     */
    PatientSteps addPatientList(String clientName, String patientListName);

    /**
     * Verifies that patient list is created
     *
     * @param patientListName   patient list name
     * @return ProgramSteps object
     */
    boolean isPatientListCreated(String patientListName);

    /**
     * Add patient to patient list
     *
     * @param patientName           patient name
     * @param patientListName       patient list name
     * @return ProgramSteps object
     */
    boolean isPatientAddedToPatientList(String patientName, String patientListName);

}
