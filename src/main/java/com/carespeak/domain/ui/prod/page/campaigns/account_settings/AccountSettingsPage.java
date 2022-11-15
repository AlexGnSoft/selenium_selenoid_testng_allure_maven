package com.carespeak.domain.ui.prod.page.campaigns.account_settings;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.prod.component.table.AccountCreationTable;
import com.carespeak.domain.ui.prod.page.campaigns.AbstractCampaignsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AccountSettingsPage extends AbstractCampaignsPage {

    public AccountCreationTable accountCreationTable;

    @ElementName("Add question button")
    @FindBy(id = "addQuestionBtn")
    public Button addQuestionButton;

    @ElementName("Toggle validation message button")
    @FindBy(id = "toggleValidationMessageBtn")
    public Button toggleValidationMessageButton;

    @ElementName("Save button")
    @FindBy(id = "asSaveBtn")
    public Button saveButton;

    public AccountSettingsPage() {
        accountCreationTable = new AccountCreationTable(By.id("cs-as-table"));
    }
}
