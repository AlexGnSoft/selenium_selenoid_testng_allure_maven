package com.carespeak.domain.steps.holders;

import com.carespeak.core.config.Config;
import com.carespeak.core.helper.IStepsReporter;
import com.carespeak.domain.steps.*;
import com.carespeak.domain.steps.imp.prod.*;

public class SiteStepsHolder {

    private Config config;
    private IStepsReporter reporter;

    private LoginSteps loginSteps;
    private AdminToolsSteps adminToolsSteps;
    private ProgramSteps programSteps;
    private MessagesSteps messagesSteps;
    private ClientSteps clientSteps;
    private CampaignSteps campaignSteps;
    private PatientSteps patientSteps;

    public SiteStepsHolder(Config config, IStepsReporter reporter) {
        this.config = config;
        this.reporter = reporter;
    }

    public boolean isCurrentProdVersion() {
        return config.get("app_env").equals("demo");
    }

    public LoginSteps loginSteps() {
        if (loginSteps == null) {
            Class clazz = isCurrentProdVersion() ? ProdLoginSteps.class : null;
            loginSteps = reporter.createStepProxy(clazz);
        }
        return loginSteps;
    }

    public AdminToolsSteps adminToolsSteps() {
        if (adminToolsSteps == null) {
            Class clazz = isCurrentProdVersion() ? ProdAdminToolsSteps.class : null;
            adminToolsSteps = reporter.createStepProxy(clazz);
        }
        return adminToolsSteps;
    }

    public ProgramSteps programSteps() {
        if (programSteps == null) {
            Class clazz = isCurrentProdVersion() ? ProdProgramSteps.class : null;
            programSteps = reporter.createStepProxy(clazz);
        }
        return programSteps;
    }

    public PatientSteps patientSteps() {
        if (patientSteps == null) {
            Class clazz = isCurrentProdVersion() ? ProdPatientSteps.class : null;
            patientSteps = reporter.createStepProxy(clazz);
        }
        return patientSteps;
    }

    public MessagesSteps messagesSteps() {
        if (messagesSteps == null) {
            Class clazz = isCurrentProdVersion() ? ProdMessagesSteps.class : null;
            messagesSteps = reporter.createStepProxy(clazz);
        }
        return messagesSteps;
    }

    public ClientSteps clientSteps() {
        if (clientSteps == null) {
            Class clazz = isCurrentProdVersion() ? ProdClientSteps.class : null;
            clientSteps = reporter.createStepProxy(clazz);
        }
        return clientSteps;
    }

    public CampaignSteps campaignSteps() {
        if (campaignSteps == null) {
            Class clazz = isCurrentProdVersion() ? ProdCampaignSteps.class : null;
            campaignSteps = reporter.createStepProxy(clazz);
        }
        return campaignSteps;
    }

}
