package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.campaign.*;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.patient.Patient;
import com.carespeak.domain.steps.CampaignSteps;
import com.carespeak.domain.ui.prod.component.table.QuestionRowItem;
import com.carespeak.domain.ui.prod.component.table.base.TableRowItem;
import com.carespeak.domain.ui.prod.page.campaigns.account_settings.AccountSettingsPage;
import com.carespeak.domain.ui.prod.page.campaigns.messages.CampaignMessagesPage;
import com.carespeak.domain.ui.prod.page.programs.ProgramsPage;
import com.carespeak.domain.ui.prod.page.programs.campaign.MedsCampaignsPage;
import com.carespeak.domain.ui.prod.page.programs.campaign.ProgramsCampaignsPage;
import com.carespeak.domain.ui.prod.page.programs.general.ProgramGeneralSettingsPage;
import com.carespeak.domain.ui.prod.page.programs.campaign.AlertTimeContainer;
import com.carespeak.domain.ui.prod.page.campaigns.CampaignsPage;
import com.carespeak.domain.ui.prod.page.campaigns.general.CampaignsGeneralPage;
import com.carespeak.domain.ui.prod.page.campaigns.time_table.CampaignsTimeTablePage;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.page.programs.patient_message_logs.ProgramPatientMessageLogsPage;
import com.carespeak.domain.ui.prod.page.programs.patients.ProgramsPatientsPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.ProgramPatientsTab;
import com.carespeak.domain.ui.prod.popup.AddCampaignToPatientPopup;
import com.carespeak.domain.ui.prod.popup.AvailableMessagesPopup;
import org.openqa.selenium.Keys;
import org.testng.collections.CollectionUtils;
import java.util.List;


public class ProdCampaignSteps implements CampaignSteps {

    private DashboardPage dashboardPage;
    private CampaignsPage campaignsPage;
    private ProgramsPage programsPage;
    private AccountSettingsPage accountSettingsPage;
    private ProgramGeneralSettingsPage programGeneralSettingsPage;
    private ProgramsCampaignsPage programsCampaignsPage;
    private ProdProgramSteps prodProgramSteps;
    private CampaignsGeneralPage campaignsGeneralPage;
    private CampaignsTimeTablePage timeTablePage;
    private CampaignMessagesPage campaignMessagesPage;
    private ProgramPatientMessageLogsPage programPatientMessageLogsPage;
    private ProgramsPatientsPage programsPatientsPage;
    private ProgramPatientsTab programPatientsTab;
    private MedsCampaignsPage medsCampaignsPage;
    private AddCampaignToPatientPopup addCampaignToPatientPopup;
    private AvailableMessagesPopup availableMessagesPopup;

    public ProdCampaignSteps() {
        dashboardPage = new DashboardPage();
        campaignsPage = new CampaignsPage();
        programsPage = new ProgramsPage();
        campaignsGeneralPage = new CampaignsGeneralPage();
        timeTablePage = new CampaignsTimeTablePage();
        campaignMessagesPage = new CampaignMessagesPage();
        programsCampaignsPage = new ProgramsCampaignsPage();
        programGeneralSettingsPage = new ProgramGeneralSettingsPage();
        prodProgramSteps = new ProdProgramSteps();
        accountSettingsPage = new AccountSettingsPage();
        programPatientMessageLogsPage = new ProgramPatientMessageLogsPage();
        programsPatientsPage = new ProgramsPatientsPage();
        programPatientsTab = new ProgramPatientsTab();
        addCampaignToPatientPopup = new AddCampaignToPatientPopup();
        medsCampaignsPage = new MedsCampaignsPage();
        availableMessagesPopup = new AvailableMessagesPopup();
    }

    @Override
    public CampaignSteps goToCampaignsTab() {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.campaignsMenuItem.click();
        waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);

        return this;
    }

    @Override
    public List<Module> getAvailableModules(String clientName) {
        campaignsPage.searchClient.search(clientName);
        campaignsPage.addCampaignButton.click();
        List<String> options = campaignsPage.selectModuleActionTypePopup.moduleDropdownOnCampaignsTab.getAvailableOptions();
        campaignsPage.selectModuleActionTypePopup.nextButton.click();
        return Module.getModules(options);
    }

    @Override
    public CampaignSteps addEducationalSurveyCampaign(String clientName, Module module, String name, CampaignAccess access, String description, String... tags) {
        if (!campaignsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.campaignsMenuItem.click();
            waitFor(() -> !url.equals(campaignsPage.getCurrentUrl()));
        }

        campaignsPage.searchClient.search(clientName);
        campaignsPage.addCampaignButton.click();
        campaignsPage.selectModulePopup.waitForDisplayed();
        campaignsPage.selectModulePopup.moduleDropdown.select(module.getValue());
        campaignsPage.selectModulePopup.nextButton.click();
        campaignsPage.selectModulePopup.waitForDisappear();
        campaignsGeneralPage.nameInput.enterText(name);
        campaignsGeneralPage.campaignAccessDropdown.select(access.getValue());
        campaignsGeneralPage.campaignDescriptionInput.enterText(description);
        if (tags != null && tags.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String tag : tags) {
                builder.append(tag);
                builder.append(" ");
            }
            campaignsGeneralPage.tagsInput.enterText(builder.toString());
        }
        campaignsGeneralPage.nextButton.click();
        timeTablePage.sendImmediatelyRadio.click();
        timeTablePage.selectDailyButton.click();
        List<AlertTimeContainer> alertContainers = timeTablePage.alertTimeComponent.findAlertTimeContainers();
        if (!CollectionUtils.hasElements(alertContainers)) {
            throw new AssertionError("Alert time containers were not found!");
        }
        AlertTimeContainer firstAlert = alertContainers.get(0);
        firstAlert.amPmDropdown().select("AM");
        timeTablePage.nextButton.click();
        campaignMessagesPage.allocateButton.click();
        campaignsPage.availableMessagesPopup.waitForDisplayed();
        campaignsPage.availableMessagesPopup.checkBoxToSelectMessage.click();
        campaignsPage.availableMessagesPopup.allocateButtonOnPopup.click();
        campaignsPage.availableMessagesPopup.waitForDisappear();
        campaignsPage.sleepWait(1000);
        campaignMessagesPage.saveCampaignButton.doubleClick();
        campaignsPage.sleepWait(1000);
        return this;
    }

    @Override
    public CampaignSteps addBiometricAccountSettingCampaignScheduleProtocol(String clientName, Module module, String name, CampaignAccess access, String description, CampaignScheduleType campaignScheduleType, CampaignAnchor campaignAnchor, String programName, CampaignAdjustDate campaignAdjustDate, CampaignDays campaignDays, String... tags) {
        if (!campaignsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.campaignsMenuItem.click();
            waitFor(() -> !url.equals(campaignsPage.getCurrentUrl()));
        }

        campaignsPage.searchClient.search(clientName);
        campaignsPage.addCampaignButton.click();
        campaignsPage.selectModulePopup.waitForDisplayed();
        campaignsPage.selectModulePopup.moduleDropdown.select(module.getValue());
        campaignsPage.selectModulePopup.nextButton.click();
        campaignsPage.selectModulePopup.waitForDisappear();
        campaignsGeneralPage.nameInput.enterText(name);
        campaignsGeneralPage.campaignAccessDropdown.select(access.getValue());
        campaignsGeneralPage.campaignDescriptionInput.enterText(description);
        if (tags != null && tags.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String tag : tags) {
                builder.append(tag);
                builder.append(" ");
            }
            campaignsGeneralPage.tagsInput.enterText(builder.toString());
        }
        String url = campaignsPage.getCurrentUrl();
        campaignsGeneralPage.nextButton.click();
        waitFor(() -> !url.equals(timeTablePage.getCurrentUrl()));
        timeTablePage.scheduleTypeDropdown.select(campaignScheduleType.getValue());
        timeTablePage.anchorDropDown.select(campaignAnchor.getValue());
        //timeTablePage.programDropDown.select(programName);  // as test has only 1 program, don't select it from dropdown
        timeTablePage.adjustDate.click();
        timeTablePage.adjustDateDropDown.select(campaignAdjustDate.getValue());
        timeTablePage.daysDropDown.select(campaignDays.getValue());
        timeTablePage.daysNumberInput.enterText(1);
        timeTablePage.alertTimeAmPmProtocolDropDown.select("AM");
        timeTablePage.nextButton.click();
        campaignMessagesPage.allocateButton.click();
        campaignsPage.availableMessagesPopup.waitForDisplayed();
        campaignsPage.availableMessagesPopup.checkBoxToSelectMessage.click();
        campaignsPage.availableMessagesPopup.allocateButtonOnPopup.click();
        campaignsPage.availableMessagesPopup.waitForDisappear();
        campaignsPage.sleepWait(1000);
        campaignMessagesPage.saveCampaignButton.doubleClick();
        campaignsPage.sleepWait(1000);
        return this;
    }

    @Override
    public CampaignSteps addMedicationCampaignScheduleProtocol(String clientName, Module module, String name, CampaignAccess access, String description, CampaignScheduleType campaignScheduleType, CampaignAnchor campaignAnchor, String campaignLocation, String... tags) {
        if (!campaignsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.campaignsMenuItem.click();
            waitFor(() -> !url.equals(campaignsPage.getCurrentUrl()));
        }

        campaignsPage.searchClient.search(clientName);
        campaignsPage.addCampaignButton.click();
        campaignsPage.selectModulePopup.waitForDisplayed();
        campaignsPage.selectModulePopup.moduleDropdown.select(module.getValue());
        campaignsPage.selectModulePopup.nextButton.click();
        campaignsPage.selectModulePopup.waitForDisappear();
        campaignsGeneralPage.nameInput.enterText(name);
        campaignsGeneralPage.campaignAccessDropdown.select(access.getValue());
        campaignsGeneralPage.campaignDescriptionInput.enterText(description);
        if (tags != null && tags.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String tag : tags) {
                builder.append(tag);
                builder.append(" ");
            }
            campaignsGeneralPage.tagsInput.enterText(builder.toString());
        }
        String url = campaignsPage.getCurrentUrl();
        campaignsGeneralPage.nextButton.click();
        waitFor(() -> !url.equals(timeTablePage.getCurrentUrl()));
        timeTablePage.scheduleTypeDropdown.select(campaignScheduleType.getValue());
        timeTablePage.anchorDropDown.select(campaignAnchor.getValue());
        timeTablePage.anchorFixedDateField.enterText(timeTablePage.currentDayMonthYear());
        timeTablePage.anchorFixedDateField.sendKeys(Keys.ENTER);
        timeTablePage.intakeUnitsInput.enterText("0");
        timeTablePage.daysInput.enterText("0");
        timeTablePage.alertTimeHoursProtocolDropDown.select(timeTablePage.hoursDropDownSelectionAtAnyCity(campaignLocation));
        timeTablePage.alertTimeMinutesProtocolDropDown.select(timeTablePage.minutesDropDownNewYorkTime());
        timeTablePage.alertTimeAmPmProtocolDropDown.select(timeTablePage.amPmDropDownNewYorkTime());
        timeTablePage.nextButton.click();
        campaignMessagesPage.allocateButton.click();
        campaignsPage.availableMessagesPopup.waitForDisplayed();
        campaignsPage.availableMessagesPopup.checkBoxToSelectMessage.click();
        campaignsPage.availableMessagesPopup.allocateButtonOnPopup.click();
        campaignsPage.availableMessagesPopup.waitForDisappear();
        campaignMessagesPage.saveCampaignButton.doubleClick();
        campaignsPage.availableMessagesPopup.waitForDisappear();
        campaignsPage.refreshPage();
        return this;
    }

    @Override
    public CampaignSteps addMedicationCampaignScheduleProtocolWithSeveralMessages(String clientName, Module module, String name, CampaignAccess access, String description, CampaignScheduleType campaignScheduleType, CampaignAnchor campaignAnchor, String campaignLocation, String... tags) {
        if (!campaignsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.campaignsMenuItem.click();
            waitFor(() -> !url.equals(campaignsPage.getCurrentUrl()));
        }

        campaignsPage.searchClient.search(clientName);
        campaignsPage.addCampaignButton.click();
        campaignsPage.selectModulePopup.waitForDisplayed();
        campaignsPage.selectModulePopup.moduleDropdown.select(module.getValue());
        campaignsPage.selectModulePopup.nextButton.click();
        campaignsPage.selectModulePopup.waitForDisappear();
        campaignsGeneralPage.nameInput.enterText(name);
        campaignsGeneralPage.campaignAccessDropdown.select(access.getValue());
        campaignsGeneralPage.campaignDescriptionInput.enterText(description);
        if (tags != null && tags.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String tag : tags) {
                builder.append(tag);
                builder.append(" ");
            }
            campaignsGeneralPage.tagsInput.enterText(builder.toString());
        }
        String url = campaignsPage.getCurrentUrl();
        campaignsGeneralPage.nextButton.click();
        waitFor(() -> !url.equals(timeTablePage.getCurrentUrl()));
        timeTablePage.scheduleTypeDropdown.select(campaignScheduleType.getValue());
        timeTablePage.anchorDropDown.select(campaignAnchor.getValue());
        timeTablePage.anchorFixedDateField.enterText(timeTablePage.currentDayMonthYear());
        timeTablePage.anchorFixedDateField.sendKeys(Keys.ENTER);
        timeTablePage.intakeUnitsInput.enterText("0");
        timeTablePage.daysInput.enterText("0");
        timeTablePage.alertTimeHoursProtocolDropDown.select(timeTablePage.hoursDropDownSelectionAtAnyCity(campaignLocation));
        timeTablePage.alertTimeMinutesProtocolDropDown.select(timeTablePage.minutesDropDownNewYorkTime());
        timeTablePage.alertTimeAmPmProtocolDropDown.select(timeTablePage.amPmDropDownNewYorkTime());
        timeTablePage.nextButton.click();
        campaignMessagesPage.allocateButton.click();
        campaignsPage.availableMessagesPopup.waitForDisplayed();
        campaignsPage.availableMessagesPopup.checkBoxToSelectMessage.click();
        campaignsPage.availableMessagesPopup.allocateButtonOnPopup.click();
        campaignMessagesPage.allocateButton.click();
        campaignsPage.availableMessagesPopup.checkBoxToSelectMessage.click();
        campaignsPage.availableMessagesPopup.allocateButtonOnPopup.click();
        campaignsPage.availableMessagesPopup.waitForDisappear();

        return this;
    }

    @Override
    public CampaignSteps addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(boolean isMandatory, String clientName, String moduleName, String name, CampaignAccess access, String description, String campaignScheduleType, String dropDownfield, String questionText, String onErrorText, String campaignLocation) {
        if (!campaignsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.campaignsMenuItem.click();
            waitFor(() -> !url.equals(campaignsPage.getCurrentUrl()));
        }

        campaignsPage.searchClient.search(clientName);
        campaignsPage.addCampaignButton.click();
        campaignsPage.selectModulePopup.waitForDisplayed();
        campaignsPage.selectModulePopup.moduleDropdown.select(moduleName);
        campaignsPage.selectModulePopup.nextButton.click();
        campaignsPage.selectModulePopup.waitForDisappear();
        campaignsGeneralPage.nameInput.enterText(name);
        campaignsGeneralPage.campaignAccessDropdown.select(access.getValue());
        campaignsGeneralPage.campaignDescriptionInput.enterText(description);

        String url = campaignsPage.getCurrentUrl();
        campaignsGeneralPage.nextButton.click();
        waitFor(() -> !url.equals(timeTablePage.getCurrentUrl()));
        timeTablePage.scheduleTypeDropdown.select(campaignScheduleType);

        timeTablePage.alertTimeHoursOccasionDropDown.select(timeTablePage.hoursDropDownSelectionAtAnyCity(campaignLocation));
        timeTablePage.alertTimeMinutesOccasionDropDown.select(timeTablePage.minutesDropDownNewYorkTime());
        timeTablePage.alertTimeAmPmOccasionDropDown.select(timeTablePage.amPmDropDownNewYorkTime());

        timeTablePage.nextButton.click();
        accountSettingsPage.addQuestionButton.click();
        QuestionRowItem questionItem = accountSettingsPage.accountCreationTable.getLastQuestionRowItem();
        if (isMandatory) {
            questionItem.mandatoryQuestionCheckbox().check();
        } else {
            questionItem.mandatoryQuestionCheckbox().uncheck();
        }
        questionItem.fieldDropdown().select(dropDownfield);
        questionItem.questionTextInput().enterText(questionText);
        questionItem.onErrorTextInput().enterText(onErrorText);
        accountSettingsPage.saveButton.click();
        return this;
    }

    @Override
    public boolean isCampaignExist(String clientName, String expectedCampaignName) {
        boolean result = false;
        waitFor(()->campaignsPage.isOpened());
        campaignsPage.campaignTable.searchInTable("Name", expectedCampaignName);
        campaignsPage.campaignDataTableWrapper.isDisplayed();
        TableRowItem messageRow = campaignsPage.campaignTable.getFirstRowItem();
        if(messageRow == null){
            throw new RuntimeException("Campaign was not found!");
        }

        String actualCampaignName = messageRow.getDataByHeader("Name");
        if(actualCampaignName.equals(expectedCampaignName))
            result = true;

        Logger.info("Is campaign'" + expectedCampaignName + "' is found? '"+ result);
        return result;
    }

    @Override
    public boolean removeAllMessagesFromCampaign() {
        boolean result = false;
        campaignMessagesPage.selectAllMessagesFromCampaign();
        campaignsPage.removeMessageButton.click();

        waitFor(()-> campaignsPage.emptyCampaignMessagesTable.isDisplayed());
        if(campaignsPage.emptyCampaignMessagesTable.getText().equals("No data available in table")){
            result = true;
        }
        Logger.info("Are all messages were deleted " + result);
        return result;
    }

    @Override
    public boolean isCampaignAddedToProgram(String campaignName) {
        boolean result = false;
        waitFor(() -> programsCampaignsPage.searchFieldButton.isVisible());
        TableRowItem tableRowItem = programsCampaignsPage.addedCampaignsToProgramTable.searchInTable("Name", campaignName);
        programsPage.sleepWait(1000);
        if(tableRowItem.getDataByHeader("Name").equals(campaignName))
            result = true;

        Logger.info("Is campaign'" + campaignName + "' added to program? '"+ result);
        return result;
    }

    @Override
    public CampaignSteps addCampaignToPatient(Patient patient, String campaignName) {
        goToPatientMedCampaignsTab(patient.getFirstName());
        addPatientToCampaign(patient.getFirstName(), campaignName);

        return this;
    }

    @Override
    public boolean isCampaignDeletedFromPatient(String campaignName) {
        boolean result = false;
        medsCampaignsPage.addedCampaignsToPatientTable.searchFor(campaignName);

        if(medsCampaignsPage.emptyTableElement.isDisplayed()){
            result = true;
        }
        Logger.info("Is campaign'" + campaignName + "' deleted from patient? '"+ result);
        return result;
    }

    @Override
    public boolean isCampaignAddedToPatient(String campaignName) {
        boolean result = false;
        medsCampaignsPage.sleepWait(1500);
        medsCampaignsPage.addedCampaignsToPatientTable.searchFor(campaignName);

        TableRowItem firstRowItem = medsCampaignsPage.addedCampaignsToPatientTable.getFirstRowItem();
        String actualCampaign = firstRowItem.getDataByHeader("Campaign");

        if(actualCampaign.equals(campaignName))
            result = true;

        Logger.info("Is campaign'" + campaignName + "' added to patient? '"+ result);
        return result;
    }

    @Override
    public String didCampaignMessageArrive(String expectedCampaignMessage, String patientFirstName) {
        String actualMessageFromLogs;

        long start = System.currentTimeMillis();
        boolean messageFoundInLogs;
        boolean searchTakingTooLong;

        do {
            MessageLogItem lastPatientMessageFromLogs = prodProgramSteps.getLastPatientMessageFromLogs(patientFirstName);
            actualMessageFromLogs = lastPatientMessageFromLogs.getMessage();
            prodProgramSteps.pageRefresh();

            messageFoundInLogs = actualMessageFromLogs.equals(expectedCampaignMessage);
            searchTakingTooLong = System.currentTimeMillis() - start > 420000;

        } while (!messageFoundInLogs && !searchTakingTooLong);

        return actualMessageFromLogs;
    }

    @Override
    public CampaignSteps addCampaignToProgram(String clientName, String programName, Module module, String campaignName) {
        prodProgramSteps.goToProgramSettings(clientName, programName);
        programGeneralSettingsPage.sideBarMenu.openItem("Campaigns");
        programsCampaignsPage.addButton.click();
        programsCampaignsPage.addCampaignToProgramPopup.waitForDisplayed();
        programsCampaignsPage.addCampaignToProgramPopup.moduleDropDown.click();
        programsCampaignsPage.addCampaignToProgramPopup.moduleDropDown.select(module.getValue());
        programsCampaignsPage.addCampaignToProgramPopup.saveButton.click();
        programsCampaignsPage.addCampaignToProgramPopup.waitForDisappear();
        return this;
    }

    @Override
    public CampaignSteps addCampaignToProgram(String clientName, String programName, String moduleName, String campaignName) {
        prodProgramSteps.goToProgramSettings(clientName, programName);
        programGeneralSettingsPage.sideBarMenu.openItem("Campaigns");
        programsCampaignsPage.addButton.click();
        programsCampaignsPage.addCampaignToProgramPopup.waitForDisplayed();
        programsCampaignsPage.addCampaignToProgramPopup.moduleDropDown.click();
        programsCampaignsPage.addCampaignToProgramPopup.moduleDropDown.select(moduleName);
        programsCampaignsPage.addCampaignToProgramPopup.saveButton.click();
        programsCampaignsPage.addCampaignToProgramPopup.waitForDisappear();
        return this;
    }

    @Override
    public boolean areMessagesAddedToCampaign(int expectedNumberOfMessages) {
        boolean result = waitFor(() -> campaignMessagesPage.checkBoxesToSelectMessages.size() == expectedNumberOfMessages);

        int actualNumberOfMessages = campaignMessagesPage.checkBoxesToSelectMessages.size();

        if(actualNumberOfMessages == expectedNumberOfMessages){
            result = true;
        }
        Logger.info("Is number of messages in campaign equals to " + expectedNumberOfMessages + " "+ result);
        return result;
    }

    @Override
    public CampaignSteps removeCampaignFromPatient(String campaignName) {
        programsPage.sideBarMenu.openItem("Meds/Campaigns");
        medsCampaignsPage.deleteCampaignButton.click();
        //medsCampaignsPage.deleteCampaignFromPatientPopup.waitForDisplayed();
        medsCampaignsPage.deleteCampaignFromPatientPopup.okButton.click();
        medsCampaignsPage.deleteCampaignFromPatientPopup.waitForDisappear();
        medsCampaignsPage.statusMessage.waitForDisplayed();
        medsCampaignsPage.closeStatusMessageButton.click();
        return this;
    }

    @Override
    public CampaignSteps removeCampaignFromProgram(String campaignName) {
        programsPage.programListButton.click();
        programsPage.sideBarMenu.openItem("Campaigns");
        //campaignsPage.campaignTable.searchFor(campaignName);
        campaignsPage.deleteCampaignButton.click();
        //campaignsPage.deleteCampaignFromPatientPopup.waitForDisplayed();
        campaignsPage.deleteCampaignFromProgramPopup.okButton.doubleClick();
        campaignsPage.deleteCampaignFromProgramPopup.waitForDisappear();
        return this;
    }

    @Override
    public CampaignSteps addPatientToCampaign(String patientName, String campaignName) {
        medsCampaignsPage.addButton.click();
        medsCampaignsPage.addCampaignToProgramPopup.waitForDisplayed();
        //medsCampaignsPage.addCampaignToPatientPopup.selectCampaignByName(campaignName);
        medsCampaignsPage.addCampaignToPatientPopup.campaignFromPopUp.doubleClick();
        medsCampaignsPage.addCampaignToPatientPopup.saveButton.click();
        //medsCampaignsPage.addCampaignToProgramPopup.waitForDisappear();
        medsCampaignsPage.refreshPage();

        return this;
    }

    @Override
    public CampaignSteps goToPatientMedCampaignsTab(String patientName) {
        programsPage.sideBarMenu.openItem("Patients");
        programPatientsTab.programPatientsTable.searchFor(patientName);
        prodProgramSteps.selectPatientByName(patientName);
        programsPage.sideBarMenu.openItem("Meds/Campaigns");
        waitFor(()-> medsCampaignsPage.isOpened());

        return this;
    }

    @Override
    public boolean isSameCampaignCannotBeAddedTwice(Module module, String campaignName) {
        boolean result = false;
        programsCampaignsPage.addButton.click();
        programsCampaignsPage.addCampaignToProgramPopup.waitForDisplayed();
        programsCampaignsPage.addCampaignToProgramPopup.moduleDropDown.click();
        programsCampaignsPage.addCampaignToProgramPopup.moduleDropDown.select(module.getValue());

        programsCampaignsPage.addCampaignToProgramPopup.campaignDropDown.click();

        List<String> availableOptions = programsCampaignsPage.addCampaignToProgramPopup.campaignDropDown.getAvailableOptions();
        for (String option: availableOptions) {
            if(option.contains(campaignName)){
                Logger.info("Campaign '" + campaignName + "' is present in Campaign dropdown. This is error!");
                return result;
            } else {
                result = true;
            }
        }

        programsCampaignsPage.addCampaignToProgramPopup.campaignDropDown.click();
        programsCampaignsPage.addCampaignToProgramPopup.closeButtonRedefined.doubleClick();
        programsCampaignsPage.addCampaignToProgramPopup.waitForDisappear();
        prodProgramSteps.pageRefresh();
        programsPage.programListButton.click();
        return result;
    }

    @Override
    public CampaignSteps addCampaignAfterDeletion(String campaignName) {
        medsCampaignsPage.addButton.click();
        medsCampaignsPage.addCampaignToProgramPopup.waitForDisplayed();
        //medsCampaignsPage.addCampaignToPatientPopup.selectCampaignByName(campaignName);
        medsCampaignsPage.addCampaignToPatientPopup.campaignFromPopUp.doubleClick();
        medsCampaignsPage.addCampaignToPatientPopup.saveButton.click();
        //medsCampaignsPage.addCampaignToProgramPopup.waitForDisappear();
        medsCampaignsPage.refreshPage();

        return this;
    }


    @Override
    public boolean isSameCampaignCanBeAddedToPatientAfterDeletion(String campaignName) {
        boolean result = false;
        medsCampaignsPage.sleepWait(1500);
        TableRowItem tableRowItem = medsCampaignsPage.addedCampaignsToPatientTable.searchInTable("Campaign", campaignName);
        String actualCampaign = tableRowItem.getDataByHeader("Campaign");
        if(actualCampaign.equals(campaignName)){
            result = true;
            Logger.info("Is campaign'" + campaignName + "' could be again added to patient? '"+ result);
        }
        Logger.info("Is campaign'" + campaignName + "' could be again added to patient? '"+ result);
        return result;
    }
}
