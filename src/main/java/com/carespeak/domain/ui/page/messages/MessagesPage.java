package com.carespeak.domain.ui.page.messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.component.search.SearchWithSelection;
import com.carespeak.domain.ui.page.AbstractPage;
import com.carespeak.domain.ui.popup.SelectModuleActionTypePopup;
import org.openqa.selenium.support.FindBy;

public class MessagesPage extends AbstractPage {

    public SearchWithSelection searchClient;
    public SelectModuleActionTypePopup selectModuleActionTypePopup;

    @ElementName("Add message button")
    @FindBy(id = "messageAddBtn")
    public Button addButton;

    public MessagesPage() {
        searchClient = new SearchWithSelection();
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
    }

}
