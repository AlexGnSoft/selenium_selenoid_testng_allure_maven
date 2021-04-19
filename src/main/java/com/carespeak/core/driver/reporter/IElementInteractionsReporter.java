package com.carespeak.core.driver.reporter;

import java.util.concurrent.Callable;

/**
 * Interface provides reporting functionality for UI actions,
 * and should be used for direct UI interactions reporting (e.g. click, double click, selecting option, etc)
 */
public interface IElementInteractionsReporter {

    /**
     * Calls actionFunction and reports this action with specified message.
     * actionFunction should be UI interaction.
     *
     * @param message        text to report
     * @param actionFunction function to be called
     * @param <T>            any type or return value
     * @return the actionFunction result
     */
    <T> T reportAction(String message, Callable<T> actionFunction);

}
