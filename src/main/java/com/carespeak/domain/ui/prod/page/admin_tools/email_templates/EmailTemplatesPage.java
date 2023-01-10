package com.carespeak.domain.ui.prod.page.admin_tools.email_templates;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmailTemplatesPage extends AbstractPage {

    public HeaderMenu headerMenu;
    public SearchWithSelection searchClient;
    public ItemsTable emailTemplateTable;

    @ElementName("Add template button")
    @FindBy(id = "email-template-add-btn")
    public Button addTemplateButton;

    @ElementName("Template name field")
    @FindBy(id = "template-name")
    public Input templateName;

    @ElementName("Content field")
    @FindBy(xpath = "//div[@class='note-editable']")
    public Input contentField;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    @ElementName("First template name")
    @FindBy(xpath = "//td[@class='sorting_1']")
    public ClickableElement firstTemplateName;

    @ElementName("email templates table wrapper")
    @FindBy(id = "email-template-table_wrapper")
    public WebElement emailTemplatesTableWrapper;

    public EmailTemplatesPage() {
        headerMenu = new HeaderMenu();
        searchClient = new SearchWithSelection();
        emailTemplateTable = new ItemsTable(By.id("email-template-table"));
        //emailTemplateTable = new ItemsTable(By.id("email-template-table-wrapper"));
    }

    @Override
    public boolean isOpened(){
        return getCurrentUrl().equals("email-template");
    }
}
