package com.carespeak.domain.steps.imp;

import com.carespeak.domain.entities.campaign.CampaignAccess;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.steps.CampaignSteps;
import com.carespeak.domain.ui.page.campaigns.CampaignsPage;
import com.carespeak.domain.ui.page.campaigns.general.CampaignsGeneralPage;
import com.carespeak.domain.ui.page.campaigns.time_table.CampaignsTimeTablePage;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;
import org.apache.commons.lang3.NotImplementedException;

public class ProdCampaignSteps implements CampaignSteps {

    private DashboardPage dashboardPage;
    private CampaignsPage campaignsPage;
    private CampaignsGeneralPage generalPage;
    private CampaignsTimeTablePage timeTablePage;

    public ProdCampaignSteps() {
        dashboardPage = new DashboardPage();
        campaignsPage = new CampaignsPage();
        generalPage = new CampaignsGeneralPage();
        timeTablePage = new CampaignsTimeTablePage();
    }

    @Override
    public CampaignSteps addCampaign(Client client, String name, CampaignAccess access, String description, String... tags) {
        if (!campaignsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.campaignsMenuItem.click();
            waitFor(()-> !url.equals(campaignsPage.getCurrentUrl()));
        }
        campaignsPage.addCampaignButton.click();
        campaignsPage.selectModulePopup.waitForDisplayed();
        campaignsPage.selectModulePopup.moduleDropdown.select("Education");
        campaignsPage.selectModulePopup.nextButton.click();
        campaignsPage.selectModulePopup.waitForDisappear();

        throw new NotImplementedException("Add campaign is not implemented yet!");
    }
}
