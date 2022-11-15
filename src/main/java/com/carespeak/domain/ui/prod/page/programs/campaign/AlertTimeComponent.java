package com.carespeak.domain.ui.prod.page.programs.campaign;

import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.domain.ui.prod.component.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Component that responsible for alert times on campaign creation
 */
public class AlertTimeComponent extends AbstractComponent {

    private static final String ROOT_LOCATOR = "//div[contains(@class, 'occasionPicker')]//*[contains(@class, 'tmpLocalTimeTableEntry')]";
    private static final String HOUR_LOCATOR = "//*[contains(@class, 'hour')]";
    private static final String MINUTE_LOCATOR = "//*[contains(@class, 'minute')]";
    private static final String AM_PM_LOCATOR = "//*[contains(@class, 'ampm')]";

    public List<AlertTimeContainer> findAlertTimeContainers() {
        List<AlertTimeContainer> res = new ArrayList<>();
        List<WebElement> containers = driver.findElements(By.xpath(ROOT_LOCATOR));
        for (WebElement container : containers) {
            Dropdown hour = new Dropdown(container.findElement(By.xpath(HOUR_LOCATOR)), "Alert hour dropdown");
            Dropdown minute = new Dropdown(container.findElement(By.xpath(MINUTE_LOCATOR)), "Alert minute dropdown");
            Dropdown apPm = new Dropdown(container.findElement(By.xpath(AM_PM_LOCATOR)), "Alert AM/PM dropdown");
            AlertTimeContainer alertTimeContainer = new AlertTimeContainer(hour, minute, apPm);
            res.add(alertTimeContainer);
        }
        return res;
    }


}
