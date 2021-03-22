package com.carespeak.core.listener;

import com.carespeak.core.helper.IDataGenerator;
import com.carespeak.core.logger.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

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
}
