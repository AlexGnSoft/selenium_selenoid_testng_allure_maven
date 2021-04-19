package com.carespeak.domain.ui.prod.page.dashboard;

import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.page.AbstractPage;

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
