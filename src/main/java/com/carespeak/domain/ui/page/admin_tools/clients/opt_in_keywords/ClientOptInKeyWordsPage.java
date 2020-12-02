package com.carespeak.domain.ui.page.admin_tools.clients.opt_in_keywords;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.page.admin_tools.clients.AbstractClientPage;
import org.openqa.selenium.support.FindBy;

public class ClientOptInKeyWordsPage extends AbstractClientPage {

    @ElementName("Use default checkbox")
    @FindBy(id = "useDefault")
    public CheckBox useDefaultCheckbox;

    @ElementName("Custom keywords panel")
    @FindBy(id = "customKeywords")
    public ClickableElement customKeyWordsPanel;

    @ElementName("Start Keywords Input")
    @FindBy(id = "startKeywords")
    public Input startKeywordsInput;

    @ElementName("Stop Keywords Input")
    @FindBy(id = "stopKeywords")
    public Input stopKeywordsInput;

    @ElementName("Help Keywords Input")
    @FindBy(id = "helpKeywords")
    public Input helpKeywordsInput;

    @ElementName("Agree Keywords Input")
    @FindBy(id = "agreeKeywords")
    public Input agreeKeywordsInput;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    public ClientOptInKeyWordsPage(){
    }


}
