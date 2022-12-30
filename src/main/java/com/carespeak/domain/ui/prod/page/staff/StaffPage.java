package com.carespeak.domain.ui.prod.page.staff;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.core.logger.Logger;
import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.StatusPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StaffPage extends AbstractPage {

    public HeaderMenu headerMenu;
    public ItemsTable staffManagersTable;
    public SearchWithSelection searchClient;
    public StatusPopup statusPopup;

    public StaffPage() {
        headerMenu = new HeaderMenu();
        staffManagersTable = new ItemsTable(By.id("staffTableWrapper"));
        searchClient = new SearchWithSelection();
        statusPopup = new StatusPopup();
    }

    private static final String MULTI_CLIENT_ADMIN = "Multi client program manager";
    private static final String ROLE_PROGRAM_ADMIN = "Client level program manager";
    private static final String ROLE_PROGRAM_STAFF = "Regular staff program manager";
    private static final String ROLE_AGGREGATE_ONLY_STAFF = "Staff dashboard read-only";


    @ElementName("Add button")
    @FindBy(xpath = "//*[@id='staffTableButtonsContainer']//*[text()='Add']")
    public Button addButton;
    @ElementName("Delete first staff manager button")
    @FindBy(xpath = "//button[@class='btn btn-danger btn-sm delete-btn']")
    public Button deleteFirstStaffManagerButton;

    @ElementName("Delete first staff manager button")
    @FindBy(xpath = "//button[contains(@class,'success ui-button')]")
    public Button deleteStaffManagerOkButton;

    @ElementName("Impersonate this staff member icon")
    @FindBy(xpath = "//a[@href]/img[@alt='Impersonate this staff member']")
    public Button impersonateStaffMemberIcon;

    @ElementName("Cell phone input")
    @FindBy(id = "mobile")
    public Input cellPhoneInput;
    @ElementName("Tim Zone dropdown")
    @FindBy(id = "formUser.timeZone")
    public Dropdown timeZoneDropdown;

    @ElementName("Time zone list dropdown")
    @FindBy(xpath = "//span[@class='text' and contains(text(),'Time')]")
    public List<WebElement> timeZoneListDropDownOptions;

    @ElementName("Cell phone confirmation input")
    @FindBy(id = "mobileConfirm")
    public Input cellPhoneConfirmationInput;

    @ElementName("Status dropdown")
    @FindBy(xpath= "//button[@data-id='formUser.status']")
    public Dropdown statusDropdown;

    @ElementName("First name input")
    @FindBy(xpath = "//input[@id='formUser.firstName']")
    public Input firstNameInput;

    @ElementName("Last name input")
    @FindBy(xpath = "//input[@id='formUser.lastName']")
    public Input lastNameInput;
    @ElementName("Email input")
    @FindBy(id = "email")
    public Input emailInput;

    @ElementName("Email confirmation input")
    @FindBy(id = "emailConfirm")
    public Input emailConfirmationInput;

    @ElementName("Zip Code input")
    @FindBy(xpath = "//input[@id='formUser.postalCode']")
    public Input zipCodeInput;

    @ElementName("Phone input")
    @FindBy(id = "mobile")
    public Input phoneInput;

    @ElementName("Staff Security Roles")
    @FindBy(xpath = "//input[@name='staffRole']")
    public List<ClickableElement> staffSecurityRolesRadioButtons;
    @ElementName("Multi client program manager (can manage all programs within assigned clients)")
    @FindBy(xpath = "//input[@value='MULTI_CLIENT_ADMIN']")
    public ClickableElement multiClientProgramManagerRadioButton;
    @ElementName("Client level program manager")
    @FindBy(xpath = "//input[@value='ROLE_PROGRAM_ADMIN']")
    public ClickableElement clientLevelProgramManagerRadioButton;
    @ElementName("Regular staff program manager")
    @FindBy(xpath = "//input[@value='ROLE_PROGRAM_STAFF']")
    public ClickableElement regularStaffProgramManagerRadioButton;
    @ElementName("Staff dashboard read-only")
    @FindBy(xpath = "//input[@value='ROLE_AGGREGATE_ONLY_STAFF']")
    public ClickableElement staffDashBoardReadOnlyProgramManagerRadioButton;

    @ElementName("Save button")
    @FindBy(xpath = "//button//*[contains(text(), 'Save')]")
    public Button saveButton;
    @ElementName("Staff List Back Button")
    @FindBy(xpath = "//a[@class='cs-back-to btn btn-danger']")
    public Button staffListBackButton;

    @ElementName("Profile Button")
    @FindBy(xpath = "//a[@id='actions-user']//span")
    public WebElement profileButton;

    @ElementName("Return to Admin Button")
    @FindBy(xpath = "//ul[@class='dropdown-menu']//a[@href='/carespeak/adminExitUser']")
    public Button returnToAdminButton;

    @ElementName("Can not delete self")
    @FindBy(xpath = "//div[text()='Can not delete self']")
    public WebElement canNotDeleteSelfMessage;

//    @ElementName("You don't have permission to delete this staff message")
//    @FindBy(xpath = "//button[@class='btn btn-danger btn-sm disabled']")
//    public List<WebElement> youDonotHavePermissionToDeleteThisStaffMessage;

    public void selectSecurityRole(String requiredRole){
        switch (requiredRole) {
            case MULTI_CLIENT_ADMIN:
                multiClientProgramManagerRadioButton.click();
                break;
            case ROLE_PROGRAM_ADMIN:
                clientLevelProgramManagerRadioButton.click();
                break;
            case ROLE_PROGRAM_STAFF:
                regularStaffProgramManagerRadioButton.click();
                break;
            case ROLE_AGGREGATE_ONLY_STAFF:
                staffDashBoardReadOnlyProgramManagerRadioButton.click();
                break;
            default:
                Logger.error("Staff role does not match with available list of roles");
        }
    }
}
