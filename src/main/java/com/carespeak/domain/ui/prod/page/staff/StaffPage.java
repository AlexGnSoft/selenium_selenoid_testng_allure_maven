package com.carespeak.domain.ui.prod.page.staff;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import org.openqa.selenium.support.FindBy;

public class StaffPage extends AbstractPage {

    public HeaderMenu headerMenu;

    @ElementName("Add button")
    @FindBy(xpath = "//*[@id='staffTableButtonsContainer']//*[text()='Add']")
    public Button addButton;

    public StaffPage() {
        headerMenu = new HeaderMenu();
    }
}
