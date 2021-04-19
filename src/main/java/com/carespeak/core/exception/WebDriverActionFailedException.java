package com.carespeak.core.exception;

import org.openqa.selenium.WebDriverException;

public class WebDriverActionFailedException extends WebDriverException {

    public WebDriverActionFailedException() {
        super();
    }

    public WebDriverActionFailedException(String message) {
        super(message);
    }

    public WebDriverActionFailedException(Throwable cause) {
        super(cause);
    }

    public WebDriverActionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
