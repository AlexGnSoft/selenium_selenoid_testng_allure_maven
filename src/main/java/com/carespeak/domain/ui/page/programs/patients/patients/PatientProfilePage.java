package com.carespeak.domain.ui.page.programs.patients.patients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.domain.ui.page.programs.AbstractProgramPage;
import org.openqa.selenium.support.FindBy;

public class PatientProfilePage extends AbstractProgramPage {

    @ElementName("Endpoint dropdown")
    @FindBy(id = "endpoint")
    public Dropdown endpointDropdown;
}
