package com.carespeak.domain.ui.component.sidebar;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.logger.Logger;
import com.carespeak.domain.ui.component.AbstractComponent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AttachmentSideBar extends AbstractComponent {

    @ElementName("Patient message log attachment dropdown")
    @FindBy(xpath = "//div[contains(@class, 'attachment')]//img")
    public ClickableElement attachment;

    public boolean isImageDisplayed(WebElement imageWebElement) {
        boolean result = (boolean) ((JavascriptExecutor) DriverFactory.getDriver()).executeScript(
                "return arguments[0].complete && " +
                        "typeof arguments[0].naturalWidth != \"undefined\" && " +
                        "arguments[0].naturalWidth > 0", imageWebElement);

        Logger.info("Is image displayed? - " + result);
        return result;
    }

}
