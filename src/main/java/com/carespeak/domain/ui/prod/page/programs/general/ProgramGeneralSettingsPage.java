package com.carespeak.domain.ui.prod.page.programs.general;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.core.logger.Logger;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.popup.StatusPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ProgramGeneralSettingsPage extends AbstractProgramPage {

    public StatusPopup statusPopup;
    public SearchWithSelection searchClient;

    private static final String LINKED_PATIENT_PROGRAM_DROPDOWN_VALUE_XPATH = "//a[@aria-selected='true']/span[contains(text(),'%s')]";

    @ElementName("Program name input")
    @FindBy(id = "programName")
    public Input programNameInput;

    @ElementName("Program URL name input")
    @FindBy(id = "programUrlName")
    public Input programUrlNameInput;

    @ElementName("Program access dropdown")
    @FindBy(id = "programAccess")
    public Dropdown programAccessDropDown;

    @ElementName("Program type dropdown")
    @FindBy(id = "programType")
    public Dropdown programTypeDropDown;

    @ElementName("Linked patient program dropdown")
    @FindBy(xpath = "//button[@data-id='patientProgram']")
    public Dropdown linkedPatientProgramDropDown;

    @ElementName("Endpoint dropdown")
    @FindBy(xpath = "//*[@data-id='endpoints']")
    public Dropdown programEndpointDropdown;

    @ElementName("Endpoint list")
    @FindBy(xpath = "//body/div/div[@role='combobox']//span[@class='text']")
    public List<WebElement> endpointList;

    @ElementName("Save program button")
    @FindBy(id = "programGeneralSaveBtn")
    public Button saveButton;

    @ElementName("Linked caregiver program element")
    @FindBy(xpath = "//div[contains(@class,'cs-form-element')]/a[@href]")
    public WebElement linkedCaregiverProgramElement;



    public ProgramGeneralSettingsPage() {
        statusPopup = new StatusPopup();
        searchClient = new SearchWithSelection();
    }

    public List<String> getEndpoints() {
        List<String> result = new ArrayList<>();
        for (WebElement endpoint : endpointList) {
            result.add(endpoint.getText());
        }
        Logger.info("Complete list of endpoints: " + result);
        return result;
    }

    public void selectLinkedPatientProgram(String program1) {
        By locator = By.xpath(String.format(LINKED_PATIENT_PROGRAM_DROPDOWN_VALUE_XPATH, program1));
        ClickableElement program = new ClickableElement(driver.findElement(locator), program1 + " dropDownElement");
        program.click();
    }

}