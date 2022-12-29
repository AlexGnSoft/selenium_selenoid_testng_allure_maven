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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;
import java.util.List;

public class ProgramGeneralSettingsPage extends AbstractProgramPage {

    public StatusPopup statusPopup;
    public SearchWithSelection searchClient;

    @ElementName("Program name input")
    @FindBy(id = "programName")
    public Input programNameInput;

    @ElementName("Program type dropdown")
    @FindBy(id = "programType")
    public Dropdown programTypeDropDown;

    @ElementName("Program access dropdown")
    @FindBy(id = "programAccess")
    public Dropdown programAccessDropDown;


    @ElementName("Endpoint dropdown")
    @FindBy(xpath = "//*[@data-id='endpoints']")
    public Dropdown programEndpointDropdown;

    @ElementName("Endpoint list")
    @FindBy(xpath = "//body/div/div[@role='combobox']//span[@class='text']")
    public List<WebElement> endpointList;

    @ElementName("Linked patient programs list")
    @FindBy(xpath = "//ul[@aria-expanded='true']/li/a/span[@class='text']")
    public List<WebElement> linkedPatientProgramsList;

    @ElementName("Programs dropdown button")
    @FindBy(xpath = "//div[@id='patient-program-wrapper']//button")
    public ClickableElement linkedPatientProgramExpandButton;


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
        linkedPatientProgramExpandButton.click();
        for (WebElement option: linkedPatientProgramsList) {
            if(option.getText().equals(program1))
                option.click();
            break;
        }
    }
}