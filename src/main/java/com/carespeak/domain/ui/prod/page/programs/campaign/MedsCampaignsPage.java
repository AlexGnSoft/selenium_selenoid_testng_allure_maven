package com.carespeak.domain.ui.prod.page.programs.campaign;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.popup.AddCampaignToProgramPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class MedsCampaignsPage extends AbstractProgramPage {

    public ItemsTable addedCampaignsToPatientTable;
    public AddCampaignToProgramPopup addCampaignToProgramPopup;

    @ElementName("Campaign add button")
    @FindBy(id = "csAddCampaignBtn")
    public Button addButton;

    @ElementName("Campaign add button")
    @FindBy(xpath = "//tbody/tr[@role='row']/td/a")
    public ClickableElement firstAddedCampaignName;

    public MedsCampaignsPage() {
        addedCampaignsToPatientTable = new ItemsTable(By.id("csDataTableWrapper clearfix"));
        addCampaignToProgramPopup = new AddCampaignToProgramPopup();
    }
}
