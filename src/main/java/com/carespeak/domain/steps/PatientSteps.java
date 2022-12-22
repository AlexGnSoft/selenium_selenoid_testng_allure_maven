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
     * To verify that patient is added to patient list
     *
     * @param patientName           patient name
     * @param patientListName       patient list name
     * @return ProgramSteps object
     */
    boolean isPatientAddedToPatientList(String patientName, String patientListName);

    /**
     * To verify that patient is deleted from patient list
     *
     * @param patientName           patient name
     * @param patientListName       patient list name
     * @return ProgramSteps object
     */
    PatientSteps deletePatientFromPatientList(String patientName, String patientListName);

    /**
     * To verify that patient is deleted from patient list
     *
     * @param patientName           patient list name
     * @param patientName           patient name
     * @return ProgramSteps object
     */
    boolean isPatientDeletedFromUsersPage(String programName, String patientName);

    /**
     * To verify that patients are added to patient list
     *
     * @param patientsNumber           number of patients
     * @param patientFirst             first Patient
     * @param patientSecond            second Patient
     * @return ProgramSteps object
     */
    boolean arePatientsAddedToPatientList(int patientsNumber, String patientFirst, String patientSecond);

}
