package com.carespeak.domain.steps.imp;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.common.Language;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.steps.AdminToolsSteps;
import com.carespeak.domain.ui.component.table.base.TableRowItem;
import com.carespeak.domain.ui.page.admin_tools.clients.ClientsPage;
import com.carespeak.domain.ui.page.admin_tools.sms_send_simulator.SendSMSSimulatorPage;
import com.carespeak.domain.ui.page.admin_tools.clients.language_settings.ClientLanguageSettingsPage;
import com.carespeak.domain.ui.page.admin_tools.clients.modules.ClientModulesPage;
import com.carespeak.domain.ui.page.admin_tools.clients.general.ClientSettingsPage;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;

import java.util.ArrayList;
import java.util.List;

public class ProdAdminToolsSteps implements AdminToolsSteps {

    private DashboardPage dashboardPage;
    private ClientsPage clientsPage;
    private ClientSettingsPage clientSettingsPage;
    private ClientModulesPage clientModulesPage;
    private ClientLanguageSettingsPage clientLanguageSettingsPage;
    private SendSMSSimulatorPage sendSMSSimulatorPage;

    public ProdAdminToolsSteps() {
        clientsPage = new ClientsPage();
        dashboardPage = new DashboardPage();
        clientSettingsPage = new ClientSettingsPage();
        clientModulesPage = new ClientModulesPage();
        clientLanguageSettingsPage = new ClientLanguageSettingsPage();
        sendSMSSimulatorPage = new SendSMSSimulatorPage();
    }

    @Override
    public AdminToolsSteps addNewClient(String code, String username, String notes, String endpoint, Module... modules) {
        if (!clientsPage.isOpened()) {
            dashboardPage.headerMenu.adminTools().goToSubMenu("Clients");
        }

        clientsPage.addClientButton.click();
        clientSettingsPage.codeInput.enterText(code);
        clientSettingsPage.nameInput.enterText(username);
        clientSettingsPage.notesInput.enterText(notes);
        clientSettingsPage.endpointsDropdown.select(clientSettingsPage.endpointsExpand, endpoint);
        clientSettingsPage.nextButton.click();
        clientModulesPage.check(modules);
        clientModulesPage.saveButton.click();

        clientModulesPage.statusPopup.waitForDisplayed();
        clientModulesPage.statusPopup.close();
        clientModulesPage.statusPopup.waitForDisappear();
        return this;
    }

    @Override
    public AdminToolsSteps addNewClient(Client client, Module... modules) {
        return addNewClient(client.getCode(), client.getName(), client.getNotes(), client.getEndpoint(), modules);
    }

    @Override
    public AdminToolsSteps addNewClient(Client client) {
        Module[] modules = new Module[client.getModules().size()];
        modules = client.getModules().toArray(modules);
        return addNewClient(client.getCode(), client.getName(), client.getNotes(), client.getEndpoint(), modules);
    }

    @Override
    public AdminToolsSteps removeClient(Client client) {
        if (!clientsPage.isOpened()) {
            dashboardPage.headerMenu.adminTools().goToSubMenu("Clients");
        }
        clientsPage.clientsTable.searchInTable("Name", client.getName());
        clientsPage.clientsTable.removeFirstItemButton().click();
        clientsPage.confirmationPopup.waitForDisplayed();
        clientsPage.confirmationPopup.okButton.click();
        clientsPage.confirmationPopup.waitForDisappear();
        return this;
    }

    @Override
    public AdminToolsSteps editClientsModules(String clientCode, Module... newModules) {
        if (!clientsPage.isOpened()) {
            dashboardPage.headerMenu.adminTools().goToSubMenu("Clients");
        }
        TableRowItem tableRowItem = clientsPage.clientsTable.searchInTable("Code", clientCode);
        if (tableRowItem == null) {
            throw new RuntimeException("Client was not found by code '" + clientCode + "'!");
        }
        clientsPage.clientsTable.editFirstItemButton().click();
        clientsPage.sideBarMenu.openItem("Modules");
        clientModulesPage.uncheckAll();
        clientModulesPage.check(newModules);
        clientModulesPage.saveButton.click();
        clientModulesPage.statusPopup.waitForDisplayed();
        clientModulesPage.statusPopup.close();
        clientModulesPage.statusPopup.waitForDisappear();
        return this;
    }

    @Override
    public Client getClientByCode(String code) {
        if (!clientsPage.isOpened()) {
            dashboardPage.headerMenu.adminTools().goToSubMenu("Clients");
        }
        TableRowItem tableRowItem = clientsPage.clientsTable.searchInTable("Code", code);
        if (tableRowItem == null) {
            Logger.info("Client was not found by code '" + code + "'!");
            return null;
        }
        clientsPage.clientsTable.editFirstItemButton().click();
        String selectedEndpoints = clientSettingsPage.endpointsExpand.getText();

        Client client = new Client();
        client.setName(tableRowItem.getDataByHeader("Name"));
        client.setCode(tableRowItem.getDataByHeader("Code"));
        client.setModules(getModules(tableRowItem.getDataByHeader("Modules")));
        client.setEndpoint(selectedEndpoints);
        return client;
    }

    @Override
    public AdminToolsSteps goToClientSettings(String code) {
        if (!clientsPage.isOpened()) {
            dashboardPage.headerMenu.adminTools().goToSubMenu("Clients");
        }
        TableRowItem tableRowItem = clientsPage.clientsTable.searchInTable("Code", code);
        if (tableRowItem == null) {
            throw new RuntimeException("Client was not found by code '" + code + "'!");
        }
        clientsPage.clientsTable.editFirstItemButton().click();
        return this;
    }

    @Override
    public AdminToolsSteps addAdditionalLanguage(List<Language> languages) {
        Language[] res = new Language[languages.size()];
        return addAdditionalLanguage(languages.toArray(res));
    }

    @Override
    public AdminToolsSteps addAdditionalLanguage(Language... languages) {
        if (!clientLanguageSettingsPage.isOpened()) {
            clientsPage.sideBarMenu.openItem("Language Settings");
        }

        for (Language language : languages) {
            int languagesCount = clientLanguageSettingsPage.getAdditionalLanguages().size();
            clientLanguageSettingsPage.addLanguageButton.click();
            waitFor(() -> clientLanguageSettingsPage.getAdditionalLanguages().size() > languagesCount);
            clientLanguageSettingsPage.getLastLanguageDropdown().select(language.getLanguageName());
        }
        clientLanguageSettingsPage.saveButton.click();
        return this;
    }

    @Override
    public AdminToolsSteps removeAdditionalLanguage(Language... languages) {
        if (!clientLanguageSettingsPage.isOpened()) {
            clientsPage.sideBarMenu.openItem("Language Settings");
        }

        for (Language language : languages) {
            int languagesCount = clientLanguageSettingsPage.getAdditionalLanguages().size();
            clientLanguageSettingsPage.getLanguageRemoveButton(language.getLanguageName()).click();
            waitFor(() -> clientLanguageSettingsPage.getAdditionalLanguages().size() < languagesCount);
        }
        clientLanguageSettingsPage.saveButton.click();
        return this;
    }

    @Override
    public List<Language> getAdditionalLanguages() {
        if (!clientLanguageSettingsPage.isOpened()) {
            clientLanguageSettingsPage.sideBarMenu.openItem("Language Settings");
        }

        List<Language> res = new ArrayList<>();
        for (String lang : clientLanguageSettingsPage.getAdditionalLanguages()) {
            res.add(Language.getLanguage(lang));
        }
        return res;
    }

    @Override
    public Language getDefaultLanguage() {
        if (!clientLanguageSettingsPage.isOpened()) {
            clientLanguageSettingsPage.sideBarMenu.openItem("Language Settings");
        }
        return Language.getLanguage(clientLanguageSettingsPage.defaultLanguage.getAttribute("value"));
    }

    @Override
    public AdminToolsSteps simulateSMSToClient(String fromPhoneNumber, String endpoint, String smsText) {
        if (!sendSMSSimulatorPage.isOpened()) {
            clientsPage.headerMenu.adminTools().goToSubMenu("SMS Send Simulator");
        }

        sendSMSSimulatorPage.toEndpointDropdown.select(endpoint);
        sendSMSSimulatorPage.fromInput.enterText(fromPhoneNumber);
        sendSMSSimulatorPage.bodyInput.enterText(smsText);
        sendSMSSimulatorPage.sendButton.click();
        return this;
    }

    private List<Module> getModules(String moduleShortNames) {
        String[] modulesShortName = moduleShortNames.split(",");
        List<Module> modules = new ArrayList<>();
        for (String shortName : modulesShortName) {
            modules.add(Module.getModule(shortName));
        }
        return modules;
    }

}
