package com.carespeak.domain.ui.prod.page.admin_tools.clients.consent_management;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.page.admin_tools.clients.AbstractClientPage;
import org.openqa.selenium.support.FindBy;

public class ClientConsentManagementPage extends AbstractClientPage {

    @ElementName("Enable Opt-Out checkbox")
    @FindBy(name = "enabled")
    public CheckBox enableOptOutCheckbox;

    @ElementName("Opt-out Form Header")
    @FindBy(xpath = "//*[@name='header']/following-sibling::*//div[@role='textbox']")
    public Input optOutHeaderInput;

    @ElementName("Opt-out Form Body")
    @FindBy(xpath = "//*[@name='body']/following-sibling::*//div[@role='textbox']")
    public Input optOutBodyInput;

    @ElementName("Opt-out Form Footer")
    @FindBy(xpath = "//*[@name='footer']/following-sibling::*//div[@role='textbox']")
    public Input optOutFooterInput;

    @ElementName("Save button")
    @FindBy(id = "save-btn")
    public Button saveButton;

    public boolean isOpened() {
        String url = getCurrentUrl();
        return url.contains("client") && url.contains("consent-management.page");
    }

}