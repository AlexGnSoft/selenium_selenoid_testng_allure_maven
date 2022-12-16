package com.carespeak.domain.ui.prod.component.table.base;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.helper.ICanWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ItemsTable implements ICanWait {

    protected TableSearch tableSearch;
    protected TableBody tableBody;
    protected TableFooter tableFooter;

    public ItemsTable(By tableBodyLocator) {
        tableSearch = new TableSearch();
        tableBody = new TableBody(tableBodyLocator);
        tableFooter = new TableFooter();
    }

    /**
     * Find table row and by value from columnName header
     *
     * @param columnName - table header
     * @param tableValue - value to search in column
     * @return TableItem
     */
    public TableRowItem searchInTable(String columnName, String tableValue) {
        //TODO: split this part to search filter and search
        tableSearch.search(tableValue);
        return tableBody.findItemBy(columnName, tableValue);
    }

    public void searchFor(String tableValue) {
        List<TableRowItem> itemsBefore = tableBody.getItems();
        tableSearch.search(tableValue);
        //TODO: fix minor logging issue here with no rows found
        waitFor(() -> tableBody.getItems().size() != itemsBefore.size(), 3);
    }

    public TableRowItem getFirstRowItem() {
        return tableBody.getFirstRowItem();
    }

    public String getFirstRowPatientNameString(){
        return tableBody.getFirstRowPatientNameString();
    }

    public ClickableElement editFirstItemButton() {
        return tableBody.editFirstRecordButton();
    }

    public ClickableElement removeFirstItemButton() {
        return tableBody.deleteFirstRecordButton();
    }

    protected WebElement getRowWebElementBy(String header, String value) {
        return tableBody.findRowWebElementBy(header, value);
    }

    protected List<WebElement> getRowsWebElements() {
        return tableBody.getRowsWebElements();
    }

    public List<TableRowItem> getItems() {
        return tableBody.getItems();
    }

}
