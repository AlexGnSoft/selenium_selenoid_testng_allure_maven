package com.carespeak.domain.ui.component.sidebar;

import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.component.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class SideBarMenu extends AbstractComponent {

    private static final String DISPLAYED_ITEMS_LOCATOR = "//*[@id='cs-sidebar']//ul/li[not(contains(@style, 'display:none'))]//*[@class='link-text']";

    public void openItem(String menuItem) {
        String url = driver.getCurrentUrl();
        menuItem(menuItem).click();
        waitFor(() -> !driver.getCurrentUrl().equals(url), 10, false);
        waitFor(() -> !driver.findElement(By.id("cs-sub-header")).getText().trim().contains(menuItem), 10, false);
    }

    private ClickableElement menuItem(String itemName) {
        waitFor(() -> driver.findElements(By.xpath(DISPLAYED_ITEMS_LOCATOR)).size() > 0, false);
        List<WebElement> menuItems = driver.findElements(By.xpath(DISPLAYED_ITEMS_LOCATOR));
        for (WebElement menuItem : menuItems) {
            if (itemName.toLowerCase().equals(menuItem.getText().trim().toLowerCase())) {
                return new ClickableElement(menuItem, itemName + " menu item ");
            }
        }
        throw new NoSuchElementException("No such menu item '" + itemName + "'");
    }

}
