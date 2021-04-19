package com.carespeak.domain.ui.prod.page.programs.patients;

import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.ProgramPatientsTab;
import org.openqa.selenium.By;

public class ProgramsPatientsPage extends AbstractProgramPage {

    private static final String TAB_LOCATOR = "//ul[@role='tablist']/li//a[contains(text(), '%s')]";
    private static final String ACTIVE_TAB_LOCATOR = "//ul[@role='tablist']//li[contains(@class, 'ui-state-active')]//a[contains(text(), '%s')]";

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
