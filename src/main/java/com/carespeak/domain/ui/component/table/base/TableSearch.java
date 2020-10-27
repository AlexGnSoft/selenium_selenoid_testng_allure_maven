package com.carespeak.domain.ui.component.table.base;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.component.AbstractComponent;
import org.openqa.selenium.support.FindBy;

class TableSearch extends AbstractComponent {

    @ElementName("Table search input")
    @FindBy(xpath = ".//input[@type='search']")
    private Input searchInput;

    public void search(String text) {
        searchInput.sendKeys(text);
    }


}
