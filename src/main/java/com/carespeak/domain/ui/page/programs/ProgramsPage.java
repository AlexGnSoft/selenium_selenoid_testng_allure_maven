package com.carespeak.domain.ui.page.programs;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.component.message.StatusMessage;
import com.carespeak.domain.ui.component.search.SearchWithSelection;
import com.carespeak.domain.ui.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.component.table.base.ItemsTable;
import com.carespeak.domain.ui.page.AbstractPage;
import com.carespeak.domain.ui.popup.ConfirmationPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ProgramsPage extends AbstractPage {

    public SearchWithSelection searchClient;

    public SideBarMenu sideBarMenu;

    public ItemsTable programTable;

    public ConfirmationPopup confirmationPopup;

    public StatusMessage statusMessage;

    @ElementName("Add Program button")
    @FindBy(id = "programAddButton")
    public Button addProgramButton;

    public ProgramsPage() {
        searchClient = new SearchWithSelection();
        programTable = new ItemsTable(By.id("programDataTable"));
        sideBarMenu = new SideBarMenu();
        confirmationPopup = new ConfirmationPopup();
        statusMessage = new StatusMessage();
    }

}
