package com.carespeak.domain.ui.prod.page.campaigns;

import com.carespeak.domain.ui.prod.component.header.HeaderMenu;
import com.carespeak.domain.ui.prod.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.prod.page.AbstractPage;

public abstract class AbstractCampaignsPage extends AbstractPage {

    public HeaderMenu headerMenu;

    public SideBarMenu sideBarMenu;

    public AbstractCampaignsPage() {
        headerMenu = new HeaderMenu();
        sideBarMenu = new SideBarMenu();
    }
}
