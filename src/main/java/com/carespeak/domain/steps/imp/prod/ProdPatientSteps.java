package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.steps.PatientSteps;
import com.carespeak.domain.ui.prod.component.table.base.TableRowItem;
import com.carespeak.domain.ui.prod.page.admin_tools.users.UserPatientsPage;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.page.patient_lists.PatientListsPage;

public class ProdPatientSteps implements PatientSteps {

    private PatientListsPage patientListsPage;
    private DashboardPage dashboardPage;
    private UserPatientsPage userPatientsPage;


    public ProdPatientSteps() {
        patientListsPage = new PatientListsPage();
        dashboardPage = new DashboardPage();
        userPatientsPage = new UserPatientsPage();
    }

    @Override
    public PatientSteps addPatientList(String clientName, String patientListName) {
        if(!patientListsPage.isOpened()){
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.patientListsMenuItem.click();
            patientListsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
        }

        patientListsPage.newButton.click();
        patientListsPage.newPatientListPopup.waitForDisplayed();
        patientListsPage.newPatientListPopup.nameInput.enterText(patientListName);
        patientListsPage.newPatientListPopup.saveButton.click();
        patientListsPage.newPatientListPopup.waitForDisappear();
        return this;
    }

    @Override
    public boolean isPatientListCreated(String patientListName) {
        boolean isPatientListCreated = false;
        waitFor(() -> patientListsPage.isOpened());
        patientListsPage.patientDataTableWrapper.searchInTable("Name", patientListName);
        patientListsPage.sleepWait(2000);

        TableRowItem tableRowItem = patientListsPage.patientDataTableWrapper.getFirstRowItem();
        if(tableRowItem == null){
            throw new RuntimeException("Patient list was not found!");
        }

        String actualPatientListName = tableRowItem.getDataByHeader("Name");
        if(actualPatientListName.equals(patientListName))
            isPatientListCreated = true;

        Logger.info("Is patient list'" + patientListName + "' is found? '"+ isPatientListCreated);
        return isPatientListCreated;
    }

    @Override
    public boolean isPatientAddedToPatientList(String patientName, String patientListName) {
        boolean isPatientAddedToPatientList = false;
        if(!patientListsPage.isOpened()){
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.patientListsMenuItem.click();
            patientListsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
        }

        patientListsPage.firstPatientList.click();
        patientListsPage.sleepWait(1500);
        //patientListsPage.patientDataTableWrapper.searchInTable("Name", patientName); //search module does not work (bug is created)

        TableRowItem patientData = patientListsPage.patientListTable.getFirstRowItem();
        if(patientData == null){
            throw  new RuntimeException("Patient was not found in patient list");
        }

        String actualPatientName = patientData.getDataByHeader("Name");
        if(actualPatientName.equals(patientName))
            isPatientAddedToPatientList = true;

        Logger.info("Is patient found? '" + isPatientAddedToPatientList);
        return isPatientAddedToPatientList;
    }

    @Override
    public PatientSteps deletePatientFromAdmitToolsUsers(String patientName, String patientCellPhone) {
        if (!userPatientsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.adminTools().goToSubMenu("Users");
            userPatientsPage.waitFor(() -> !userPatientsPage.getCurrentUrl().equals(url));
        }

        userPatientsPage.searchClient.search("All");
        userPatientsPage.patientsTable.searchFor(patientCellPhone);
        userPatientsPage.deletePatientButton.click();
        waitFor(() -> userPatientsPage.mobileHealthManagerPatientsPopup.okButton.isDisplayed());
        userPatientsPage.mobileHealthManagerPatientsPopup.okButton.click();
        return this;
    }

    @Override
    public PatientSteps deletePatientFromPatientList(String patientName, String patientListName) {
        if(!patientListsPage.isOpened()){
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.patientListsMenuItem.click();
            patientListsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
        }

        patientListsPage.firstPatientList.click();
        waitFor(()-> patientListsPage.checkBoxOfFirstPatient.isDisplayed());
        //patientListsPage.patientDataTableWrapper.searchInTable("Name", patientName); //search module does not work (bug is created)

        patientListsPage.checkBoxOfFirstPatient.click();
        patientListsPage.deletePatientButton.click();
        waitFor(()->patientListsPage.deleteSelectedPatientPopup.okButton.isVisible());
        patientListsPage.deleteSelectedPatientPopup.okButton.click();
        waitFor(() -> !patientListsPage.checkBoxOfFirstPatient.isDisplayed());

        return this;
    }


    @Override
    public boolean arePatientsAddedToPatientList(int numberOfPatients, String patientFirst, String patientSecond, String patientListName) {
        if(!patientListsPage.isOpened()){
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.patientListsMenuItem.click();
            patientListsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
        }

        patientListsPage.patientListTable.searchFor(patientListName);
        //waitFor(()-> patientListsPage.firstPatientList.isVisible());
        patientListsPage.firstPatientList.click();
        String[] patientArray = {patientFirst, patientSecond, "", ""};

        return patientListsPage.isPatientListContainsPatients(numberOfPatients, patientArray);
    }
}
