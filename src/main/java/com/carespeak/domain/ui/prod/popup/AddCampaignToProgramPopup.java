package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddCampaignToProgramPopup extends AbstractPopup{

    @ElementName("Module dropDown")
    @FindBy(xpath = "//div[@class='cs-select']/select[contains(@id,'module')]")
    public Dropdown moduleDropDown;

    @ElementName("Module title")
    @FindBy(xpath = "//span[text()='Add campaign to program']")
    public ClickableElement title;

    @ElementName("Campaign dropDown")
    @FindBy(xpath = "//div[@class='cs-select']/select[contains(@id,'campaign')]")
    public Dropdown campaignDropDown;

    @ElementName("Campaign is program default checkbox")
    @FindBy(xpath = "//input[contains(@id,'program')]")
    public ClickableElement campaignIsProgramDefaultCheckbox;

    @ElementName("Available to consumer checkbox")
    @FindBy(xpath = "//input[contains(@id,'availableToConsumer')]")
    public ClickableElement availableToConsumerCheckbox;

    @ElementName("Add campaign to program Save button")
    @FindBy(xpath = "//button[contains(@class,'success btn-save ui-button')]")
    public Button saveButton;

    @ElementName("Add campaign to program Close button")
    @FindBy(xpath = "//button[contains(@class,'-button-icon-only ui-dialog-titlebar-close')]")
    public Button closeButton;

    @ElementName("Add campaign to program Close button")
    @FindBy(xpath = "//button[contains(@class,'-button-icon-only ui-dialog-titlebar-close')]")
    public Button closeButtonRedefined;

    @Override
    public String getPopupName() {
        return "Add campaign to program";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
