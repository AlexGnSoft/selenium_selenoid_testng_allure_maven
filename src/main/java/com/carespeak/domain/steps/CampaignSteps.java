package com.carespeak.domain.steps;

import com.carespeak.domain.entities.campaign.CampaignAccess;
import com.carespeak.domain.entities.client.Client;

public interface CampaignSteps extends BaseSteps {

    CampaignSteps addCampaign(Client client, String name, CampaignAccess access, String description, String... tags);

}
