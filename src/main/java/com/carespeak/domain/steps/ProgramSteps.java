package com.carespeak.domain.steps;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.AutoRespondersStatus;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import com.carespeak.domain.entities.program.ProgramOptOutForm;
import com.carespeak.domain.steps.imp.prod.ProdProgramSteps;

import java.util.List;

public interface ProgramSteps extends BaseSteps {

    /**
     * Creates new Program for provided client.
     *
     * @param clientName    client name
     * @param programName   program name
     * @param programAccess program access type
     * @return ProgramSteps object
     */
    ProgramSteps addNewProgram(String clientName, String programName, ProgramAccess programAccess);


    /**
     * Adds OptOut Header for specified client and program with specified message
     *
     * @param client      client to use
     * @param programName program name
     * @param message     message to add
     * @return ProgramSteps object
     */
    ProgramSteps addOptOutHeader(Client client, String programName, String message);


    /**
     * Adds OptOut Body for specified client and program with specified message
     *
     * @param client      client to use
     * @param programName program name
     * @param message     message to add
     * @return ProgramSteps object
     */
    ProgramSteps addOptOutBody(Client client, String programName, String message);


    /**
     * Adds OptOut Footer for specified client and program with specified message
     *
     * @param client      client to use
     * @param programName program name
     * @param message     message to add
     * @return ProgramSteps object
     */
    ProgramSteps addOptOutFooter(Client client, String programName, String message);


    /**
     * Go to program with specified name.
     *
     * @param clientName  client name
     * @param programName program name
     * @return ProgramSteps object
     */
    ProgramSteps goToProgramSettings(String clientName, String programName);

    /**
     * Returns all program names as list for specified client
     *
     * @param client client to use
     * @return list of program names as String
     */
    List<String> getProgramsForClient(Client client);

    /**
     * Add destination program question keywords
     *
     * @param keyword     keyword to move patient
     * @param programName destination program name
     * @return Program Steps object
     */
    ProgramSteps addDestinationProgramQuestionKeywords(String keyword, String programName);

    /**
     * Move patient to another program manually
     *
     * @param clientName               client first name
     * @param landingProgramName       destination program name
     * @param patientFirstName         patient first name
     * @return Program Steps object
     */
    ProgramSteps movePatientManually(String clientName, String landingProgramName, String patientFirstName);

    /**
     * Add account creation question for keyword sign up.
     *
     * @param isMandatory  is question mandatory ?
     * @param field        field name to receive from client's response
     * @param questionText question to send
     * @param onErrorText  text to show on error
     * @return ProgramSteps object
     */
    ProgramSteps addAccountCreationQuestion(boolean isMandatory, String field, String questionText, String onErrorText);

    /**
     * Add create account questions.
     *
     * @param keyword keyword to sign up
     * @return Program Steps object
     */
    ProgramSteps addKeywordForSignUp(String keyword);

    /**
     * @param fieldName custom field name to select in the dropdown menu in the question field
     * @return Program Steps object
     */
    ProgramSteps addCustomFields(String fieldName);

    /**
     * Add validation message for keyword sign up
     *
     * @param validationMessage validation message
     * @return Program Steps object
     */
    ProgramSteps addValidationMessage(String validationMessage);


    /**
     * Add completed message for keyword signup
     *
     * @param completedMessage completed message
     * @return Program Steps object
     */
    ProgramSteps addCompletedMessage(String completedMessage);

    /**
     * Returns last message from Programs Messages Logs for specified phone number
     *
     * @param number phoneNumber phone number to analyze
     * @return Messages object
     */
    MessageLogItem getLastMessageFromLogsForNumber(String number);

    /**
     * Reject unsolicited message for specified program and client
     *
     * @param client         client to use
     * @param programName    program to be select
     * @param messagePattern accepted message pattern
     * @return Program Steps object
     */
    ProgramSteps rejectUnsolicitedMessages(Client client, String programName, String messagePattern);

    /**
     * Removes specified program for specified client
     *
     * @param client      client to use
     * @param programName program to remove
     * @return Program Steps object
     */
    ProgramSteps removeProgram(Client client, String programName);

    /**
     * Add patient to specific program
     *
     * @param client      client to use
     * @param programName program to be select
     * @return Program Steps object
     */
    ProgramSteps addNewPatient(Patient patient, Client client, String programName);

    /**
     * Add patient to specific program with Last name, Email, Sex
     *
     * @param client      client to use
     * @param programName program to be select
     * @return Program Steps object
     */
    ProgramSteps addNewPatientAllFields(Patient patient, Client client, String programName);


    /**
     * Get program by name from column
     *
     * @param clientName  client to use
     * @param programName program name
     * @return program name String
     */
    String getProgramByName(String clientName, String programName);

    /**
     * Get program by name from column
     *
     * @param clientName  client to use
     * @param programName program name
     *  @param patientName patient name
     * @return program name String
     */
    String getPatientByName(String clientName, String programName, String patientName);


    /**
     * Get program access modifier from column
     *
     * @param clientName    client to use
     * @param programAccess program access level
     * @return access modifier String
     */
    String getProgramAccessModifier(String clientName, String programAccess);


    /**
     * Returns OptOut form that set on program level
     *
     * @param clientName client to use
     * @return ProgramOptOutForm object
     */
    ProgramOptOutForm getProgramOptOutForm(String clientName, String programName);

    /**
     * Get all program endpoints on program level
     *
     * @param programName name of program to use
     * @return List of endpoints
     */
    List<String> getEndpointsOnProgramLevel(String programName);

    /**
     * Get all program endpoints on patient level
     *
     * @param programName name of program to get endpoints
     * @param patient     patient to get endpoints
     * @return List of endpoints
     */
    List<String> getEndpointsOnPatientLevel(Client client, String programName, Patient patient);


    /**
     * Add program optin messages with no attachment
     *
     * @param isSendConfirmMessage true if want to send confirm message, false if not
     * @return Program Steps object
     */
    ProgramSteps addOptInMessagesWithoutAttachment(boolean isSendConfirmMessage);

    /**
     * Add program optin messages with attachment
     *
     * @param filePath             the path to the data file
     * @param isSendConfirmMessage true if want to send confirm message, false if not
     * @return Program Steps object
     */
    ProgramSteps addOptInMessagesWithAttachment(String filePath, boolean isSendConfirmMessage);


    /**
     * Select data file
     *
     * @param filePath the path to the data file
     */
    void selectFile(String filePath);


    /**
     * Simulate response from Program Patient Message Logs
     *
     * @param message          text to send
     * @param patientFirstName patient who will have a simulation
     * @return Message object
     */
    ProgramSteps simulateResponse(String patientFirstName, String message);

    /**
     * Simulate response and get the last Patient Message Log
     *
     * @param message          text to send
     * @param patient          patient who will have a simulation
     * @return Message object
     */
    MessageLogItem simulateResponseAndGetLastPatientMessage(Patient patient, String message);

    /**
     * Get the last Patient Message from Logs
     *
     * @param patient          patient who will have a simulation
     * @return Message object
     */
    MessageLogItem getLastPatientMessage(Patient patient);


    /**
     * Returns last message from Programs Patient Messages Logs
     *
     * @param patientFirstName patient to be select
     * @return Messages object
     */
    MessageLogItem getLastPatientMessageFromLogs(String patientFirstName);

    /**
     * Returns true if patient is in the specified program otherwise false
     *
     * @param newProgramName new program name
     * @param patientName        patient to be select
     * @return return true if patient is in the specified program otherwise false
     */
    boolean isInProgram(String newProgramName, String patientName);

    /**
     * Returns true if attached image is displayed otherwise false
     *
     * @return true if attached image is displayed otherwise false
     */
    boolean isAttachedImageDisplayed(String patientName);

    /**
     * Add new Auto Responder on program level
     *
     * @param client      client to use
     * @param programName program name
     * @param status      status autoresponders
     * @param message     message for Auto response
     * @return Program Steps Object
     */
    ProgramSteps addAutoResponder(Client client, String programName, AutoRespondersStatus status, String message);

    List<Patient> getPatients(Client client, String programName);

    ProgramSteps pageRefresh();
}
