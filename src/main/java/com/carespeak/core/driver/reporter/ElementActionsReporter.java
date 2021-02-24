package com.carespeak.core.driver.reporter;

import java.util.concurrent.Callable;

public class ElementActionsReporter {

    private static IElementInteractionsReporter reporter;

    public static void init(IElementInteractionsReporter reporterInstance) {
        reporter = reporterInstance;
    }

    public static <T> T report(String message, Callable<T> action){
        return reporter.reportAction(message, action);
    }
}
