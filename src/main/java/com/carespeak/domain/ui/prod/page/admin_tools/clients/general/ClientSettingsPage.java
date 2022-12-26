package com.carespeak.domain.ui.prod.page.admin_tools.clients.general;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.page.admin_tools.clients.AbstractClientPage;
import org.openqa.selenium.support.FindBy;

public class ClientSettingsPage extends AbstractClientPage {

    @ElementName("Code input")
    @FindBy(id = "code")
    public Input codeInput;

    @ElementName("Name input")
    @FindBy(id = "name")
    public Input nameInput;

    @ElementName("Notes input")
    @FindBy(id = "notes")
    public Input notesInput;

    @ElementName("Endpoints dropdown button")
    @FindBy(xpath = "//*[@data-id='endpoints']")
    public ClickableElement endpointsExpand;

    @ElementName("Endpoints dropdown")
    @FindBy(xpath = "//ul[@role='listbox']")
    public Dropdown endpointsDropdown;

    @ElementName("Next button")
    @FindBy(id = "nextBtn")
    public Button nextButton;

    @ElementName("Cancel button")
    @FindBy(id = "cancelBtn")
    public Button cancelButton;

}
