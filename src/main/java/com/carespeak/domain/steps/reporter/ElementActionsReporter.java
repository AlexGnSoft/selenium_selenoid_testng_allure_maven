package com.carespeak.domain.steps.reporter;

import com.carespeak.core.driver.reporter.IElementInteractionsReporter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.util.UUID;
import java.util.concurrent.Callable;

import static io.qameta.allure.Allure.getLifecycle;

public class ElementActionsReporter implements IElementInteractionsReporter {

    @Override
    public <T> T reportAction(String message, Callable<T> actionFunction) {
        final String uuid = UUID.randomUUID().toString();
        final StepResult result = new StepResult()
                .withName(message);
        getLifecycle().startStep(uuid, result);
        try {
            T res = actionFunction.call();
            getLifecycle().updateStep(uuid, s -> s.withStatus(Status.PASSED));
            return res;
        } catch (Throwable t) {
            if (t instanceof Error || t.getCause() instanceof Error) {
                getLifecycle().updateStep(uuid, s -> s.withStatus(Status.FAILED));
            } else {
                getLifecycle().updateStep(uuid, s -> s.withStatus(Status.BROKEN));
            }
            throw new RuntimeException(t.getCause());
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }
}
