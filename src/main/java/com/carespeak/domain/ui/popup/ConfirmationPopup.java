package com.carespeak.domain.ui.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//span[contains(@class, 'ui-dialog-title')]")
    public ClickableElement title;

    @ElementName("Close popup button")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//button[@role='button']")
    public Button closePopupButton;

    @ElementName("Popup Ok button")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Ok')]")
    public Button okButton;

    @ElementName("Popup Cancel button")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Cancel')]")
    public Button cancelButton;

    @Override
    public String getPopupName() {
        return "Confirmation popup";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }

}
