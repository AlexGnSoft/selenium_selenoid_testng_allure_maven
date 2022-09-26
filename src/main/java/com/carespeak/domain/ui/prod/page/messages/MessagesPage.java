package com.carespeak.domain.ui.prod.page.messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.steps.imp.prod.ProdMessagesSteps;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import com.carespeak.domain.ui.prod.popup.SelectModuleActionTypePopup;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesPage extends AbstractPage {

    public SearchWithSelection searchClient;
    public SelectModuleActionTypePopup selectModuleActionTypePopup;
    public DashboardPage dashboardPage;
    public ProdMessagesSteps prodMessagesSteps;

    @ElementName("Add message button")
    @FindBy(id = "messageAddBtn")
    public Button addButton;

    public MessagesPage() {
        searchClient = new SearchWithSelection();
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
    }
}
