package com.carespeak.domain.ui.prod.page.admin_tools.clients.modules;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.page.admin_tools.clients.AbstractClientPage;
import com.carespeak.domain.ui.prod.popup.StatusPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class ClientModulesPage extends AbstractClientPage {

    public SideBarMenu sideBarMenu;
    public StatusPopup statusPopup;

    @ElementName("Check all module checkbox")
    @FindBy(id = "moduleCheckAll")
    public CheckBox checkAllCheckBox;

    @ElementName("Appointments module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='AP']")
    public CheckBox appointmentsCheckBox;

    @ElementName("Biometric module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='BI']")
    public CheckBox biometricCheckBox;

    @ElementName("Education module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='ED']")
    public CheckBox educationCheckBox;

    @ElementName("Incentive module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='IN']")
    public CheckBox incentiveCheckBox;

    @ElementName("Journal module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='JO']")
    public CheckBox journalCheckBox;

    @ElementName("Medication module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='ME']")
    public CheckBox medicationCheckBox;

    @ElementName("Motivation module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='MO']")
    public CheckBox motivationCheckBox;

    @ElementName("Rules module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='RU']")
    public CheckBox rulesCheckBox;

    @ElementName("Survey module checkbox")
    @FindBy(xpath = "//input[@type='checkbox' and @value='SU']")
    public CheckBox surveyCheckBox;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    public ClientModulesPage() {
        super();
        sideBarMenu = new SideBarMenu();
        statusPopup = new StatusPopup();
    }

    public void check(Module... modules) {
        check(Arrays.asList(modules));
    }

    public void check(List<Module> modules) {
        for (Module module : modules) {
            if (Module.CHECK_ALL.equals(module)) {
                checkAllCheckBox.check();
            } else {
                String value = module.name().toUpperCase().substring(0, 2);
                By checkboxLocator = By.xpath("//input[@type='checkbox' and @value='" + value + "']");
                new CheckBox(driver.findElement(checkboxLocator), module.name() + " module checkbox").check();
            }
        }
    }

    public void uncheckAll() {
        checkAllCheckBox.uncheck();
        for (Module module : Module.getAllModules()) {
            String value = module.name().toUpperCase().substring(0, 2);
            By checkboxLocator = By.xpath("//input[@type='checkbox' and @value='" + value + "']");
            CheckBox checkBox = new CheckBox(driver.findElement(checkboxLocator), module.name() + " module checkbox");
            checkBox.scrollIntoView();
            checkBox.uncheck();
        }
    }

}
