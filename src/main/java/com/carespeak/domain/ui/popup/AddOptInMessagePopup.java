package com.carespeak.domain.ui.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.ClickableElement;
import org.openqa.selenium.support.FindBy;

public class AddOptInMessagePopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//div[contains(@class,'alert-content')]")
    public ClickableElement title;

    @ElementName("Close popup button")
    @FindBy(xpath = "//button[contains(@class,'close')]")
    public ClickableElement closePopupButton;


    @Override
    public String getPopupName() {
        return "Program opt-in messages successfully saved popup";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
