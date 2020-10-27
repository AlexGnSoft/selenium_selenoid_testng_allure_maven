package com.carespeak.domain.ui.page.programs;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.component.search.SearchWithSelection;
import com.carespeak.domain.ui.popup.StatusPopup;
import org.openqa.selenium.support.FindBy;

public class ProgramGeneralSettingsPage extends AbstractProgramPage {

    public StatusPopup statusPopup;
    public SearchWithSelection searchClient;

    @ElementName("Program name input")
    @FindBy(id = "programName")
    public Input programNameInput;

    @ElementName("Program URL name input")
    @FindBy(id = "programUrlName")
    public Input programUrlNameInput;

    @ElementName("Program access dropdown")
    @FindBy(id = "programAccess")
    public Dropdown programAccessDropDown;

    @ElementName("Save program button")
    @FindBy(id = "programGeneralSaveBtn")
    public Button saveButton;

    public ProgramGeneralSettingsPage() {
        statusPopup = new StatusPopup();
        searchClient = new SearchWithSelection();
    }

}
