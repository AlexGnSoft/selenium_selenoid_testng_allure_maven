package com.carespeak.domain.ui.prod.page.programs.patients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.message.StatusMessage;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.ProgramPatientsTab;
import com.carespeak.domain.ui.prod.popup.ConfirmationPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ProgramsPatientsPage extends AbstractProgramPage {

    public ItemsTable patientTable;

    private static final String TAB_LOCATOR = "//ul[@role='tablist']/li//a[contains(text(), '%s')]";
    private static final String ACTIVE_TAB_LOCATOR = "//ul[@role='tablist']//li[contains(@class, 'ui-state-active')]//a[contains(text(), '%s')]";

    @ElementName("First patient name")
    @FindBy(xpath = "//tbody/tr[@role='row']/td[2]/a[@sortbias]")
    public ClickableElement firstPatientName;

    public ProgramsPatientsPage() {
        patientTable = new ItemsTable(By.id("patient_wrapper"));
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

    private void waitForTabToBecomeActive(String tabName) {
        waitFor(() -> driver.findElement(By.xpath(String.format(ACTIVE_TAB_LOCATOR, tabName))).isDisplayed());
    }


}
