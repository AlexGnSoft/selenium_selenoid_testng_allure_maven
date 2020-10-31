package com.carespeak.domain.steps.imp;

import com.carespeak.core.config.Config;
import com.carespeak.core.config.ConfigProvider;
import com.carespeak.domain.steps.LoginSteps;
import com.carespeak.domain.ui.page.LoginPage;
import com.carespeak.domain.ui.page.dashboard.DashboardPage;

public class ProdLoginSteps implements LoginSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private Config config;

    public ProdLoginSteps() {
        config = ConfigProvider.provide();
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
    }

    @Override
    public LoginSteps openSite() {
        loginPage.openSite();
        return this;
    }

    @Override
    public LoginSteps loginAs(String user, String password) {
        loginPage.loginInput.enterText(user);
        loginPage.passwordInput.enterText(password);
        loginPage.loginButton.click();
        return this;
    }

    @Override
    public boolean isUserLoggedIn() {
        String userName = config.get("data.username");
        return dashboardPage.headerMenu.isUserLoggedIn(userName);
    }
}
