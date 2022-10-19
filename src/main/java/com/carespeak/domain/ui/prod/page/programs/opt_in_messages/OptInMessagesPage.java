package com.carespeak.domain.ui.prod.page.programs.opt_in_messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.AddOptInMessagePopup;
import org.openqa.selenium.support.FindBy;

public class OptInMessagesPage extends AbstractPage {

    public AddOptInMessagePopup addOptInMessagePopup;

    @ElementName("Opt-In Message input")
    @FindBy(id = "messageOptInText")
    public Input messageInput;

    @ElementName("Select Opt-In Message Attachment")
    @FindBy(xpath = "//input[@type='file']")
    public Button selectButton;

    @ElementName("Remove Opt-in Message Attachment")
    @FindBy(xpath = "//button[contains(@class, 'remove-btn')]")
    public Button removeButton;

    @ElementName("Do NOT send opt in confirmation message checkbox")
    @FindBy(xpath = "//input[@id='doNotSendOptInConfirmation']")
    public CheckBox dontSendMessageCheckBox;

    @ElementName("Upload Message Attachment")
    @FindBy(xpath = "//form//input[@value='Upload']")
    public Button uploadButton;

    @ElementName("Opt-In Confirmation Message input")
    @FindBy(id = "messageConfirmText")
    public Input messageConfirmInput;

    @ElementName("Start Message input")
    @FindBy(id = "messageStartText")
    public Input messageStartInput;

    @ElementName("Stop Message input")
    @FindBy(id = "messageStopText")
    public Input messageStopInput;

    @ElementName("Help Message input")
    @FindBy(id = "messageHelpText")
    public Input messageHelpInput;

    @ElementName("Service Stop Reminder Message input")
    @FindBy(id = "messageServiceStopReminderText")
    public Input messageStopReminderButton;

    @ElementName("Save button")
    @FindBy(id = "programOptInMessagesSaveBtn")
    public Input saveButton;

    public OptInMessagesPage() {
        addOptInMessagePopup = new AddOptInMessagePopup();
    }

    @Override
    public boolean isOpened() {
        return driver.getCurrentUrl().contains("message-logs.page");
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

}
