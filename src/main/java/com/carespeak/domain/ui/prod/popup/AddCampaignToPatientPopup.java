package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class AddCampaignToPatientPopup extends AbstractPopup{
    private static final String CAMPAIGN_NAME = "//select[@id='csCampaignSelection']//option[contains(text(),'%s')]";

    @ElementName("Module title")
    @FindBy(xpath = "//span[text()='Add campaign(s) to patient']")
    public ClickableElement title;

    @ElementName("Campaign from Add campaign to patient pop-up")
    @FindBy(xpath = "//select[@id='csCampaignSelection']/option[@value]")
    public ClickableElement campaignFromPopUp;

    @ElementName("Add campaign to patient Save button")
    @FindBy(xpath = "//button[contains(@class,'success btn-save ui-button')]")
    public Button saveButton;

    @ElementName("Add campaign to patient Close button")
    @FindBy(xpath = "//button[contains(@id,'btn-close')]")
    public Button closeButton;

    @Override
    public String getPopupName() {
        return "Add campaign(s) to patient";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }

    public void selectCampaignByName(String campaignName) {
        By locator = By.xpath(String.format(CAMPAIGN_NAME, campaignName));
        ClickableElement campaign = new ClickableElement(driver.findElement(locator), campaignName + " button");
        campaign.doubleClick();
    }
}
