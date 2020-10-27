package com.carespeak.domain.steps.imp;

import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.ProgramAccess;
import com.carespeak.domain.steps.ProgramSteps;
import com.carespeak.domain.ui.component.table.QuestionRowItem;
import com.carespeak.domain.ui.component.table.base.TableRowItem;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.page.programs.ProgramGeneralSettingsPage;
import com.carespeak.domain.ui.page.programs.ProgramKeywordSignupPage;
import com.carespeak.domain.ui.page.programs.ProgramMessageLogsPage;
import com.carespeak.domain.ui.page.programs.ProgramsPage;

public class ProdProgramSteps implements ProgramSteps {

    private DashboardPage dashboardPage;
    private ProgramGeneralSettingsPage programSettingsPage;
    private ProgramsPage programsPage;
    private ProgramKeywordSignupPage programKeywordSignupPage;
    private ProgramMessageLogsPage programMessageLogsPage;

    public ProdProgramSteps() {
        dashboardPage = new DashboardPage();
        programSettingsPage = new ProgramGeneralSettingsPage();
        programsPage = new ProgramsPage();
        programKeywordSignupPage = new ProgramKeywordSignupPage();
        programMessageLogsPage = new ProgramMessageLogsPage();
    }

    @Override
    public ProgramSteps addNewProgram(String clientName, String programName, ProgramAccess programAccess) {
        if (!programsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            programsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
        }

        programsPage.searchClient.search(clientName);
        programsPage.addProgramButton.click();
        programSettingsPage.programNameInput.enterText(programName);
        programSettingsPage.programAccessDropDown.select(programAccess.getValue());
        programSettingsPage.saveButton.click();
        programSettingsPage.statusPopup.waitForDisplayed();
        programSettingsPage.statusPopup.close();
        programSettingsPage.statusPopup.waitForDisappear();
        return this;
    }

    @Override
    public ProgramSteps goToProgramSettings(String clientName, String programName) {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.programsMenuItem.click();
        programsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
        programsPage.searchClient.search(clientName);

        TableRowItem programRow = programsPage.programTable.searchInTable("Name", programName);
        if (programRow == null) {
            throw new RuntimeException("Program was not found by name '" + programName + "'!");
        }
        programsPage.programTable.editFirstItemButton().click();
        return this;
    }

    @Override
    public ProgramSteps addAccountCreationQuestion(boolean isMandatory, String field, String questionText, String onErrorText) {
        if (!programKeywordSignupPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Keyword Signup");
        }

        programKeywordSignupPage.addQuestionButton.click();
        QuestionRowItem questionItem = programKeywordSignupPage.accountCreationTable.getLastQuestionRowItem();
        if (isMandatory) {
            questionItem.mandatoryQuestionCheckbox().check();
        } else {
            questionItem.mandatoryQuestionCheckbox().uncheck();
        }
        questionItem.fieldDropdown().select(field);
        questionItem.questionTextInput().enterText(questionText);
        questionItem.onErrorTextInput().enterText(onErrorText);
        programKeywordSignupPage.saveButton.click();
        programKeywordSignupPage.statusPopup.waitForDisplayed();
        programKeywordSignupPage.statusPopup.waitForDisappear();
        return this;
    }

    @Override
    public ProgramSteps addKeywordForSignUp(String keyword) {
        if (!programKeywordSignupPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Keyword Signup");
        }
        programKeywordSignupPage.keywordInput.enterText(keyword);
        programKeywordSignupPage.saveButton.click();
        return this;
    }

    @Override
    public MessageLogItem getLastMessageFromLogs() {
        programsPage.sideBarMenu.openItem("Message Logs");

        TableRowItem messageLogRow = programMessageLogsPage.messageLogsTable.getFirstRowItem();
        if (messageLogRow == null) {
            throw new RuntimeException("Message log was not found!");
        }
        MessageLogItem logItem = new MessageLogItem();
        logItem.setFlow(messageLogRow.getDataByHeader("Flow"));
        logItem.setFrom(messageLogRow.getDataByHeader("From"));
        logItem.setTo(messageLogRow.getDataByHeader("To"));
        logItem.setSent(messageLogRow.getDataByHeader("Sent"));
        logItem.setDelivery(messageLogRow.getDataByHeader("Delivery"));
        logItem.setStatus(messageLogRow.getDataByHeader("Status"));
        logItem.setMessage(messageLogRow.getDataByHeader("Message"));
        return logItem;
    }

    @Override
    public ProgramSteps goToMessageLogsForNumber(String phoneNumber) {
        programsPage.sideBarMenu.openItem("Message Logs");
        programMessageLogsPage.messageLogsTable.searchInTable("From", phoneNumber);
        return this;
    }
}
