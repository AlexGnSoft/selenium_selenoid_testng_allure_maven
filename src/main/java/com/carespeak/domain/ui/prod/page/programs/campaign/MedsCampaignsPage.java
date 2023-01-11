package com.carespeak.domain.ui.prod.page.programs.campaign;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.message.StatusMessage;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.popup.AddCampaignToPatientPopup;
import com.carespeak.domain.ui.prod.popup.AddCampaignToProgramPopup;
import com.carespeak.domain.ui.prod.popup.DeleteCampaignFromPatientPopup;
import com.carespeak.domain.ui.prod.popup.DeleteCampaignFromProgramPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MedsCampaignsPage extends AbstractProgramPage {

    public ItemsTable addedCampaignsToPatientTable;
    public AddCampaignToProgramPopup addCampaignToProgramPopup;
    public AddCampaignToPatientPopup addCampaignToPatientPopup;
    public DeleteCampaignFromPatientPopup deleteCampaignFromPatientPopup;
    public DeleteCampaignFromProgramPopup deleteCampaignFromProgramPopup;
    public StatusMessage statusMessage;

    @ElementName("Campaign add button")
    @FindBy(id = "csAddCampaignBtn")
    public Button addButton;

    @ElementName("Delete campaign button")
    @FindBy(className = "deleteButton")
    public Button deleteCampaignButton;

    @ElementName("Close button")
    @FindBy(xpath = "//button[@class='close']")
    public Button closeStatusMessageButton;

    @ElementName("Campaign name")
    @FindBy(xpath = "//td[@class='campaignName']//a[@href]")
    public ClickableElement firstCampaignName;

    @ElementName("No data available in table text")
    @FindBy(xpath = "//td[@class='dataTables_empty']")
    public WebElement emptyTableElement;

    public MedsCampaignsPage() {
        addedCampaignsToPatientTable = new ItemsTable(By.id("csPatientAlertsTable"));
        addCampaignToProgramPopup = new AddCampaignToProgramPopup();
        deleteCampaignFromPatientPopup = new DeleteCampaignFromPatientPopup();
        deleteCampaignFromProgramPopup = new DeleteCampaignFromProgramPopup();
        addCampaignToPatientPopup = new AddCampaignToPatientPopup();
        statusMessage = new StatusMessage();
    }
}
