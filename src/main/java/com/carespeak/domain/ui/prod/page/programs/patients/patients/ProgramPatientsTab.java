package com.carespeak.domain.ui.prod.page.programs.patients.patients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProgramPatientsTab extends AbstractProgramPage {

    public ItemsTable programPatientsTable;

    private static final String PATIENT_NAME = "//table[@id='patient']//a[contains(text(),'%s')]";
    private static final String PATIENT_CHECKBOX = "//input[@type='checkbox' and @patientname='%s']";
    private static final String CLIENT_PROGRAM_NAME = "//select[contains(@id,'programSelect')]//option[contains(text(),'%s')]";

    public ProgramPatientsTab() {
        programPatientsTable = new ItemsTable(By.id("patient"));
    }

    @ElementName("Add patient button")
    @FindBy(xpath = "//a[contains(@href, '/patients/add/new.page')]")
    public Button addPatientBtn;

    @ElementName("Move to program button")
    @FindBy(xpath = "//span[@id='csPatientGroupMove']")
    public Button moveToProgramBtn;

    @ElementName("Move selected patients to program button")
    @FindBy(xpath = "//select[contains(@name, 'programSelect')]")
    public Dropdown moveSelectedPatientsToProgramDropdown;

    @ElementName("Move button")
    @FindBy(xpath = "//span[@class='ui-button-text' and text()='Move']")
    public Button moveButton;

    @ElementName("Confirm move button")
    @FindBy(xpath = "//span[text()='Confirm move']")
    public Button confirmMoveButton;

    public void selectPatientByName(String patientName) {
        By locator = By.xpath(String.format(PATIENT_NAME, patientName));
        ClickableElement patient = new ClickableElement(driver.findElement(locator), patientName + " button");

        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(patient));
        patient.click();
    }

    public void clickOnPatientCheckbox(String patientName) {
        By locator = By.xpath(String.format(PATIENT_CHECKBOX, patientName));
        ClickableElement patientCheckbox = new ClickableElement(driver.findElement(locator), patientName + " button");
        patientCheckbox.click();
    }

    public void selectProgramByClientAndProgramName(String landingProgramName) {
        moveSelectedPatientsToProgramDropdown.click();
        By locator = By.xpath(String.format(CLIENT_PROGRAM_NAME, landingProgramName));
        WebElement landingProgram = driver.findElement(locator);
        landingProgram.click();
    }
}
