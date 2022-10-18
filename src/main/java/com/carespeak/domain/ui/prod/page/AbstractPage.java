package com.carespeak.domain.ui.prod.page;

import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.driver.decorator.CustomDecorator;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.ICanWait;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPage implements ICanWait {

    protected RemoteWebDriver driver;

    public AbstractPage() {
        driver = DriverFactory.getDriver();
        PageFactory.initElements(new CustomDecorator(new DefaultElementLocatorFactory(driver)), this);
    }

    public void openSite() {
        driver = DriverFactory.getDriver();
        PageFactory.initElements(new CustomDecorator(new DefaultElementLocatorFactory(driver)), this);
        driver.get(ConfigProvider.provide().get("app_url"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOpened() {
        String pageSuffix = getClass().getSimpleName().toLowerCase().replace("page", "");
        return getCurrentUrl().contains(pageSuffix) || driver.getTitle().toLowerCase().contains(pageSuffix);
    }

    public void switchToNewTab() {
        String currentWindowHandle = driver.getWindowHandle();
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        if (windowHandles.size() > 1) {
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(currentWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    return;
                }
            }
        }
        throw new RuntimeException("No new tabs opened! Tabs count is: " + windowHandles.size());
    }

    //TODO: improve implementation
    public void switchBack() {
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        if (windowHandles.size() > 1) {
            driver.switchTo().window(windowHandles.get(0));
            return;
        }
        throw new RuntimeException("No new tabs opened! Tabs count is: " + windowHandles.size());
    }

    public void sleepWait(int waitTime){
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
