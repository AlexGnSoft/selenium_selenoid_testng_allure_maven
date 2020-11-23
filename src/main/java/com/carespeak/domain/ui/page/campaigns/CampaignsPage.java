package com.carespeak.domain.ui.page.campaigns;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.component.search.SearchWithSelection;
import com.carespeak.domain.ui.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.component.table.base.ItemsTable;
import com.carespeak.domain.ui.popup.SelectModulePopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class CampaignsPage extends AbstractCampaignsPage {

    public SearchWithSelection searchClient;

    public SideBarMenu sideBarMenu;

    public ItemsTable programTable;

    public SelectModulePopup selectModulePopup;

    @ElementName("Add Campaign button")
    @FindBy(id = "campaignAddBtn")
    public Button addCampaignButton;

    public CampaignsPage() {
        searchClient = new SearchWithSelection();
        programTable = new ItemsTable(By.id("campaignsTableWrapper"));
        sideBarMenu = new SideBarMenu();
        selectModulePopup = new SelectModulePopup();
    }
}
