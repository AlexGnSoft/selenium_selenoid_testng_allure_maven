package com.carespeak.domain.ui.page.campaigns.time_table;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.domain.ui.page.campaigns.AbstractCampaignsPage;
import org.openqa.selenium.support.FindBy;

public class CampaignsTimeTablePage extends AbstractCampaignsPage {

    @ElementName("Schedule Type dropdown")
    @FindBy(id = "timeTable-timeTablePicker-picker")
    public Dropdown scheduleTypeDropdown;

    @ElementName("Send first message immediately radiobutton")
    @FindBy(xpath = "//input[contains(@name, 'sendImmediately') and @value='true']")
    public ClickableElement sendImmediatelyRadio;

    @ElementName("Do not send first message immediately radiobutton")
    @FindBy(xpath = "//input[contains(@name, 'sendImmediately') and @value='false']")
    public ClickableElement doNotSendImmediatelyRadio;

    @ElementName("Select daily button")
    @FindBy(xpath = "//td[contains(@class, 'selectDaily')]")
    public Button selectDailyButton;

    @ElementName("Select daily button")
    @FindBy(xpath = "//td[contains(@class, 'selectWeekly')]")
    public Button selectWeeklyButton;

    @ElementName("Select daily button")
    @FindBy(xpath = "//td[contains(@class, 'selectMonthly')]")
    public Button selectMonthlyButton;

    @ElementName("Select daily button")
    @FindBy(xpath = "//td[contains(@class, 'selectOnOff')]")
    public Button selectOnOffButton;

    @ElementName("Back button")
    @FindBy(id = "timeTableBackBtn")
    public Button backButton;

    @ElementName("Cancel button")
    @FindBy(id = "timeTableCancelBtn")
    public Button cancelButton;

    @ElementName("Next button")
    @FindBy(id = "timeTableNextBtn")
    public Button nextButton;

    //TODO: add Alert time table with remove alerts
}
