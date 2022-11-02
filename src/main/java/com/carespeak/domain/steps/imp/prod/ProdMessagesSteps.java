package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.message.NotificationType;
import com.carespeak.domain.steps.MessagesSteps;
import com.carespeak.domain.ui.prod.component.table.base.TableRowItem;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.page.messages.MessagesPage;
import com.carespeak.domain.ui.prod.popup.SelectModuleActionTypePopup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProdMessagesSteps implements MessagesSteps {

    private DashboardPage dashboardPage;
    private MessagesPage messagesPage;
    private SelectModuleActionTypePopup selectModuleActionTypePopup;
    private Dropdown dropdown;


    public ProdMessagesSteps() {
        dashboardPage = new DashboardPage();
        messagesPage = new MessagesPage();
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
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
        List<String> options = messagesPage.selectModuleActionTypePopup.moduleDropdownOnMessageTab.getAvailableOptions();
        messagesPage.selectModuleActionTypePopup.nextButton.click();
        return Module.getModules(options);
    }
    @Override
    public Boolean isMessageModulesEqualToClient(Module[] modules, Client client) {
        dashboardPage.headerMenu.messagesMenuItem.click();
        List<Module> selectedModulesOnClientLevel = getAvailableModules(client.getName());
        List<Module> modulesInDropDownOnMessageLevel = new ArrayList<>(Arrays.asList(modules));

        return selectedModulesOnClientLevel.equals(modulesInDropDownOnMessageLevel);
    }

    @Override
    public MessagesSteps addSmsMessage(Module module, Action action, MessageType messageType, String messageName, NotificationType notificationType, String smsMessage) {
        goToMessagesTab();
        messagesPage.addButton.click();
        messagesPage.actionsDropDown.select(action.getValue());
        messagesPage.typeDropDown.select(messageType.getValue());
        messagesPage.modulesDropDown.select(module.getValue());

        String url = dashboardPage.getCurrentUrl();
        selectModuleActionTypePopup.nextButton.click();
        waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);
        messagesPage.messageName.enterText(messageName);
        messagesPage.notificationTriggerTypeDropDown.select(notificationType.getValue());
        String url2 = dashboardPage.getCurrentUrl();
        messagesPage.nextButton.click();
        waitFor(() -> !dashboardPage.getCurrentUrl().equals(url2), false);
        messagesPage.messageTextField.enterText(smsMessage);
        messagesPage.saveButton.click();
        return this;
    }

    @Override
    public boolean isMessageCreated(String clientName, String messageName) {
        TableRowItem messageRow = messagesPage.messageTable.getFirstRowItem();
        if(messageRow == null){
            throw new RuntimeException("Message was not found!");
        }

        findMessageByName(messageName);

        boolean result = messagesPage.firstMessageName.getText().equals(messageName);

        Logger.info("Is message'" + messageName + "' is found? '"+ result);
        return result;
    }

    private void findMessageByName(String messageName) {
        messagesPage.findMessageByName(messageName);
    }
}
