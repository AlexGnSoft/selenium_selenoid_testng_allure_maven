package com.carespeak.domain.ui.page.campaigns.general;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.core.driver.element.Input;
import com.carespeak.domain.ui.page.campaigns.AbstractCampaignsPage;
import org.openqa.selenium.support.FindBy;

public class CampaignsGeneralPage extends AbstractCampaignsPage {

    @ElementName("Campaigns name input")
    @FindBy(id = "campaignName")
    public Input nameInput;

    @ElementName("Campaign access dropdown")
    @FindBy(id = "campaignAccess")
    public Dropdown campaignAccessDropdown;

    @ElementName("Campaign Status dropdown")
    @FindBy(id = "campaignStatus")
    public Dropdown campaignStatusDropdown;

    @ElementName("Campaign start date input")
    @FindBy(id = "campaignDynamicDateRangePicker-durationDatePickers-startDate-dynamicDatePicker-fixedDate")
    public Input startDateInput;

    @ElementName("Campaign end date input")
    @FindBy(id = "campaignDynamicDateRangePicker-durationDatePickers-endDate-dynamicDatePicker-fixedDate")
    public Input endDateInput;

    @ElementName("Campaign Advanced setup button")
    @FindBy(id = "campaignDynamicDateRangePicker-durationDatePickers-showAdvancedForm")
    public Button advancedSetupButton;

    @ElementName("Campaign tags input")
    @FindBy(id = "campaignTags")
    public Input tagsInput;

    @ElementName("Campaign description input")
    @FindBy(id = "campaignDescription")
    public Input campaignDescriptionInput;

    @ElementName("Campaign Advanced params input")
    @FindBy(id = "advanced-params-btn")
    public Button advancedParamsButton;

    @ElementName("Cancel button")
    @FindBy(id = "generalCancelBtn")
    public Button cancelButton;

    @ElementName("Next button")
    @FindBy(id = "generalNextBtn")
    public Button nextButton;

    public boolean isOpened() {
        String url = getCurrentUrl();
        return url.contains("campaigns") && url.contains("general.page");
    }

}
