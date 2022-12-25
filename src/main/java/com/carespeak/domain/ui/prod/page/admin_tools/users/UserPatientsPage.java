package com.carespeak.domain.ui.prod.page.admin_tools.users;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.popup.ConfirmationPopup;
import com.carespeak.domain.ui.prod.popup.MobileHealthManagerPatientsPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class UserPatientsPage extends AbstractUsersPage {

    public HeaderMenu headerMenu;
    public SideBarMenu sideBarMenu;
    public ItemsTable patientsTable;
    public ConfirmationPopup confirmationPopup;
    public MobileHealthManagerPatientsPopup mobileHealthManagerPatientsPopup;
    public SearchWithSelection searchClient;

    @ElementName("Delete patient button")
    @FindBy(xpath = "//span[@class='btn btn-danger btn-sm delete-btn']")
    public Button deletePatientButton;

    public UserPatientsPage() {
        headerMenu = new HeaderMenu();
        patientsTable = new ItemsTable(By.id("patientTable"));
        sideBarMenu = new SideBarMenu();
        confirmationPopup = new ConfirmationPopup();
        searchClient = new SearchWithSelection();
        mobileHealthManagerPatientsPopup = new MobileHealthManagerPatientsPopup();
    }

    @Override
    public boolean isOpened() {
        return driver.getCurrentUrl().contains("users/patients.page");
    }

}
