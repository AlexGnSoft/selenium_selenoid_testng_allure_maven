package com.carespeak.domain.ui.prod.page.messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.SelectModuleActionTypePopup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MessagesPage extends AbstractPage {

    public SearchWithSelection searchClient;
    public SelectModuleActionTypePopup selectModuleActionTypePopup;
    public ItemsTable messageTable;
    public Dropdown dropdown;

    private static final String MESSAGE_NAME = "//tbody/tr[@role='row']/td/strong/a[contains(text(), '%s')]";

    @ElementName("Add message button")
    @FindBy(id = "messageAddBtn")
    public Button addButton;

    @ElementName("Message name")
    @FindBy(id = "name")
    public Input messageName;

    @ElementName("Module dropdown")
    @FindBy(xpath = "//label[text()='Module']/following-sibling::select")
    public Dropdown modulesDropDown;

    @ElementName("Action dropdown")
    @FindBy(xpath = "//label[text()='Action']/following-sibling::select")
    public Dropdown actionsDropDown;

    @ElementName("Message type dropdown")
    @FindBy(xpath = "//label[text()='Type']/following-sibling::select")
    public Dropdown typeDropDown;

    @ElementName("Notification trigger type")
    @FindBy(id = "type")
    public Dropdown notificationTriggerTypeDropDown;

    @ElementName("Next button")
    @FindBy(id = "nextBtn")
    public Button nextButton;

    @ElementName("Message text field")
    @FindBy(id = "text")
    public Input messageTextField;

    @ElementName("Save message button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    @ElementName("Message name")
    @FindBy(xpath = "//tbody/tr[@role='row']/td/strong/a")
    public ClickableElement firstMessageName;

    @ElementName("Text of message")
    @FindBy(xpath = "//td[@class=' text-wrap']")
    public ClickableElement messageText;

    @ElementName("Save message button")
    @FindBy(id = "saveBtn")
    public Button editMessageButton;

    @ElementName("Sidebar Messages button")
    @FindBy(xpath = "//div[@id='cs-sidebar']/div/a")
    public Button sideBarMessagesButton;

    @ElementName("Side bar link button")
    @FindBy(id = "sidebarLinkEmail")
    public Button sidebarLinkEmailButton;

    public MessagesPage() {
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
        messageTable = new ItemsTable(By.id("messagesTableWrapper"));
        searchClient = new SearchWithSelection();

    }
    public boolean isOpened() {
        return driver.getCurrentUrl().contains("messages/list.page");
    }

    public boolean isCreatedMessageDisplayed(String messageName){
        By locator = By.xpath(String.format(MESSAGE_NAME, messageName));
        ClickableElement message = new ClickableElement(driver.findElement(locator), messageName + " button");

        return message.isDisplayed();
    }

    public String getMessageText(){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(messageText));

        return messageText.getText();
    }

    public boolean areMessageTextUpdated(String initialMessage, String ExpectedUpdatedMessage){
        return !initialMessage.equals(ExpectedUpdatedMessage);
    }
}
