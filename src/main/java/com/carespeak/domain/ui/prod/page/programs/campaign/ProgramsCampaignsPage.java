package com.carespeak.domain.ui.prod.page.programs.campaign;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.campaigns.AbstractCampaignsPage;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.page.programs.patients.patients.ProgramPatientsTab;
import com.carespeak.domain.ui.prod.popup.AddCampaignToProgramPopup;
import com.carespeak.domain.ui.prod.popup.AddWebHooksPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ProgramsCampaignsPage extends AbstractProgramPage {

    public ItemsTable addedCampaignsToProgramTable;
    public AddCampaignToProgramPopup addCampaignToProgramPopup;

    @ElementName("Campaign add button")
    @FindBy(id = "campaignAddBtn")
    public Button addButton;

    @ElementName("Search field, used as button")
    @FindBy(xpath = "//input[@type='search']")
    public Button searchFieldButton;


    @ElementName("Campaign add button")
    @FindBy(xpath = "//tbody/tr[@role='row']/td/a")
    public ClickableElement firstAddedCampaignName;

    public ProgramsCampaignsPage() {
        addedCampaignsToProgramTable = new ItemsTable(By.id("programCampaigns_wrapper"));
        addCampaignToProgramPopup = new AddCampaignToProgramPopup();
    }
}
