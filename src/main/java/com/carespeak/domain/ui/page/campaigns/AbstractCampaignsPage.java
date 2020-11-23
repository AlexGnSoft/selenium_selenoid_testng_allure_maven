package com.carespeak.domain.ui.page.campaigns;

import com.carespeak.domain.ui.component.header.HeaderMenu;
import com.carespeak.domain.ui.component.search.SearchWithSelection;
import com.carespeak.domain.ui.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.page.AbstractPage;

public abstract class AbstractCampaignsPage extends AbstractPage {

    public HeaderMenu headerMenu;

    public SideBarMenu sideBarMenu;

    public AbstractCampaignsPage() {
        headerMenu = new HeaderMenu();
        sideBarMenu = new SideBarMenu();
    }
}
