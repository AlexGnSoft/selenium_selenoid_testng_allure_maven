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
    @FindBy(id = "value1_n0")
    public Input fieldNameInput;

    @ElementName("Add Custom Field button")
    @FindBy(xpath = "//div[@id='cs-content']//a[contains(@class, 'addCustomField')]")
    public Button addCustomFieldBtn;

    @ElementName("Save button")
    @FindBy(id = "csCustomFieldsBtn")
    public Button saveButton;

    public ProgramCustomFieldsPage() {
        statusPopup = new StatusPopup();
    }
}
