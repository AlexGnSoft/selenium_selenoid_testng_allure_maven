package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import org.openqa.selenium.support.FindBy;

public class DeleteSelectedPatientPopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//span[@class='ui-dialog-title']")
    public ClickableElement title;

    @ElementName("Popup save button")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Ok')]")
    public Button okButton;

    @Override
    public String getPopupName() {
        return "Delete selected patients";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
