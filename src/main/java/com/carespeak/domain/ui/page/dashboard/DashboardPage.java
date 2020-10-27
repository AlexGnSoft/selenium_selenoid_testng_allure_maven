package com.carespeak.domain.ui.page.dashboard;

import com.carespeak.domain.ui.component.header.HeaderMenu;
import com.carespeak.domain.ui.page.AbstractPage;

/**
 * Dashboard is the main page that user see after login
 */
public class DashboardPage extends AbstractPage {

    public HeaderMenu headerMenu;

    public DashboardPage() {
        super();
        headerMenu = new HeaderMenu();
    }
}
