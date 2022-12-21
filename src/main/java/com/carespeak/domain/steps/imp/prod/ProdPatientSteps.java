package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.steps.PatientSteps;
import com.carespeak.domain.ui.prod.component.table.base.TableRowItem;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.page.patient_lists.PatientListsPage;

public class ProdPatientSteps implements PatientSteps {

    private PatientListsPage patientListsPage;
    private DashboardPage dashboardPage;


    public ProdPatientSteps() {
        patientListsPage = new PatientListsPage();
        dashboardPage = new DashboardPage();
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
        patientListsPage.newPatientListPopup.waitForDisplayed();
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
        patientListsPage.sleepWait(1000);
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
    public boolean arePatientsAddedToPatientList(int numberOfPatients, String patientOne, String patientListName) {
        if(!patientListsPage.isOpened()){
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.patientListsMenuItem.click();
            patientListsPage.waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));
        }

        patientListsPage.firstPatientList.click();
        String[] patientArray = {patientOne, patientListName, "", ""};

        return patientListsPage.isPatientListContainsPatients(numberOfPatients, patientArray);
    }
}
