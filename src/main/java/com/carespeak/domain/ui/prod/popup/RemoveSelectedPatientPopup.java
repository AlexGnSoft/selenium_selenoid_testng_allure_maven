package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import org.openqa.selenium.support.FindBy;

public class RemoveSelectedPatientPopup extends AbstractPopup{

    @ElementName("Delete campaign pop up title")
    @FindBy(xpath = "//span[@class='ui-dialog-title']")
    public ClickableElement title;

    @ElementName("Ok button")
    @FindBy(xpath = "//button[contains(@class,'success ui-button')]")
    public Button okButton;

    @ElementName("Cancel button")
    @FindBy(xpath = "//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']")
    public Button cancelButton;
    @Override
    public String getPopupName() {
        return "Remove selected patients";
    }
    @Override
    public ClickableElement getTitleElement() {
        return title;
    }
}
