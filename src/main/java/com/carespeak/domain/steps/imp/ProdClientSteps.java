package com.carespeak.domain.steps.imp;

import com.carespeak.domain.entities.common.Day;
import com.carespeak.domain.entities.program.Client;
import com.carespeak.domain.steps.ClientSteps;
import com.carespeak.domain.ui.component.container.AutoResponderContainer;
import com.carespeak.domain.ui.component.table.base.TableRowItem;
import com.carespeak.domain.ui.page.admin_tools.ClientsPage;
import com.carespeak.domain.ui.page.admin_tools.client.ClientAutoRespondersPage;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public class ProdClientSteps implements ClientSteps {

    private ClientAutoRespondersPage autoRespondersPage;
    private DashboardPage dashboardPage;
    private ClientsPage clientsPage;

    public ProdClientSteps() {
        autoRespondersPage = new ClientAutoRespondersPage();
        dashboardPage = new DashboardPage();
        clientsPage = new ClientsPage();
    }

    @Override
    public ClientSteps addAutoResponder(Client client, String message, boolean isAllDay, Day... days) {
        if (!autoRespondersPage.isOpened()) {
            goToClientSettingsPage(client.getCode());
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

    public void goToClientSettingsPage(String code) {
        if (!clientsPage.isOpened()) {
            dashboardPage.headerMenu.adminTools().goToSubMenu("Clients");
        }
        TableRowItem tableRowItem = clientsPage.clientsTable.searchInTable("Code", code);
        if (tableRowItem == null) {
            throw new RuntimeException("Client was not found by code '" + code + "'!");
        }
        clientsPage.clientsTable.editFirstItemButton().click();
        String url = dashboardPage.getCurrentUrl();
        clientsPage.sideBarMenu.openItem("Auto Responders");
        autoRespondersPage.waitFor(() -> !autoRespondersPage.getCurrentUrl().equals(url));
    }
}
