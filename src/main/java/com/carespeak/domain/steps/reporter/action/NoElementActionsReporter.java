package com.carespeak.domain.steps.reporter.action;

import com.carespeak.core.driver.reporter.IElementInteractionsReporter;

import java.util.concurrent.Callable;

public class NoElementActionsReporter implements IElementInteractionsReporter {

    @Override
    public <T> T reportAction(String message, Callable<T> actionFunction) {
        try {
            return actionFunction.call();
        } catch (Throwable t) {
            throw new RuntimeException(t.getCause());
        }
    }
}
