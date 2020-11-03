package com.carespeak.domain.ui.page.admin_tools.client;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.ClickableElement;
import com.carespeak.core.driver.element.Dropdown;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientLanguageSettingsPage extends AbstractClientPage {

    @ElementName("Default language dropdown")
    @FindBy(id = "defaultLanguage")
    public Dropdown defaultLanguage;

    @ElementName("Add language button")
    @FindBy(id = "addLanguage")
    public Button addLanguageButton;

    @ElementName("Save button")
    @FindBy(id = "saveBtn")
    public Button saveButton;

    @ElementName("Additional language rows element")
    @FindBy(id = "languageRows")
    private ClickableElement languageRows;

    public List<String> getAdditionalLanguages() {
        String languageRowsXpath = ".//div[contains(@class, 'language-row')]//select";
        List<String> additionalLanguages = new ArrayList<>();
        waitFor(() -> languageRows.findElements(By.xpath(languageRowsXpath)) != null);
        List<WebElement> languageRowsElements = languageRows.findElements(By.xpath(languageRowsXpath));
        if (languageRowsElements.size() != 0) {
            for (WebElement languageRow : languageRowsElements) {
                String langShortName = languageRow.getAttribute("value");
                String lang = languageRow.findElement(By.xpath(".//option[@value='" + langShortName + "']")).getText();
                additionalLanguages.add(lang);
            }
        }
        return additionalLanguages;
    }

    public Dropdown getLastLanguageDropdown() {
        List<WebElement> languageRowsElements = languageRows.findElements(By.xpath(".//div[contains(@class, 'language-row')]//select"));
        WebElement element = languageRowsElements.get(languageRowsElements.size() - 1);
        return new Dropdown(element, "Last language dropdown");
    }

    public Button getLanguageRemoveButton(String value) {
        List<WebElement> languageRowsElements = languageRows.findElements(By.xpath(".//div[contains(@class, 'language-row')]"));
        if (languageRowsElements.size() != 0) {
            for (WebElement languageRow : languageRowsElements) {
                String langShortName = languageRow.findElement(By.xpath(".//select")).getAttribute("value");
                String lang = languageRow.findElement(By.xpath(".//option[@value='" + langShortName + "']")).getText();
                if (lang.equalsIgnoreCase(value)) {
                    WebElement removeButton = languageRow.findElement(By.xpath(".//button"));
                    return new Button(removeButton, value + " language remove button");
                }
            }
        }
        throw new NoSuchElementException("Language dropdown with selected value '" + value + "' was not found!");
    }


}
