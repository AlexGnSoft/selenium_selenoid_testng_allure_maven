package com.carespeak.domain.steps;

import com.carespeak.domain.entities.campaign.CampaignAccess;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;

import java.util.List;

public interface CampaignSteps extends BaseSteps {

    CampaignSteps goToCampaignsTab();

    List<Module> getAvailableModules(String clientName);

    CampaignSteps addCampaign(Client client, String name, Module module, CampaignAccess access, String description, String... tags);



}
