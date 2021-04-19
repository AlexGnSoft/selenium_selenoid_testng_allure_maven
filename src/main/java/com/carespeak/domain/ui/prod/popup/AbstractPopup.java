package com.carespeak.domain.ui.prod.popup;

import com.carespeak.core.driver.decorator.CustomDecorator;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.ICanWait;
import com.carespeak.core.logger.Logger;
import org.openqa.selenium.JavascriptExecutor;
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
        Logger.info("Wait for " + popupName + " popup to be displayed...");
        boolean isDisplayed = waitForElementAppear(getTitleElement());
        Logger.info(popupName + " is displayed? - " + isDisplayed);
    }

    public void waitForDisappear() {
        String popupName = getPopupName();
        Logger.info("Wait for " + popupName + " popup to disappear...");
        boolean isDisappeared = waitForElementDisappear(getTitleElement());
        Logger.info("Did " + popupName + " disappear? - " + isDisappeared);
    }

    protected boolean waitForElementAppear(WebElement el) {
        return waitFor(() -> isVisible(el), false);
    }

    protected boolean waitForElementDisappear(WebElement el) {
        return waitFor(() -> !isVisible(el), false);
    }

    private boolean isVisible(WebElement element) {
        try {
            return (Boolean) ((JavascriptExecutor) driver).executeScript(
                    "var elem = arguments[0],                 " +
                            "  box = elem.getBoundingClientRect(),    " +
                            "  cx = box.left + box.width / 2,         " +
                            "  cy = box.top + box.height / 2,         " +
                            "  e = document.elementFromPoint(cx, cy); " +
                            "for (; e; e = e.parentElement) {         " +
                            "  if (e === elem)                        " +
                            "    return true;                         " +
                            "}                                        " +
                            "return false;                            "
                    , element);
        } catch (WebDriverException ex) {
            return false;
        }
    }

}
