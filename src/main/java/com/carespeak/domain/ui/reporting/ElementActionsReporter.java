package com.carespeak.domain.ui.reporting;

import com.carespeak.core.driver.reporter.IElementInteractionsReporter;
import io.qameta.allure.Allure;

import java.util.concurrent.Callable;

public class ElementActionsReporter implements IElementInteractionsReporter {

    @Override
    public <T> T reportAction(String message, Callable<T> actionFunction) {
        return Allure.step(message, actionFunction::call);
    }
}
