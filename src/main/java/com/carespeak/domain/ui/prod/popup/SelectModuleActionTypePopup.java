package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import org.openqa.selenium.support.FindBy;

public class SelectModuleActionTypePopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//span[contains(@class, 'ui-dialog-title')]")
    public ClickableElement title;

    @ElementName("Close popup button")
    @FindBy(xpath = "//*[contains(@class, 'ui-dialog-title')]//button[@role='button']")
    public Button closePopupButton;

    @ElementName("Module dropdown on Message tab")
    @FindBy(xpath = "//label[text()='Module']/following-sibling::select")
    public Dropdown moduleDropdownOnMessageTab;

    @ElementName("Module dropdown on Campaigns tab")
    @FindBy(xpath = "//select[@class='form-control cs-campaign-mp-select']")
    public Dropdown moduleDropdownOnCampaignsTab;

    @ElementName("Action dropdown")
    @FindBy(xpath = "//label[text()='Action']/following-sibling::select")
    public Dropdown actionDropdown;

    @ElementName("Type dropdown")
    @FindBy(xpath = "//label[text()='Type']/following-sibling::select")
    public Dropdown typeDropdown;

    @ElementName("Popup button Next")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Next')]")
    public Button nextButton;

    @ElementName("Popup button Cancel")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Cancel')]")
    public Button cancelButton;

    @Override
    public String getPopupName() {
        return "Select/Action/Type popup";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }

}
