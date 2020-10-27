package com.carespeak.domain.steps.holders;

import com.carespeak.core.config.Config;
import com.carespeak.domain.steps.AdminToolsSteps;
import com.carespeak.domain.steps.LoginSteps;
import com.carespeak.domain.steps.MessagesSteps;
import com.carespeak.domain.steps.ProgramSteps;
import com.carespeak.domain.steps.imp.ProdAdminToolsSteps;
import com.carespeak.domain.steps.imp.ProdLoginSteps;
import com.carespeak.domain.steps.imp.ProdMessagesSteps;
import com.carespeak.domain.steps.imp.ProdProgramSteps;

public class SiteStepsHolder {

    private Config config;

    private LoginSteps loginSteps;
    private AdminToolsSteps adminToolsSteps;
    private ProgramSteps programSteps;
    private MessagesSteps messagesSteps;

    public SiteStepsHolder(Config config) {
        this.config = config;
    }

    public boolean isCurrentProdVersion() {
        return config.get("app.env").equals("demo");
    }

    public LoginSteps loginSteps() {
        if (loginSteps == null) {
            loginSteps = isCurrentProdVersion() ? new ProdLoginSteps() : null;
        }
        return loginSteps;
    }

    public AdminToolsSteps adminToolsSteps() {
        if (adminToolsSteps == null) {
            adminToolsSteps = isCurrentProdVersion() ? new ProdAdminToolsSteps() : null;
        }
        return adminToolsSteps;
    }

    public ProgramSteps programSteps() {
        if (programSteps == null) {
            programSteps = isCurrentProdVersion() ? new ProdProgramSteps() : null;
        }
        return programSteps;
    }

    public MessagesSteps messagesSteps() {
        if (messagesSteps == null) {
            messagesSteps = isCurrentProdVersion() ? new ProdMessagesSteps() : null;
        }
        return messagesSteps;
    }

}
