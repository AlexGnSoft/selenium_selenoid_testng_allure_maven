package com.carespeak.domain.ui.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import org.openqa.selenium.support.FindBy;

public class SelectModulePopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//span[contains(@class, 'ui-dialog-title')]")
    public ClickableElement title;

    @ElementName("Close popup button")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//button[@role='button']")
    public Button closePopupButton;

    @ElementName("Module dropdown")
    @FindBy(xpath = "//label[text()='Module']/../..//select")
    public Dropdown moduleDropdown;

    @ElementName("Popup button Next")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Next')]")
    public Button nextButton;

    @ElementName("Popup button Cancel")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Cancel')]")
    public Button cancelButton;

    @Override
    public String getPopupName() {
        return "Select module";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
