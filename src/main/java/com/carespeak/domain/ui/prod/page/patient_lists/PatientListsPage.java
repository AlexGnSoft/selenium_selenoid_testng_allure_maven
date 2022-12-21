package com.carespeak.domain.ui.prod.page.patient_lists;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.NewPatientListPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class PatientListsPage extends AbstractPage {

    public SearchWithSelection searchClient;
    public ItemsTable patientDataTableWrapper;
    public ItemsTable patientListTable;
    public NewPatientListPopup newPatientListPopup;

    @ElementName("Add new patient list button")
    @FindBy(id = "pl-add-btn")
    public Button newButton;

    @ElementName("First patient list button")
    @FindBy(xpath = "//tr[@role='row']//td/a")
    public ClickableElement firstPatientList;

//    @ElementName("Patient table wrapper button")
//    @FindBy(id="patient-list-table_wrapper")
//    public ClickableElement patientDataTableWrapper;

    public PatientListsPage() {
        searchClient = new SearchWithSelection();
        patientDataTableWrapper = new ItemsTable(By.id("patient-list-table_wrapper"));
        patientListTable = new ItemsTable(By.id("patient-list-items-table"));
        newPatientListPopup = new NewPatientListPopup();

    }
}
