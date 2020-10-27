package com.carespeak.domain.ui.page.programs;

import com.carespeak.domain.ui.component.table.base.ItemsTable;
import org.openqa.selenium.By;

public class ProgramMessageLogsPage extends AbstractProgramPage {

    public ItemsTable messageLogsTable;

    public ProgramMessageLogsPage() {
        messageLogsTable = new ItemsTable(By.id("messageLogsTable"));
    }

}
