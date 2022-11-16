package com.carespeak.domain.ui.prod.page.messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MedicationPage extends AbstractPage {

    @ElementName("Medication field")
    @FindBy(id = "medication")
    public Input medicationField;

    @ElementName("Medication name field")
    @FindBy(id = "name")
    public Input medicationName;

    @ElementName("Next button")
    @FindBy(id = "nextBtn")
    public Button nextBtn;

    @ElementName("Cancel button")
    @FindBy(id = "cancelBtn")
    public Button cancelBtn;
}
