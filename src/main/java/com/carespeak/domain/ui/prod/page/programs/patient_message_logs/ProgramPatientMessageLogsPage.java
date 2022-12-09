package com.carespeak.domain.ui.prod.page.programs.patient_message_logs;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.prod.component.sidebar.AttachmentSideBar;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.AbstractPage;
import com.carespeak.domain.ui.prod.popup.SimulateResponsePopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProgramPatientMessageLogsPage extends AbstractPage {

    public SimulateResponsePopup simulateResponsePopup;
    public ItemsTable patientMessageTable;
    public AttachmentSideBar attachmentSideBar;

    @ElementName("Program name button")
    @FindBy(xpath = "//a[contains(@class, 'program')]/strong")
    public Button programNameButton;

    @ElementName("Patient name text")
    @FindBy(id = "csHeaderName")
    public ClickableElement patientNameText;

    @ElementName("Simulate Response button")
    @FindBy(xpath = "//div[@id='csMessagesLogsTable_wrapper']//button[@class='btn btn-default cs-button']")
    public Button simulateResponseBtn;

    @ElementName("Message attachment button")
    @FindBy(xpath = "//table[@id='csMessagesLogsTable']//i[contains(@class, 'message-attachment')]")
    public Button attachmentButton;

    @ElementName("List of messages")
    @FindBy(xpath = "//td[@class=' text-wrap text-clip']")
    public List<WebElement> listOfMessages;

    public ProgramPatientMessageLogsPage() {
        patientMessageTable = new ItemsTable(By.id("csMessagesLogsTable_wrapper"));
        simulateResponsePopup = new SimulateResponsePopup();
        attachmentSideBar = new AttachmentSideBar();
    }

    public boolean isOpened() {
        String url = getCurrentUrl();
        return url.contains("programs") && url.contains("message-logs.page");
    }

}
