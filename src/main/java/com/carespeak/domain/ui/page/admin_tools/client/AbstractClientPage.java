package com.carespeak.domain.ui.page.admin_tools.client;

import com.carespeak.domain.ui.component.header.HeaderMenu;
import com.carespeak.domain.ui.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.page.AbstractPage;

/**
 * How to get to any 'Abstract Client Page'?
 * Admin Tools -> Clients -> Search Client and click on it.
 * Any page that available by click on sidebar menu are instance of AbstractClientPage,
 * so should extend this class.
 */
public abstract class AbstractClientPage extends AbstractPage {

    public HeaderMenu headerMenu;

    public SideBarMenu sideBarMenu;

    public AbstractClientPage() {
        headerMenu = new HeaderMenu();
        sideBarMenu = new SideBarMenu();
    }

}
