package com.carespeak.domain.ui.prod.page.campaigns.time_table;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.prod.page.programs.campaign.AlertTimeComponent;
import com.carespeak.domain.ui.prod.page.campaigns.AbstractCampaignsPage;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CampaignsTimeTablePage extends AbstractCampaignsPage {

    public AlertTimeComponent alertTimeComponent;

    public CampaignsTimeTablePage() {
        alertTimeComponent = new AlertTimeComponent();
    }

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

    @ElementName("Save button")
    @FindBy(id = "timeTableSaveBtn")
    public Button saveButton;

    @ElementName("Anchor dropDown")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-anchorType")
    public Dropdown anchorDropDown;

    @ElementName("Anchor fixed date field")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-fixedDate")
    public Input anchorFixedDateField;

    @ElementName("Program dropDown")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-eventDateName")
    public Dropdown programDropDown;

    @ElementName("Adjust date checkbox")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-adjustmentsCheck")
    public ClickableElement adjustDate;

    @ElementName("Adjust date Dropdown")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-adjustments")
    public Dropdown adjustDateDropDown;

    @ElementName("Days Dropdown")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-entryList-protocolEntryList-entry-0-protocolEntry-beforeSelect")
    public Dropdown daysDropDown;

    @ElementName("Days number input")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-entryList-protocolEntryList-entry-0-protocolEntry-daysInput")
    public Input daysNumberInput;

    @ElementName("Alert time Occasion dropDown")
    @FindBy(xpath = "//select[contains(@id,'localTimeTableEntry-timePicker-timePicker-hour')]")
    public Dropdown alertTimeHoursOccasionDropDown;

    @ElementName("Alert time Protocol dropDown")
    @FindBy(xpath = "//select[contains(@id,'protocolEntryList-entry-0-protocolEntry-timePicker-timePicker-hour')]")
    public Dropdown alertTimeHoursProtocolDropDown;

    @ElementName("Alert time Occasion dropDown")
    @FindBy(xpath = "//select[contains(@id,'localTimeTableEntry-timePicker-timePicker-minute')]")
    public Dropdown alertTimeMinutesOccasionDropDown;

    @ElementName("Alert time Protocol dropDown")
    @FindBy(xpath = "//select[contains(@id,'protocolEntryList-entry-0-protocolEntry-timePicker-timePicker-minute')]")
    public Dropdown alertTimeMinutesProtocolDropDown;

    @ElementName("Alert time Protocol dropDown")
    @FindBy(xpath = "//select[contains(@id,'protocolEntry-timePicker-timePicker-ampm')]")
    public Dropdown alertTimeAmPmProtocolDropDown;

    @ElementName("Alert time Occasion dropDown")
    @FindBy(xpath = "//select[contains(@id,'occasionTimeTable-entry-0-localTimeTableEntry-timePicker-timePicker-ampm')]")
    public Dropdown alertTimeAmPmOccasionDropDown;

    @ElementName("Intake units input")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-entryList-protocolEntryList-entry-0-protocolEntry-units")
    public Input intakeUnitsInput;

    @ElementName("Days input")
    @FindBy(id = "timeTable-timeTablePicker-timeTable-protocolTimeTable-entryList-protocolEntryList-entry-0-protocolEntry-daysInput")
    public Input daysInput;

    public String hoursDropDownSelectionAtAnyCity(String zoneId){
        LocalDateTime machineTime = LocalDateTime.now();
        //need to move to next hour, as we can not select a time 55+ minutes in dropdown, and our test execution takes more than 3 minutes
        if(machineTime.getMinute() >= 53) {
            machineTime = machineTime.plusHours(1);
        }

        ZoneId machineTimeZone = ZoneId.systemDefault();
        ZoneId cityTimeZone = ZoneId.of(zoneId);

        //Displaying current time in 12 hours format with AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        LocalDateTime cityTime = machineTime.atZone(machineTimeZone)
                .withZoneSameInstant(cityTimeZone)
                .toLocalDateTime();

        String cityTimeHoursMinutes = cityTime.format(formatter);

        String cityHours = cityTimeHoursMinutes.substring(0, 2);

        return cityHours;
    }

    public String minutesDropDownNewYorkTime(){
        int machineCurrentMinute = LocalTime.now().getMinute();

        if(machineCurrentMinute < 5){
            machineCurrentMinute = 05;
        } else if(machineCurrentMinute <= 7){
            machineCurrentMinute = 10;
        } else if(machineCurrentMinute <= 12) {
            machineCurrentMinute = 15;
        } else if(machineCurrentMinute <= 17) {
            machineCurrentMinute = 20;
        } else if(machineCurrentMinute <= 22) {
            machineCurrentMinute = 25;
        } else if(machineCurrentMinute <= 27){
            machineCurrentMinute = 30;
        } else if(machineCurrentMinute <= 32) {
            machineCurrentMinute = 35;
        } else if(machineCurrentMinute <= 37) {
            machineCurrentMinute = 40;
        } else if(machineCurrentMinute <= 42) {
            machineCurrentMinute = 45;
        } else if(machineCurrentMinute <= 47) {
            machineCurrentMinute = 50;
        } else if(machineCurrentMinute <= 52) {
            machineCurrentMinute = 55;
        } else if(machineCurrentMinute >= 53) {
            machineCurrentMinute = 5;
        }
        return String.valueOf(machineCurrentMinute);
    }

    public String amPmDropDownNewYorkTime(){
        //Displaying current time in 12 hours format with AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        int timeDiffEuroVsNewYork = 6;

        LocalDateTime machineTime = (LocalDateTime.now()).minusHours(timeDiffEuroVsNewYork);
        int currentMinutes = machineTime.getMinute();
        String timeNewYork = machineTime.format(formatter);
        String amPm = "";

        if(currentMinutes > 57 & (timeNewYork.contains("AM"))){
            amPm = "PM";
        } else {
            amPm = timeNewYork.substring(6, 8);
        }

        return amPm;
    }

    public String currentDayMonthYear(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentDate = LocalDate.now();

        return currentDate.format(formatter);
    }
}
