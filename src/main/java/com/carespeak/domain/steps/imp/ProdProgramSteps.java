package com.carespeak.domain.steps.imp;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import com.carespeak.domain.entities.program.ProgramOptOutForm;
import com.carespeak.domain.steps.ProgramSteps;
import com.carespeak.domain.ui.component.table.QuestionRowItem;
import com.carespeak.domain.ui.component.table.base.TableRowItem;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.page.programs.ProgramsPage;
import com.carespeak.domain.ui.page.programs.auto_responders.ProgramAutoRespondersPage;
import com.carespeak.domain.ui.page.programs.consent_management.ProgramConsentManagementPage;
import com.carespeak.domain.ui.page.programs.consent_management.ProgramOptOutFormPage;
import com.carespeak.domain.ui.page.programs.general.ProgramGeneralSettingsPage;
import com.carespeak.domain.ui.page.programs.keyword_signup.ProgramKeywordSignupPage;
import com.carespeak.domain.ui.page.programs.message_logs.ProgramMessageLogsPage;
import com.carespeak.domain.ui.page.programs.patients.ProgramsPatientsPage;
import com.carespeak.domain.ui.page.programs.patients.patients.AddPatientsPage;
import com.carespeak.domain.ui.page.programs.patients.patients.ProgramPatientsTab;

import java.util.List;

public class ProdProgramSteps implements ProgramSteps {

    private DashboardPage dashboardPage;
    private ProgramGeneralSettingsPage programSettingsPage;
    private ProgramsPage programsPage;
    private ProgramKeywordSignupPage programKeywordSignupPage;
    private ProgramMessageLogsPage programMessageLogsPage;
    private ProgramAutoRespondersPage programAutoRespondersPage;
    private ProgramsPatientsPage programsPatientsPage;
    private AddPatientsPage addPatientsPage;
    private ProgramConsentManagementPage consentManagementPage;
    private ProgramOptOutFormPage optOutFormPage;

    public ProdProgramSteps() {
        dashboardPage = new DashboardPage();
        programSettingsPage = new ProgramGeneralSettingsPage();
        programsPage = new ProgramsPage();
        programKeywordSignupPage = new ProgramKeywordSignupPage();
        programMessageLogsPage = new ProgramMessageLogsPage();
        programAutoRespondersPage = new ProgramAutoRespondersPage();
        programsPatientsPage = new ProgramsPatientsPage();
        addPatientsPage = new AddPatientsPage();
        consentManagementPage = new ProgramConsentManagementPage();
        optOutFormPage = new ProgramOptOutFormPage();
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
    public ProgramSteps addOptOutHeader(Client client, String programName, String message) {
        goToConsentManagementPage(client);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutHeaderInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    @Override
    public ProgramSteps addOptOutBody(Client client, String programName, String message) {
        goToConsentManagementPage(client);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutBodyInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    @Override
    public ProgramSteps addOptOutFooter(Client client, String programName, String message) {
        goToConsentManagementPage(client);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutFooterInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    private void goToConsentManagementPage(Client client) {
        if (!consentManagementPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            dashboardPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
            programsPage.searchClient.search(client.getName());
            programsPage.programTable.editFirstItemButton().click();
            programsPage.sideBarMenu.openItem("Consent Management");
        }
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
    public MessageLogItem getLastMessageFromLogsForNumber(String phoneNumber) {
        programsPage.sideBarMenu.openItem("Message Logs");
        programMessageLogsPage.messageLogsTable.searchInTable("From", phoneNumber);

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
    public ProgramSteps rejectUnsolicitedMessages(Client client, String programName) {
        if (!programAutoRespondersPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            programsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
            programsPage.searchClient.search(client.getName());
            programsPage.programTable.editFirstItemButton().click();
            programsPage.sideBarMenu.openItem("Auto Responders");
        }
        programAutoRespondersPage.rejectSolicitedCheckbox.check();
        programAutoRespondersPage.acceptedMessageRegexInput.enterText("Accepted");
        programAutoRespondersPage.saveButton.click();
        return this;
    }

    @Override
    public ProgramSteps addNewPatient(Patient patient, Client client, String programName) {
        if (!addPatientsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            dashboardPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
            programsPage.searchClient.search(client.getName());
            programsPage.programTable.editFirstItemButton().click();
        }
        programSettingsPage.sideBarMenu.openItem("Patients");
        ProgramPatientsTab patientsTab = programsPatientsPage.goToPatientsTab();
        patientsTab.addPatientBtn.click();
        addPatientsPage.cellPhoneInput.enterText(patient.getCellPhone());
        addPatientsPage.cellPhoneConfirmationInput.enterText(patient.getCellPhone());
        addPatientsPage.firstNameInput.enterText(patient.getFirstName());
        addPatientsPage.lastNameInput.enterText(patient.getLastName());
        addPatientsPage.emailInput.enterText(patient.getEmail());
        addPatientsPage.emailConfirmationInput.enterText(patient.getEmail());
        if (patient.getSex() != null) {
            if (patient.getSex().equals(Sex.MALE)) {
                addPatientsPage.maleRadioButtonOption.click();
            } else {
                addPatientsPage.femaleRadioButtonOption.click();
            }
        }
        addPatientsPage.timezoneDropdown.select(patient.getTimezone());
        addPatientsPage.saveButton.click();
        return this;
    }

    public void goToProgramTab() {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.programsMenuItem.click();
        waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
    }

    @Override
    public String getProgramByName(String clientName, String programName) {
        goToProgramTab();
        programsPage.searchClient.search(clientName);
        TableRowItem tableRowItem = programsPage.programTable.searchInTable("Name", programName);
        if (tableRowItem == null) {
            Logger.info("Program was not found by name '" + programName + "'!");
            return null;
        }
        return tableRowItem.getDataByHeader("Name");
    }

    @Override
    public String getProgramAccessModifier(String clientName, String accessModifier) {
        goToProgramTab();
        programsPage.searchClient.search(clientName);
        TableRowItem tableRowItem = programsPage.programTable.searchInTable("Access", accessModifier);
        if (tableRowItem == null) {
            Logger.info("Access was not found by access modifier '" + accessModifier + "'!");
            return null;
        }
        String[] access = tableRowItem.getDataByHeader("Access").split(" ");

        return access[0];
    }

    @Override
    public ProgramOptOutForm getProgramOptOutForm(String clientName, String programName) {
        if (!consentManagementPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            dashboardPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
            programsPage.searchClient.search(clientName);
            programsPage.programTable.editFirstItemButton().click();
        }
        if (consentManagementPage.statusMessage.getTitleElement().isVisible()) {
            String message = consentManagementPage.statusMessage.getTitleElement().getText();
            Logger.error("Status message showed with text - " + message);
            return null;
        }
        consentManagementPage.sideBarMenu.openItem("Consent Management");
        if (!consentManagementPage.viewFormButton.isVisible()) {
            Logger.error("View Form button is not visible!");
            return null;
        }
        consentManagementPage.viewFormButton.click();
        consentManagementPage.switchToNewTab();
        ProgramOptOutForm form = new ProgramOptOutForm();
        form.setHeader(optOutFormPage.header.getText());
        form.setBody(optOutFormPage.body.getText());
        form.setFooter(optOutFormPage.footer.getText());
        consentManagementPage.switchBack();
        return form;
    }

    @Override
    public List<String> getEndpointsOnProgramLevel() {
        dashboardPage.headerMenu.programsMenuItem.click();
        programsPage.programTable.editFirstItemButton().click();
        programSettingsPage.programEndpointDropdown.click();
        return programSettingsPage.getEndpoints();
    }

    @Override
    public List<String> getEndpointsOnPatientLevel() {
        waitFor(() -> programsPage.programTable.editFirstItemButton().isDisplayed());
        programsPage.programTable.editFirstItemButton().click();
        addPatientsPage.endpointDropdown.click();
        return addPatientsPage.getEndpoints();
    }
}
