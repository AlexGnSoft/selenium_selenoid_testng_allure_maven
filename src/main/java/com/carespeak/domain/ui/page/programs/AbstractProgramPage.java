package com.carespeak.domain.ui.page.programs;

import com.carespeak.domain.ui.component.header.HeaderMenu;
import com.carespeak.domain.ui.component.search.SearchWithSelection;
import com.carespeak.domain.ui.component.sidebar.SideBarMenu;
import com.carespeak.domain.ui.page.AbstractPage;

/**
 * How to get to any 'Abstract Program Page'?
 * Programs -> Search Client and click on it.
 * Any page that available by click on sidebar menu are instance of AbstractProgramPage,
 * so should extend this class.
 */
public abstract class AbstractProgramPage extends AbstractPage {

    public HeaderMenu headerMenu;

    public SideBarMenu sideBarMenu;

    public SearchWithSelection programSearch;

    public AbstractProgramPage() {
        headerMenu = new HeaderMenu();
        sideBarMenu = new SideBarMenu();
        programSearch = new SearchWithSelection();
    }
}
