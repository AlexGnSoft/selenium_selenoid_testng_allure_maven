package com.carespeak.domain.steps.reporter.action;

import com.carespeak.core.driver.reporter.IElementInteractionsReporter;
import com.carespeak.core.exception.WebDriverActionFailedException;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.util.UUID;
import java.util.concurrent.Callable;

import static io.qameta.allure.Allure.getLifecycle;

/**
 * Provides Allure reporting functionality for UI actions,
 * and should be used for direct UI interactions reporting (e.g. click, double click, selecting option, etc)
 */
public class AllureElementActionsReporter implements IElementInteractionsReporter {

    @Override
    public <T> T reportAction(String message, Callable<T> actionFunction) {
        final String uuid = UUID.randomUUID().toString();
        final StepResult result = new StepResult()
                .setName(message);
        getLifecycle().startStep(uuid, result);
        try {
            T res = actionFunction.call();
            getLifecycle().updateStep(uuid, s -> s.setStatus(Status.PASSED));
            return res;
        } catch (Throwable t) {
            if (t instanceof Error || t.getCause() instanceof Error) {
                getLifecycle().updateStep(uuid, s -> s.setStatus(Status.FAILED));
            } else {
                getLifecycle().updateStep(uuid, s -> s.setStatus(Status.BROKEN));
            }
            throw new WebDriverActionFailedException(t);
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }
}
