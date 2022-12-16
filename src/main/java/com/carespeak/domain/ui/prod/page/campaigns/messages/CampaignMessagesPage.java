package com.carespeak.domain.ui.prod.page.campaigns.messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.prod.page.campaigns.AbstractCampaignsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CampaignMessagesPage extends AbstractCampaignsPage {
    @ElementName("Allocate messages button")
    @FindBy(id = "messageAllocateBtn")
    public Button allocateButton;

    @ElementName("Campaign save button")
    @FindBy(xpath = "//div[@class='cs-form-element-full-container']/button[@id='campaignSaveBtn']")
    public Button saveCampaignButton;

    @ElementName("Checkboxes to select messages")
    @FindBy(xpath = "//div[@class='center']/input")
    public List<WebElement> checkBoxesToSelectMessages;

    public void clickOnSaveCampaignButton() {
        driver.navigate().refresh();
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//div[@class='cs-form-element-full-container']/button[@id='campaignSaveBtn']"));
        actions.doubleClick(element).perform();
    }

    public void selectAllMessagesFromCampaign() {
        for (int i = 0; i < checkBoxesToSelectMessages.size(); i++) {
            checkBoxesToSelectMessages.get(i).click();
        }
    }


}
