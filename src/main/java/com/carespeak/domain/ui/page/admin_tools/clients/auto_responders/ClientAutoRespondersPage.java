package com.carespeak.domain.ui.page.admin_tools.clients.auto_responders;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.component.container.AutoResponderContainer;
import com.carespeak.domain.ui.component.message.StatusMessage;
import com.carespeak.domain.ui.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ClientAutoRespondersPage extends AbstractPage {

    @FindBy(id = "autoResponderEntryAddBtn")
    @ElementName("Add Auto Responder button")
    public Button addAutoResponderButton;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    public StatusMessage statusMessage;

    public ClientAutoRespondersPage() {
        statusMessage = new StatusMessage();
    }

    @Override
    public boolean isOpened() {
        String url = getCurrentUrl();
        return url.contains("client") && url.contains("auto-responders.page");
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
