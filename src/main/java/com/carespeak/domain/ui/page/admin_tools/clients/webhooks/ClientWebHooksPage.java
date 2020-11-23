package com.carespeak.domain.ui.page.admin_tools.clients.webhooks;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.component.table.base.ItemsTable;
import com.carespeak.domain.ui.page.admin_tools.clients.AbstractClientPage;
import com.carespeak.domain.ui.popup.AddWebHooksPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ClientWebHooksPage extends AbstractClientPage {

    public AddWebHooksPopup webHooksPopup;

    @ElementName("Add button")
    @FindBy(id = "webhook-add-btn")
    public Button addButton;

    @ElementName("Remove button")
    @FindBy(id = "webhook-delete-group-btn")
    public Button removeButton;

    public ItemsTable webHooksTable;

    public ClientWebHooksPage(){
        webHooksTable = new ItemsTable(By.id("webhooks-table-wrapper"));
        webHooksPopup = new AddWebHooksPopup();
    }

}
