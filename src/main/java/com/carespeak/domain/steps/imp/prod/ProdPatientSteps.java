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
        boolean result = false;
        waitFor(() -> patientListsPage.isOpened());
        patientListsPage.patientListTable.searchInTable("Name", patientListName);
        patientListsPage.sleepWait(2000);

        TableRowItem tableRowItem = patientListsPage.patientListTable.getFirstRowItem();
        if(tableRowItem == null){
            throw new RuntimeException("Patient list was not found!");
        }

        String actualPatientListName = tableRowItem.getDataByHeader("Name");
        if(actualPatientListName.equals(patientListName))
            result = true;

        Logger.info("Is patient list'" + patientListName + "' is found? '"+ result);
        return result;
    }
}
