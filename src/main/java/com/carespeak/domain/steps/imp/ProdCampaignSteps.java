package com.carespeak.domain.steps.imp;

import com.carespeak.domain.entities.campaign.CampaignAccess;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.steps.CampaignSteps;
import com.carespeak.domain.ui.page.campaigns.CampaignsPage;
import com.carespeak.domain.ui.page.campaigns.general.CampaignsGeneralPage;
import com.carespeak.domain.ui.page.campaigns.time_table.CampaignsTimeTablePage;
import org.apache.commons.lang3.NotImplementedException;

public class ProdCampaignSteps implements CampaignSteps {

    private CampaignsPage campaignsPage;
    private CampaignsGeneralPage generalPage;
    private CampaignsTimeTablePage timeTablePage;

    public ProdCampaignSteps() {
        campaignsPage = new CampaignsPage();
        generalPage = new CampaignsGeneralPage();
        timeTablePage = new CampaignsTimeTablePage();
    }

    @Override
    public CampaignSteps addCampaign(Client client, String name, CampaignAccess access, String description, String... tags) {
        throw new NotImplementedException("Add campaign is not implemented yet!");
    }
}
