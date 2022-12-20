package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.AutoRespondersStatus;
import com.carespeak.domain.entities.patient.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import com.carespeak.domain.entities.program.ProgramOptOutForm;
import com.carespeak.domain.steps.ProgramSteps;
import com.carespeak.domain.ui.prod.component.container.AutoResponderContainer;
import com.carespeak.domain.ui.prod.component.table.QuestionRowItem;
import com.carespeak.domain.ui.prod.component.table.base.TableRowItem;
import com.carespeak.domain.ui.prod.page.admin_tools.users.UserPatientsPage;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.page.patient_lists.PatientListsPage;
import com.carespeak.domain.ui.prod.page.programs.ProgramsPage;
import com.carespeak.domain.ui.prod.page.programs.auto_responders.ProgramAutoRespondersPage;
import com.carespeak.domain.ui.prod.page.programs.consent_management.ProgramConsentManagementPage;
import com.carespeak.domain.ui.prod.page.programs.consent_management.ProgramOptOutFormPage;
import com.carespeak.domain.ui.prod.page.programs.custom_fields.ProgramCustomFieldsPage;
import com.carespeak.domain.ui.prod.page.programs.general.ProgramGeneralSettingsPage;
import com.carespeak.domain.ui.prod.page.programs.keyword_signup.ProgramKeywordSignupPage;
import com.carespeak.domain.ui.prod.page.programs.message_logs.ProgramMessageLogsPage;
import com.carespeak.domain.ui.prod.page.programs.opt_in_messages.OptInMessagesPage;
import com.carespeak.domain.ui.prod.page.programs.patient_message_logs.ProgramPatientMessageLogsPage;
import com.carespeak.domain.ui.prod.page.programs.patients.ProgramsPatientsPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.AddPatientsPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.MonthDayYearContainer;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.PatientProfilePage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.ProgramPatientsTab;
import org.testng.collections.CollectionUtils;
import java.util.ArrayList;
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
    private PatientProfilePage patientProfilePage;
    private ProgramPatientsTab programPatientsTab;
    private UserPatientsPage userPatientsPage;
    private PatientListsPage patientListsPage;

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
        patientProfilePage = new PatientProfilePage();
        programPatientsTab = new ProgramPatientsTab();
        userPatientsPage = new UserPatientsPage();
    }

    @Override
    public ProgramSteps addNewProgram(String clientName, String programName, ProgramAccess programAccess) {
        if (!programsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            programsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
        }
        dashboardPage.headerMenu.programsMenuItem.click();
        //programsPage.searchClient.search(clientName);
        //waitFor(()-> programsPage.isOpened());
        programsPage.sleepWait(2000);
        programsPage.addProgramButton.click();
        programSettingsPage.programNameInput.enterText(programName);
        programSettingsPage.programAccessDropDown.select(programAccess.getValue());
        programSettingsPage.saveButton.click();
        programSettingsPage.statusPopup.waitForDisplayed();
        //programSettingsPage.statusPopup.close();
        return this;
    }

    @Override
    public ProgramSteps linkedOneProgramToAnotherPatientProgram(String programName1, String programName2) {
        programSettingsPage.programTypeDropDown.select("Caregiver program");
        programSettingsPage.linkedPatientProgramDropDown.click();

        programSettingsPage.selectLinkedPatientProgram(programName1);
        programSettingsPage.saveButton.click();

        return this;
    }

    @Override
    public boolean isProgramLinked(String clientName, String programName1, String programName2) {
        goToProgramSettings(clientName, programName1);
        String actualLinkedProgram = programSettingsPage.linkedCaregiverProgramElement.getText();

        if(actualLinkedProgram.equals(programName2)){
            Logger.info("Caregiver program " + programName2 + " was linked to Patient program");
            return true;
        }
        return false;
    }


    @Override
    public ProgramSteps addOptOutHeader(Client client, String programName, String message) {
        goToConsentManagementPage(client, programName);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutHeaderInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    @Override
    public ProgramSteps addOptOutBody(Client client, String programName, String message) {
        goToConsentManagementPage(client, programName);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutBodyInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    @Override
    public ProgramSteps addOptOutFooter(Client client, String programName, String message) {
        goToConsentManagementPage(client, programName);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutFooterInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    private void goToConsentManagementPage(Client client, String programName) {
        if (!consentManagementPage.isOpened()) {
            goToProgramSettings(client.getName(), programName);
            programsPage.sideBarMenu.openItem("Consent Management");
        }
    }

    @Override
    public ProgramSteps movePatientManually(String clientName, String landingProgramName, String patientFirstName) {
        programsPage.sideBarMenu.openItem("Patients");
        ProgramPatientsTab patientsTab = programsPatientsPage.goToPatientsTab();
        clickOnPatientCheckbox(patientFirstName);
        patientsTab.moveToProgramBtn.click();
        patientsTab.selectProgramByClientAndProgramName(landingProgramName);
        patientsTab.moveButton.click();
        programsPage.sleepWait(1500);
        //waitFor(() -> patientsTab.confirmMoveButton.isVisible());  this waiter doesn't work, that is why sleepWait was added
        patientsTab.confirmMoveButton.doubleClick();
        programsPage.sleepWait(1000);

        return this;
    }

    @Override
    public ProgramSteps goToProgramSettings(String clientName, String programName) {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.programsMenuItemRedefined.click();
        programsPage.waitFor(() -> !programsPage.getCurrentUrl().equals(url), false);
        waitFor(()-> programsPage.isOpened());
        programsPage.searchClient.search(clientName);
        //programsPage.isClientSelected(clientName);
        programsPage.programDataTableWrapper.isDisplayed();
        programsPage.programTable.searchFor(programName);
        programsPage.programTable.editFirstItemButton().click();
        return this;
    }

    @Override
    public List<String> getProgramsForClient(Client client) {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.programsMenuItem.click();
        programsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
        programsPage.searchClient.search(client.getName());
        List<TableRowItem> programRows = programsPage.programTable.getItems();
        List<String> res = new ArrayList<>();
        for (TableRowItem programRow : programRows) {
            res.add(programRow.getDataByHeader("Name"));
        }
        return res;
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
    public ProgramSteps addAccountCreationQuestion(boolean isMandatory, String dropDownfield, String questionText, String onErrorText) {
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
        questionItem.fieldDropdown().select(dropDownfield);
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
        programKeywordSignupPage.statusPopup.close();
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
        //programKeywordSignupPage.addQuestionButton.click();
        programKeywordSignupPage.validationMessageButton.isDisplayed();
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
        pageRefresh();
        programsPage.programListButton.doubleClick();
        return logItem;
    }

    @Override
    public ProgramSteps rejectUnsolicitedMessages(Client client, String programName, String messagePattern) {
        if (!programAutoRespondersPage.isOpened()) {
            goToProgramSettings(client.getName(), programName);
            programsPage.sideBarMenu.openItem("Auto Responders");
        }
        programAutoRespondersPage.rejectSolicitedCheckbox.check();
        //TODO: But not from Pending Patients checkbox is needs to be checked. Implementation:
        programAutoRespondersPage.acceptPendingUserCheckbox.check();
        programAutoRespondersPage.acceptedMessageRegexInput.enterText(messagePattern);
        programAutoRespondersPage.saveButton.click();
        return this;
    }

    @Override
    public ProgramSteps removeProgram(Client client, String programName) {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.programsMenuItem.click();
        programsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
        programsPage.programTable.searchFor(programName);
        programsPage.programTable.removeFirstItemButton().click();
        programsPage.confirmationPopup.waitForDisplayed();
        programsPage.confirmationPopup.okButton.click();
        programsPage.confirmationPopup.waitForDisappear();
        programsPage.statusMessage.waitForDisplayed();
        return this;
    }

    @Override
    public ProgramSteps addNewPatient(Patient patient, Client client, String programName) {
        if (!addPatientsPage.isOpened()) {
            goToProgramSettings(client.getName(), programName);
        }
        programSettingsPage.sideBarMenu.openItem("Patients");
        ProgramPatientsTab patientsTab = programsPatientsPage.goToPatientsTab();
        patientsTab.addPatientBtn.click();
        addPatientsPage.cellPhoneInput.enterText(patient.getCellPhone());
        addPatientsPage.cellPhoneConfirmationInput.enterText(patient.getCellPhone());
        addPatientsPage.firstNameInput.enterText(patient.getFirstName());
        addPatientsPage.timezoneDropdown.select(patient.getTimezone());
        addPatientsPage.saveButton.click();
        return this;
    }



    @Override
    public ProgramSteps removePatient(String patientFirstName) {
        programsPatientsPage.patientTable.searchInTable("Name", patientFirstName);
        String actualPatientFirstName = programsPatientsPage.patientTable.getFirstRowPatientNameString();
        if(actualPatientFirstName.equals(patientFirstName)){
            programsPatientsPage.programPatientsTab.clickOnPatientCheckbox(patientFirstName);
            programsPatientsPage.removeButton.click();
            programsPatientsPage.removeSelectedPatientPopup.okButton.click();
            programsPatientsPage.removeSelectedPatientPopup.waitForDisappear();
        }

        Logger.info("Created patient'" + patientFirstName + "' is not the one we created? '");

        return this;
    }

    @Override
    public boolean isPatientRemovedFromUsersPage(String patientFirstName) {
        boolean result = false;
        if (!userPatientsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.adminTools().goToSubMenu("Users");
            userPatientsPage.waitFor(() -> !userPatientsPage.getCurrentUrl().equals(url));
        }

        TableRowItem patientSearchResult = userPatientsPage.patientsTable.searchInTable("Name", patientFirstName);
        if(patientSearchResult == null){
            result = true;
        }
        Logger.info("Is patient'" + patientFirstName + "' was deleted? '"+ result);
        return result;
    }

    @Override
    public ProgramSteps addNewPatientAllFields(Patient patient, Client client, String programName) {
        if (!addPatientsPage.isOpened()) {
            goToProgramSettings(client.getName(), programName);
        }
        programSettingsPage.sideBarMenu.openItem("Patients");
        ProgramPatientsTab patientsTab = programsPatientsPage.goToPatientsTab();
        patientsTab.addPatientBtn.click();

        addPatientsPage.cellPhoneInput.enterText(patient.getCellPhone());
        addPatientsPage.cellPhoneConfirmationInput.enterText(patient.getCellPhone());
        addPatientsPage.timezoneDropdown.select(patient.getTimezone());
        addPatientsPage.firstNameInput.enterText(patient.getFirstName());
        addPatientsPage.lastNameInput.enterText(patient.getLastName());
        addPatientsPage.emailInput.enterText(patient.getEmail());
        addPatientsPage.emailConfirmationInput.enterText(patient.getEmail());
        addPatientsPage.zipCodeInput.enterText(patient.getZipCode());

        List<MonthDayYearContainer> dateContainers = addPatientsPage.monthDayYearComponent.findMonthDayYearContainer();
        if (!CollectionUtils.hasElements(dateContainers)) {
            throw new AssertionError("Date containers were not found!");
        }
        MonthDayYearContainer firstContainer = dateContainers.get(0);
        firstContainer.getMonthDropdown().select(patient.getMonthOfBirth());
        firstContainer.getYearDropdown().select(patient.getYearOfBirth());
        firstContainer.getDayDropdown().select(patient.getDayOfBirth());

        if (patient.getSex() != null) {
            if (patient.getSex().equals(Sex.MALE)) {
                addPatientsPage.maleRadioButtonOption.click();
            } else {
                addPatientsPage.femaleRadioButtonOption.click();
            }
        }

        addPatientsPage.saveButton.click();
        waitFor(()-> programsPatientsPage.isOpened());

        return this;
    }

    @Override
    public ProgramSteps updatePatientAllFields(Patient patient) {
        programsPatientsPage.editButton.click();
        addPatientsPage.cellPhoneInput.enterText(patient.getCellPhone());
        addPatientsPage.cellPhoneConfirmationInput.enterText(patient.getCellPhone());
        addPatientsPage.timezoneDropdown.select(patient.getTimezone());
        addPatientsPage.firstNameInput.enterText(patient.getFirstName());
        addPatientsPage.lastNameInput.enterText(patient.getLastName());
        addPatientsPage.emailInput.enterText(patient.getEmail());
        addPatientsPage.emailConfirmationInput.enterText(patient.getEmail());
        addPatientsPage.zipCodeInput.enterText(patient.getZipCode());

        List<MonthDayYearContainer> dateContainers = addPatientsPage.monthDayYearComponent.findMonthDayYearContainer();
        if (!CollectionUtils.hasElements(dateContainers)) {
            throw new AssertionError("Date containers were not found!");
        }
        MonthDayYearContainer firstContainer = dateContainers.get(0);
        firstContainer.getMonthDropdown().select("April");
        firstContainer.getYearDropdown().select("2021");
        firstContainer.getDayDropdown().select("10");

        if (patient.getSex() != null) {
            if (patient.getSex().equals(Sex.FEMALE)) {
                addPatientsPage.maleRadioButtonOption.click();
            } else {
                addPatientsPage.femaleRadioButtonOption.click();
            }
        }

        addPatientsPage.saveButton.click();
        addPatientsPage.programListButton.click();
        waitFor(()-> programsPatientsPage.isOpened());

        return this;
    }

    @Override
    public Patient getPatientObject(Patient actualPatient) {
        programsPatientsPage.editButton.click();

        actualPatient.setCellPhone(addPatientsPage.cellPhoneInput.getAttribute("value"));
        actualPatient.setTimezone(addPatientsPage.timezoneDropdownSelected.getText());
        actualPatient.setFirstName(addPatientsPage.firstNameInput.getAttribute("value"));
        actualPatient.setLastName(addPatientsPage.lastNameInput.getAttribute("value"));
        actualPatient.setEmail(addPatientsPage.emailInput.getAttribute("value"));
        actualPatient.setZipCode(addPatientsPage.zipCodeInput.getAttribute("value"));
        actualPatient.setMonthOfBirth(addPatientsPage.monthInputDropdownSelected.getText());
        actualPatient.setYearOfBirth(addPatientsPage.yearInputDropdownSelected.getText());
        actualPatient.setDayOfBirth(addPatientsPage.dayInputDropdownSelected.getText());
        actualPatient.setSexType(addPatientsPage.sexRadioButtonSelected.getAttribute("value"));

        addPatientsPage.saveButton.click();

        return actualPatient;
    }

    @Override
    public ProgramSteps addNewPatientLimitedFields(Patient patient, Client client, String programName) {
        if (!addPatientsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.programsMenuItem.click();
            dashboardPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
            programsPage.searchClient.search(client.getName());
            programsPage.programTable.searchFor(programName);
            waitFor(() -> programsPage.programTable.editFirstItemButton().isDisplayed());
            programsPage.programTable.editFirstItemButton().click();
        }
        programSettingsPage.sideBarMenu.openItem("Patients");
        ProgramPatientsTab patientsTab = programsPatientsPage.goToPatientsTab();
        patientsTab.addPatientBtn.click();
        addPatientsPage.cellPhoneInput.enterText(patient.getCellPhone());
        addPatientsPage.cellPhoneConfirmationInput.enterText(patient.getCellPhone());
        addPatientsPage.timezoneDropdown.select(patient.getTimezone());
        addPatientsPage.firstNameInput.enterText(patient.getFirstName());
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
    public String getPatientByName(String clientName, String programName, String patientName) {
        goToProgramSettings(clientName, programName);
        programSettingsPage.sideBarMenu.openItem("Patients");
        waitFor(()->programsPatientsPage.isOpened());
        programsPatientsPage.patientTable.searchFor(patientName);

        TableRowItem tableRowItem = programsPatientsPage.patientTable.searchInTable("Name", patientName);
        if (tableRowItem == null) {
            Logger.info("Patient was not found by name '" + patientName + "'!");
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
    public ProgramOptOutForm getProgramOptOutForm(Client client, String programName) {
        if (!consentManagementPage.isOpened()) {
            goToProgramSettings(client.getName(), programName);

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
    public List<String> getEndpointsOnProgramLevel(String programName) {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.programsMenuItemRedefined.click();
        programsPage.waitFor(() -> !programsPage.getCurrentUrl().equals(url), false);
        waitFor(()-> programsPage.isOpened());
        programsPage.programTable.searchFor(programName);
        programsPage.programTable.editFirstItemButton().click();
        programSettingsPage.programEndpointDropdown.click();
        return programSettingsPage.getEndpoints();
    }

    @Override
    public List<String> getEndpointsOnPatientLevel(Client client, String programName, Patient patient) {
        if (!programsPage.isOpened()) {
            goToProgramSettings(client.getName(), programName);
        }
        programsPatientsPage.sideBarMenu.openItem("Patients");
        //TODO: to confirm that it is expected that we cannot search by First + last name
        ProgramPatientsTab programPatientsTab = programsPatientsPage.goToPatientsTab();
        programPatientsTab.programPatientsTable.searchFor(patient.getFirstName());
        waitFor(() -> programPatientsTab.programPatientsTable.editFirstItemButton().isDisplayed());
        programPatientsTab.programPatientsTable.editFirstItemButton().click();
        return patientProfilePage.endpointDropdown.getAvailableOptions();
    }

    @Override
    public ProgramSteps addOptInMessagesWithoutAttachment(boolean isSendConfirmMessage) {
        programsPage.sideBarMenu.openItem("Opt-in Messages");

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
    public ProgramSteps addOptInMessagesWithAttachment(String filePath, boolean isSendConfirmMessage) {
        programsPage.sideBarMenu.openItem("Opt-in Messages");

        if (isSendConfirmMessage) {
            optInMessagesPage.dontSendMessageCheckBox.uncheck();
        } else {
            optInMessagesPage.dontSendMessageCheckBox.check();
        }

        selectFile(filePath);
        optInMessagesPage.uploadButton.click();

        optInMessagesPage.saveButton.click();
        optInMessagesPage.addOptInMessagePopup.closePopupButton.click();
        optInMessagesPage.addOptInMessagePopup.waitForDisappear();
        return this;
    }

    @Override
    public void selectFile(String filePath) {
        optInMessagesPage.selectButton.sendKeys(filePath);
    }

    public void selectPatientByName(String patientFirstName) {
        programsPatientsPage.goToPatientsTab()
                .selectPatientByName(patientFirstName);
    }

    public void clickOnPatientCheckbox(String patientFirstName) {
        programsPatientsPage.goToPatientsTab()
                .clickOnPatientCheckbox(patientFirstName);
    }

    @Override
    public ProgramSteps simulateResponse(String patientFirstName, String message) {
        if (!patientMessageLogsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Patients");
            selectPatientByName(patientFirstName);
        }

        patientMessageLogsPage.sleepWait(3000); //Preventive measure. Regular waiters are not in 10% of the time with this button.
        patientMessageLogsPage.simulateResponseBtn.click();
        patientMessageLogsPage.simulateResponsePopup.responseInput.enterText(message);
        patientMessageLogsPage.simulateResponsePopup.sendButton.click();
        patientMessageLogsPage.sleepWait(3000); //to give time for the system message to be shown
        return this;
    }

    @Override
    public MessageLogItem simulateResponseAndGetLastPatientMessage(Patient patient, String message) {
        simulateResponse(patient.getFirstName(), message);

        return getLastPatientMessage(patient);
    }

    @Override
    public MessageLogItem getLastPatientMessage(Patient patient) {

        return getLastPatientMessageFromLogs(patient.getFirstName());
    }

    @Override
    public MessageLogItem getLastPatientMessageFromLogs(String patientFirstName) {
        if (!patientMessageLogsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Patients");
            selectPatientByName(patientFirstName);
        }

        waitFor(()-> patientMessageLogsPage.isOpened());

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
    public boolean isInProgram(String newProgramName, String patientName) {
        if (!patientMessageLogsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Patients");
        }

        TableRowItem patientRow = programsPatientsPage.goToPatientsTab().programPatientsTable.getFirstRowItem();
        if (patientRow == null) {
            throw new RuntimeException("Patient was not found!");
        }
        selectPatientByName(patientName);

        boolean result = patientMessageLogsPage.programNameButton.getText().equals(newProgramName);

        Logger.info("Is patient '" + patientName + "' in '" + newProgramName + "' program? - " + result);
        return result;
    }
    @Override
    public ProgramSteps updatePatientStatusShort(Patient patient, String patientStatus) {
        programsPatientsPage.patientTable.searchFor(patient.getFirstName());
        programsPatientsPage.editButton.click();
        if(!addPatientsPage.isOpened()){
            waitFor(()-> addPatientsPage.isOpened());
        }

        addPatientsPage.statusDropdown.select(patientStatus);
        addPatientsPage.saveButton.click();
        addPatientsPage.statusPopup.waitForDisplayed();

        return this;
    }

    @Override
    public boolean isStatusUpdatedOnMessageLogsPage(String patientNewStatus) {
        boolean result = false;
        addPatientsPage.sideBarMenu.openItem("Message Logs");
        if(!programMessageLogsPage.isOpened()){
            waitFor(()->programMessageLogsPage.isOpened());
        }

        String actualStatusOnMessageLogsPage = programMessageLogsPage.messageLogsPatientStatus.getText();
        programsPage.programListButton.click();
        String actualStatusOnProgramPatientsPage = programsPatientsPage.statusOfPatient.getText();

        if(actualStatusOnMessageLogsPage.equals(patientNewStatus) && actualStatusOnProgramPatientsPage.equals(patientNewStatus))
            result = true;
        Logger.info("Is patient status was updated on Message Logs and Program patients page? '" + result);
        return result;
    }


    @Override
    public boolean isAttachedImageDisplayed(String patientName) {
        waitFor(()-> programsPatientsPage.isOpened());
        programsPatientsPage.patientTable.searchInTable("Name", patientName);
        selectPatientByName(patientName);
        patientMessageLogsPage.attachmentButton.click();
        waitFor(patientMessageLogsPage.attachmentSideBar.attachment::isDisplayed);
        return patientMessageLogsPage.attachmentSideBar.isImageDisplayed(patientMessageLogsPage.attachmentSideBar.attachment);
    }

    @Override
    public ProgramSteps addAutoResponder(Client client, String programName, AutoRespondersStatus status, String message) {
        if (!programAutoRespondersPage.isOpened()) {
            goToProgramSettings(client.getName(), programName);
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

    @Override
    public List<Patient> getPatients(Client client, String programName) {
        List<Patient> entitiesList = new ArrayList<>();

        goToProgramSettings(client.getName(), programName);
        if (!programsPatientsPage.isOpened()) {
            programsPage.sideBarMenu.openItem("Patients");
        }
        List<TableRowItem> rows = programsPatientsPage.goToPatientsTab().programPatientsTable.getItems();
        if (CollectionUtils.hasElements(rows)) {
            for (TableRowItem rowItem : rows) {
                Patient patient = new Patient();
                patient.setCellPhone(rowItem.getDataByHeader("Mobile"));
                patient.setTimezone(rowItem.getDataByHeader("Time Zone"));
                String[] name = rowItem.getDataByHeader("Name").split(" ");
                patient.setFirstName(name[0]);
                patient.setLastName(name[1]);
                entitiesList.add(patient);
            }
        }
        return entitiesList;
    }

    @Override
    public ProgramSteps pageRefresh() {
        optInMessagesPage.refreshPage();
        return this;
    }

}