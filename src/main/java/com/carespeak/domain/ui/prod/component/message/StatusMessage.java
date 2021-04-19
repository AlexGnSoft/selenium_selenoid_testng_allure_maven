package com.carespeak.domain.ui.prod.component.message;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.logger.Logger;
import com.carespeak.domain.ui.prod.component.AbstractComponent;
import org.openqa.selenium.support.FindBy;

public class StatusMessage extends AbstractComponent {

    @ElementName("Status content element")
    @FindBy(xpath = "//*[contains(@id,'cs-alert-container')]//div[contains(@class, 'alert-content')]")
    private ClickableElement statusMessage;

    public ClickableElement getTitleElement() {
        return statusMessage;
    }

    public void waitForDisplayed() {
        Logger.info("Wait for status message to be displayed...");
        boolean isDisplayed = waitForElementAppear(getTitleElement());
        Logger.info("Status message is displayed? - " + isDisplayed);
    }

    public void waitForDisappear() {
        Logger.info("Wait for status message to disappear...");
        boolean isDisappeared = waitForElementDisappear(getTitleElement());
        Logger.info("Did status message disappear? - " + isDisappeared);
    }

}
