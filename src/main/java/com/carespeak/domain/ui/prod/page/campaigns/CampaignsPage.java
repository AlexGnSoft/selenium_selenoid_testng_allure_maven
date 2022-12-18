package com.carespeak.domain.ui.prod.page.campaigns;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.popup.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CampaignsPage extends AbstractCampaignsPage {

    public SearchWithSelection searchClient;

    public SideBarMenu sideBarMenu;

    public ItemsTable campaignTable;

    public SelectModulePopup selectModulePopup;
    public AvailableMessagesPopup availableMessagesPopup;

    public SelectModuleActionTypePopup selectModuleActionTypePopup;
    public DeleteCampaignFromPatientPopup deleteCampaignFromPatientPopup;
    public DeleteCampaignFromProgramPopup deleteCampaignFromProgramPopup;

    @ElementName("Add Campaign button")
    @FindBy(id = "campaignAddBtn")
    public Button addCampaignButton;

    @ElementName("Close button of campaign saved message")
    @FindBy(xpath = "//button[@class='close']")
    public Button closeButtonOfCampaignSavedMessage;

    @ElementName("Campaign data table wrapper")
    @FindBy(id = "campaignsTableWrapper")
    public WebElement campaignDataTableWrapper;

    @ElementName("Campaign name")
    @FindBy(xpath = "//tbody/tr[@role='row']/td/strong/a")
    public ClickableElement firstCampaignName;

    @ElementName("Delete campaign button")
    @FindBy(xpath = "//button[contains(@id,'campaignsTableDeleteButton')]")
    public Button deleteCampaignButton;

    @ElementName("Remove message button")
    @FindBy(id = "messageRemoveBtn")
    public Button removeMessageButton;

    @ElementName("Empty campaign Messages table")
    @FindBy(xpath = "//tr[@class='odd ui-droppable']/td")
    public ClickableElement emptyCampaignMessagesTable;

    public CampaignsPage() {
        searchClient = new SearchWithSelection();
        campaignTable = new ItemsTable(By.id("campaignList"));
        sideBarMenu = new SideBarMenu();
        selectModulePopup = new SelectModulePopup();
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
        availableMessagesPopup = new AvailableMessagesPopup();
        deleteCampaignFromPatientPopup = new DeleteCampaignFromPatientPopup();
        deleteCampaignFromProgramPopup = new DeleteCampaignFromProgramPopup();
    }
}
