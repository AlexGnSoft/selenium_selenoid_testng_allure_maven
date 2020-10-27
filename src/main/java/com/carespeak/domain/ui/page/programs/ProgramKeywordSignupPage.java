package com.carespeak.domain.ui.page.programs;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.component.table.AccountCreationTable;
import com.carespeak.domain.ui.popup.StatusPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ProgramKeywordSignupPage extends AbstractProgramPage {

    public AccountCreationTable accountCreationTable;
    public StatusPopup statusPopup;

    @ElementName("Keyword for patient signup input")
    @FindBy(id = "keyword")
    public Input keywordInput;

    @ElementName("Skip question response input")
    @FindBy(id = "SKIP_QUESTION")
    public Input skipQuestionResponseInput;

    @ElementName("Save button")
    @FindBy(id = "keywordSignupSaveBtn")
    public Button saveButton;

    //Account creation section
    @ElementName("Add account creation question button")
    @FindBy(id = "addAccountCreationQuestionBtn")
    public Button addQuestionButton;

    public ProgramKeywordSignupPage() {
        accountCreationTable = new AccountCreationTable(By.id("ac-table"));
        statusPopup = new StatusPopup();
    }


}
