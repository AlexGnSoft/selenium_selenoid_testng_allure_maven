package com.carespeak.domain.ui.prod.page.programs.message_logs;

import com.carespeak.domain.ui.prod.component.table.base.ItemsTable;
import com.carespeak.domain.ui.prod.page.programs.AbstractProgramPage;
import org.openqa.selenium.By;

public class ProgramMessageLogsPage extends AbstractProgramPage {

    public ItemsTable messageLogsTable;

    public ProgramMessageLogsPage() {
        messageLogsTable = new ItemsTable(By.id("messageLogsTable"));
    }

}
