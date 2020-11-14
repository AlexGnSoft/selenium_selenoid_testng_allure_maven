package com.carespeak.domain.ui.page.programs.patients.patients;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.page.programs.AbstractProgramPage;
import org.openqa.selenium.support.FindBy;

public class ProgramPatientsTab extends AbstractProgramPage {

    @ElementName("Add patient button")
    @FindBy(xpath = "//a[contains(@href, '/patients/add/new.page')]")
    public Button addPatientBtn;


}
