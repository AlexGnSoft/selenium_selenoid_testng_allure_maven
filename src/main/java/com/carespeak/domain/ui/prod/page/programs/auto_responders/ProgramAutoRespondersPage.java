package com.carespeak.domain.ui.prod.page.programs.auto_responders;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.component.container.AutoResponderContainer;
import com.carespeak.domain.ui.prod.component.message.StatusMessage;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ProgramAutoRespondersPage extends AbstractProgramPage {

    @ElementName("Add Auto Responder button")
    @FindBy(id = "autoResponderEntryAddBtn")
    public Button addAutoResponderButton;

    @ElementName("Reject Unsolicited Messages checkbox")
    @FindBy(id = "reject-unsolicited")
    public CheckBox rejectSolicitedCheckbox;

    @ElementName("Accepted message regexp input")
    @FindBy(xpath = "//*[@id='regexRows']//input")
    public Input acceptedMessageRegexInput;

    @ElementName("Add button")
    @FindBy(id = "addRegex")
    public Button addRegexButton;

    @ElementName("Status dropdown button")
    @FindBy(id = "arStatusSelect")
    public Dropdown statusExpand;

    @ElementName("Status dropdown")
    @FindBy(xpath = "//select[@id='arStatusSelect']//option")
    public Dropdown statusDropdown;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    public StatusMessage statusMessage;

    public ProgramAutoRespondersPage() {
        statusMessage = new StatusMessage();
    }

    @Override
    public boolean isOpened() {
        String url = getCurrentUrl();
        return url.contains("programs") && url.contains("auto-responders.page");
    }

    public List<AutoResponderContainer> autoResponders() {
        List<AutoResponderContainer> containers = new ArrayList<>();
        List<WebElement> containerElements = driver.findElements(By.xpath("//*[contains(@id, 'auto-responder-entry-container')]"));
        for (WebElement containerElement : containerElements) {
            if (containerElement.isDisplayed()) {
                containers.add(new AutoResponderContainer(containerElement));
            }
        }
        return containers;
    }

    public AutoResponderContainer getLatestResponder() {
        List<AutoResponderContainer> containers = autoResponders();
        return containers.size() == 0 ? null : autoResponders().get(0);
    }
}
