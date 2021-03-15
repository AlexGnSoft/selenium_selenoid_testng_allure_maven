package com.carespeak.core.listener;

import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.IDataGenerator;
import com.carespeak.core.logger.Logger;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import static io.qameta.allure.Allure.getLifecycle;

public class AllureReportListener implements ITestListener, IDataGenerator {

    /**
     * Invoked each time before a test will be invoked. The <code>ITestResult</code> is only partially
     * filled with the references to class, method, start millis and status.
     */
    public void onTestStart(ITestResult result) {
        Logger.info("Test '" + description(result) + "' started...");
    }

    /**
     * Invoked each time a test succeeds.
     */
    public void onTestSuccess(ITestResult result) {
        Logger.info("Test '" + description(result) + "' successfully finished!");
    }

    /**
     * Invoked each time a test fails.
     */
    public void onTestFailure(ITestResult result) {
        String failureMessage = "Test '" + description(result) + "' failed! See screenshot attached.";
        Logger.error(failureMessage);
        if (Boolean.parseBoolean(ConfigProvider.provide().get("driver.recordVideo"))) {
            final String uuid = UUID.randomUUID().toString();
            final StepResult res = new StepResult()
                    .setName("Video for current selenium session:" + result.getMethod());
            getLifecycle().startStep(uuid, res);
            getLifecycle().updateStep(uuid, s -> s.withStatus(Status.FAILED));
            attachVideo(DriverFactory.getDriver().getSessionId().toString());
            getLifecycle().stopStep(uuid);
        }
    }

    /**
     * Returns description or forms description from method name
     *
     * @param result - ITestResult object
     * @return formed description
     */
    private static String description(ITestResult result) {
        String description = result.getMethod().getDescription();
        if (description != null && !description.isEmpty()) {
            return description;
        } else {
            return splitByCamelCase(result.getMethod().getMethodName());
        }
    }

    private static String splitByCamelCase(String str) {
        String splitCamelCaseRegex = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        String[] words = str.split(splitCamelCaseRegex);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.toLowerCase());
            sb.append(" ");
        }
        String res = sb.toString();
        return res.substring(0, 1).toUpperCase() + res.substring(1);
    }


    private static void attachVideo(String sessionId) {
        try {
            URL videoUrl = new URL(ConfigProvider.provide().get("driver.hub") + "/video/" + sessionId + ".mp4");
            InputStream is = getSelenoidVideo(videoUrl);
            Allure.addAttachment("Video", "video/mp4", is, "mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static InputStream getSelenoidVideo(URL url) {
        int lastSize = 0;
        int exit = 2;
        for (int i = 0; i < 20; i++) {
            try {
                int size = Integer.parseInt(url.openConnection().getHeaderField("Content-Length"));
                if (size > lastSize) {
                    lastSize = size;
                    Thread.sleep(1500);
                } else if (size == lastSize) {
                    exit--;
                    Thread.sleep(1000);
                }
                if (exit < 0) {
                    return url.openStream();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
