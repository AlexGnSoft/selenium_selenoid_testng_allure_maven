package com.carespeak.domain.steps;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import com.carespeak.domain.entities.program.ProgramOptOutForm;

public interface ProgramSteps extends BaseSteps {

    /**
     * Creates new Program for provided client.
     *
     * @param clientName    - client name
     * @param programName   - program name
     * @param programAccess - program access type
     * @return ProgramSteps object
     */
    ProgramSteps addNewProgram(String clientName, String programName, ProgramAccess programAccess);

    /**
     * Go to program with specified name.
     *
     * @param clientName  - client name
     * @param programName - program name
     * @return ProgramSteps object
     */
    ProgramSteps goToProgramSettings(String clientName, String programName);

    /**
     * Add account creation question for keyword sign up.
     *
     * @param isMandatory  - is question mandatory ?
     * @param field        - field name to receive from client's response
     * @param questionText - question to send
     * @param onErrorText  - text to show on error
     * @return ProgramSteps object
     */
    ProgramSteps addAccountCreationQuestion(boolean isMandatory, String field, String questionText, String onErrorText);

    /**
     * Add create account questions.
     *
     * @param keyword
     * @return Program Steps object
     */
    ProgramSteps addKeywordForSignUp(String keyword);


    /**
     * Returns last message from Programs Messages Logs for specified phone number
     *
     * @param number - phoneNumber phone number to analyze
     * @return Messages object
     */
    MessageLogItem getLastMessageFromLogsForNumber(String number);

    /**
     * Reject unsolicited message for specified program and client
     *
     * @param client      - client to use
     * @param programName - program to be select
     * @return Program Steps object
     */
    ProgramSteps rejectUnsolicitedMessages(Client client, String programName);

    /**
     * Add patient to specific program
     *
     * @param client      - client to use
     * @param programName - program to be select
     * @return Program Steps object
     */
    ProgramSteps addNewPatient(Patient patient, Client client, String programName);


    /**
     * Get program by name from column
     *
     * @param clientName  - client to use
     * @param programName - program name
     * @return program name String
     */
    String getProgramByName(String clientName, String programName);


    /**
     * Get program access modifier from column
     *
     * @param clientName    - client to use
     * @param programAccess - program access level
     * @return access modifier String
     */
    String getProgramAccessModifier(String clientName, String programAccess);


    /**
     * Returns OptOut form that set on program level
     *
     * @param clientName - client to use
     * @return ProgramOptOutForm object
     */
    ProgramOptOutForm getProgramOptOutForm(String clientName, String programName);
}
