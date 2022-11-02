package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.logger.Logger;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Action;
import com.carespeak.domain.entities.message.MessageType;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.entities.message.NotificationType;
import com.carespeak.domain.steps.MessagesSteps;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.TableRowItem;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.page.messages.MessagesPage;
import com.carespeak.domain.ui.prod.popup.SelectModuleActionTypePopup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProdMessagesSteps implements MessagesSteps {

    private final DashboardPage dashboardPage;
    private final MessagesPage messagesPage;
    private final SelectModuleActionTypePopup selectModuleActionTypePopup;
    private final SideBarMenu sideBarMenu;


    public ProdMessagesSteps() {
        dashboardPage = new DashboardPage();
        messagesPage = new MessagesPage();
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
        sideBarMenu = new SideBarMenu();
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
        selectModuleActionTypePopup.nextButton.click();
        messagesPage.messageName.enterText(messageName);
        messagesPage.notificationTriggerTypeDropDown.select(notificationType.getValue());
        messagesPage.nextButton.click();
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

        isCreatedMessageDisplayed(messageName);

        boolean result = false;
        String actualSmsName = messagesPage.firstMessageName.getText();
        if(actualSmsName.equals(messageName))
            result = true;

        Logger.info("Is message'" + messageName + "' is found? '"+ result);
        return result;
    }

    @Override
    public boolean isCreatedMessageDisplayed(String messageName) {
        return messagesPage.isCreatedMessageDisplayed(messageName);
    }

    @Override
    public String getMessageText() {
        return messagesPage.getMessageText();
    }

    @Override
    public MessagesSteps updateTextMessageBody(String newMessage) {
        messagesPage.messageTable.editFirstItemButton().click();
        sideBarMenu.openItem("SMS");
        messagesPage.messageTextField.enterText(newMessage);
        messagesPage.saveButton.click();
        messagesPage.sideBarMessagesButton.click();
        return this;
    }

    @Override
    public boolean areMessageTextUpdated(String initialMessage, String expectedUpdatedMessage) {
        return messagesPage.areMessageTextUpdated(initialMessage, expectedUpdatedMessage);
    }

}
