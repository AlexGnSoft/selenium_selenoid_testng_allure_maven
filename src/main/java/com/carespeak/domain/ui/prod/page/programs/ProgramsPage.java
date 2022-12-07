package com.carespeak.domain.ui.prod.page.programs;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.domain.ui.prod.component.message.StatusMessage;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.ConfirmationPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProgramsPage extends AbstractPage {

    public SearchWithSelection searchClient;

    public SideBarMenu sideBarMenu;

    public ItemsTable programTable;

    public ConfirmationPopup confirmationPopup;

    public StatusMessage statusMessage;

    private static final String CAMPAIGN_DROPDOWN_VALUE_XPATH = "//span[contains(text(),'%s')]";
    private static final String PROGRAM_VALUE_XPATH = "//a[contains(text(),'%s')]";

    @ElementName("Add Program button")
    @FindBy(id = "programAddButton")
    public Button addProgramButton;

    @ElementName("Program List button")
    @FindBy(xpath = "//a[@href]//span[@class='cs-back-to-text']")
    public Button programListButton;

    @ElementName("Program data table wrapper")
    @FindBy(id = "programDataTableWrapper")
    public WebElement programDataTableWrapper;

    public ProgramsPage() {
        searchClient = new SearchWithSelection();
        programTable = new ItemsTable(By.id("programDataTable"));
        sideBarMenu = new SideBarMenu();
        confirmationPopup = new ConfirmationPopup();
        statusMessage = new StatusMessage();
    }

    public boolean isClientSelected(String clientName) {
        By locator = By.xpath(String.format(CAMPAIGN_DROPDOWN_VALUE_XPATH, clientName));
        ClickableElement client = new ClickableElement(driver.findElement(locator), clientName + " button");
        return client.isDisplayed();
    }

    public boolean isProgramDisplayed(String programName) {
        try {
            By locator = By.xpath(String.format(PROGRAM_VALUE_XPATH, programName));

            WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), 3);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            ClickableElement program = new ClickableElement(driver.findElement(locator), programName + " button");
            return program.isDisplayed();
        } catch (TimeoutException e) {
            throw new RuntimeException("Program was not found by name '" + programName + "'!");
        }
    }

}
