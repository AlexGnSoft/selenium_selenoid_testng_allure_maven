package com.carespeak.domain.ui.page.programs.auto_responders;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.page.programs.AbstractProgramPage;
import org.openqa.selenium.support.FindBy;

public class ProgramAutoRespondersPage extends AbstractProgramPage {

    @ElementName("Reject Unsolicited Messages checkbox")
    @FindBy(id = "reject-unsolicited")
    public CheckBox rejectSolicitedCheckbox;

    @ElementName("Accepted message regexp input")
    @FindBy(xpath = "//*[@id='regexRows']//input")
    public Input acceptedMessageRegexInput;

    @ElementName("Add button")
    @FindBy(id = "addRegex")
    public Button addRegexButton;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    public boolean isOpened() {
        String url = getCurrentUrl();
        return url.contains("programs") && url.contains("auto-responders.page");
    }
}
