package com.carespeak.domain.steps.imp;

import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.steps.MessagesSteps;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.page.messages.MessagesPage;

import java.util.List;

public class ProdMessagesSteps implements MessagesSteps {

    private DashboardPage dashboardPage;
    private MessagesPage messagesPage;

    public ProdMessagesSteps() {
        dashboardPage = new DashboardPage();
        messagesPage = new MessagesPage();
    }

    @Override
    public MessagesSteps goToMessagesTab() {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.messagesMenuItem.click();
        waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
        return this;
    }

    @Override
    public List<Module> getAvailableModules(String clientName) {
        messagesPage.searchClient.search(clientName);
        messagesPage.addButton.click();
        messagesPage.selectModuleActionTypePopup.waitForDisplayed();
        messagesPage.selectModuleActionTypePopup.moduleDropdown.click();
        List<String> options = messagesPage.selectModuleActionTypePopup.moduleDropdown.getAvailableOptions();
        return Module.getModules(options);
    }
}
