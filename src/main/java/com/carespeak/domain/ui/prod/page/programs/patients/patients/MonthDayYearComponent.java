package com.carespeak.domain.ui.prod.page.programs.patients.patients;

import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.domain.ui.prod.component.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class MonthDayYearComponent extends AbstractComponent {

    private static final String ROOT_LOCATOR = "//*[contains(@class, 'cs-form-element-column-container right')][7]";
    private static final String MONTH_LOCATOR = "//select[@id='formUser.birthDate-month']";
    private static final String DAY_LOCATOR = "//select[@id='formUser.birthDate-day']";
    private static final String YEAR_LOCATOR = "//select[@id='formUser.birthDate-year']";

    public List<MonthDayYearContainer> findMonthDayYearContainer() {
        List<MonthDayYearContainer> res = new ArrayList<>();
        List<WebElement> containers = driver.findElements(By.xpath(ROOT_LOCATOR));
        for (WebElement container : containers) {
            Dropdown month = new Dropdown(container.findElement(By.xpath(MONTH_LOCATOR)), "Month dropdown");
            Dropdown day = new Dropdown(container.findElement(By.xpath(DAY_LOCATOR)), "Day dropdown");
            Dropdown year = new Dropdown(container.findElement(By.xpath(YEAR_LOCATOR)), "Year dropdown");
            MonthDayYearContainer monthDayYearContainer = new MonthDayYearContainer(month, day, year);
            res.add(monthDayYearContainer);
        }
        return res;
    }
}
