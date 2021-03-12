package com.carespeak.domain.steps.reporter.action;

import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.driver.reporter.IElementInteractionsReporter;
import com.carespeak.core.logger.Logger;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
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
            ByteArrayInputStream is = new ByteArrayInputStream(((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES));
            Allure.addAttachment("Screenshot", is);
            throw new RuntimeException(t.getCause());
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }
}
