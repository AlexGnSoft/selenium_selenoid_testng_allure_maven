package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Input;
import org.openqa.selenium.support.FindBy;

public class AddWebHooksPopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//span[contains(@class, 'ui-dialog-title')]")
    public ClickableElement title;

    @ElementName("Close popup button")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//button[@role='button']")
    public Button closePopupButton;

    @ElementName("Name popup input")
    @FindBy(name = "name")
    public Input nameInput;

    @ElementName("URL Template popup input")
    @FindBy(name = "urlTemplate")
    public Input urlTemplateInput;

    @ElementName("Expiry Interval Minutes popup input")
    @FindBy(name = "expiryIntervalMinutes")
    public Input expiryIntervalMinutesInput;

    @ElementName("Save popup button")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Save')]")
    public Button saveButton;

    @ElementName("Close popup button")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Cancel')]")
    public Button closeButton;

    @Override
    public String getPopupName() {
        return "Add Webhook";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
