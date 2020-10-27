package com.carespeak.domain.ui.page.admin_tools;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.component.header.HeaderMenu;
import com.carespeak.domain.ui.page.AbstractPage;
import org.openqa.selenium.support.FindBy;

public class SendSMSSimulatorPage extends AbstractPage {

    public HeaderMenu headerMenu;

    @ElementName("Search patients for phone number input")
    @FindBy(id = "search")
    public Input searchPatientForPhoneNumberInput;

    @ElementName("To dropdown")
    @FindBy(id = "to")
    public Dropdown toEndpointDropdown;

    @ElementName("From input")
    @FindBy(id = "from")
    public Input fromInput;

    @ElementName("Body text element")
    @FindBy(id = "body")
    public Input bodyInput;

    @ElementName("Send button")
    @FindBy(id = "send")
    public Button sendButton;

    public SendSMSSimulatorPage() {
        headerMenu = new HeaderMenu();
    }

    @Override
    public boolean isOpened() {
        return getCurrentUrl().contains("sms-sender-simulator.page");
    }

}
