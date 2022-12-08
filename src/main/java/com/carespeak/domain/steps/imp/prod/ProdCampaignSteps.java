package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.campaign.*;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.steps.CampaignSteps;
import com.carespeak.domain.ui.prod.component.table.AccountCreationTable;
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
import com.carespeak.domain.ui.prod.page.programs.message_logs.ProgramMessageLogsPage;
import com.carespeak.domain.ui.prod.page.programs.patient_message_logs.ProgramPatientMessageLogsPage;
import com.carespeak.domain.ui.prod.page.programs.patients.ProgramsPatientsPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.ProgramPatientsTab;
import com.carespeak.domain.ui.prod.popup.AddCampaignToPatientPopup;
import com.carespeak.domain.ui.prod.popup.AddCampaignToProgramPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.collections.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
        campaignMessagesPage.clickOnSaveCampaignButton();
        campaignMessagesPage.saveCampaignButton.click();
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
        campaignMessagesPage.clickOnSaveCampaignButton();
        campaignMessagesPage.saveCampaignButton.click();
        campaignsPage.availableMessagesPopup.waitForDisappear();
        campaignsPage.closeButtonOfCampaignSavedMessage.click();
        return this;
    }

    @Override
    public CampaignSteps addMedicationCampaignScheduleProtocol(String clientName, Module module, String name, CampaignAccess access, String description, CampaignScheduleType campaignScheduleType, CampaignAnchor campaignAnchor,  String... tags) {
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
        timeTablePage.alertTimeHoursProtocolDropDown.select(timeTablePage.hoursDropDownNewYorkTime());
        timeTablePage.alertTimeMinutesProtocolDropDown.select(timeTablePage.minutesDropDownNewYorkTime());
        timeTablePage.alertTimeAmPmProtocolDropDown.select(timeTablePage.amPmDropDownNewYorkTime());
        timeTablePage.nextButton.click();
        campaignMessagesPage.allocateButton.click();
        campaignsPage.availableMessagesPopup.waitForDisplayed();
        campaignsPage.availableMessagesPopup.checkBoxToSelectMessage.click();
        campaignsPage.availableMessagesPopup.allocateButtonOnPopup.click();
        campaignsPage.availableMessagesPopup.waitForDisappear();
        campaignMessagesPage.clickOnSaveCampaignButton();
        campaignMessagesPage.saveCampaignButton.click();
        campaignsPage.availableMessagesPopup.waitForDisappear();
        return this;
    }

    @Override
    public CampaignSteps addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(boolean isMandatory, String clientName, String moduleName, String name, CampaignAccess access, String description, String campaignScheduleType, String dropDownfield, String questionText, String onErrorText) {
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

        timeTablePage.alertTimeHoursOccasionDropDown.select(timeTablePage.hoursDropDownNewYorkTime());
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
    public boolean isCampaignExist(String clientName, String campaignName) {
        TableRowItem messageRow = campaignsPage.campaignTable.getFirstRowItem();
        if(messageRow == null){
            throw new RuntimeException("Campaign was not found!");
        }

        campaignsPage.searchClient.search(clientName);
        campaignsPage.campaignTable.searchFor(campaignName);

        boolean result = false;
        String actualSmsName = campaignsPage.firstCampaignName.getText();
        if(actualSmsName.equals(campaignName))
            result = true;

        Logger.info("Is campaign'" + campaignName + "' is found? '"+ result);
        return result;
    }

    @Override
    public boolean isCampaignAddedToProgram(String campaignName) {
        boolean result = false;
        String actualSmsName = programsCampaignsPage.firstAddedCampaignName.getText();
        if(actualSmsName.equals(campaignName))
            result = true;

        Logger.info("Is campaign'" + campaignName + "' added to program? '"+ result);
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
            searchTakingTooLong = System.currentTimeMillis() - start > 360000;

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
    public CampaignSteps addPatientToCampaign(String patientName, String campaignName) {
        programsPage.sideBarMenu.openItem("Meds/Campaigns");
        medsCampaignsPage.addButton.click();
        medsCampaignsPage.addCampaignToProgramPopup.waitForDisplayed();
        addCampaignToPatientPopup.selectCampaignByName(campaignName);
        addCampaignToPatientPopup.saveButton.click();
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

}
