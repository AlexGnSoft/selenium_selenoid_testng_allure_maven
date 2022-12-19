package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Input;
import org.openqa.selenium.support.FindBy;

public class NewPatientListPopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//span[@class='ui-dialog-title']")
    public ClickableElement title;

    @ElementName("Patient name input")
    @FindBy(xpath = "//input[@name='name']")
    public Input nameInput;

    @ElementName("Popup save button")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Save')]")
    public Button saveButton;

    @Override
    public String getPopupName() {
        return "New patient list";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
