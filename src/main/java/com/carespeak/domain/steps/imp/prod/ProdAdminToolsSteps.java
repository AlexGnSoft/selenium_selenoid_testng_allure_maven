package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Language;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.staff.StaffManager;
import com.carespeak.domain.steps.AdminToolsSteps;
import com.carespeak.domain.ui.prod.component.table.base.TableRowItem;
import com.carespeak.domain.ui.prod.page.admin_tools.clients.ClientsPage;
import com.carespeak.domain.ui.prod.page.admin_tools.clients.general.ClientSettingsPage;
import com.carespeak.domain.ui.prod.page.admin_tools.clients.language_settings.ClientLanguageSettingsPage;
import com.carespeak.domain.ui.prod.page.admin_tools.clients.modules.ClientModulesPage;
import com.carespeak.domain.ui.prod.page.admin_tools.sms_send_simulator.SendSMSSimulatorPage;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.page.staff.StaffPage;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProdAdminToolsSteps implements AdminToolsSteps {

    private DashboardPage dashboardPage;
    private ClientsPage clientsPage;
    private ClientSettingsPage clientSettingsPage;
    private ClientModulesPage clientModulesPage;
    private ClientLanguageSettingsPage clientLanguageSettingsPage;
    private SendSMSSimulatorPage sendSMSSimulatorPage;
    private ProdProgramSteps prodProgramSteps;
    private StaffPage staffPage;

    public ProdAdminToolsSteps() {
        clientsPage = new ClientsPage();
        dashboardPage = new DashboardPage();
        clientSettingsPage = new ClientSettingsPage();
        clientModulesPage = new ClientModulesPage();
        clientLanguageSettingsPage = new ClientLanguageSettingsPage();
        sendSMSSimulatorPage = new SendSMSSimulatorPage();
        staffPage = new StaffPage();
        prodProgramSteps = new ProdProgramSteps();
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
        clientModulesPage.checkUncheckAll();
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
        client.setModules(getModulesFromWebElements());
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
    public AdminToolsSteps goToSpecificTab(String specificTab) {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.adminTools().goToSubMenu(specificTab);
        waitFor(() -> !dashboardPage.getCurrentUrl().equals(url));

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

    @Override
    public AdminToolsSteps initiateKeywordSignupSendAgreeNameAndSkip(String clientName, String programName, String phoneNumber, String endpoint, String keyword) {
        simulateSMSToClient(phoneNumber, endpoint, keyword);
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "AGREE");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "Name");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "SKIP");

        return this;
    }

    @Override
    public AdminToolsSteps initiateKeywordSignupSendAgreeNameAndSkipDate(String clientName, String programName, String phoneNumber, String endpoint, String keyword, String patientName, String date) {
        simulateSMSToClient(phoneNumber, endpoint, keyword);
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "AGREE");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, patientName);
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "SKIP");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, date);

        return this;
    }

    @Override
    public AdminToolsSteps initiateSmsFirstLastNameDateSex(String clientName, String programName, String phoneNumber, String endpoint,String sex) {

        simulateSMSToClient(phoneNumber, endpoint, "Tom Cruise");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "10/12/22");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, sex);
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        return this;
    }

    @Override
    public AdminToolsSteps initiateSignUpAgreeFirstLastNameDate(String clientName, String programName, String phoneNumber, String endpoint,String keyword) {

        simulateSMSToClient(phoneNumber, endpoint, keyword);
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "AGREE");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "Bob Marley");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "10/11/22");
        prodProgramSteps.goToProgramSettings(clientName, programName);

        return this;
    }

    @Override
    public AdminToolsSteps addStaffManager(StaffManager staffManager, String requiredRole, String timeZone) {
        if(!staffPage.isOpened()){
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.staffMenuItem.click();
            staffPage.waitFor(()-> !dashboardPage.getCurrentUrl().equals(url), false);
        }

        staffPage.addButton.click();
        staffPage.firstNameInput.enterText(staffManager.getFirstName());
        staffPage.lastNameInput.enterText(staffManager.getLastName());
        staffPage.emailInput.enterText(staffManager.getEmail());
        staffPage.emailConfirmationInput.enterText(staffManager.getEmail());
        staffPage.timeZoneDropdown.select(timeZone);
        staffPage.selectSecurityRole(requiredRole);
        staffPage.saveButton.click();
        staffPage.statusPopup.waitForDisplayed();
        staffPage.statusPopup.close();
        staffPage.statusPopup.waitForDisappear();

        return this;
    }

    @Override
    public AdminToolsSteps impersonateStaffMember(StaffManager staffManager) {
        staffPage.staffListBackButton.click();
        staffPage.staffManagersTable.searchFor(staffManager.getEmail());

        staffPage.impersonateStaffMemberIcon.click();

        return this;
    }

    @Override
    public boolean isStaffManagerImpersonated(StaffManager staffManager){
        boolean result = false;

        staffPage.waitFor(()-> staffPage.profileButton.isDisplayed());
        String profileFullName = staffPage.profileButton.getText().trim();
        String expectedProfileFullName = staffManager.getFirstName() + " " + staffManager.getLastName();

        if(profileFullName.equals(expectedProfileFullName)){
            staffPage.profileButton.click();
            staffPage.returnToAdminButton.click();
            result = true;
        } else {
            staffPage.profileButton.click();
            staffPage.returnToAdminButton.click();
        }
        Logger.info("Is staff manager was impersonated? " + result);
        return result;
    }

    @Override
    public AdminToolsSteps deleteStaffManager(StaffManager staffManager){
        staffPage.staffListBackButton.click();
        staffPage.staffManagersTable.searchFor(staffManager.getEmail());

        staffPage.deleteFirstStaffManagerButton.click();
        if (!staffPage.deleteStaffManagerOkButton.isDisplayed()) {
            waitFor(() -> staffPage.deleteStaffManagerOkButton.isVisible());
        }
        staffPage.deleteStaffManagerOkButton.click();
        waitFor(()-> !staffPage.deleteStaffManagerOkButton.isVisible());

        return this;
    }

    @Override
    public boolean isStaffManagerCreated(String clientName, String staffEmail) {
        boolean result = false;

        staffPage.staffListBackButton.click();
        staffPage.staffManagersTable.searchFor(staffEmail);

        TableRowItem tableRowItem = staffPage.staffManagersTable.getFirstRowItem();
        if(tableRowItem == null){
            throw new RuntimeException("Staff manager was not found by email '" + staffEmail + "'!");
        }

        String actualEmail = tableRowItem.getDataByHeader("Email");
        if(actualEmail.equals(staffEmail))
            return true;

        Logger.info("Is staff manager email '" + staffEmail + "' is found? '"+ result);
        return result;
    }

    @Override
    public boolean isStaffManagerDeleted(StaffManager staffManager){
        boolean result = false;

        TableRowItem firstRowItem = staffPage.staffManagersTable.getFirstRowItem();
        if(firstRowItem == null){
            result = true;
        }

        Logger.info("Is staff manager was deleted? " + result);
        return result;

    }

    @Override
    public AdminToolsSteps initiateKeywordSignupSendAndAgreeTherapyStartDate(String clientName, String programName, String phoneNumber, String endpoint, String keyword) {
        simulateSMSToClient(phoneNumber, endpoint, keyword);
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "AGREE");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "10/10/22");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        return this;
    }

    @Override
    public AdminToolsSteps initiateKeywordSignupSendAndAgree(String clientName, String programName, String phoneNumber, String endpoint, String keyword) {
        simulateSMSToClient(phoneNumber, endpoint, keyword);
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        simulateSMSToClient(phoneNumber, endpoint, "AGREE");
        prodProgramSteps.goToProgramSettings(clientName, programName).getLastPatientMessageFromLogs(phoneNumber);

        return this;
    }

    private List<Module> getModulesFromWebElements() {
        clientsPage.sideBarMenu.openItem("Modules");

        // Strings of modules
        List<String> modules = new ArrayList<>();
        for (WebElement allModuleName : clientModulesPage.allModuleNames) {
            modules.add(allModuleName.getText());
        }


        //Array of Module objects
        Module[] modulesToSet = new Module[modules.size()];
        for (int i = 0; i < modules.size()-1; i++) {
            modulesToSet[i] = Module.getModule(modules.get(i));
        }

        //List of Modules
        List<Module> module = new ArrayList<>();
        for (int i = 0; i < modulesToSet.length-1; i++) {
            module.add(modulesToSet[i]);
        }

        //Sort in alphabetical order
        module.sort(Comparator.comparing(Module::name));

        return module;
    }
}
