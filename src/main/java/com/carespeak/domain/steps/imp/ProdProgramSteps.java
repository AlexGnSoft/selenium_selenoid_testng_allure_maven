package com.carespeak.domain.steps.imp;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.AutoRespondersStatus;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import com.carespeak.domain.entities.program.ProgramOptOutForm;
import com.carespeak.domain.steps.ProgramSteps;
import com.carespeak.domain.ui.component.container.AutoResponderContainer;
import com.carespeak.domain.ui.component.table.QuestionRowItem;
import com.carespeak.domain.ui.component.table.base.TableRowItem;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.page.programs.ProgramsPage;
import com.carespeak.domain.ui.page.programs.auto_responders.ProgramAutoRespondersPage;
import com.carespeak.domain.ui.page.programs.consent_management.ProgramConsentManagementPage;
import com.carespeak.domain.ui.page.programs.consent_management.ProgramOptOutFormPage;
import com.carespeak.domain.ui.page.programs.custom_fields.ProgramCustomFieldsPage;
import com.carespeak.domain.ui.page.programs.general.ProgramGeneralSettingsPage;
import com.carespeak.domain.ui.page.programs.keyword_signup.ProgramKeywordSignupPage;
import com.carespeak.domain.ui.page.programs.message_logs.ProgramMessageLogsPage;
import com.carespeak.domain.ui.page.programs.opt_in_messages.OptInMessagesPage;
import com.carespeak.domain.ui.page.programs.patient_message_logs.ProgramPatientMessageLogsPage;
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
    private OptInMessagesPage optInMessagesPage;
    private ProgramPatientMessageLogsPage patientMessageLogsPage;
    private ProgramCustomFieldsPage programCustomFieldsPage;

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
        optInMessagesPage = new OptInMessagesPage();
        patientMessageLogsPage = new ProgramPatientMessageLogsPage();
        programCustomFieldsPage = new ProgramCustomFieldsPage();
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
    public ProgramSteps addDestinationProgramQuestionKeywords(String keyword, String programName) {
        if (!programKeywordSignupPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Keyword Signup");
        }

        programKeywordSignupPage.addDestinationProgramKeywordBtn.click();
        programKeywordSignupPage.destinationProgramKeywordInput.enterText(keyword);

        programKeywordSignupPage.destinationProgramDropdownButton.click();
        programKeywordSignupPage.selectProgramByName(programName);
        programKeywordSignupPage.saveButton.click();
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
    public ProgramSteps addCustomFields(String fieldName) {
        if (!programCustomFieldsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Custom Fields");
        }
        programCustomFieldsPage.addCustomFieldBtn.click();
        programCustomFieldsPage.fieldNameInput.enterText(fieldName);
        programCustomFieldsPage.saveButton.click();
        programCustomFieldsPage.statusPopup.waitForDisplayed();
        programCustomFieldsPage.statusPopup.waitForDisappear();
        return this;
    }

    @Override
    public ProgramSteps addValidationMessage(String validationMessage) {
        if (!programKeywordSignupPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Keyword Signup");
        }

        programKeywordSignupPage.validationMessageButton.click();
        programKeywordSignupPage.validationMessageInput.enterText(validationMessage);
        programKeywordSignupPage.saveButton.click();
        return this;
    }

    @Override
    public ProgramSteps addCompletedMessage(String completedMessage) {
        if (!programKeywordSignupPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Keyword Signup");
        }

        programKeywordSignupPage.completedMessageButton.click();
        programKeywordSignupPage.completedMessageInput.enterText(completedMessage);
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
    public ProgramSteps rejectUnsolicitedMessages(Client client, String programName, String messagePattern) {
        if (!programAutoRespondersPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            programsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
            programsPage.searchClient.search(client.getName());
            programsPage.programTable.editFirstItemButton().click();
            programsPage.sideBarMenu.openItem("Auto Responders");
        }
        programAutoRespondersPage.rejectSolicitedCheckbox.check();
        programAutoRespondersPage.acceptedMessageRegexInput.enterText(messagePattern);
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

    @Override
    public ProgramSteps addOptInMessages(String filePath, boolean isSendConfirmMessage) {
        if (!optInMessagesPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Opt-in Messages");
        }
        selectFile(filePath);
        optInMessagesPage.uploadButton.click();

        if (isSendConfirmMessage) {
            optInMessagesPage.dontSendMessageCheckBox.uncheck();
        } else {
            optInMessagesPage.dontSendMessageCheckBox.check();
        }

        optInMessagesPage.saveButton.click();
        optInMessagesPage.addOptInMessagePopup.closePopupButton.click();
        optInMessagesPage.addOptInMessagePopup.waitForDisappear();
        return this;
    }

    @Override
    public void selectFile(String filePath) {
        optInMessagesPage.selectButton.sendKeys(filePath);
    }

    private void selectPatientByName(String patientFirstName) {
        programsPatientsPage.goToPatientsTab()
                .selectPatientByName(patientFirstName);
    }

    @Override
    public ProgramSteps simulateResponse(String patientFirstName, String message) {
        if (!patientMessageLogsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Patients");
            selectPatientByName(patientFirstName);
        }
        waitFor(() -> patientMessageLogsPage.simulateResponseBtn.isDisplayed());
        patientMessageLogsPage.simulateResponseBtn.click();
        patientMessageLogsPage.simulateResponsePopup.waitForDisplayed();
        patientMessageLogsPage.simulateResponsePopup.responseInput.enterText(message);
        patientMessageLogsPage.simulateResponsePopup.sendButton.click();
        waitFor(() -> patientMessageLogsPage.simulateResponseBtn.isDisplayed());
        return this;
    }

    @Override
    public MessageLogItem getLastPatientMessageFromLogs(String patientFirstName) {
        if (!patientMessageLogsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Patients");
            selectPatientByName(patientFirstName);
        }

        TableRowItem messageLogRow = patientMessageLogsPage.patientMessageTable.getFirstRowItem();
        if (messageLogRow == null) {
            throw new RuntimeException("Message log was not found!");
        }
        MessageLogItem logItem = new MessageLogItem();
        logItem.setFlow(messageLogRow.getDataByHeader("Flow"));
        logItem.setSent(messageLogRow.getDataByHeader("Sent"));
        logItem.setStatus(messageLogRow.getDataByHeader("Status"));
        logItem.setDelivery(messageLogRow.getDataByHeader("Delivery"));
        logItem.setMessage(messageLogRow.getDataByHeader("Message"));
        return logItem;
    }

    @Override
    public boolean isInProgram(String newProgramName, Patient patient) {
        if (!patientMessageLogsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Patients");
        }

        TableRowItem patientRow = programsPatientsPage.goToPatientsTab().programPatientsTable.getFirstRowItem();
        if (patientRow == null) {
            throw new RuntimeException("Patient was not found!");
        }
        selectPatientByName(patient.getFirstName());

        boolean result = patientMessageLogsPage.patientNameText.getText().equals(patient.getFirstName()) &&
                patientMessageLogsPage.programNameButton.getText().equals(newProgramName);

        Logger.info("Is patient '" + patient.getFirstName() + "' in '" + newProgramName + "' program? - " + result);
        return result;
    }

    @Override
    public boolean isAttachedImageDisplayed() {
        patientMessageLogsPage.attachmentButton.click();
        waitFor(patientMessageLogsPage.attachmentSideBar.attachment::isDisplayed);
        return patientMessageLogsPage.attachmentSideBar.isImageDisplayed(patientMessageLogsPage.attachmentSideBar.attachment);
    }

    @Override
    public ProgramSteps addAutoResponder(Client client, AutoRespondersStatus status, String message) {
        if (!programAutoRespondersPage.isOpened()) {
            programsPage.searchClient.search(client.getName());
            programsPage.programTable.editFirstItemButton().click();
            programsPatientsPage.sideBarMenu.openItem("Auto Responders");
        }
        programAutoRespondersPage.statusExpand.select(status.getValue());
        List<AutoResponderContainer> containers = programAutoRespondersPage.autoResponders();
        programAutoRespondersPage.addAutoResponderButton.click();
        //wait for new responder to be added
        waitFor(() -> programAutoRespondersPage.autoResponders().size() > containers.size());
        AutoResponderContainer lastResponder = programAutoRespondersPage.getLatestResponder();
        lastResponder.allDayCheckbox().check();
        lastResponder.messageInput().enterText(message);
        programAutoRespondersPage.saveButton.click();
        programAutoRespondersPage.statusMessage.waitForDisplayed();
        programAutoRespondersPage.statusMessage.waitForDisappear();
        return this;
    }
}
