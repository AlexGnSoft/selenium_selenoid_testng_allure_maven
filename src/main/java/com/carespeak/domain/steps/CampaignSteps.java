package com.carespeak.domain.steps;

import com.carespeak.domain.entities.campaign.*;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;

import java.util.List;

public interface CampaignSteps extends BaseSteps {

    CampaignSteps goToCampaignsTab();

    List<Module> getAvailableModules(String clientName);

    /**
     * Create Educational or Survey campaign. Make sure that message with corresponding type is created inh advance.
     *
     * @param clientName         client name for which a campaign will be created
     * @param module             module type
     * @param name               campaign name
     * @param access             campaign access
     * @param description        campaign description
     * @param tags               optional list of tags
     * @return CampaignSteps object
     */
    CampaignSteps addEducationalSurveyCampaign(String clientName, Module module, String name, CampaignAccess access, String description, String... tags);

    /**
     * Create Biometric or Account Setting campaign with Schedule type: Protocol. Make sure that message with corresponding type is created inh advance.
     *
     * @param clientName               client name for which a campaign will be created
     * @param module                   module type
     * @param name                     campaign name
     * @param access                   campaign access
     * @param description              campaign description
     * @param campaignScheduleType     campaign schedule type
     * @param campaignAnchor           campaign anchor
     * @param programName              program name, created in advance
     * @param programName              program name, created in advance
     * @param campaignDays             campaign days
     * @return CampaignSteps object
     */
    CampaignSteps addBiometricAccountSettingCampaignScheduleProtocol(String clientName, Module module, String name, CampaignAccess access, String description, CampaignScheduleType campaignScheduleType, CampaignAnchor campaignAnchor, String programName, CampaignAdjustDate campaignAdjustDate, CampaignDays campaignDays, String... tags);

    /**
     * Create Biometric or Account Setting campaign with Schedule type: Occasion and campaign questions.
     * Make sure that message with corresponding type is created inh advance.
     *
     * @param clientName               client name for which a campaign will be created
     * @param moduleName               module name
     * @param name                     campaign name
     * @param access                   campaign access
     * @param description              campaign description
     * @param campaignScheduleType     campaign schedule type
     * @return CampaignSteps object
     */
    CampaignSteps addBiometricAccountSettingCampaignScheduleOccasionWithQuestions(boolean isMandatory, String clientName, String moduleName, String name, CampaignAccess access, String description, String campaignScheduleType, String dropDownfield, String questionText, String onErrorText);

    /**
     * Verify that campaign is created
     *
     * @param clientName      client name
     * @param campaignName    campaign name
     * @return return true if message is found, otherwise false
     */
    boolean isCampaignExist(String clientName, String campaignName);

    /**
     * Verify that campaign is added to program
     *
     * @param campaignName    campaign name
     * @return return true if message is found, otherwise false
     */
    boolean isCampaignAddedToProgram(String campaignName);

    /**
     * Verify that same campaign can not be added twice
     *
     * @param campaignName    campaign name
     * @return return true if message is found, otherwise false
     */
    boolean isSameCampaignCannotBeAddedTwice(Module module, String campaignName);

    public String didCampaignMessageArrive(String campaignMessage, String patientFirstName);

    /**
     * Add a campaign to program
     *
     * @param clientName       client name
     * @param programName      program name
     * @param campaignName     campaign name
     * @return return true if message is found, otherwise false
     */
    CampaignSteps addCampaignToProgram(String clientName, String programName, Module module, String campaignName);

    /**
     * Add a campaign to program
     *
     * @param clientName       client name
     * @param programName      program name
     * @param campaignName     campaign name
     * @return return true if message is found, otherwise false
     */
    CampaignSteps addAccountSettingCampaignToProgram(String clientName, String programName, String moduleName, String campaignName);

    /**
     * Add a campaign to program
     *
     * @param patientName      patient name
     * @param campaignName     campaign name
     * @return return CampaignSteps object
     */
    CampaignSteps addPatientToCampaign(String patientName, String campaignName);
}
