package com.carespeak.core.driver.element;

import com.carespeak.core.driver.reporter.ElementActionsReporter;
import io.qameta.allure.Allure;
import org.apache.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Input extends ClickableElement {

    private static final String LOG_NAME = Input.class.getCanonicalName();

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ClickableElement.class);

    public Input(WebElement element) {
        super(element);
    }

    public Input(Object element, String name) {
        super(element, name);
    }

    public Input(Object element, String name, By locator) {
        super(element, name, locator);
    }

    public Input(Object element) {
        super(element);
    }

    public void enterText(String text) {
        scrollIntoView();
        waitFor(() -> innerElement.isDisplayed(), 10, false);
        if (text != null) {
            LOG.log(LOG_NAME, Level.DEBUG, "Clear " + name, null);
            highlight();
            innerElement.clear();
            sendKeys(text);
        } else {
            Allure.step("Provided text is null, nothing to enter to " + name);
            LOG.log(LOG_NAME, Level.INFO, "Provided text is null, nothing to enter to " + name, null);
        }
    }

    public void enterText(Integer text) {
        scrollIntoView();
        waitFor(() -> innerElement.isDisplayed(), 10, false);
        if (text != null) {
            LOG.log(LOG_NAME, Level.DEBUG, "Clear " + name, null);
            highlight();
            innerElement.clear();
            sendKeys(String.valueOf(text));
        } else {
            ElementActionsReporter.report("Provided text is null, nothing to enter to " + name, () -> {
                LOG.log(LOG_NAME, Level.INFO, "Provided text is null, nothing to enter to " + name, null);
                return null;
            });
        }
    }
}
