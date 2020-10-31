package com.carespeak.domain.ui.popup;

import com.carespeak.core.driver.decorator.CustomDecorator;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.ICanWait;
import com.carespeak.core.logger.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class AbstractPopup implements ICanWait {

    protected RemoteWebDriver driver;

    public AbstractPopup() {
        driver = DriverFactory.getDriver();
        PageFactory.initElements(new CustomDecorator(new DefaultElementLocatorFactory(driver)), this);
    }

    public abstract String getPopupName();

    public abstract ClickableElement getTitleElement();

    public void waitForDisplayed() {
        String popupName = getPopupName();
        Logger.info("Wait for " + popupName + " to be displayed...");
        boolean isDisplayed = waitForElementAppear(getTitleElement());
        Logger.info(popupName + " is displayed? - " + isDisplayed);
    }

    public void waitForDisappear() {
        String popupName = getPopupName();
        Logger.info("Wait for " + popupName + " to disappear...");
        boolean isDisappeared = waitForElementDisappear(getTitleElement());
        Logger.info("Did " + popupName + " disappear? - " + isDisappeared);
    }

    protected boolean waitForElementAppear(WebElement el) {
        return waitFor(el::isEnabled, false);
    }

    protected boolean waitForElementDisappear(WebElement el) {
        return waitFor(() -> {
            try {
                return el.isEnabled();
            } catch (WebDriverException e) {
                return true;
            }
        }, false);
    }


}
