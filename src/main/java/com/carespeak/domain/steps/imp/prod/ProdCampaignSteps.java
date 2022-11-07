package com.carespeak.domain.steps.imp.prod;

import com.carespeak.core.driver.decorator.CustomDecorator;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.domain.entities.campaign.CampaignAccess;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.Module;
import com.carespeak.domain.steps.CampaignSteps;
import com.carespeak.domain.ui.prod.page.programs.general.campaign.AlertTimeContainer;
import com.carespeak.domain.ui.prod.page.campaigns.CampaignsPage;
import com.carespeak.domain.ui.prod.page.campaigns.general.CampaignsGeneralPage;
import com.carespeak.domain.ui.prod.page.campaigns.time_table.CampaignsTimeTablePage;
import com.carespeak.domain.ui.prod.page.dashboard.DashboardPage;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.testng.collections.CollectionUtils;

import java.util.List;

public class ProdCampaignSteps implements CampaignSteps {

    private DashboardPage dashboardPage;
    private CampaignsPage campaignsPage;
    private CampaignsGeneralPage generalPage;
    private CampaignsTimeTablePage timeTablePage;

    public ProdCampaignSteps() {
        dashboardPage = new DashboardPage();
        campaignsPage = new CampaignsPage();
        generalPage = new CampaignsGeneralPage();
        timeTablePage = new CampaignsTimeTablePage();
    }

    @Override
    public CampaignSteps goToCampaignsTab() {
        String url = dashboardPage.getCurrentUrl();
        dashboardPage.headerMenu.campaignsMenuItem.click();
        waitFor(() -> !dashboardPage.getCurrentUrl().equals(url), false);

        return this;
    }

    @Override
    public List<Module> getAvailableModules(String clientName) {
        campaignsPage.searchClient.search(clientName);
        campaignsPage.addCampaignButton.click();
        List<String> options = campaignsPage.selectModuleActionTypePopup.moduleDropdownOnCampaignsTab.getAvailableOptions();
        campaignsPage.selectModuleActionTypePopup.nextButton.click();
        return Module.getModules(options);
    }

    @Override
    public CampaignSteps addCampaign(Client client, String name, Module module, CampaignAccess access, String description, String... tags) {
        if (!campaignsPage.isOpened()) {
            String url = dashboardPage.getCurrentUrl();
            dashboardPage.headerMenu.campaignsMenuItem.click();
            waitFor(() -> !url.equals(campaignsPage.getCurrentUrl()));
        }
        campaignsPage.addCampaignButton.click();
        campaignsPage.selectModulePopup.waitForDisplayed();
        campaignsPage.selectModulePopup.moduleDropdown.select(module.getValue());
        campaignsPage.selectModulePopup.nextButton.click();
        campaignsPage.selectModulePopup.waitForDisappear();
        generalPage.nameInput.enterText(name);
        generalPage.campaignAccessDropdown.select(access.getValue());
        generalPage.campaignDescriptionInput.enterText(description);
        if (tags != null && tags.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String tag : tags) {
                builder.append(tag);
                builder.append(" ");
            }
            generalPage.tagsInput.enterText(builder.toString());
        }
        generalPage.nextButton.click();
        timeTablePage.sendImmediatelyRadio.click();
        timeTablePage.selectDailyButton.click();
        List<AlertTimeContainer> alertContainers = timeTablePage.alertTimeComponent.findAlertTimeContainers();
        if (!CollectionUtils.hasElements(alertContainers)) {
            throw new AssertionError("Alert time containers were not found!");
        }
        AlertTimeContainer firstAlert = alertContainers.get(0);
        firstAlert.amPmDropdown().select("AM");
        timeTablePage.nextButton.click();
        //TODO: implement add Messages screen handling
        throw new NotImplementedException("Add campaign is not implemented yet!");
    }
}
