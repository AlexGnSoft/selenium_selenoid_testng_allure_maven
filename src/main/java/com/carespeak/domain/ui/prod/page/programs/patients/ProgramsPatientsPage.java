package com.carespeak.domain.ui.prod.page.programs.patients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.ProgramPatientsTab;
import com.carespeak.domain.ui.prod.popup.AddSelectedPatientToPatientListPopup;
import com.carespeak.domain.ui.prod.popup.RemoveSelectedPatientPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProgramsPatientsPage extends AbstractProgramPage {

    public ItemsTable patientTable;
    public RemoveSelectedPatientPopup removeSelectedPatientPopup;
    public ProgramPatientsTab programPatientsTab;
    public AddSelectedPatientToPatientListPopup addSelectedPatientToPatientListPopup;

    private static final String TAB_LOCATOR = "//ul[@role='tablist']/li//a[contains(text(), '%s')]";
    private static final String ACTIVE_TAB_LOCATOR = "//ul[@role='tablist']//li[contains(@class, 'ui-state-active')]//a[contains(text(), '%s')]";

    @ElementName("Status of patient")
    @FindBy(xpath = "//td[@class=' nowrap']/span")
    public ClickableElement statusOfPatient;
    @ElementName("Remove button")
    @FindBy(id = "csPatientGroupDelete")
    public Button removeButton;
    @ElementName("Edit button")
    @FindBy(xpath = "//a[@class='btn btn-primary']")
    public Button editButton;
    @ElementName("Add to patient list button")
    @FindBy(id = "csPatientListAdd")
    public Button addToPatientListBtn;

    @ElementName("Checkbox of the first patient")
    @FindBy(xpath = "//input[contains(@id,'user-')]")
    public CheckBox checkboxOfFirstPatient;

    @ElementName("Checkbox of the all patient")
    @FindBy(xpath = "//input[contains(@id,'user-')]")
    public List<WebElement> checkboxesListOfPatients;

    public ProgramsPatientsPage() {
        patientTable = new ItemsTable(By.id("patient"));
        removeSelectedPatientPopup = new RemoveSelectedPatientPopup();
        programPatientsTab = new ProgramPatientsTab();
        addSelectedPatientToPatientListPopup = new AddSelectedPatientToPatientListPopup();
    }

    public ProgramPatientsTab goToPatientsTab() {
        goToTab("Patients");
        return new ProgramPatientsTab();
    }

    protected void goToTab(String tabName) {
        By locator = By.xpath(String.format(TAB_LOCATOR, tabName));
        ClickableElement tab = new ClickableElement(driver.findElement(locator), tabName + " tab");
        tab.click();
        waitForTabToBecomeActive(tabName);
    }

    protected void waitForTabToBecomeActive(String tabName) {
        waitFor(() -> driver.findElement(By.xpath(String.format(ACTIVE_TAB_LOCATOR, tabName))).isDisplayed());
    }

    public void selectMultiplePatients(int numberOfPatients) {
        for (int i = 0; i < numberOfPatients; i++) {
            checkboxesListOfPatients.get(i).click();
        }
    }
}
