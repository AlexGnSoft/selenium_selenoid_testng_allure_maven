package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import org.openqa.selenium.support.FindBy;

//TODO: review status popup usages
public class StatusPopup extends AbstractPopup {

    @ElementName("Status content element")
    @FindBy(xpath = "//*[contains(@id,'cs-alert-container')]//div[contains(@class, 'alert-content')]")
    private ClickableElement statusMessage;

    @ElementName("Close status popup")
    @FindBy(xpath = "//button[@class='close']")
    private Button closeButton;

    @Override
    public String getPopupName() {
        return "Status popup";
    }

    @Override
    public ClickableElement getTitleElement() {
        return statusMessage;
    }

    public void close() {
        closeButton.click();
    }

}
