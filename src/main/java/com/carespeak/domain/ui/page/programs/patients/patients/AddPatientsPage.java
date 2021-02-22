package com.carespeak.domain.ui.page.programs.patients.patients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.core.logger.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class AddPatientsPage extends ProgramPatientsTab {

    @ElementName("Cell phone input")
    @FindBy(id = "mobile")
    public Input cellPhoneInput;

    @ElementName("Cell phone confirmation input")
    @FindBy(id = "mobileConfirm")
    public Input cellPhoneConfirmationInput;

    @ElementName("TimeZone dropdown")
    @FindBy(id = "formUser.timeZone")
    public Dropdown timezoneDropdown;

    @ElementName("Status dropdown")
    @FindBy(id = "formUser.status")
    public Dropdown statusDropdown;

    @ElementName("Email status dropdown")
    @FindBy(id = "emailStatus")
    public Dropdown emailStatusDropdown;

    @ElementName("First name input")
    @FindBy(id = "formUser.firstName")
    public Input firstNameInput;

    @ElementName("Last name input")
    @FindBy(id = "formUser.lastName")
    public Input lastNameInput;

    @ElementName("Email input")
    @FindBy(id = "email")
    public Input emailInput;

    @ElementName("Email confirmation input")
    @FindBy(id = "emailConfirm")
    public Input emailConfirmationInput;

    @ElementName("Zip Code input")
    @FindBy(id = "formUser.postalCode")
    public Input zipCodeInput;

    @ElementName("Phone input")
    @FindBy(id = "formUser.voice")
    public Input phoneInput;

    @ElementName("Sex radiobutton option - Male")
    @FindBy(xpath = "//input[@name='formUser.gender' and @value='M']")
    public ClickableElement maleRadioButtonOption;

    @ElementName("Sex radiobutton option - Female")
    @FindBy(xpath = "//input[@name='formUser.gender' and @value='F']")
    public ClickableElement femaleRadioButtonOption;

    @ElementName("Date of Birth Month input")
    @FindBy(id = "formUser.birthDate-month")
    public Input monthInput;

    @ElementName("Date of Birth Day input")
    @FindBy(id = "formUser.birthDate-day")
    public Input dayInput;

    @ElementName("Date of Birth Year input")
    @FindBy(id = "formUser.birthDate-year")
    public Input yearInput;

    @ElementName("Tags input")
    @FindBy(id = "formUser.tags")
    public Input tagsInput;

    @ElementName("Endpoint dropdown")
    @FindBy(id = "endpoint")
    public Dropdown endpointDropdown;

    @ElementName("Endpoint list")
    @FindBy(xpath = "//select[@id='endpoint']/option")
    public List<WebElement> endpointList;

    @ElementName("Enroll patient radiobutton option")
    @FindBy(id = "assignAllOn")
    public ClickableElement yesEnrollPatientsOption;

    @ElementName("Do not wish to enroll radiobutton option")
    @FindBy(id = "assignNone")
    public ClickableElement noEnrolPatientsoption;

    @ElementName("Save button")
    @FindBy(xpath = "//button//*[contains(text(), 'Save')]")
    public Button saveButton;

    public boolean isOpened() {
        return getCurrentUrl().contains("patients/add/new.page");
    }

    public List<String> getEndpoints() {
        List<String> result = new ArrayList<>();
        for (WebElement endpoint : endpointList) {
            result.add(endpoint.getText());
        }
        Logger.info("Complete list of endpoints: " + result);
        return result;
    }
/*
    public String getCellPhoneText() {
        String cellPhoneText = cellPhoneInput.getAttribute("value");
        Logger.info("Get cell phone text - " + cellPhoneText);
        return cellPhoneText;
    }

    public String getTimeZoneText() {
        Select selected = new Select(timezoneDropdown);
        String timeZoneText = selected.getFirstSelectedOption().getText();
        Logger.info("Get time zone text - " + timeZoneText);
        return timeZoneText;
    }

    public String getStatusText() {
        Select selected = new Select(statusDropdown);
        String status = selected.getFirstSelectedOption().getText();
        Logger.info("Get status text - " + status);
        return status;
    }

    public String getEmailStatusText() {
        Select selected = new Select(emailStatusDropdown);
        String emailStatus = selected.getFirstSelectedOption().getText();
        Logger.info("Get email status text - " + emailStatus);
        return emailStatus;
    }

    public String getFirstNameText() {
        String firstNameText = firstNameInput.getAttribute("value");
        Logger.info("Get first name text - " + firstNameText);
        return firstNameText;
    }

    public String getLastNameText() {
        String lastNameText = lastNameInput.getAttribute("value");
        Logger.info("Get last name text - " + lastNameText);
        return lastNameText;
    }

    public String getEmailText() {
        String emailText = emailInput.getAttribute("value");
        Logger.info("Get email text - " + emailText);
        return emailText;
    }*/

}
