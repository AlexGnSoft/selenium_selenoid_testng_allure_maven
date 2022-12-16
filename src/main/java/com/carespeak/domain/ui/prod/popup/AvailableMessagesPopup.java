package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import org.openqa.selenium.support.FindBy;


public class AvailableMessagesPopup extends AbstractPopup{

    @ElementName("Popup title")
    @FindBy(xpath = "//span[@id='ui-id-1' and text()='Available Messages:']")
    public ClickableElement title;

    @ElementName("Available Messages Allocate button")
    @FindBy(xpath = "//button[contains(@class,'success ui-button ui-widget')]")
    public Button allocateButtonOnPopup;

    @ElementName("Checkbox to select a message")
    @FindBy(xpath = "//table[contains(@id,'availableMessages')]//div[@class='center']/input")
    public ClickableElement checkBoxToSelectMessage;


    @Override
    public String getPopupName() {
        return "Available Messages:";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }


}
