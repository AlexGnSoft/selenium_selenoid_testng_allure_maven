package com.carespeak.domain.ui.page.programs.consent_management;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.domain.ui.page.AbstractPage;
import org.openqa.selenium.support.FindBy;

/**
 * Opt Out Form page is opened in new tab and it is quite simple,
 * so it extends AbstractPage.
 */
public class ProgramOptOutFormPage extends AbstractPage {

    @ElementName("Header text element")
    @FindBy(xpath = "(//*[contains(@class, 'cs-page-section-inner')])[1]")
    public ClickableElement header;

    @ElementName("Body text element")
    @FindBy(xpath = "(//*[contains(@class, 'cs-page-section-inner')])[2]")
    public ClickableElement body;

    @ElementName("Footer text element")
    @FindBy(xpath = "(//*[contains(@class, 'cs-page-section-inner')])[3]")
    public ClickableElement footer;

}
