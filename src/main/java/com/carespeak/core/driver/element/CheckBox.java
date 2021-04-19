package com.carespeak.core.driver.element;

import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.driver.reporter.ElementActionsReporter;
import org.apache.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckBox extends ClickableElement {

    private static final String LOG_NAME = CheckBox.class.getCanonicalName();

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CheckBox.class);


    public CheckBox(WebElement element) {
        super(element);
    }

    public CheckBox(Object element, String name) {
        super(element, name);
    }

    public CheckBox(Object element, String name, By locator) {
        super(element, name, locator);
    }

    public CheckBox(Object element) {
        super(element);
    }

    public void check() {
        LOG.log(LOG_NAME, Level.INFO, "Checking " + name, null);
        ElementActionsReporter.report("Checking " + name, () -> {
            if (!isChecked()) {
                innerElement.click();
            }
            return null;
        });
    }

    public void uncheck() {
        LOG.log(LOG_NAME, Level.INFO, "Unchecking " + name, null);
        ElementActionsReporter.report("Unchecking " + name, () -> {
            if (isChecked()) {
                innerElement.click();
            }
            return null;
        });
    }

    public boolean isChecked() {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(innerElement));
        return innerElement.isSelected();
    }
}