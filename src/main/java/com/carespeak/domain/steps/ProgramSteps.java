package com.carespeak.domain.steps;

import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.ProgramAccess;

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
     * @return
     */
    ProgramSteps addKeywordForSignUp(String keyword);


    /**
     * Returns last message from Programs Messages Logs.
     *
     * @return Messages object
     */
    MessageLogItem getLastMessageFromLogs();


    ProgramSteps goToMessageLogsForNumber(String number);

}
