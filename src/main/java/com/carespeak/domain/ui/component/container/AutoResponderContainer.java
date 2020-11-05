package com.carespeak.domain.ui.component.container;

import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.CheckBox;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.entities.common.Day;
import com.carespeak.domain.ui.component.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Represents Auto Responder container on Client -> Auto Responders tab
 */
public class AutoResponderContainer extends AbstractComponent {

    private By containerLocator;
    private WebElement containerRoot;

    public AutoResponderContainer(By containerLocator) {
        this.containerLocator = containerLocator;
    }

    public AutoResponderContainer(WebElement containerRoot) {
        this.containerRoot = containerRoot;
    }

    public Button removeAutoResponderButton() {
        return new Button(findInContainerById("auto-responder-entry-remove-button"), "Remove Auto Responder button");
    }

    public CheckBox allDayCheckbox() {
        return new CheckBox(findInContainerById("auto-responder-all-day"), "All day checkbox");
    }

    public CheckBox dayCheckBox(Day day) {
        WebElement checkbox = findInContainer(By.xpath(".//input[@name='auto-responder-days-' and @value='" + day.getDayNumber() + "']"));
        return new CheckBox(checkbox, day.getDayName() + " checkbox");
    }

    public Input messageInput() {
        return new Input(findInContainerById("auto-responder-input-message"), "Response message input");
    }

    private CheckBox dayCheckBox(int dayNumber) {
        return dayCheckBox(Day.getDay(dayNumber));
    }

    private WebElement findInContainerById(String id) {
        return container().findElement(By.xpath(".//*[contains(@id, '" + id + "')]"));
    }

    private WebElement findInContainer(By locator) {
        return container().findElement(locator);
    }

    private WebElement container() {
        if (containerLocator != null) {
            return driver.findElement(containerLocator);
        } else {
            return containerRoot;
        }
    }
}
