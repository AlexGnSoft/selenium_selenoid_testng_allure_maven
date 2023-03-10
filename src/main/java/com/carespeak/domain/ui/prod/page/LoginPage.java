package com.carespeak.domain.ui.prod.page;

import com.carespeak.core.driver.annotation.ElementName;
import com.carespeak.core.driver.element.Button;
import com.carespeak.core.driver.element.Input;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @ElementName("Login input")
    @FindBy(id = "username")
    public Input loginInput;

    @ElementName("Password input")
    @FindBy(id = "password")
    public Input passwordInput;

    @ElementName("Log In button")
    @FindBy(tagName = "button")
    public Button loginButton;

}
