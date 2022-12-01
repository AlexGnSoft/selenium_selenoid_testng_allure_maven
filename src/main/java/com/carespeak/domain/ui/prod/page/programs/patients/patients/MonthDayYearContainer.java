package com.carespeak.domain.ui.prod.page.programs.patients.patients;

import com.carespeak.core.driver.element.Dropdown;
import com.carespeak.domain.ui.prod.component.AbstractComponent;

public class MonthDayYearContainer extends AbstractComponent {

    private Dropdown monthDropdown;
    private Dropdown dayDropdown;
    private Dropdown yearDropdown;

    public Dropdown getMonthDropdown() {
        return monthDropdown;
    }

    public Dropdown getDayDropdown() {
        return dayDropdown;
    }

    public Dropdown getYearDropdown() {
        return yearDropdown;
    }

    public MonthDayYearContainer(Dropdown monthDropdown, Dropdown dayDropdown, Dropdown yearDropdown) {
        this.monthDropdown = monthDropdown;
        this.dayDropdown = dayDropdown;
        this.yearDropdown = yearDropdown;
    }

    private MonthDayYearContainer() {

    }

}
