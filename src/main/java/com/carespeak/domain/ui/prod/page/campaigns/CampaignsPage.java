package com.carespeak.domain.ui.prod.page.campaigns;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.popup.AvailableMessagesPopup;
import com.carespeak.domain.ui.prod.popup.SelectModuleActionTypePopup;
import com.carespeak.domain.ui.prod.popup.SelectModulePopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class CampaignsPage extends AbstractCampaignsPage {

    public SearchWithSelection searchClient;

    public SideBarMenu sideBarMenu;

    public ItemsTable campaignTable;

    public SelectModulePopup selectModulePopup;
    public AvailableMessagesPopup availableMessagesPopup;

    public SelectModuleActionTypePopup selectModuleActionTypePopup;

    @ElementName("Add Campaign button")
    @FindBy(id = "campaignAddBtn")
    public Button addCampaignButton;

    @ElementName("Campaign name")
    @FindBy(xpath = "//tbody/tr[@role='row']/td/strong/a")
    public ClickableElement firstCampaignName;

    public CampaignsPage() {
        searchClient = new SearchWithSelection();
        campaignTable = new ItemsTable(By.id("campaignsTableWrapper"));
        sideBarMenu = new SideBarMenu();
        selectModulePopup = new SelectModulePopup();
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
        availableMessagesPopup = new AvailableMessagesPopup();
    }
}
