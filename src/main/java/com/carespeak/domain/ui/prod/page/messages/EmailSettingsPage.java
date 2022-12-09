package com.carespeak.domain.ui.prod.page.messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmailSettingsPage extends AbstractPage {

    @ElementName("Custom E-mails field")
    @FindBy(id = "emailTo")
    public Input customEmailField;

    @ElementName("Subject field")
    @FindBy(id = "subject")
    public Input subject;

    @ElementName("Body field")
    @FindBy(xpath = "//div[@class='note-editable']")
    public Input bodyField;

    @ElementName("Send test message button")
    @FindBy(id = "send-test-sms-btn")
    public Button sendTestMessageButton;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    @ElementName("Emails template drop down")
    @FindBy(xpath = "//button/span[@class='filter-option pull-left']")
    public Dropdown emailsTemplateDropDown;

    @ElementName("'Message was saved successfully' pop-up")
    @FindBy(xpath = "//div[@class='cs-table-cell top alert-content']")
    public WebElement successPopUp;

    @ElementName("'Test message has been sent successfully' pop-up")
    @FindBy(xpath = "//div[@class='cs-table-cell top alert-content']")
    public WebElement testMessageHasBeenSentSuccessfullyPopUp;

    @ElementName("to Email Field")
    @FindBy(xpath = "//input[@class='form-control control']")
    public Input toEmailPopUpField;

    @ElementName("send pop up button")
    @FindBy(xpath = "//button[contains(@class,'success ui-button ui-widget ui')]")
    public Button sendPopUpButton;

    @ElementName("To email field")
    @FindBy(xpath = "//input[@class='form-control control']")
    public Input toEmailField;


}
