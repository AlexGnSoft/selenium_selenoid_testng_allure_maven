package com.carespeak.domain.steps.imp;

import com.carespeak.domain.entities.common.Day;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.steps.ClientSteps;
import com.carespeak.domain.ui.component.container.AutoResponderContainer;
import com.carespeak.domain.ui.component.table.base.TableRowItem;
import com.carespeak.domain.ui.page.admin_tools.clients.ClientsPage;
import com.carespeak.domain.ui.page.admin_tools.clients.auto_responders.ClientAutoRespondersPage;
import com.carespeak.domain.ui.page.admin_tools.clients.consent_management.ClientConsentManagementPage;
import com.carespeak.domain.ui.page.admin_tools.clients.webhooks.ClientWebHooksPage;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public class ProdClientSteps implements ClientSteps {

    private ClientAutoRespondersPage autoRespondersPage;
    private DashboardPage dashboardPage;
    private ClientsPage clientsPage;
    private ClientConsentManagementPage consentManagementPage;
    private ClientWebHooksPage webHooksPage;

    public ProdClientSteps() {
        autoRespondersPage = new ClientAutoRespondersPage();
        dashboardPage = new DashboardPage();
        clientsPage = new ClientsPage();
        consentManagementPage = new ClientConsentManagementPage();
        webHooksPage = new ClientWebHooksPage();
    }

    @Override
    public ClientSteps addAutoResponder(Client client, String message, boolean isAllDay, Day... days) {
        if (!autoRespondersPage.isOpened()) {
            goToClientSettingsPage(client.getCode());
            String url = dashboardPage.getCurrentUrl();
            clientsPage.sideBarMenu.openItem("Auto Responders");
            autoRespondersPage.waitFor(() -> !autoRespondersPage.getCurrentUrl().equals(url));
        }
        List<AutoResponderContainer> containers = autoRespondersPage.autoResponders();
        autoRespondersPage.addAutoResponderButton.click();
        //wait for new responder to be added
        waitFor(() -> autoRespondersPage.autoResponders().size() > containers.size());
        AutoResponderContainer lastResponder = autoRespondersPage.getLatestResponder();
        if (isAllDay) {
            lastResponder.allDayCheckbox().check();
        } else {
            lastResponder.allDayCheckbox().uncheck();
        }
        //uncheck all days first
        for (Day day : Day.values()) {
            lastResponder.dayCheckBox(day).uncheck();
        }
        //check all required days checkbox
        for (Day day : days) {
            lastResponder.dayCheckBox(day).check();
        }
        lastResponder.messageInput().enterText(message);
        autoRespondersPage.saveButton.click();
        autoRespondersPage.statusMessage.waitForDisplayed();
        autoRespondersPage.statusMessage.waitForDisappear();
        return this;
    }

    @Override
    public ClientSteps addAutoResponder(Client client, String message, String from, String to, boolean isAllDay, Day... days) {
        throw new NotImplementedException("implement from and to dates parsing first");
    }

    @Override
    public ClientSteps addOptOutHeader(Client client, String message) {
        goToConsentManagementPage(client);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutHeaderInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    @Override
    public ClientSteps addOptOutBody(Client client, String message) {
        goToConsentManagementPage(client);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutBodyInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    @Override
    public ClientSteps addOptOutFooter(Client client, String message) {
        goToConsentManagementPage(client);
        consentManagementPage.enableOptOutCheckbox.check();
        consentManagementPage.optOutFooterInput.enterText(message);
        consentManagementPage.saveButton.click();
        return this;
    }

    @Override
    public ClientSteps addWebhook(Client client, String webhookName, String webhookUrl, Integer interval) {
        if (!webHooksPage.isOpened()) {
            goToClientSettingsPage(client.getCode());
            String url = dashboardPage.getCurrentUrl();
            clientsPage.sideBarMenu.openItem("Webhooks");
            webHooksPage.waitFor(() -> !autoRespondersPage.getCurrentUrl().equals(url));
        }
        webHooksPage.addButton.click();
        webHooksPage.webHooksPopup.waitForDisplayed();
        webHooksPage.webHooksPopup.nameInput.enterText(webhookName);
        webHooksPage.webHooksPopup.urlTemplateInput.enterText(webhookUrl);
        webHooksPage.webHooksPopup.expiryIntervalMinutesInput.enterText(interval);
        webHooksPage.webHooksPopup.saveButton.click();
        webHooksPage.webHooksPopup.waitForDisappear();
        return this;
    }

    private void goToConsentManagementPage(Client client){
        if (!consentManagementPage.isOpened()) {
            goToClientSettingsPage(client.getCode());
            String url = dashboardPage.getCurrentUrl();
            clientsPage.sideBarMenu.openItem("Consent Management");
            consentManagementPage.waitFor(() -> !consentManagementPage.getCurrentUrl().equals(url));
        }
    }

    private void goToClientSettingsPage(String code) {
        if (!clientsPage.isOpened()) {
            dashboardPage.headerMenu.adminTools().goToSubMenu("Clients");
        }
        TableRowItem tableRowItem = clientsPage.clientsTable.searchInTable("Code", code);
        if (tableRowItem == null) {
            throw new RuntimeException("Client was not found by code '" + code + "'!");
        }
        clientsPage.clientsTable.editFirstItemButton().click();
    }
}
