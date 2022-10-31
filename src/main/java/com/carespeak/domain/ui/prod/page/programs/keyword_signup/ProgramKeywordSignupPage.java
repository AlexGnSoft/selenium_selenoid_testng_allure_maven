package com.carespeak.domain.ui.prod.page.programs.keyword_signup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.component.table.AccountCreationTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import com.carespeak.domain.ui.prod.popup.StatusPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ProgramKeywordSignupPage extends AbstractProgramPage {

    public AccountCreationTable accountCreationTable;
    public StatusPopup statusPopup;

    private static final String PROGRAM_NAME = "//span[text() = '%s']";

    @ElementName("Keyword for patient signup input")
    @FindBy(id = "keyword")
    public Input keywordInput;

    @ElementName("Destination program question keywords button")
    @FindBy(id = "cs-as-dpk-items-add")
    public Button addDestinationProgramKeywordBtn;

    @ElementName("Destination program question keyword input")
    @FindBy(xpath = "//div[@class = 'cs-as-dpk-items']//input[@name='keyword']")
    public Input destinationProgramKeywordInput;

    @ElementName("Destination program dropdown button")
    @FindBy(xpath = "//div[@class='cs-as-dpk-items']//button[@role='button']")
    public Button destinationProgramDropdownButton;

    @ElementName("Skip question response input")
    @FindBy(id = "SKIP_QUESTION")
    public Input skipQuestionResponseInput;

    @ElementName("Save button")
    @FindBy(id = "keywordSignupSaveBtn")
    public Button saveButton;

    //Account creation section
    @ElementName("Add account creation question button")
    @FindBy(id = "addQuestionBtn")
    public Button addQuestionButton;

    @ElementName("Validation message button")
    @FindBy(id = "toggleValidationMessageBtn")
    public Button validationMessageButton;

    @ElementName("Validation message button disabled")
    @FindBy(xpath = "//button[@id='toggleValidationMessageBtn' and @disabled='disabled']")
    public Button validationMessageDisabledButton;

    @ElementName("Validation message input")
    @FindBy(id = "validationMessage")
    public Input validationMessageInput;

    @ElementName("Completed message button")
    @FindBy(id = "toggleCompletedMessageBtn")
    public Button completedMessageButton;

    @ElementName("Completed message input")
    @FindBy(id = "completedMessage")
    public Input completedMessageInput;


    public ProgramKeywordSignupPage() {
        accountCreationTable = new AccountCreationTable(By.id("ac-table"));
        statusPopup = new StatusPopup();
    }

    public void selectProgramByName(String programName) {
        By locator = By.xpath(String.format(PROGRAM_NAME, programName));
        ClickableElement program = new ClickableElement(driver.findElement(locator), programName + " button");
        program.click();
    }

}
