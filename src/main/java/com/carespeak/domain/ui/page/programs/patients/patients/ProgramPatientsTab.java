package com.carespeak.domain.ui.page.programs.patients.patients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.component.table.base.ItemsTable;
import com.carespeak.domain.ui.page.programs.AbstractProgramPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ProgramPatientsTab extends AbstractProgramPage {

    public ItemsTable programPatientsTable;

    private static final String PATIENT_NAME = "//table[@id='patient']//a[contains(text(),'%s')]";

    public ProgramPatientsTab() {
        programPatientsTable = new ItemsTable(By.id("patient"));
    }

    @ElementName("Add patient button")
    @FindBy(xpath = "//a[contains(@href, '/patients/add/new.page')]")
    public Button addPatientBtn;

    public void selectPatientByName(String patientName) {
        By locator = By.xpath(String.format(PATIENT_NAME, patientName));
        ClickableElement patient = new ClickableElement(driver.findElement(locator), patientName + " button");
        patient.click();
    }


}
