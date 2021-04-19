package com.carespeak.domain.ui.prod.component;

import com.carespeak.core.driver.decorator.CustomDecorator;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.ICanWait;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class AbstractComponent implements ICanWait {

    protected RemoteWebDriver driver;

    public AbstractComponent() {
        driver = DriverFactory.getDriver();
        PageFactory.initElements(new CustomDecorator(new DefaultElementLocatorFactory(driver)), this);
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
