package com.carespeak.domain.ui.prod.page.programs.message_logs;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ProgramMessageLogsPage extends AbstractProgramPage {

    @ElementName("Message Logs Patient Status")
    @FindBy(xpath = "//span[@id='csHeaderStatus']//strong")
    public Button messageLogsPatientStatus;

    public ItemsTable messageLogsTable;

    public ProgramMessageLogsPage() {
        messageLogsTable = new ItemsTable(By.id("messageLogsTable"));
    }

}
