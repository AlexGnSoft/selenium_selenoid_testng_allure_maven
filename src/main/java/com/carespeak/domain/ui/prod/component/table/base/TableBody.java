package com.carespeak.domain.ui.prod.component.table.base;

import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.logger.Logger;
import com.carespeak.domain.ui.prod.component.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: reimplement to count only visible rows
class TableBody extends AbstractComponent {

    private static final String TABLE_HEADERS = ".//th";
    private static final String TABLE_ROWS = ".//tr";
    private static final String TABLE_CELLS = ".//td";
    private static final String TABLE_CELL_LOCATOR = ".//td[%s]";

    private WebElement table;
    private By tableLocator;

    public TableBody(By tableLocator) {
        this.tableLocator = tableLocator;
    }

    public TableRowItem findItemBy(String tableHeader, String valueToSearch) {
        Logger.info("Searching for '" + valueToSearch + "' in column '" + tableHeader + "'");
        try {
            waitFor(() -> driver.findElement(tableLocator) != null);
            table = driver.findElement(tableLocator);
            Map<String, Integer> headersMap = getHeadersMap();
            int tdIndex = headersMap.get(tableHeader);
            //FIXME: search supported for exact match. Support wait for table to be fully loaded
            waitFor(() -> table.findElements(By.xpath(TABLE_ROWS)).size() == 2, false);
            List<WebElement> rows = table.findElements(By.xpath(TABLE_ROWS));
            if (rows.size() >= 2) {
                List<WebElement> tds = rows.get(1).findElements(By.xpath(TABLE_CELLS));
                if (tds.size() < headersMap.size()) {
                    Logger.info("Item with value '" + valueToSearch + "' wasn't found in table");
                    return null;
                }
            }
            //skip 0 index here because first tr is filled with th items
            for (int i = 1; i < rows.size(); i++) {
                WebElement td = rows.get(i).findElement(By.xpath(String.format(TABLE_CELL_LOCATOR, tdIndex)));
                if (valueToSearch.contains(td.getText())) {
                    Logger.info("Item found!");
                    return createTableRowItem(rows.get(i), headersMap);
                }
            }
        } catch (Throwable t) {
            //Error during table analyzing, do nothing here
            Logger.error("Failed to parse table", t);
        }
        Logger.error("Item with value '" + valueToSearch + "' in column '" + tableHeader + "' wasn't found in table");
        return null;
    }

    WebElement findRowWebElementBy(String tableHeader, String valueToSearch) {
        Logger.info("Searching for '" + valueToSearch + "' in column '" + tableHeader + "'");
        try {
            waitFor(() -> driver.findElement(tableLocator) != null);
            table = driver.findElement(tableLocator);
            Map<String, Integer> headersMap = getHeadersMap();
            int tdIndex = headersMap.get(tableHeader);
            //FIXME: search supported for exact match. Support wait for table to be fully loaded
            waitFor(() -> table.findElements(By.xpath(TABLE_ROWS)).size() == 2, false);
            List<WebElement> rows = table.findElements(By.xpath(TABLE_ROWS));
            if (rows.size() >= 2) {
                List<WebElement> tds = rows.get(1).findElements(By.xpath(TABLE_CELLS));
                if (tds.size() < headersMap.size()) {
                    Logger.debug("Row with value '" + valueToSearch + "' in column '" + tableHeader + "' wasn't found in table");
                    return null;
                }
            }
            //skip 0 index here because first tr is filled with th items
            for (int i = 1; i < rows.size(); i++) {
                WebElement td = rows.get(i).findElement(By.xpath(String.format(TABLE_CELL_LOCATOR, tdIndex)));
                if (valueToSearch.contains(td.getText())) {
                    Logger.debug("WebElement found!");
                    return rows.get(i);
                }
            }
        } catch (Throwable t) {
            //Error during table analyzing, do nothing here
            Logger.error("Failed to parse table and find required web element", t);
        }
        Logger.error("WebElement with value '" + valueToSearch + "' wasn't found in table");
        return null;
    }

    //FIXME: Add only visible items
    List<WebElement> getRowsWebElements() {
        Logger.debug("Receiving rows web elements...");
        try {
            waitFor(() -> driver.findElement(tableLocator) != null);
            table = driver.findElement(tableLocator);
            //FIXME: search supported for exact match. Support wait for table to be fully loaded
            waitFor(() -> table.findElements(By.xpath(TABLE_ROWS)).size() == 2, 5, false);
            return table.findElements(By.xpath(TABLE_ROWS));
        } catch (Throwable t) {
            //Error during table analyzing, do nothing here
            Logger.error("Failed to parse table and find rows web elements", t);
        }
        Logger.error("Rows web elements were not found");
        return null;
    }

    public TableRowItem getFirstRowItem() {
        Logger.info("Receiving first row item...");
        try {
            waitFor(() -> driver.findElement(tableLocator) != null);
            table = driver.findElement(tableLocator);
            Map<String, Integer> headersMap = getHeadersMap();
            waitFor(() -> table.findElements(By.xpath(TABLE_ROWS)).size() == 2, 5, false);
            List<WebElement> rows = table.findElements(By.xpath(TABLE_ROWS));
            return createTableRowItem(rows.get(1), headersMap);
        } catch (Throwable t) {
            //Error during table analyzing, do nothing here
            Logger.error("Failed to parse table", t);
        }
        Logger.error("First row item wasn't found");
        return null;
    }

    public List<TableRowItem> getItems() {
        List<TableRowItem> res = new ArrayList<>();
        try {
            waitFor(() -> driver.findElement(tableLocator) != null);
            table = driver.findElement(tableLocator);
            Map<String, Integer> headersMap = getHeadersMap();
            waitFor(() -> table.findElements(By.xpath(TABLE_ROWS)).size() == 2, 5, false);
            List<WebElement> rows = table.findElements(By.xpath(TABLE_ROWS));
            for (int i = 1; i < rows.size(); i++) {
                res.add(createTableRowItem(rows.get(i), headersMap));
            }
            return res;
        } catch (Throwable t) {
            //Error during table analyzing, do nothing here
            Logger.debug("Failed to parse table", t);
        }
        Logger.info("No items found in table");
        return res;
    }

    //Returns table from TableRowItem
    private TableRowItem createTableRowItem(WebElement row, Map<String, Integer> headersMap) {
        TableRowItem tableRowItem = new TableRowItem();
        for (int i = 0; i < headersMap.size(); i++) {
            String value = row.findElement(By.xpath(String.format(TABLE_CELL_LOCATOR, i + 1))).getText();
            tableRowItem.addData(getHeaderByIndex(i + 1, headersMap), value);
        }
        return tableRowItem;
    }

    //Returns header name by index
    private String getHeaderByIndex(int index, Map<String, Integer> headersMap) {
        for (Map.Entry<String, Integer> entry : headersMap.entrySet()) {
            if (index == entry.getValue()) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("No such header by index " + index);
    }

    //Returns header name and it's td position index starting from 1
    private Map<String, Integer> getHeadersMap() {
        Map<String, Integer> headersMap = new HashMap<>();
        List<WebElement> headers = table.findElements(By.xpath(TABLE_HEADERS));
        int duplicants = 0;
        for (int i = 0; i < headers.size(); i++) {
            String key = headers.get(i).getText().trim();
            if (headersMap.get(key) != null) {
                ++duplicants;
                headersMap.put(key + "[" + duplicants + "]", i + 1);
            } else {
                headersMap.put(key, i + 1);
            }
        }
        return headersMap;
    }

    public ClickableElement editFirstRecordButton() {
        By locator = By.xpath("//*[contains(@class, 'cell-controls')]//a");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        WebElement element = driver.findElement(locator);
        String elementName = "Edit record button";
        return new ClickableElement(element, elementName);
    }

    public ClickableElement deleteFirstRecordButton() {
        By locator = By.xpath("//*[contains(@class, 'cell-controls')]//button");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        WebElement element = driver.findElement(locator);
        String elementName = "Delete record button";
        return new ClickableElement(element, elementName);
    }

}
