package com.carespeak.domain.ui.page;

import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.driver.decorator.CustomDecorator;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.ICanWait;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class AbstractPage implements ICanWait {

    protected RemoteWebDriver driver;

    public AbstractPage() {
        driver = DriverFactory.getDriver();
        PageFactory.initElements(new CustomDecorator(new DefaultElementLocatorFactory(driver)), this);
    }

    public void openSite() {
        driver.get(ConfigProvider.provide().get("app.url"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOpened() {
        String pageSuffix = getClass().getSimpleName().toLowerCase().replace("page", "");
        return getCurrentUrl().contains(pageSuffix) || driver.getTitle().toLowerCase().contains(pageSuffix);
    }

}
