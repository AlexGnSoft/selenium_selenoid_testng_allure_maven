package com.carespeak.domain.ui.prod.page.admin_tools.clients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.ConfirmationPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ClientsPage extends AbstractPage {

    public HeaderMenu headerMenu;

    public SideBarMenu sideBarMenu;

    public ItemsTable clientsTable;

    public ConfirmationPopup confirmationPopup;

    @ElementName("Add Client Button")
    @FindBy(id = "clientAddBtn")
    public ClickableElement addClientButton;

    public ClientsPage() {
        headerMenu = new HeaderMenu();
        clientsTable = new ItemsTable(By.id("clientsTable"));
        sideBarMenu = new SideBarMenu();
        confirmationPopup = new ConfirmationPopup();
    }

    @Override
    public boolean isOpened() {
        return driver.getCurrentUrl().contains("clients/list.page");
    }

}
