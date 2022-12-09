package com.carespeak.domain.ui.prod.component.table.base;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Input;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.domain.ui.prod.component.AbstractComponent;
import com.carespeak.domain.ui.prod.page.programs.ProgramsPage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class TableSearch extends AbstractComponent {

    @ElementName("Table search input")
    @FindBy(xpath = ".//input[@type='search']")
    private Input searchInput;

    public void search(String text) {
        searchInput.clear();
        searchInput.sendKeys(text);
    }
}
