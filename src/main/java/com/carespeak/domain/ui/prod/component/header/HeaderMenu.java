package com.carespeak.domain.ui.prod.component.header;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.logger.Logger;
import com.carespeak.domain.ui.prod.component.AbstractComponent;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class HeaderMenu extends AbstractComponent {

    //User profile dropdown
    @ElementName("User profile dropdown")
    @FindBy(id = "top-user-dropdown")
    public ClickableElement userProfileDropdown;

    @ElementName("User name element")
    @FindBy(xpath = "//*[@id='top-user-dropdown']//span")
    public ClickableElement userNameSpan;

    //Menu items
    @ElementName("Dashboard")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li//*[contains(text(), 'Dashboard')]")
    public ClickableElement dashboardMenuItem;

    @ElementName("Programs")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li//*[contains(text(), 'Programs') and contains(@href,'list.page')]")
    public ClickableElement programsMenuItem;

    @ElementName("Programs")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li//*[contains(text(), 'Programs') and contains(@href,'list.page')]")
    public ClickableElement programsMenuItemRedefined;

    @ElementName("Patient Lists")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li//*[contains(text(), 'Patient Lists')]")
    public ClickableElement patientListsMenuItem;

    @ElementName("Staff")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li//*[contains(text(), 'Staff')]")
    public ClickableElement staffMenuItem;

    @ElementName("Campaigns")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li//*[contains(text(), 'Campaigns')]")
    public ClickableElement campaignsMenuItem;

    @ElementName("Messages")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li//*[contains(text(), 'Messages')]")
    //@CacheLookup
    public ClickableElement messagesMenuItem;

    @ElementName("Reports dropdown")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li/*[contains(text(), 'Reports')]/..")
    public Dropdown reportsDropdown;

    @ElementName("Admin tools dropdown")
    @FindBy(xpath = "//*[@id='cs-main-menu']//li/*[contains(text(), 'Admin Tools')]/..")
    private Dropdown adminToolsDropdown;

    public HeaderMenu() {
    }

    public boolean isUserLoggedIn(String username) {
        try {
            return userNameSpan.getText().contains(username);
        } catch (WebDriverException ex) {
            Logger.error("Cannot get username", ex);
            return false;
        }
    }

    public AdminToolsDropdown adminTools() {
        return new AdminToolsDropdown();
    }

    public class AdminToolsDropdown {

        public void goToSubMenu(String subMenu) {
            String url = driver.getCurrentUrl();
            adminToolsDropdown.scrollIntoView();
            adminToolsDropdown.select(subMenu);
            waitFor(() -> !driver.getCurrentUrl().equals(url), false);
        }
    }
}
