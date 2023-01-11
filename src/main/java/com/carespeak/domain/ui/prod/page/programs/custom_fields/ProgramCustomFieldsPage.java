package com.carespeak.domain.ui.prod.page.programs.custom_fields;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.popup.StatusPopup;
import org.openqa.selenium.support.FindBy;

public class ProgramCustomFieldsPage extends AbstractProgramPage {

    public StatusPopup statusPopup;

    @ElementName("Custom field name input")
    @FindBy(xpath = "//input[@class='form-control cs-custom-field-name' and @value='']")
    public Input fieldNameInput;

    @ElementName("Add Custom Field button")
    @FindBy(xpath = "//a[contains(@class, 'cs-new-custom-field-btn')]/i")
    public Button addCustomFieldBtn;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    public ProgramCustomFieldsPage() {
        statusPopup = new StatusPopup();
    }
}
