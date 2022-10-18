package com.carespeak.domain.ui.prod.page.programs.general.campaign;

import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.domain.ui.prod.component.AbstractComponent;

public class AlertTimeContainer extends AbstractComponent {

    private Dropdown hourDropdown;
    private Dropdown minuteDropdown;
    private Dropdown amPmDropdown;

    AlertTimeContainer(Dropdown hourDropdown, Dropdown minuteDropdown, Dropdown amPmDropdown) {
        this.hourDropdown = hourDropdown;
        this.minuteDropdown = minuteDropdown;
        this.amPmDropdown = amPmDropdown;
    }

    private AlertTimeContainer() {
        //you should not directly create this object, just using AlertTimeComponent
    }

    public Dropdown hourDropdown() {
        return hourDropdown;
    }

    public Dropdown minuteDropdown() {
        return minuteDropdown;
    }

    public Dropdown amPmDropdown() {
        return amPmDropdown;
    }
}
