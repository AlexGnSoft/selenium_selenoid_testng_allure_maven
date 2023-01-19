package com.carespeak.domain.ui.prod.page.messages;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.*;
import com.carespeak.domain.ui.prod.component.search.SearchWithSelection;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.SelectModuleActionTypePopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MessagesPage extends AbstractPage {

    public SearchWithSelection searchClient;
    public SelectModuleActionTypePopup selectModuleActionTypePopup;
    public ItemsTable messageTable;
    public Dropdown dropdown;

    private static final String MESSAGE_NAME = "//td/strong/a[contains(text(), '%s')]";

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

    @ElementName("Close button of saved message")
    @FindBy(xpath = "//button[@class='close']")
    public ClickableElement closeButtonOfSavedMessage;

    @ElementName("Message name")
    @FindBy(xpath = "//tr[@class='odd']/td/strong/a")
    public ClickableElement firstMessageName;

    @ElementName("Message name with MMS")
    @FindBy(xpath = "//div[@class='cs-table-cell middle']")
    public ClickableElement firstMessageNameWithMms;

    @ElementName("Text of message")
    @FindBy(xpath = "//td[contains(@class,'text-wrap')]")
    public ClickableElement messageText;

    @ElementName("Save message button")
    @FindBy(id = "saveBtn")
    public Button editMessageButton;

    @ElementName("Delete message button")
    @FindBy(xpath = "//button[@class='btn btn-danger btn-sm delete-btn']")
    public Button deleteMessageButton;

    @ElementName("Delete message Ok button")
    @FindBy(xpath = "//button[contains(@class,'success ui-button')]")
    public Button deleteMessageOkButton;

    @ElementName("Sidebar Messages button")
    @FindBy(xpath = "//div[@id='cs-sidebar']/div/a")
    public Button sideBarMessagesButton;

    @ElementName("Side bar link button")
    @FindBy(id = "sidebarLinkEmail")
    public Button sidebarLinkEmailButton;

    @ElementName("Select picture button list")
    @FindBy(xpath = "//input[@type='file']")
    public List<WebElement> selectPictureButtonList;

    @ElementName("Upload picture button list")
    @FindBy(xpath = "//input[@value='Upload']")
    public List<WebElement> uploadButtonList;

    @ElementName("New Medication button")
    @FindBy(id = "newMedicationBtn")
    public Button newMedicationBtn;

    @ElementName("MMS icon on message name")
    @FindBy(xpath = "//i[@class='far fa-image table-status-icon cs-color-active mms-indicator']")
    public ClickableElement mmsNameIcon;

    @ElementName("MMS icon on text column name")
    @FindBy(xpath = "//div[@class='icon']/i")
    public Button mmsTextIcon;

    @ElementName("MMS message attachment item")
    @FindBy(xpath = "//div[@class='message-attachment-item']")
    public ClickableElement messageAttachmentItem;


    public MessagesPage() {
        selectModuleActionTypePopup = new SelectModuleActionTypePopup();
        messageTable = new ItemsTable(By.id("messagesTableWrapper"));
        searchClient = new SearchWithSelection();

    }

    public boolean isOpened() {
        return driver.getCurrentUrl().contains("messages/list.page");
    }

    public boolean isCreatedMessageDisplayed(String messageName) {
        By locator = By.xpath(String.format(MESSAGE_NAME, messageName));
        ClickableElement message = new ClickableElement(driver.findElement(locator), messageName + " button");

        return message.isDisplayed();
    }

    public String getMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(messageText));

        return messageText.getText();
    }

    public boolean areMessageTextUpdated(String initialMessage, String ExpectedUpdatedMessage) {
        return !initialMessage.equals(ExpectedUpdatedMessage);
    }

    public void selectMmsPicture(String filePath) {
        for (int i = 0; i < selectPictureButtonList.size(); i++) {
            selectPictureButtonList.get(1).sendKeys(filePath);
        }

        for (int i = 0; i < uploadButtonList.size(); i++) {
            uploadButtonList.get(1).click();
        }
    }
}

