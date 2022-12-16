package com.carespeak.domain.ui.prod.page.programs;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.message.StatusMessage;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.ConfirmationPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


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
}
