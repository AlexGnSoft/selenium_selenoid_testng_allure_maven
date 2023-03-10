package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Input;
import org.openqa.selenium.support.FindBy;

public class SimulateResponsePopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//span[@class='ui-dialog-title']")
    public ClickableElement title;

    @ElementName("Response input field")
    @FindBy(id = "srResponseText")
    public Input responseInput;

    @ElementName("Close popup button")
    @FindBy(xpath = "//span[contains(text(),'close')]")
    public Input closePopupButton;

    @ElementName("Send popup button")
    @FindBy(xpath = "//span[@class='ui-button-text' and text()='Send']")
    public Input sendButton;

    @ElementName("Close popup button")
    @FindBy(xpath = "//span[contains(text(),'Close')]")
    public Input closeButton;

    @Override
    public String getPopupName() {
        return "Simulate Response";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
