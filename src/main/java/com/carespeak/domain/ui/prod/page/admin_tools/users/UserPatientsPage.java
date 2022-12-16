package com.carespeak.domain.ui.prod.page.admin_tools.users;

import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.popup.ConfirmationPopup;
import org.openqa.selenium.By;

public class UserPatientsPage extends AbstractUsersPage {

    public HeaderMenu headerMenu;

    public SideBarMenu sideBarMenu;
    public ItemsTable patientsTable;
    public ConfirmationPopup confirmationPopup;

    public UserPatientsPage() {
        headerMenu = new HeaderMenu();
        patientsTable = new ItemsTable(By.id("patientTable"));
        sideBarMenu = new SideBarMenu();
        confirmationPopup = new ConfirmationPopup();
    }

    @Override
    public boolean isOpened() {
        return driver.getCurrentUrl().contains("users/patients.page");
    }

}
