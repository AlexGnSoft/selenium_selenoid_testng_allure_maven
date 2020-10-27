package com.carespeak.domain.ui.component;

import com.carespeak.core.driver.decorator.CustomDecorator;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.ICanWait;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class AbstractComponent implements ICanWait {

    protected RemoteWebDriver driver;

    public AbstractComponent() {
        driver = DriverFactory.getDriver();
        PageFactory.initElements(new CustomDecorator(new DefaultElementLocatorFactory(driver)), this);
    }

}
