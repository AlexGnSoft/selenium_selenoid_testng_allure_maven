package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AddSelectedPatientToPatientListPopup extends AbstractPopup {

    @ElementName("Popup title")
    @FindBy(xpath = "//span[@class='ui-dialog-title']")
    public ClickableElement title;

    @ElementName("Patient list dropdown button")
    @FindBy(xpath = "//div[contains(@class,'bs-container')]//div[@class='dropdown-menu open']")
    public Dropdown patientListExpand;

    @ElementName("Patient list dropdown")
    @FindBy(xpath = "//div[contains(@class,'btn-group bootstrap-select form-control pl')]//button")
    public Dropdown patientListDropDown;

    @ElementName("Patient list dropdown")
    @FindBy(xpath = "//div[contains(@class,'btn-group bootstrap-select form-control pl')]//button")
    public Dropdown patientListDropDownButton;

    @ElementName("Patient list dropdown")
    @FindBy(xpath = "//span[@class='text' and contains(text(),'Patient list name')]")
    public List<WebElement> patientListDropDownOptions;

    @ElementName("Popup add button")
    @FindBy(xpath = "//div[contains(@class, 'ui-dialog-buttonset')]//button//*[contains(text(), 'Add')]")
    public Button addButton;

    @Override
    public String getPopupName() {
        return "Add selected patient to patient list";
    }

    @Override
    public ClickableElement getTitleElement() {
        return title;
    }

    public void selectPatientList(String patientListName) {
        for (int i = 0; i < patientListDropDownOptions.size(); i++) {
            if(patientListDropDownOptions.get(i).getText().equals(patientListName)){
                patientListDropDownOptions.get(i).click();
                break;
            }
        }
    }
}
