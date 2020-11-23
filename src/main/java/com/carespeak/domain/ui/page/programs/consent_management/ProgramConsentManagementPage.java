package com.carespeak.domain.ui.page.programs.consent_management;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.component.message.StatusMessage;
import com.carespeak.domain.ui.page.programs.AbstractProgramPage;
import org.openqa.selenium.support.FindBy;

public class ProgramConsentManagementPage extends AbstractProgramPage {

    public StatusMessage statusMessage;

    @ElementName("Enable Opt Out Checkbox")
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
    @FindBy(id = "saveBtn")
    public Button saveButton;

    public ProgramConsentManagementPage() {
        statusMessage = new StatusMessage();
    }

}
