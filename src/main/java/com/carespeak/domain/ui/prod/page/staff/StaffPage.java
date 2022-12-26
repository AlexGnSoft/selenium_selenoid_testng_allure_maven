package com.carespeak.domain.ui.prod.page.staff;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StaffPage extends AbstractPage {

    public HeaderMenu headerMenu;

    private static final String MULTI_CLIENT_ADMIN = "Multi client program manager";
    private static final String ROLE_PROGRAM_ADMIN = "Client level program manager";
    private static final String ROLE_PROGRAM_STAFF = "Regular staff program manager";
    private static final String ROLE_AGGREGATE_ONLY_STAFF = "Staff dashboard read-only";

    @ElementName("Add button")
    @FindBy(xpath = "//*[@id='staffTableButtonsContainer']//*[text()='Add']")
    public Button addButton;

    @ElementName("Cell phone input")
    @FindBy(id = "mobile")
    public Input cellPhoneInput;

    @ElementName("Time zone dropdown selected element")
    @FindBy(xpath = "")
    public Dropdown timezoneDropdownSelected;
    @ElementName("Cell phone confirmation input")
    @FindBy(id = "mobileConfirm")
    public Input cellPhoneConfirmationInput;
    @ElementName("TimeZone dropdown")
    @FindBy(xpath = "//button[@data-id='formUser.timeZone']")
    public Dropdown timezoneDropdown;

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


    public StaffPage() {
        headerMenu = new HeaderMenu();
    }

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
        }
    }
}
